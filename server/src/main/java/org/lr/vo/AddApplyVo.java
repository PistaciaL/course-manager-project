package org.lr.vo;

import lombok.Data;
import org.lr.enumType.ApplyEnum;

@Data
public class AddApplyVo {
    private ApplyEnum type;
    private String content;
    private Integer courseId;
    private String studentId;
    private Integer newMark;
}
