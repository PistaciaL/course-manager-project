package org.lr;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.lr.entity.TestEntity;
import org.lr.exception.TokenException;
import org.lr.utils.JWTUtil;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
class CourseManagerProjectApplicationTests {

    @Test
    void contextLoads() throws TokenException {
        Map<String, TestEntity> map = new HashMap<>();
        map.put("testKey1", new TestEntity(1l, "content"));
        map.put("testKey2", new TestEntity(2l, "content"));
        map.put("testKey2", new TestEntity(3l, "content"));
        String token = JWTUtil.getToken(1234567l);
        log.info(token);
        JWTUtil.verifyToken(token);
    }

}
