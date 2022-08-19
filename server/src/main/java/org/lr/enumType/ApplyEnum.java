package org.lr.enumType;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ApplyEnum implements IEnum<String> {
        Supplementary("选课申请"),
        Withdraw("退课申请"),
        MarkChange("成绩申请");

        private String value;

        ApplyEnum(String value){
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String toString() {
                return this.getValue();
        }
}