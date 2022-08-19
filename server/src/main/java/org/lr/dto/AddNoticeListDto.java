package org.lr.dto;

import lombok.Data;
import org.lr.entity.Course;
import org.lr.enumType.NoticeEnum;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddNoticeListDto {
    private NoticeEnum type;
    private Integer courseId;
    private LocalDateTime createTime;
    private List<String> receiverId;
}
