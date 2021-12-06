package com.evan.wj.dao;

import com.evan.wj.pojo.Receive_kh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Receive_khDAO extends JpaRepository<Receive_kh, Integer> {

    @Query
    List<Receive_kh> findAllByProjectid(int projectid);

    @Query
    Receive_kh findByProjectidAndIsfinalreceive(int projectid, String isfinalreceive);

    @Query
    List<Receive_kh> findAllByProjectidAndIsfinalreceive(int projectid, String isfinalreceive);

    boolean existsByProjectidAndIsfinalreceive(int projectid, String isfinalreceive);

    boolean existsByProjectidAndTestresult(int projectid, String Testresult);

    boolean existsByProjectid(int projectid);


}
