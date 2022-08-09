package org.lr.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lr.enumType.IdentityEnum;
import org.lr.enumType.PermissionEnum;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("\"user\"")
public class User {

    @TableId(value = "user_id", type = IdType.INPUT)
    private String id;
    private String name;
    private String avatar;
    private String phone;
    @TableField(select = false)
    private String password;
    private College college;
    private Specialty specialty;
    private Integer studyYear;
    private IdentityEnum identity;
    private PermissionEnum permission;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableLogic
    private boolean isDeleted;
}

