package org.lr.dto;

import lombok.Data;

@Data
public class SearchCourseDto {
    private String teacherId;
    private String studentId;
    private Integer termId;
    private Integer subjectId;
    private Integer collegeId;
    private Long page;
    private Long pageSize;
}
