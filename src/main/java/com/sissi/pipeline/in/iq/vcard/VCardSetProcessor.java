package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class VCardSetProcessor implements Input {

	private final VCardContext vcardContext;

	public VCardSetProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.vcardContext.push(context.jid(), protocol.cast(VCard.class));
		return true;
	}

}
