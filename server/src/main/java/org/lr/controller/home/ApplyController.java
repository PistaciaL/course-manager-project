package org.lr.controller.home;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.dto.ApplyDto;
import org.lr.dto.CourseDto;
import org.lr.entity.Apply;
import org.lr.entity.College;
import org.lr.entity.Course;
import org.lr.entity.User;
import org.lr.enumType.ApplyEnum;
import org.lr.service.ApplyService;
import org.lr.service.CourseService;
import org.lr.service.SubjectService;
import org.lr.service.UserService;
import org.lr.utils.JWTUtil;
import org.lr.vo.AddApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/add")
    public Result addApply(@RequestBody AddApplyVo vo, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Claim> tokenMap = JWTUtil.verifyToken(token);
        String userFrom = tokenMap.get("workNumb").asString();
        Apply apply = new Apply();
        apply.setType(vo.getType());
        apply.setUserFrom(new User(userFrom));
        apply.setCourse(new Course(vo.getCourseId()));
        apply.setContent(vo.getContent());
        apply.setCreateTime(LocalDateTime.now());
        CourseDto courseBaseInfo = courseService.selectBaseInfoById(vo.getCourseId());
        if(vo.getType().equals(ApplyEnum.MarkChange)){
            //成绩修改申请
            apply.setCollegeTo(new College(subjectService.selectById(courseBaseInfo.getSubjectId()).getCollege().getId()));
            apply.setStudent(new User(vo.getStudentId()));
            apply.setNewMark(vo.getNewMark());
        } else {
            apply.setUserTo(new User(courseService.selectBaseInfoById(vo.getCourseId()).getTeacherId()));
        }
        boolean isSaved = applyService.saveApply(apply);
        if(isSaved){
            return Result.success();
        } else {
            return Result.fail("申请失败");
        }
    }
}
