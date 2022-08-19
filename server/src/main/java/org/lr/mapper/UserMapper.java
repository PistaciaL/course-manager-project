package org.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lr.dto.ChangeAvatarDto;
import org.lr.dto.ChangePasswdDto;
import org.lr.dto.ChangePhoneDto;
import org.lr.dto.SearchPageSimpleDto;
import org.lr.entity.User;
import org.lr.vo.PageInfoVo;
import org.lr.vo.UserSimpleVo;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Override
    int insert(User user);

    @Override
    User selectById(Serializable id);

    User selectByPhone(String phoneNumb);

    String selectPasswdById(String numb);

    int updateAvatarByUserId(ChangeAvatarDto dto);

    int updatePasswdById(ChangePasswdDto dto);

    int updatePhone(ChangePhoneDto dto);

    List<UserSimpleVo> selectUserSimple(SearchPageSimpleDto dto);

    Integer selectUserSimpleCount(SearchPageSimpleDto dto);

    List<String> selectCollegeManagerByCollegeId(Integer courseId);
}
