package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exam {
    @TableId(type = IdType.AUTO)
    @TableField("exam_id")
    private Integer id;
    private Course course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
