package com.evan.wj.vo;

import lombok.Data;

@Data
public class FileVo {
    String fileurl;
    String originname;

    public FileVo(String address, String originname) {
        this.fileurl = address;
        this.originname = originname;
    }

    public FileVo() {
        fileurl = new String();
        originname = new String();
    }
}
