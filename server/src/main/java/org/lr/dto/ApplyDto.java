package org.lr.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.lr.entity.College;
import org.lr.entity.Course;
import org.lr.entity.User;
import org.lr.enumType.ApplyEnum;

import java.time.LocalDateTime;

@Data
@TableName("apply")
public class ApplyDto {
    @TableId(value = "apply_id",type = IdType.AUTO)
    private Integer id;
    private ApplyEnum type;
    private String userFrom;
    private String userTo;
    private Integer collegeTo;
    private Integer courseId;
    private String studentId;
    private Integer newMark;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime handleTime;
    private Integer handleUser;
    private boolean isAgree;
}
