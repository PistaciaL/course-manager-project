package org.lr.controller.home;

import com.auth0.jwt.interfaces.Claim;
import org.lr.api.Result;
import org.lr.dto.ChangeAvatarDto;
import org.lr.dto.ChangePasswdDto;
import org.lr.service.UserService;
import org.lr.utils.JWTUtil;
import org.lr.vo.ChangePasswdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @PostMapping("/avatar")
    public Result changeAvatar(@RequestParam("avatarUrl") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Claim> tokenMap = JWTUtil.verifyToken(token);
        String id = tokenMap.get("workNumb").asString();
        boolean b = userService.changeAvatar(new ChangeAvatarDto(id, fileName));
        if(b){
            String newToken = JWTUtil.getToken(userService.selectById(id));
            response.setHeader("Authorization", newToken);
            return Result.success();
        }
        return Result.fail();
    }

    @PostMapping("/changePasswd")
    public Result changePasswd(@RequestBody ChangePasswdVo vo, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Claim> tokenMap = JWTUtil.verifyToken(token);
        String id = tokenMap.get("workNumb").asString();
        ChangePasswdDto dto = new ChangePasswdDto();
        dto.setOldPasswd(vo.getOldPasswd());
        dto.setNewPasswd(vo.getNewPasswd());
        boolean b = userService.verifyPasswd(dto);
    }
}
