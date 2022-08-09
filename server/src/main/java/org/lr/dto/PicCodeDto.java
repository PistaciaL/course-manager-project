package org.lr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PicCodeDto {
    private byte[] pic;
    private String codeId;
}
