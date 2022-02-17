package com.evan.wj.dao;

import com.evan.wj.pojo.Receive_kh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface Receive_khDAO extends JpaRepository<Receive_kh, Integer> {

    @Query
    List<Receive_kh> findAllByProjectid(int projectid);
    Receive_kh findByProjectid(int projectid);

    @Query
    Receive_kh findByProjectidAndIsfinalreceive(int projectid, String isfinalreceive);

    @Query
    List<Receive_kh> findAllByProjectidAndIsfinalreceive(int projectid, String isfinalreceive);

    boolean existsByProjectidAndIsfinalreceive(int projectid, String isfinalreceive);

    boolean existsByProjectidAndTestresult(int projectid, String Testresult);

    boolean existsByProjectid(int projectid);

    @Query("update Receive_kh  set receivekhname =?2, receivekhphone=?3,receiveintention=?4," +
            "updatename=?5,updatedate=?6 where projectid = ?1 and isfinalreceive='否'")
    @Modifying
    @Transactional
    void updateReceivekhnameAndReceivekhphoneAndReceiveintention(int projectid, String receivekhname, String receivekhphone,
                                                                 String receiveintention, String updatename, LocalDateTime updatedate);
    @Query("update Receive_kh set testresult=?2,updatename=?3,updatedate=?4 where projectid=?1 and isfinalreceive='是'")
    @Modifying
    @Transactional
    void updateTestresult(int projectid, String testresult,String updatename, LocalDateTime updatedate);

    @Query("update Receive_kh set isfinalreceive=?2,receiveprice=?3,neededtime=?4,updatename=?5,updatedate=?6 where projectid=?1 " +
            "and isfinalreceive='是'")
    @Modifying
    @Transactional
    void updatePriceAndTime(int projectid, String isfinalreceive,double receiveprice,double neededtime,String updatename, LocalDateTime updatedate);

}
