package org.lr.vo;

import lombok.Data;
import org.lr.dto.CourseExamDto;

import java.util.List;

@Data
public class TeacherCourseVo {
    private Integer id;
    private String name;
    private Integer hour;
    private Integer studentNumb;
    private Integer totalNumb;
    private String termName;
    private List<CourseExamDto> exams;
    private Boolean markIsPublish;
}
