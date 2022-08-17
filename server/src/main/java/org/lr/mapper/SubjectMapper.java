package org.lr.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lr.dto.SearchSubjectDto;
import org.lr.entity.Subject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

    <E extends IPage<Subject>> E selectPage(@Param("page") IPage<?> page, @Param("dto") SearchSubjectDto dto);

    Subject selectById(Integer id);
}
