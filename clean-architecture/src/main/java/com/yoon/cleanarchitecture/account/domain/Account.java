package com.yoon.cleanarchitecture.account.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {

    private AccountId id;
    private Money baselineBalance;
    private ActivityWindow activityWindow;

    

}
