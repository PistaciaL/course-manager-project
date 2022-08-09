package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ExamStudent {
    @TableId(type = IdType.AUTO)
    @TableField("es_id")
    private Integer id;
    private Exam exam;
    private Room room;
    private User student;
}
