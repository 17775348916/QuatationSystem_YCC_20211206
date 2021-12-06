package com.evan.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.evan.wj.dao.IdentityDAO;
import com.evan.wj.pojo.Account_information;
import com.evan.wj.pojo.Identity;
import com.evan.wj.result.Result;
import com.evan.wj.service.Account_informationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    Account_informationService account_informationService;
    @Autowired
    IdentityDAO identitydao;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody Account_information requestUser) {
        int accountid = requestUser.getAccountid();
        Account_information byId = account_informationService.getById(accountid);
        if(null == byId){
            return new Result("400",false,"账号不存在");
        }
        if(byId.getTypeid() != requestUser.getTypeid()){
            return new Result("400",false,"工号与身份不匹配");
        }
        if(!byId.getKeywords().equals(requestUser.getKeywords())){
            return new Result("400",false,"账号或者密码错误");
        }
        return new Result();
    }

    @CrossOrigin
    @PostMapping(value = "/api/adminlogin")
    @ResponseBody
    public Result<Account_information> adminlogin(@RequestBody Account_information requestUser) {
        int accountid = requestUser.getAccountid();

        Account_information account = account_informationService.get(accountid, requestUser.getKeywords());
        if (null == account) {
            return new Result("400",false,"账号不存在");
        } else {
//            System.out.println(identitydao.ffind(account.getTypeid()));
            if (account.getTypeid() != 1 && account.getTypeid() != 5){
                return new Result("400", false, "登陆身份不是管理员");
            }
            account.setKeywords("");
            return new Result<Account_information>(account);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/api/managelogin")
    @ResponseBody
    public Result<List<Account_information> > managelogin() {
        List<Account_information> tmp = account_informationService.findall();
//        System.out.println(tmp.size());
        return new Result<List<Account_information> >("200", true, tmp, "查询所有账户");
    }

    @CrossOrigin
    @PostMapping(value = "/api/identity")
    @ResponseBody
    public Result<List<Identity>> identity() {
        List<Identity> tmp = identitydao.findAll();
//        System.out.println("***");
//        System.out.println(tmp.size());
        return new Result<List<Identity>>(tmp);
    }

    @CrossOrigin
    @PostMapping(value = "/api/updateaccount")
    @ResponseBody
    public Result updateaccount(@RequestBody List<Account_information> account)
    {
//        System.out.println(JSON.toJSONStringWithDateFormat(req,JSON.DEFFAULT_DATE_FORMAT));
//        List<Account_information> account = JSON.parseArray(JSON.toJSONStringWithDateFormat(req,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat)
//                , Account_information.class);
        List<Account_information> oldaccount = account_informationService.findall();
        int oldlen = oldaccount.size();
//        List<Account_information> account = JSON.parseArray(req.toJSONString(), Account_information.class);
        for (Account_information account_information : account) {
            int index = account.indexOf(account_information);
            if(index >= oldlen){
                account_information.setCreatedate(LocalDateTime.now());
                account_information.setUpdatedate(LocalDateTime.now());
            } else {
                if(account_information.getTypeid() != oldaccount.get(index).getTypeid()
                    || !account_information.getKeywords().equals(oldaccount.get(index).getKeywords())
                    || !account_information.getName().equals(oldaccount.get(index).getName())) {
                    account_information.setUpdatedate(LocalDateTime.now());
                }
            }
            account_informationService.update(account_information);
        }

        account = account_informationService.findall();

        for (Account_information account_information : account) {
            String s = "";
            switch (account_information.getTypeid()) {
                case 1: case 5:
                    s = "ADMIN"; break;
                case 2:
                    s = "SW"; break;
                case 3:
                    s = "JS"; break;
                case 4:
                    s = "KF"; break;
            }
            account_information.setAccountname(s + account_information.getName());
            account_informationService.update(account_information);
        }
        return new Result();
    }

    @CrossOrigin
    @PostMapping(value = "/api/deleteaccount")
    @ResponseBody
    public Result updateaccount(@RequestBody JSONObject json) {
        int projectid = json.getInteger("id");
        if (account_informationService.deleteinfo(projectid)) {
            return new Result();
        } else {
            return new Result("400", false, "账号有误 无法删除");
        }
    }
//    @CrossOrigin
//    @PostMapping(value = "api/finddate")
//    @ResponseBody
//    public Date finddate()
//    {
//        return identitydao.finddate();
//    }
}
