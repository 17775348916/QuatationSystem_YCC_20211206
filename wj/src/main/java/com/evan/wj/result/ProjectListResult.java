package com.evan.wj.result;

import com.evan.wj.pojo.Project_Overview;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectListResult {
    private String code;
    private boolean success;
    private List<Project_Overview> proj;
    private List<LocalDateTime> time;

    public ProjectListResult(String code, boolean success) {

        this.code = code;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Project_Overview> getProj() {
        return proj;
    }

    public void setProj(List<Project_Overview> proj) {
        this.proj = proj;
    }

    public List<LocalDateTime> getTime() {
        return time;
    }

    public void setTime(List<LocalDateTime> time) {
        this.time = time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
