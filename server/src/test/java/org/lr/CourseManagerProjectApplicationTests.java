package org.lr;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lr.entity.*;
import org.lr.service.VerificationCodeService;
import org.lr.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class CourseManagerProjectApplicationTests {

    @Test
    void specialtyMapper(){
        //insert
        College college = new College();
        college.setId(16);
        Specialty specialty = new Specialty(null, "人工智能",college);
        specialty.insert();

        //searchById
        Specialty specialty1 = new Specialty();
        specialty1.setId(204);
        System.out.println(specialty1.selectById());

    }

    @Test
    void workNumberMapper(){
        //selectByPage
        WorkNumber workNumber = new WorkNumber();
        Page<WorkNumber> page = workNumber.selectPage(new Page<>(), new QueryWrapper<>());
        for(WorkNumber wn : page.getRecords()){
            System.out.println(wn);
        }
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void redisTest(){

    }

    @Autowired
    private VerificationCodeService verificationCodeService;
    @Test
    void verificationCodeTest(){
        verificationCodeService.sendPhoneCode("150561910170");
    }
}
