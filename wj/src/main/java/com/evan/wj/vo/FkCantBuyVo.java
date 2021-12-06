package com.evan.wj.vo;

import lombok.Data;

@Data
public class FkCantBuyVo {
    private int projectid;
    private String reason;
    private String fkname;

    public FkCantBuyVo(int projectid, String reason, String fkname) {
        this.projectid = projectid;
        this.reason = reason;
        this.fkname = fkname;
    }

    public FkCantBuyVo() {
        this.projectid = 0;
        this.reason = "";
        this.fkname = "0";
    }
}
