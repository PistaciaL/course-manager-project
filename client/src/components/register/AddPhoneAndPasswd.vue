<template>
  <el-form ref="form" :model="form" label-width="100px" :rules="rules">
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="form.phone" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="verificationCode">
      <el-input v-model="form.verificationCode" class="verification-input"></el-input>
      <el-button @click="reGetVerificationCode" :disabled="btnDisabled" class="verification-btn">{{btnContainer}}</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" plain @click="nextStep">下一步</el-button>
      <span class="other-option">
        <el-link href="/login">已有账号，去登陆</el-link>
      </span>
    </el-form-item>
  </el-form>
</template>

<script>
var that;
export default {
  data(){
    const checkPhone = (rule, value, callback) => {
      var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if(value==null || value=='' || value === undefined){
        callback()
      } else {
        if(!pattern.test(value)){
          callback(new Error('请输入正确手机号'))
        } else {
          callback()
        }
      }
    }
    return{
      form: {
        phone: '',
        verificationCode: ''
      },
      rules: {
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {validator: checkPhone, trigger: 'blur'}
        ],
        verificationCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {len: 6, message: '请输入正确验证码', trigger: 'blur'}
        ]
      },
      btnContainer: '发送验证码',
      isSend: false,
      btnDisabled: true
    }
  },
  methods: {
    nextStep() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("nextstep");
          this.$parent.stepActive = 2;
        } else {
          return false;
        }
      });
    },
    reGetVerificationCode(){
      this.btnContainer = '60'
      this.isSend = true,
      this.btnDisabled = true
      this.changeBtnText()
    },
    changeBtnText(){
      setTimeout(()=>{
        if(that.btnContainer == '发送验证码'){
          that.btnContainer = 59
          that.changeBtnText()
        } else {
          that.btnContainer = that.btnContainer-1
          if(that.btnContainer != -1){
            that.changeBtnText()
          } else {
            that.btnContainer = '发送验证码'
            that.isSend = false
            this.verifyPhone(this.form.phone)
            
          }
        }
      }, 1000)
    },
    verifyPhone(newValue){
      var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
        if(this.isSend == false && pattern.test(newValue)){
          this.btnDisabled = false;
        } else {
          this.btnDisabled = true;
        }
    }
  },
  watch: {
    'form.phone':{
      handler(newValue){
        this.verifyPhone(newValue)
      },
      deep: true,
      immediate: true
    }
  },
  mounted(){
    that = this;
  }
}
</script>

<style scoped>
.other-option {
  margin-left: 40px;
}
.verification-btn{
  width: 112px;
}
.verification-input{
  width: 173px;
  margin-right: 15px;
}
</style>