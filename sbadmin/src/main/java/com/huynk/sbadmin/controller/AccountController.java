package com.huynk.sbadmin.controller;

import com.huynk.sbadmin.entity.Account;
import com.huynk.sbadmin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    /*define name table*/
    public static final String TABLE_NAME = "Quản lý tài khoản";
    public static final String ACTION_ADD = "Thêm mới tài khoản";

    /*Define columns*/
    public static final String ACCOUNT_ID = "Mã tài khoản";
    public static final String ACCOUNT_USERNAME = "Tên tài khoản";
    public static final String ACCOUNT_PASSWORD = "Mật khẩu";
    public static final String ACCOUNT_EMAIL = "Email";
    public static final String ACCOUNT_STATUS = "Trạng thái";
    public static final String ACCOUNT_ROLE = "Vai trò";
    public List<String> COLUMNS = new ArrayList<>(Arrays.asList(
            ACCOUNT_ID, ACCOUNT_USERNAME, ACCOUNT_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_STATUS, ACCOUNT_ROLE
    ));

    @GetMapping("/accounts")
    public String accountManage(Model model) {
        model.addAttribute("table_name", TABLE_NAME);
        model.addAttribute("action_add", ACTION_ADD);
        model.addAttribute("account_columns", COLUMNS);
        return "dashboard";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/listtAllAccount")
    public ResponseEntity<List<Account>> listAllAccount(){
        List<Account> accounts = accountService.listAllAccount();
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/accounts/add")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account){
        return new ResponseEntity<>(accountService.saveOrUpdate(account), HttpStatus.OK);
    }

    @PutMapping("/accounts/edit")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        if (account.getAccountId() != null || "".equals(account.getAccountId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Account accountExist = accountService.findByAccountId(account.getAccountId());
        if (account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(accountService.saveOrUpdate(account), HttpStatus.OK);
    }

    @DeleteMapping("/accounts/delete/{accountId}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long accountId){
        try {
            accountService.deleteById(accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
