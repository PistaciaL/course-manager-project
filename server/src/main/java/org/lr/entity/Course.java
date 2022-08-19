package org.lr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Course {
    @TableId(type = IdType.AUTO)
    @TableField("course_id")
    private Integer id;
    private Subject subject;
    private User teacher;
    private Term term;
    private Integer maxStudent;
    private Integer existStudent;
    private boolean markIsPublish;

    public Course(Integer id) {
        this.id = id;
    }
}
