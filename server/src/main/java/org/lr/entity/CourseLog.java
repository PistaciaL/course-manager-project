package org.lr.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseLog {
    @TableId(type = IdType.AUTO)
    @TableField("clog_id")
    private Integer id;
    private User operationUser;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime operationTime;
    private JSONObject changedData;
}
