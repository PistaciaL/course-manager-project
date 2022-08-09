package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GuideSubject {
    @TableId(type = IdType.AUTO)
    @TableField("gs_id")
    private Integer id;
    private Guide guide;
    private Subject subject;
}
