package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class MarkInfo {
    @TableId(type = IdType.AUTO)
    @TableField("mark_id")
    private Integer id;
    private User student;
    private MarkModel markModel;
    private Integer mark;
}
