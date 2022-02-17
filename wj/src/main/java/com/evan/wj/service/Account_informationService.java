package com.evan.wj.service;

import com.evan.wj.dao.Account_informationDAO;
import com.evan.wj.dao.IdentityDAO;
import com.evan.wj.pojo.Account_information;
import com.evan.wj.pojo.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Account_informationService {
    @Autowired
    Account_informationDAO account_informationDAO;
    @Autowired
    IdentityDAO identityDAO;

    public boolean isExist(int accountid) {
        Account_information user = getById(accountid);
        return null!=user;
    }

    public Account_information getById(int accountid) {
        return account_informationDAO.findByAccountid(accountid);
    }

    public Account_information get(int accountid, String keywords){
        return account_informationDAO.getByAccountidAndKeywords(accountid, keywords);
    }

    public void add(Account_information user) {
        account_informationDAO.save(user);
    }
    public void update(Account_information user) {
        account_informationDAO.save(user);
    }

    public List<Account_information> findall() {
        return account_informationDAO.findvisible();
    }

    public String namebyId(String Saccountid) {
        int accountid = Integer.parseInt(Saccountid);
        log.info("添加新项目账号为" + Saccountid);
        Account_information account = account_informationDAO.findByAccountid(accountid);
        Identity identity = identityDAO.findByTypeid(account.getTypeid());
        if (account != null)
            return identity.getType() + "  " + account.getName() + " 工号:" + Saccountid;
        else {
            log.info("查询用户名出错！");
            return "ERROR";
        }
    }

    public boolean deleteinfo(int id) {
        if(account_informationDAO.existsById(id)) {
            account_informationDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
