package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkTransfer implements Transfer {

	private final static Log log = LogFactory.getLog(NetworkTransfer.class);

	private final GenericFutureListener<Future<Void>> futureListener;

	private ChannelHandlerContext context;

	public NetworkTransfer(ChannelHandlerContext context) {
		super();
		this.futureListener = FailLogedGenericFutureListener.FUTURE;
		this.context = context;
	}

	public NetworkTransfer(GenericFutureListener<Future<Void>> futureListener, ChannelHandlerContext context) {
		super();
		this.futureListener = futureListener;
		this.context = context;
	}

	@Override
	public Transfer transfer(TransferBuffer buffer) {
		this.context.writeAndFlush(ByteBuf.class.cast(buffer.getBuffer())).addListener(new ReleaseGenericFutureListener(buffer)).addListener(this.futureListener);
		return this;
	}

	@Override
	public void close() {
		if (this.context != null) {
			this.context.close().addListener(FailLogedGenericFutureListener.FUTURE);
			this.context = null;
		}
	}

	private class ReleaseGenericFutureListener implements GenericFutureListener<Future<Void>> {

		private final TransferBuffer transferBuffer;

		public ReleaseGenericFutureListener(TransferBuffer transferBuffer) {
			super();
			this.transferBuffer = transferBuffer;
		}

		public void operationComplete(Future<Void> future) throws Exception {
			try {
				
				this.transferBuffer.release();
			} catch (Exception e) {
				NetworkTransfer.log.error("Memory leak: " + e.toString());
			}
		}
	}

	private static class FailLogedGenericFutureListener implements GenericFutureListener<Future<Void>> {

		private final static GenericFutureListener<Future<Void>> FUTURE = new FailLogedGenericFutureListener();

		private final Log log = LogFactory.getLog(this.getClass());

		private FailLogedGenericFutureListener() {
			super();
		}

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				if (this.log.isDebugEnabled()) {
					this.logIfDetail(future.cause());
				}
			}
		}

		private void logIfDetail(Throwable cause) {
			StringWriter trace = new StringWriter();
			cause.printStackTrace(new PrintWriter(trace));
			this.log.debug(trace.toString());
		}
	}
}
