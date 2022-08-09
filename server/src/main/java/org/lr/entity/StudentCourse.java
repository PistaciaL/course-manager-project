package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StudentCourse {
    @TableId(type = IdType.AUTO)
    @TableField("sc_id")
    private Integer id;
    private Course course;
    private User student;
}
