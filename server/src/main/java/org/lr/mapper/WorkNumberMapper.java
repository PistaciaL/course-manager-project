package org.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lr.dto.RegisterVerifyDto;
import org.lr.entity.WorkNumber;

import java.io.Serializable;

@Mapper
public interface WorkNumberMapper extends BaseMapper<WorkNumber> {

    WorkNumber getNotRegisterWorkNumber(RegisterVerifyDto dto);

    @Override
    WorkNumber selectById(Serializable id);
}
