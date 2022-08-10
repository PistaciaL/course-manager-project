package org.lr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePhoneDto {
    private String userId;
    private String originPhone;
    private String originCode;
    private String newPhone;
    private String newCode;
}
