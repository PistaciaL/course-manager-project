package org.lr.enumType;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PermissionEnum implements IEnum<String> {
        Normal("普通用户"),
        College("院级管理员"),
        School("校级管理员");

        private String value;

        PermissionEnum(String value){
            this.value = value;
        }
        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }
    }