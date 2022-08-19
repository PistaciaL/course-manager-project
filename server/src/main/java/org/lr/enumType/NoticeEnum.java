package org.lr.enumType;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum NoticeEnum implements IEnum<String> {
        SupplementaryApply("补选申请"),
        WithdrawApply("退课申请"),
        SupplementaryConsole("补选结果"),
        WithdrawConsole("退课结果"),
        ExamPublish("发布考试"),
        MarkPublish("发布成绩"),
        MarkChange("修改成绩"),
        MarkApply("成绩申请");

        private String value;

        NoticeEnum(String value){
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