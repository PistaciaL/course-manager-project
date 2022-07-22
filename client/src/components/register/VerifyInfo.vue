<template>
  <el-form ref="form" :model="form" label-width="150px" :rules="rules" class="form">
    <el-form-item label="请输入学工号" prop="number" class="form-item">
      <el-input
        v-model="form.number"
        oninput="value=value.replace(/[^\d]/g,'')"
      ></el-input>
    </el-form-item>
    <el-form-item label="请输入真实姓名" prop="name" class="form-item">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="请输入证件类型" prop="documentType" class="form-item">
      <el-select v-model="form.documentType" placeholder="请选择">
        <el-option
          v-for="item in documentTypeList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="证件号" prop="documentNumb" class="form-item">
      <el-input v-model="form.documentNumb"></el-input>
    </el-form-item>
    <el-form-item class="form-item">
      <el-button type="primary" plain @click="nextStep" class="next-step-btn">下一步</el-button>
      <span class="other-option">
        <el-link href="/login">已有账号，去登陆</el-link>
      </span>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    const verifyDocNumb = (rule, value, callback) => {
      if (this.form.documentType == "") {
        callback(new Error("请先选择证件类型"));
      } else if (this.form.documentType == 1) {
        var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (pattern.test(value)) {
          if (value.length == 18) {
              var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,5, 8, 4, 2); // 将前17位加权因子保存在数组里
              var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); // 这是除以11后，可能产生的11位余数、验证码，也保存成数组
              var idCardWiSum = 0; // 用来保存前17位各自乖以加权因子后的总和
              for (var i = 0; i < 17; i++) {
                idCardWiSum += value.substring(i, i + 1) * idCardWi[i];
              }
              var idCardMod = idCardWiSum % 11; // 计算出校验码所在数组的位置
              var idCardLast = value.substring(17); // 得到最后一位身份证号码
              // 如果等于2，则说明校验码是10，身份证号码最后一位应该是X
              if (idCardMod == 2) {
                if (idCardLast == "X" || idCardLast == "x") {
                  callback()
                } else {
                  callback(new Error("身份证验证失败"))
                }
              } else {
                // 用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast == idCardY[idCardMod]) {
                  //alert("恭喜通过验证啦！");
                  callback()
                } else {
                  //alert("身份证号码错误！");
                  callback(new Error("身份证验证失败"))
                }
              }
            } else {
              callback()
            }
        } else {
          callback(new Error("身份证验证失败"));
        }
      } else {
        callback();
      }
    };
    const verifyName = (rule, value, callback) => {
      var pattern = /^[\u4e00-\u9fa5a-zA-Z·]{2,40}$/;
      if (pattern.test(value)) {
        callback();
      } else {
        callback(new Error());
      }
    };
    return {
      form: {
        number: "",
        name: "",
        documentType: "",
        documentNumb: "",
      },
      rules: {
        number: [
          { required: true, message: "请输入学工号", trigger: "blur" },
          { len: 10, message: "请输入10位学工号", trigger: "blur" },
        ],
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { validator: verifyName, message: "请输入正确的姓名", trigger: "blur" },
        ],
        documentType: [
          { required: true, message: "请选择证件类型", trigger: "change" },
        ],
        documentNumb: [
          { required: true, message: "请输入证件号码", trigger: "blur" },
          { validator: verifyDocNumb, trigger: "blur" },
        ],
      },
      documentTypeList: [
        { value: 1, label: "身份证" },
        { value: 2, label: "其他" },
      ],
    };
  },
  methods: {
    nextStep() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("验证学工号");
          this.$parent.stepActive = 1;
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style>
.form-item{
  margin: 30px auto;
}
.other-option {
  margin-left: 30px;
}
.next-step-btn{
  width: 155px;
}
</style>