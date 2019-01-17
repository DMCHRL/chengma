package com.chengma.devplatform.service.dto.view;


/**
 * A SysRole.
 */
public class PreFundJoinDTO {

    private String id;

    private String name;

    private Double canJoin; //还可入伙金额

    private Double balance; //余额(废弃掉)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Double canJoin) {
        this.canJoin = canJoin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
