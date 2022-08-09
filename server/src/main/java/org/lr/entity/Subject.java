package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Subject {
    @TableId(type = IdType.AUTO)
    private Integer subjectId;
    private String name;
    private College college;
    private Integer lessonHour;
    private Integer credit;
}
