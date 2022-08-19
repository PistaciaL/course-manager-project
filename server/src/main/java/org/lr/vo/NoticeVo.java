package org.lr.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.lr.enumType.NoticeEnum;

import java.time.LocalDateTime;

@Data
public class NoticeVo {
    private Integer noticeId;
    private NoticeEnum type;
    private LocalDateTime createTime;
    private Integer courseId;
    private String subjectName;
}
