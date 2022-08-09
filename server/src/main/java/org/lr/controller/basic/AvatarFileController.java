package org.lr.controller.basic;

import org.lr.api.Result;
import org.lr.handler.MyException;
import org.lr.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/avatar")
public class AvatarFileController {

    private static Set<String> supportedType = new HashSet<>(Arrays.asList("image/png","image/jpeg","image/gif"));

    @Autowired
    private AvatarService avatarService;

    @PostMapping("/avatarPost")
    public Result postAvatarFile(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return Result.fail("文件为空");
        }
        if(!supportedType.contains(file.getContentType().toLowerCase())){
            return Result.fail("文件格式不支持");
        }
        byte[] fileBytes = file.getBytes();
        String md5 = DigestUtils.md5DigestAsHex(file.getBytes());
        avatarService.uploadAvatar(fileBytes, md5);
        return Result.success(md5);
    }

    @RequestMapping("/getAvatar")
    public void getAvatar(@RequestParam String url, HttpServletResponse response) throws MyException, IOException {
        byte[] bytes = avatarService.downloadAvatar(url);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Length", "" + bytes.length);
        response.addHeader("Content-Disposition", "attachment");
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
    }
}
