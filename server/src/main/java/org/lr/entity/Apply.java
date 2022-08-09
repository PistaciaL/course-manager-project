package org.lr.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Apply {
    @TableId(type = IdType.AUTO)
    @TableField("apply_id")
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

    enum ApplyEnum implements IEnum<String> {
        Supplementary("选课申请"),
        Withdraw("退课申请"),
        MarkChange("成绩申请");

        private String value;

        ApplyEnum(String value){
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }
}
