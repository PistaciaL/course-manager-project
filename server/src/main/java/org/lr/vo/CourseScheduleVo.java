package org.lr.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CourseScheduleVo {
    private Integer courseId;
    private List<CourseScheduleDetailVo> schedules;
}
