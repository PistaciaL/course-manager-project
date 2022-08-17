package org.lr.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Term {
    @TableId(value = "term_id",type = IdType.AUTO)
    private Integer id;
    private String name;
    @JSONField(format="yyyy-MM-dd")
    private LocalDate startDate;
    @JSONField(format="yyyy-MM-dd")
    private LocalDate endDate;
}
