package org.lr.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.lr.dto.PicCodeDto;
import org.lr.utils.PicCodeUtils;
import org.lr.utils.PhoneCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    private final int CODE_LENGTH = 6;
    private final String PHONE_CODE_KEY_PREFIX = "CM:PhoneCODE:";
    private final String PIC_CODE_KEY_PREFIX = "CM:PicCODE:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean sendPhoneCode(String phone){
        String code = randomGetCode();
        if(redisTemplate.getExpire(phoneCodeKey(phone))>9*60){
            return false;
        }
        redisTemplate.opsForValue().set(phoneCodeKey(phone), code.toLowerCase(), 10, TimeUnit.MINUTES);
        return PhoneCodeUtil.sendPhoneCode(phone, code);
    }

    public boolean verifyPhoneCode(String phone, String code){
        return Boolean.TRUE.equals(redisTemplate.execute(new SessionCallback<Boolean>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                if (StringUtils.equals((String) redisTemplate.opsForValue().get(phoneCodeKey(phone)), code.toLowerCase())) {
                    redisTemplate.delete(phoneCodeKey(phone));
                    return true;
                }
                return false;
            }
        }));

    }

    public PicCodeDto sendPicCode() throws IOException {
        String code = PicCodeUtils.generateVerifyCode();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PicCodeUtils.outputImage(out, code);
        byte[] bytes = out.toByteArray();
        String codeId = DigestUtils.md5DigestAsHex(bytes);
        redisTemplate.opsForValue().set(picCodeKey(codeId), code.toLowerCase(), 30, TimeUnit.MINUTES);
        return new PicCodeDto(bytes, codeId);
    }

    public boolean verifyPicCode(String codeId, String code){
        return Boolean.TRUE.equals(redisTemplate.execute(new SessionCallback<Boolean>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                if(StringUtils.equals(redisTemplate.opsForValue().get(picCodeKey(codeId)).toString(),code.toLowerCase())){
                    redisTemplate.delete(picCodeKey(codeId));
                    return true;
                }
                return false;
            }
        }));

    }

    private String randomGetCode(){
        StringBuilder sb = new StringBuilder();
        List<Character> list = new ArrayList<>();
        for(char c = 'A'; c<='Z'; c++)
            list.add(c);
        for(char c = '1'; c<='9';c++)
            list.add(c);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(list.get((int)(Math.random()*list.size())));
        }
        return sb.toString();
    }

    private String phoneCodeKey(String userPhone){
        return PHONE_CODE_KEY_PREFIX+userPhone;
    }
    private String picCodeKey(String codeId){
        return PIC_CODE_KEY_PREFIX+codeId;
    }
}
