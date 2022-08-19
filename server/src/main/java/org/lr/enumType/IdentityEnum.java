package org.lr.enumType;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum IdentityEnum implements IEnum<String> {
    Student("学生"),
    Teacher("教师");

    private String value;

    IdentityEnum(String value){
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
