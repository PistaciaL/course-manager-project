package org.lr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVerifyDto {
    private String workNumber;
    private String name;
    private String idNumber;
}
