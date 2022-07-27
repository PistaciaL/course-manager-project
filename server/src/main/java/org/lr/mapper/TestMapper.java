package org.lr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {
    int getSequenceOrderByYearAndCollege(@Param("year") int year, @Param("college") int id);
}
