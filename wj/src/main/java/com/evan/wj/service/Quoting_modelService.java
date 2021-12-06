package com.evan.wj.service;

import com.evan.wj.dao.Quoting_modelDAO;
import com.evan.wj.pojo.Quoting_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Quoting_modelService {
    @Autowired
    Quoting_modelDAO quoting_modelDAO;

    public List<Quoting_model> query() {
        return quoting_modelDAO.findAll();
    }

    public void save(List<Quoting_model> modellist) {
        List<Quoting_model> oldmodel = quoting_modelDAO.findAll();
        for(Quoting_model quoting_model : modellist) {
            int index = modellist.indexOf(quoting_model);
            if(!quoting_model.equals(oldmodel.get(index))){
                quoting_model.setUpdatedate(LocalDateTime.now());
                quoting_modelDAO.save(quoting_model);
            }
        }
    }
}
