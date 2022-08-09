package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Guide {
    @TableId(type = IdType.AUTO)
    private Integer guideId;
    private Specialty specialty;
    private Integer studyYear;
}
