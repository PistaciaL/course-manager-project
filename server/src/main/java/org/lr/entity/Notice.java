package org.lr.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.lr.enumType.NoticeEnum;

import java.time.LocalDateTime;

@Data
public class Notice {
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer id;
    private NoticeEnum type;
    private Course course;
    private User receiveUser;
    private boolean isRead;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;




}
