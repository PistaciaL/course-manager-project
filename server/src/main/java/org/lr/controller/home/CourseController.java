package org.lr.controller.home;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.dto.AddCourseDto;
import org.lr.dto.SearchCourseDto;
import org.lr.dto.WithdrawDto;
import org.lr.enumType.IdentityEnum;
import org.lr.service.CourseService;
import org.lr.utils.JWTUtil;
import org.lr.vo.AddCourseVo;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public Result addCourse(@RequestBody AddCourseVo vo, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Claim> tokenMap = JWTUtil.verifyToken(token);
        String userId = tokenMap.get("workNumb").asString();
        AddCourseDto dto = new AddCourseDto();
        dto.setUserId(userId);
        dto.setSubjectId(vo.getSubjectId());
        dto.setTermId(vo.getTermId());
        dto.setMaxStudent(vo.getMaxStudent());
        boolean b = courseService.insert(dto);
        if(b){
            return Result.success();
        }
        return Result.fail("注册失败");
    }

    @GetMapping("/search")
    public Result searchCourse(@RequestParam(required = false) Integer termId,
                               @RequestParam(required = false, defaultValue = "1") String page,
                               @RequestParam(required = false, defaultValue = "10") String pageSize,
                               HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Claim> tokenMap = JWTUtil.verifyToken(token);
        String userId = tokenMap.get("workNumb").asString();
        String identity = tokenMap.get("identity").asString();
        SearchCourseDto dto = new SearchCourseDto();
        dto.setTermId(termId);
        dto.setPage(Long.valueOf(page));
        dto.setPageSize(Long.valueOf(pageSize));
        PageInfoVo resPage = null;
        if(IdentityEnum.Teacher.getValue().equals(identity)){
            dto.setTeacherId(userId);
            resPage = courseService.selectPageTeacher(dto);
        } else if(IdentityEnum.Student.getValue().equals(identity)){
            dto.setStudentId(userId);
            resPage = courseService.selectPageStudent(dto);
        }
        return Result.success(resPage);
    }

    @GetMapping("/searchAll")
    public Result searchAll(@RequestParam(required = false) Integer subjectId,
                            @RequestParam(required = false) Integer collegeId,
                            @RequestParam(required = false) String teacherId,
                            @RequestParam(required = false) Integer termId,
                            @RequestParam(required = false, defaultValue = "1") String page,
                            @RequestParam(required = false, defaultValue = "10") String pageSize){
        SearchCourseDto dto = new SearchCourseDto();
        dto.setTeacherId(teacherId);
        dto.setSubjectId(subjectId);
        dto.setTermId(termId);
        dto.setCollegeId(collegeId);
        dto.setPage(Long.valueOf(page));
        dto.setPageSize(Long.valueOf(pageSize));
        PageInfoVo res = courseService.selectPageAll(dto);
        return Result.success(res);
    }

    @GetMapping("/withdraw/judge")
    public Result judgeWithdraw(@RequestParam Integer courseId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Claim> map = JWTUtil.verifyToken(token);
        String userId = map.get("workNumb").asString();
        WithdrawDto dto = new WithdrawDto();
        dto.setCourseId(courseId);
        dto.setUserId(userId);
        boolean b = courseService.withdraw(dto);
        if(b){
            return Result.success();
        } else {
            return Result.fail();
        }
    }
}
