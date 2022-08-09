package org.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lr.entity.Specialty;

import java.io.Serializable;

@Mapper
public interface SpecialtyMapper extends BaseMapper<Specialty> {

    @Override
    int insert(Specialty specialty);

    @Override
    Specialty selectById(Serializable id);
}
