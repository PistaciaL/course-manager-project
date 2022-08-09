package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Schedule {
    @TableId(type = IdType.AUTO)
    @TableField("schedule_id")
    private Integer id;
    private Course course;
    private Room room;
    private LocalDate classDate;
    private Integer startClassHour;
    private Integer endClassHour;
}
