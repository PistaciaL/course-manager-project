package org.lr.controller.basic;

import com.auth0.jwt.interfaces.Claim;
import org.lr.api.Result;
import org.lr.dto.LoginDto;
import org.lr.dto.RegisterVerifyDto;
import org.lr.handler.MyException;
import org.lr.service.UserService;
import org.lr.service.VerificationCodeService;
import org.lr.vo.basic.NameLoginVo;
import org.lr.vo.basic.PhoneLoginVo;
import org.lr.vo.basic.RegisterFinishVo;
import org.lr.vo.basic.RegisterVerifyVo;
import org.lr.entity.User;
import org.lr.entity.WorkNumber;
import org.lr.service.WorkNumberService;
import org.lr.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginRegisterController {
    private final static Logger log = LoggerFactory.getLogger(LoginRegisterController.class);
    @Autowired
    private WorkNumberService workNumberService;
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/login/username")
    public Result userNameLogin(@RequestBody NameLoginVo vo) throws Exception {
        LoginDto dto = new LoginDto();
        dto.setNumb(vo.getName());
        dto.setPassword(vo.getPassword());
        dto.setCodeId(vo.getCodeId());
        dto.setCode(vo.getCode());
        User user = userService.nameLogin(dto);
        String token = JWTUtil.getToken(user);
        return Result.success(token);
    }

    @PostMapping("/login/phone")
    public Result phoneLogin(@RequestBody PhoneLoginVo vo) throws MyException {
        LoginDto dto = new LoginDto();
        dto.setNumb(vo.getPhoneNumb());
        dto.setCode(vo.getCode());
        User user = userService.phoneLogin(dto);
        String token = JWTUtil.getToken(user);
        return Result.success(token);
    }

    @PostMapping("/register/verify")
    public Result verifyWorkNumb(@RequestBody RegisterVerifyVo vo){
        RegisterVerifyDto dto = new RegisterVerifyDto(vo.getWorkNumber(), vo.getName(), vo.getIdNumber());
        WorkNumber workNumber = workNumberService.getNotRegisterWorkNumber(dto);
        if(workNumber!=null){
            //构造token返回
            HashMap<String, String> tokenInfo = new HashMap<>();
            tokenInfo.put("workNumb", workNumber.getId());
            tokenInfo.put("name", workNumber.getName());
            String token = JWTUtil.getToken(tokenInfo);
            return Result.success(token);
        }
        return Result.fail("该信息已注册或不存在");
    }

    @PostMapping("/register/finish")
    public Result finishInfo(@RequestBody RegisterFinishVo dto, HttpServletRequest request) throws Exception {
        Map<String, Claim> map = null;
        String token = request.getHeader("Authorization");
        map = JWTUtil.verifyToken(token);
        if(!verificationCodeService.verifyPhoneCode(dto.getPhoneNumb(),dto.getVerifyCode())){
            return Result.fail("验证码错误或失效");
        }
        WorkNumber workNumb = workNumberService.getById(map.get("workNumb").asString());
        User user = new User();
        user.setId(workNumb.getId());
        user.setName(workNumb.getName());
        user.setAvatar(dto.getAvatarUrl());
        user.setPhone(dto.getPhoneNumb());
        user.setPassword(dto.getPassword());
        user.setCollege(workNumb.getCreateCollege());
        user.setSpecialty(workNumb.getCreateSpecialty());
        user.setStudyYear(workNumb.getStudyYear());
        user.setIdentity(workNumb.getIdentity());
        user.setPermission(workNumb.getPermission());
        if(userService.saveUser(user)){
            HashMap<String, String> tokenInfo = new HashMap<>();
            tokenInfo.put("workNumb", user.getId());
            tokenInfo.put("name", user.getName());
            tokenInfo.put("phone", user.getPhone());
            String newToken = JWTUtil.getToken(tokenInfo);
            return Result.success(newToken);
        }
        return Result.fail("注册失败");
    }
}
