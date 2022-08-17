package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StudentCourse {
    @TableId(value = "sc_id", type = IdType.AUTO)
    private Integer id;
    private Course course;
    private User student;
}
