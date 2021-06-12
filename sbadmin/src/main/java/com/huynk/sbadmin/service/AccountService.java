package com.huynk.sbadmin.service;

import com.huynk.sbadmin.entity.Account;
import com.huynk.sbadmin.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    public Account saveOrUpdate(Account account) {
        return accountRepository.save(account);
    }

    public void deleteById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account findByAccountId(Long accountId) {
        return accountRepository.findAccountByAccountId(accountId);
    }
}
