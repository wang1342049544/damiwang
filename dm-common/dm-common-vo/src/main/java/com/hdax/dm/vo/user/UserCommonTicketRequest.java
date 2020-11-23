package com.hdax.dm.vo.user;


import java.io.Serializable;

public class UserCommonTicketRequest implements Serializable {
    private String name;
    private String idCard;
    private String cardType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }
}
