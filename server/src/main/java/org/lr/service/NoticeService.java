package org.lr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lr.dto.AddNoticeListDto;
import org.lr.dto.SearchNoticeDto;
import org.lr.entity.Notice;
import org.lr.mapper.NoticeMapper;
import org.lr.vo.NoticeVo;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional(rollbackFor = Exception.class)
    public Integer saveNotice(AddNoticeListDto dto){
        Integer integer = noticeMapper.insertNotices(dto);
        return integer;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public PageInfoVo<NoticeVo> selectPageNotice(SearchNoticeDto dto){
        Page<NoticeVo> page = new Page<>(dto.getPage(),dto.getPageSize());
        Page<NoticeVo> noticeVoPage = noticeMapper.selectPageNotice(page, dto.getReceiverId());
        return new PageInfoVo<>(noticeVoPage);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer countUnreadNotice(String userId){
        return noticeMapper.countUnreadNotice(userId);
    }
}
