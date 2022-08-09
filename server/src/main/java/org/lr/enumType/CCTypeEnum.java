package org.lr.enumType;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum CCTypeEnum implements IEnum<String> {
        Chip("筹码选课"),
        Grab("先到先得");

        private String value;

        CCTypeEnum(String value){
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }