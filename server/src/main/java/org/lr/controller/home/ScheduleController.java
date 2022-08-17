package org.lr.controller.home;

import javafx.concurrent.ScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.service.ScheduleService;
import org.lr.vo.CourseScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@Slf4j
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/course/search")
    public Result searchCourse(@RequestParam Integer courseId){
        CourseScheduleVo courseScheduleVo = scheduleService.searchCourseSchedule(courseId);
        if(courseScheduleVo!=null){
            return Result.success(courseScheduleVo);
        }
        return Result.fail("该课程暂未排课");
    }
}
