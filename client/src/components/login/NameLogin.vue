<template>
  <el-form ref="form" :model="form" label-position="top" class="form" :rules="rules">
    <el-form-item class="form-item" prop="name">
      <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
    </el-form-item>
    <el-form-item class="form-item" prop="password">
      <el-input v-model="form.password"  placeholder="请输入密码" show-password></el-input>
    </el-form-item>
    <el-form-item class="form-item" prop="verificationCode">
      <el-input v-model="form.verificationCode"  placeholder="请输入验证码" class="verification-input"></el-input>
      <img :src="picFill" class="verification-code" @click="reGetVerificationCode"/>
    </el-form-item>
    <!-- checkbox start -->
    <el-form-item class="form-item form-item-checkbox" prop="rememberPasswd">
      <el-checkbox v-model="form.rememberPasswd">记住密码</el-checkbox>
    </el-form-item>
    <!-- checkbox end -->
    <div class="register-link">
      <el-link href="/register">去注册 &gt;</el-link>
    </div>
    <el-form-item class="form-item form-item-submit">
      <el-button type="primary" plain @click="onSubmit" class="btn-login">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data(){
    return{
      form: {
        name: localStorage.getItem('workNumb'),
        password: '',
        rememberPasswd: false,
        verificationCode: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        verificationCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {len: 6, message: '请输入正确验证码', trigger: 'blur'}
        ]
      },
      picFill: '',
      codeId:''
    }
  },
  mounted(){
    this.reGetVerificationCode()
  },
  methods: {
    onSubmit(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios({
            method:'post',
            url:'/login/username',
            data:{
              name:this.form.name,
              password:this.RSA.encrypt(this.form.password),
              codeId:this.codeId,
              code:this.form.verificationCode
            }
          }).then(res=>{
            if(res.data.code==200){
              if(this.form.rememberPasswd){
                localStorage.setItem('password', this.RSA.encrypt(this.form.password));
              }
              this.MyUtils.fillLocalStorage(res.data.data)
              this.$router.push("/")
            }else {
              this.reGetVerificationCode()
              this.$message({
                  message: e.response.data.message,
                  type: 'warning'
                });
            }
          }, e=>{
            this.reGetVerificationCode()
            this.$message({
                message: e.response.data.message,
                type: 'warning'
              });
          })
        } else {
          return false;
        }
      });
    },
    reGetVerificationCode(){
      this.axios({
        method:'POST',
        url:'/verifyCode/getPicCode'
      }).then(res=>{
        this.picFill="data:image/jpeg;base64,"+res.data.data.pic,
        this.codeId = res.data.data.codeId
      })
    }
  }
}
</script>

<style scoped>
.form {
  margin: 20px auto;
  width: 70%;
}
.form-item {
  margin: 19px auto 0;
}
.form-item-submit{
  margin-top: 12px;
}
.form-item-checkbox{
  margin: 10px 10px;
}
.btn-login {
  margin: 0 auto;
  padding: 16px 119px;
}
.verification-code{
  height: 40px;
  width: 112px;
  float: right
}
.verification-input {
  width: 139px;
  margin-right: 15px;
}
.register-link{
  display: block;
  float:right;
  margin-top: -41px;
}
</style>