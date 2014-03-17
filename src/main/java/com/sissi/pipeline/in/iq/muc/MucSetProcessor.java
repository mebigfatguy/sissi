package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.MucRoleBuilder;

/**
 * @author kim 2014年3月14日
 */
public class MucSetProcessor extends ProxyProcessor {

	private final MucRoleBuilder mucRoleBuilder;

	public MucSetProcessor(MucRoleBuilder mucRoleBuilder) {
		super();
		this.mucRoleBuilder = mucRoleBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Item item = protocol.cast(XMucAdmin.class).first();
		this.mucRoleBuilder.build(item.getRole()).change(super.build(protocol.parent().getTo()).resource(item.getNick()));
		return true;
	}
}