package com.evan.wj.vo;

import lombok.Data;

@Data
public class ImgVo {
    String imgpath;
    byte[] base64id;

    public ImgVo(String imgpath, byte[] base64id) {
        this.imgpath = imgpath;
        this.base64id = base64id;
    }

    public ImgVo() {
    }
}
