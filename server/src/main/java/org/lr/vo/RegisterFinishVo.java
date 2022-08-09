package org.lr.vo;

import lombok.Data;

@Data
public class RegisterFinishVo {
    private String avatarUrl;
    private String phoneNumb;
    private String password;
    private String verifyCode;
}
