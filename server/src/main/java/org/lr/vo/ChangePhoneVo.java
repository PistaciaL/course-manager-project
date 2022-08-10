package org.lr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePhoneVo {
    private String originPhone;
    private String originCode;
    private String newPhone;
    private String newCode;
}
