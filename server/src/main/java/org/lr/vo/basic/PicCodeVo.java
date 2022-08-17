package org.lr.vo.basic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PicCodeVo {
    private byte[] pic;
    private String codeId;
}
