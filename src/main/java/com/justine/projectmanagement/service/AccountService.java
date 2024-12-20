package com.justine.projectmanagement.service;


import com.justine.projectmanagement.model.Account;
import com.justine.projectmanagement.model.Company;
import com.justine.projectmanagement.repository.AccountRepository;
import com.justine.projectmanagement.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    @Transactional
    public Account save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

}
