package org.lr.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLog {
    @TableId(type = IdType.AUTO)
    @TableField("ulog_id")
    private Integer id;
    private User operationUser;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime operationTime;
    private User operatedUser;
    private JSONObject changedData;
}
