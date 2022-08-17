package org.lr.vo;

import lombok.Data;

@Data
public class AllCourseVo {
    private Integer courseId;
    private Integer subjectId;
    private String subjectName;
    private Integer collegeId;
    private String collegeName;
    private Integer teacherId;
    private String teacherName;
    private Integer termId;
    private String termName;
    private Integer studentNumb;
    private Integer maxNumb;
    private Integer hour;
}
