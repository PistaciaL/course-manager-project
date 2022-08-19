package org.lr.dto;

import lombok.Data;
import org.lr.enumType.ApplyEnum;

@Data
public class CheckApplyDto {
    private ApplyEnum type;
    private String applyUserId;
    private Integer CourseId;
}
