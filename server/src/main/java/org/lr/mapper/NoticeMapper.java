package org.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lr.dto.AddNoticeListDto;
import org.lr.dto.SearchNoticeDto;
import org.lr.vo.NoticeVo;
import org.lr.vo.PageInfoVo;

@Mapper
public interface NoticeMapper extends BaseMapper<NoticeVo> {
    Integer insertNotices(AddNoticeListDto notice);

    Page<NoticeVo> selectPageNotice(@Param("page") Page<NoticeVo> page, @Param("receiverId") String receiverId);

    Integer countUnreadNotice(String userId);
}
