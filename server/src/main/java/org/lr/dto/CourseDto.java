package org.lr.dto;

import lombok.Data;

@Data
public class CourseDto {
    private Integer id;
    private Integer subjectId;
    private String teacherId;
    private Integer termId;
    private Integer maxStudent;
    private Integer existStudent;
    private boolean markIsPublish;
}
