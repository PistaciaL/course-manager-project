package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Subject {
    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private College college;
    private Integer lessonHour;
    private Float credit;
}
