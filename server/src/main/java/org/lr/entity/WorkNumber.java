package org.lr.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lr.enumType.IdentityEnum;
import org.lr.enumType.PermissionEnum;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkNumber extends Model<WorkNumber> {
    @TableId(value = "wn_id",type = IdType.AUTO)
    private String id;
    private String name;
    private String idNumber;
    private College createCollege;
    private Specialty createSpecialty;
    private Integer studyYear;
    private IdentityEnum identity;
    private PermissionEnum permission;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableLogic
    private boolean isDeleted;
}
