package org.lr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lr.vo.CourseScheduleVo;

import java.time.LocalDate;

@Mapper
public interface ScheduleMapper {
    CourseScheduleVo searchCourseSchedule(Integer courseId);

    LocalDate selectCourseFirstClass(Integer courseId);
}
