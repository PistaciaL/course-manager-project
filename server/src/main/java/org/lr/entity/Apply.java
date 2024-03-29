package org.lr.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.lr.enumType.ApplyEnum;

import java.time.LocalDateTime;

@Data
public class Apply {
    @TableId(value = "apply_id",type = IdType.AUTO)
    private Integer id;
    private ApplyEnum type;
    private User userFrom;
    private User userTo;
    private College collegeTo;
    private Course course;
    private User student;
    private Integer newMark;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime handleTime;
    private User handleUser;
    private boolean isAgree;
}
