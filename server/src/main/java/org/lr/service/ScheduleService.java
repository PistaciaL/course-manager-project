package org.lr.service;

import org.lr.mapper.ScheduleMapper;
import org.lr.vo.CourseScheduleVo;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public CourseScheduleVo searchCourseSchedule(Integer courseId){
        return scheduleMapper.searchCourseSchedule(courseId);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public LocalDate selectCourseFirstClass(Integer courseId){
        return scheduleMapper.selectCourseFirstClass(courseId);
    }
}
