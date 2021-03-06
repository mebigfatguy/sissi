package com.sissi.protocol;

/**
 * @author kim 2013年11月25日
 */
public interface Element {

	public String getId();

	public Element setId(String id);

	public String getFrom();

	public String getFrom(boolean snapshot);

	public Element setFrom(String from);

	public String getTo();

	public Element setTo(String to);

	public String getType();

	public Element setType(String type);
}
