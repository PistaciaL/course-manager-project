package org.lr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lr.dto.AddCourseDto;
import org.lr.dto.SearchCourseDto;
import org.lr.dto.WithdrawDto;
import org.lr.entity.Course;
import org.lr.entity.StudentCourse;
import org.lr.entity.Subject;
import org.lr.entity.User;
import org.lr.enumType.IdentityEnum;
import org.lr.handler.MyException;
import org.lr.mapper.CourseMapper;
import org.lr.mapper.StudentCourseMapper;
import org.lr.vo.AllCourseVo;
import org.lr.vo.PageInfoVo;
import org.lr.vo.StudentCourseVo;
import org.lr.vo.TeacherCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean insert(AddCourseDto dto) throws MyException {
        User user = userService.selectById(dto.getUserId());
        if(!user.getIdentity().equals(IdentityEnum.Teacher)){
            throw new MyException("用户身份错误");
        }
        Subject subject = subjectService.selectById(dto.getSubjectId());
        if((user.getCollege()==null&&subject.getCollege()==null) || user.getCollege().getId()==subject.getCollege().getId()){
            return courseMapper.insert(dto)==1;
        }
        throw new MyException("不能注册该课程");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageInfoVo<TeacherCourseVo> selectPageTeacher(SearchCourseDto dto) throws MyException {
        User user = userService.selectById(dto.getTeacherId());
        if(!user.getIdentity().equals(IdentityEnum.Teacher)){
            throw new MyException("用户身份不符");
        }
        List<TeacherCourseVo> res = courseMapper.selectPageTeacher(dto);
        Long total = courseMapper.selectPageCount(dto);
        Long totalPage = total%dto.getPageSize()==0?total/dto.getPageSize():total/dto.getPageSize()+1;
        PageInfoVo<TeacherCourseVo> resPage = new PageInfoVo<>(res, dto.getPage(), totalPage);
        return resPage;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageInfoVo<AllCourseVo> selectPageAll(SearchCourseDto dto){
        List<AllCourseVo> list = courseMapper.selectPageAll(dto);
        Long total = courseMapper.selectPageCount(dto);
        Long totalPage = total%dto.getPageSize()==0?total/dto.getPageSize():total/dto.getPageSize()+1;
        PageInfoVo<AllCourseVo> resPage = new PageInfoVo<>(list, dto.getPage(), totalPage);
        return resPage;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageInfoVo<StudentCourseVo> selectPageStudent(SearchCourseDto dto) throws MyException {
        User user = userService.selectById(dto.getStudentId());
        if(!user.getIdentity().equals(IdentityEnum.Student)){
            throw new MyException("用户身份不符");
        }
        List<StudentCourseVo> list = courseMapper.selectPageStudent(dto);
        Long total = courseMapper.selectPageCount(dto);
        Long totalPage = total%dto.getPageSize()==0?total/dto.getPageSize():total/dto.getPageSize()+1;
        PageInfoVo<StudentCourseVo> resPage = new PageInfoVo<>(list, dto.getPage(), totalPage);
        return resPage;
    }

    @Transactional
    public boolean withdraw(WithdrawDto dto) throws MyException {
        LocalDate firstDay = scheduleService.selectCourseFirstClass(dto.getCourseId());
        if(firstDay!=null && LocalDate.now().isAfter(firstDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)))){
            return false;
        }
        int i = studentCourseMapper.delete(new QueryWrapper<StudentCourse>().eq("course_id",dto.getCourseId()));
        if(i==0){
            throw new MyException("退课失败");
        }
        return true;
    }
}
