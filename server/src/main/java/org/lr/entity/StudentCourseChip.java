package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentCourseChip {
    @TableId(type = IdType.AUTO)
    @TableField("scc_id")
    private Integer id;
    private Course course;
    private User student;
    private Integer chip;
    private LocalDateTime choiceTime;
    private CourseChoice courseChoice;
}
