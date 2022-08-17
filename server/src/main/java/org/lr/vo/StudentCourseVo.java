package org.lr.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentCourseVo {
    private Integer courseId;
    private String subjectName;
    private String teacherName;
    private String collegeName;
    private Float credit;
    private Integer studentNumb;
    private Integer totalNumb;
    private List<StudentMarkVo> marks;
}
