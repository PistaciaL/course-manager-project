package org.lr.dto;

import lombok.Data;

@Data
public class AddCourseDto {
    private String userId;
    private Integer termId;
    private Integer subjectId;
    private Integer maxStudent;
}
