package org.lr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.lr.dto.ChangeAvatarDto;
import org.lr.dto.ChangePasswdDto;
import org.lr.dto.ChangePhoneDto;
import org.lr.dto.LoginDto;
import org.lr.entity.User;
import org.lr.handler.MyException;
import org.lr.mapper.UserMapper;
import org.lr.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Transactional
    public boolean saveUser(User user) throws MyException {
        if(userMapper.selectCount(new QueryWrapper<User>().eq("phone", user.getPhone()))>0){
            throw new MyException("该手机号已被注册");
        }
        int insert = userMapper.insert(user);
        return insert!=0;
    }

    public User phoneLogin(LoginDto dto) throws MyException {
        boolean b = verificationCodeService.verifyPhoneCode(dto.getNumb(), dto.getCode());
        if(!b){
            throw new MyException("验证码错误");
        }
        User user = userMapper.selectByPhone(dto.getNumb());
        return user;
    }

    public User nameLogin(LoginDto dto) throws Exception {
        boolean b = verificationCodeService.verifyPicCode(dto.getCodeId(), dto.getCode());
        if(!b){
            throw new MyException("验证码错误");
        }
        String password = userMapper.selectPasswdById(dto.getNumb());
        if(password==null){
            throw new MyException("用户不存在");
        }
        log.info(RSAUtil.decrypt(dto.getPassword()));
        log.info(RSAUtil.decrypt(password));
        if(!StringUtils.equals(RSAUtil.decrypt(password),RSAUtil.decrypt(dto.getPassword()))){
            throw new MyException("用户名或密码错误");
        }
        return userMapper.selectById(dto.getNumb());
    }

    public boolean changeAvatar(ChangeAvatarDto dto){
        return userMapper.updateAvatarByUserId(dto)==1;
    }

    public User selectById(Serializable id){
        return userMapper.selectById(id);
    }

    @Transactional
    public boolean changePasswd(ChangePasswdDto dto) throws Exception {
        String passwd = userMapper.selectPasswdById(dto.getUserId());
        if(StringUtils.equals(RSAUtil.decrypt(dto.getOldPasswd()), RSAUtil.decrypt(passwd))){
            return userMapper.updatePasswdById(dto)==1;
        } else {
            throw new MyException("密码错误");
        }
    }

    public boolean changePhone(ChangePhoneDto dto) throws MyException {
        if(!verificationCodeService.verifyPhoneCode(dto.getOriginPhone(), dto.getOriginCode())){
            throw new MyException("验证码错误或过期");
        }
        if(!verificationCodeService.verifyPhoneCode(dto.getNewPhone(), dto.getNewCode())){
            throw new MyException("验证码错误或过期");
        }
        int i = userMapper.updatePhone(dto);
        return i==1;
    }
}
