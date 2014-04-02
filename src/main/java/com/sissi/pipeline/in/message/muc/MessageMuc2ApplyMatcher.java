package com.sissi.pipeline.in.message.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * @author kim 2014年3月18日
 */
public class MessageMuc2ApplyMatcher extends ClassMatcher {

	private final String type;

	public MessageMuc2ApplyMatcher(String type) {
		super(Message.class);
		this.type = type;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.isApply(protocol.cast(Message.class));
	}

	private boolean isApply(Message message) {
		return message.type(MessageType.NORMAL, MessageType.NONE) && message.dataType(this.type);
	}
}
