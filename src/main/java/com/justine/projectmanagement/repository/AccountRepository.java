package com.justine.projectmanagement.repository;

import com.justine.projectmanagement.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);
}
