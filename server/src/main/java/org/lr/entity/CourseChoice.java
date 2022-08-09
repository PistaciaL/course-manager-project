package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.lr.enumType.CCTypeEnum;

import java.time.LocalDateTime;

@Data
public class CourseChoice {
    @TableId(type = IdType.AUTO)
    @TableField("cc_id")
    private Integer id;
    private Term term;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CCTypeEnum type;
    private Integer defaultChip;


}
