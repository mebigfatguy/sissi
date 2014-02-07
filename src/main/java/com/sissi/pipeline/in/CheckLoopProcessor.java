package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月29日
 */
public class CheckLoopProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.type(ProtocolType.ERROR) ? this.tryPong(context, protocol) : true;
	}

	private boolean tryPong(JIDContext context, Protocol protocol) {
		context.pong(protocol);
		return false;
	}
}
