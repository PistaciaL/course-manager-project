package org.lr.vo;

import lombok.Data;

@Data
public class ChangePasswdVo {
    private String oldPasswd;
    private String newPasswd;
}
