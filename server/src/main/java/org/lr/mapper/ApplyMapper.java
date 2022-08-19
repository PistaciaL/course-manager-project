package org.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lr.dto.ApplyDto;
import org.lr.dto.CheckApplyDto;
import org.lr.entity.Apply;

@Mapper
public interface ApplyMapper {
    Integer insertApply(Apply apply);

    Integer selectNotHandlerApply(CheckApplyDto dto);
}
