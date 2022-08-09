package org.lr.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PhoneCodeUtil {

    private static final String MAIL_MODEL_PREFIX="【课程管理系统】您的验证码是";
    private static final String MAIL_MODEL_SUFFIX="，此验证码用于登录、注册、或是修改绑定手机，验证码10分钟内有效。";

    public static boolean sendPhoneCode(String phoneNumb, String code){
        log.info("向"+phoneNumb+"发送验证码:");
        log.info(MAIL_MODEL_PREFIX+code+MAIL_MODEL_SUFFIX);
        return true;
    }
}
