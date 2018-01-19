package com.valework.yingul.service;

import java.security.Principal;

import com.valework.yingul.model.PrimaryAccount;
import com.valework.yingul.model.SavingsAccount;

public interface AccountService {
	PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);
    
    
}
