package com.evan.wj.dao;

import com.evan.wj.pojo.Account_information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Account_informationDAO extends JpaRepository<Account_information, Integer> {
    Account_information findByAccountid(int accountid);

    Account_information getByAccountidAndKeywords(int accountid, String keywords);

    @Query(value = "select * from account_information where (select type from identity where identity.type_id = account_information.type_id) <> '无权限账号'", nativeQuery = true)
    List<Account_information> findvisible();
}
