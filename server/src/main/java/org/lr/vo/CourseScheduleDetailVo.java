package org.lr.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseScheduleDetailVo{
    private LocalDate classDate;
    private String roomName;
    private Integer startHour;
    private Integer endHour;
}