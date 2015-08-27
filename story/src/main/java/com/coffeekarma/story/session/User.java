package com.coffeekarma.story.session;

import java.math.BigDecimal;

import com.vaadin.shared.ui.colorpicker.Color;

public class User {

	private BigDecimal mId;

	private String mName;

	private String mIp;

	private String mBrowserInfo;

	// TODO UserId in constructor -> required

	public BigDecimal getId() {
		return mId;
	}

	public void setId(BigDecimal pId) {
		this.mId = pId;
	}

	public String getIp() {
		return mIp;
	}

	public void setIp(String pIp) {
		this.mIp = pIp;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		this.mName = pName;
	}

	public void setBrowserInfo(String pString) {
		mBrowserInfo = pString;
	}

	public String getBrowserInfo() {
		return mBrowserInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append(mName).append(" [IP: ")
				.append(mIp).append("] | [Browser: ")
				.append(mBrowserInfo).append("]").toString();
	}

}
