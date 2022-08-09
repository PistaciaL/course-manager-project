package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class MarkModel {
    @TableId(type = IdType.AUTO)
    @TableField("mm_id")
    private Integer id;
    private Course course;
    private String name;
    private Float percentage;
}
