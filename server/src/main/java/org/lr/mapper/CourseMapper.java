package org.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lr.dto.AddCourseDto;
import org.lr.dto.CourseDto;
import org.lr.dto.SearchCourseDto;
import org.lr.entity.Course;
import org.lr.vo.AllCourseVo;
import org.lr.vo.StudentCourseVo;
import org.lr.vo.TeacherCourseVo;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    int insert(AddCourseDto dto);

    List<TeacherCourseVo> selectPageTeacher(SearchCourseDto dto);

    List<AllCourseVo> selectPageAll(SearchCourseDto dto);
    Long selectPageCount(SearchCourseDto dto);

    List<StudentCourseVo> selectPageStudent(SearchCourseDto dto);

    CourseDto selectBaseInfoById(Integer id);
}
