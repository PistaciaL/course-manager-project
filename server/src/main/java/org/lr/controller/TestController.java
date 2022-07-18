package org.lr.controller;

import com.alibaba.fastjson.JSONObject;
import org.lr.api.ErrorCode;
import org.lr.api.Result;
import org.lr.entity.TestEntity;
import org.lr.exception.NotFoundException;
import org.lr.utils.JWTUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @RequestMapping("/{id}")
    public Object test(@PathVariable String id) throws NotFoundException {
        if("1".equals(id)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        } else if("2".equals(id)) {
            throw new NullPointerException();
        } else if("3".equals(id)) {
            return JSONObject.toJSONString(new TestEntity(100l, "test-content"));
        } else {
            return new TestEntity(100l, "test-content");
        }
    }

    @RequestMapping("/login")
    public Object getToken(@RequestBody String id) {
        String token = JWTUtil.getToken(Long.valueOf(id));
        return token;
    }
}
