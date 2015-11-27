package com.coffeekarma.story.communication;

public enum InMemoryText {
  INSTANCE;

  private String mText = "";

  public String getText() {
    return mText;
  }

  public void setText(String pText) {
    this.mText = pText;
  }

  public void appendMessage(String pUser, String pText) {
    this.mText = new StringBuilder(mText).append("<br/>").append(pUser).append(" : ").append(pText).toString();
  }

}
