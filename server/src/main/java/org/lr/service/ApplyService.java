package org.lr.service;

import org.lr.dto.AddNoticeListDto;
import org.lr.dto.ApplyDto;
import org.lr.dto.CheckApplyDto;
import org.lr.entity.Apply;
import org.lr.entity.Notice;
import org.lr.enumType.ApplyEnum;
import org.lr.enumType.NoticeEnum;
import org.lr.handler.MyException;
import org.lr.mapper.ApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class ApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveApply(Apply dto) throws MyException {
        CheckApplyDto checkApplyDto = new CheckApplyDto();
        checkApplyDto.setType(dto.getType());
        checkApplyDto.setCourseId(dto.getCourse().getId());
        checkApplyDto.setApplyUserId(dto.getUserFrom().getId());
        Integer integer = applyMapper.selectNotHandlerApply(checkApplyDto);
        if(integer>0){
            throw new MyException("请勿重复申请");
        }
        Integer applyInsert = applyMapper.insertApply(dto);
        if(applyInsert!=1){
            throw new MyException("申请失败");
        }
        AddNoticeListDto notice = new AddNoticeListDto();
        notice.setCourseId(dto.getCourse().getId());
        notice.setCreateTime(LocalDateTime.now());
        if(dto.getType().equals(ApplyEnum.MarkChange)){
            //当申请类型是修改成绩时,发送通知给'课程所在学院'的所有'院级管理员'
            notice.setType(NoticeEnum.MarkChange);
            notice.setReceiverId(userService.selectCollegeManagerByCollegeId(dto.getCollegeTo().getId()));
        } else {
            //发送通知给课程的教师
            if(dto.getType().equals(ApplyEnum.Supplementary)){
                notice.setType(NoticeEnum.SupplementaryApply);
            } else {
                notice.setType(NoticeEnum.WithdrawApply);
            }
            notice.setReceiverId(Arrays.asList(dto.getUserTo().getId()));
        }
        Integer noticeInsert = noticeService.saveNotice(notice);
        if(noticeInsert!=notice.getReceiverId().size()){
            throw new MyException("通知发送失败");
        }
        return true;
    }
}
