package com.huynk.sbadmin.repository;

import com.huynk.sbadmin.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByAccountId(Long accountId);
}
