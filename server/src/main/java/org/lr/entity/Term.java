package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Term {
    @TableId(type = IdType.AUTO)
    private Integer termId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
