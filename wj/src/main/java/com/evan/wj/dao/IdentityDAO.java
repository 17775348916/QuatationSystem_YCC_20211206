
package com.evan.wj.dao;

import com.evan.wj.pojo.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

public interface IdentityDAO extends JpaRepository<Identity,Integer> {
//    Identity findByTypeid(int typeid);
    @Query(value = "select type from Identity where typeid = ?1")
    String ffind(int typeid);

    @Query(value = "select current_date", nativeQuery = true)
    Date finddate();

    @Query(value = "select current_time", nativeQuery = true)
    Date findtime();

    Identity findByTypeid(int typeid);

////    Identity getByAccountidAndKeywords(int accountid,String keywords);
}
