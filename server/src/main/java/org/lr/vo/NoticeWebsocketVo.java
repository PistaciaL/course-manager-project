package org.lr.vo;

import lombok.Data;

import java.util.List;

@Data
public class NoticeWebsocketVo {
    private String receiverId;
    private List<NewNoticeVo> noticeVoList;
    private Integer newNoticeNumb;
}
