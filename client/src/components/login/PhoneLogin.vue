<template>
  <el-form ref="form" :model="form" :rules="rules" label-position="top" class="form">
    <el-form-item class="form-item" prop="phone">
      <el-input v-model="form.phone" :placeholder="phonePlaceHold==null?'请输入手机号':phonePlaceHold" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
    </el-form-item>
    <el-form-item class="form-item" prop="vCode">
      <el-input v-model="form.vCode" placeholder="请输入验证码" class="verification-input"></el-input>
      <el-button @click="reGetVerificationCode" :disabled="btnDisabled" class="verification-btn">{{ btnContainer }}
      </el-button>
    </el-form-item>
    <div class="register-link">
      <el-link href="/register">去注册 &gt;</el-link>
    </div>
    <el-form-item class="form-item form-item-submit">
      <el-button type="primary" plain @click="onSubmit" class="btn-login">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

var that;
export default {
  data() {
    const checkPhone = (rule, value, callback) => {
      var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (value == null || value == '' || value === undefined) {
        callback()
      } else {
        if (!pattern.test(value)) {
          callback(new Error('请输入正确手机号'))
        } else {
          callback()
        }
      }
    }
    return {
      form: {
        phone: '',
        vCode: ''
      },
      rules: {
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' }
        ],
        vCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { len: 6, message: '请输入正确验证码', trigger: 'blur' }
        ]
      },
      phonePlaceHold:localStorage.getItem('phone'),
      btnContainer: '发送验证码',
      isSend: false,
      btnDisabled: true
    }
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios({
            method:'post',
            url:'/login/phone',
            data:{
              phoneNumb:this.form.phone,
              code:this.form.vCode
            }
          }).then(res=>{
            if(res.data.code==200){
              this.MyUtils.fillLocalStorage(res.data.data)
              this.$router.push("/")
            } else {
              this.$message({
                message: res.data.message,
                type: 'warning'
              });
            }
          }, e=>{
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
    reGetVerificationCode() {
      this.btnContainer = '60'
      this.isSend = true
      this.btnDisabled = true
      this.axios({
        method:'post',
        url:'/verifyCode/getPhoneCode',
        params:{
          phoneNumb:this.form.phone
        }
      }).then(res=>{
        if(res.data.code==200){
          this.changeBtnText()
          this.$message({
            message: '验证码发送成功',
            type: 'success'
          });
        } else{
          this.$message.error('发送失败,请稍后再试');
        }
      }, error=>{
        this.btnContainer="发送验证码"
        this.isSend=false
        this.btnDisabled=false
        this.$message({
          message:"发送失败,请稍后再试",
          type:'error'
        })
      })
    },
    changeBtnText() {
      setTimeout(() => {
        if (that.btnContainer == '发送验证码') {
          that.btnContainer = 59
          that.changeBtnText()
        } else {
          that.btnContainer = that.btnContainer - 1
          if (that.btnContainer != -1) {
            that.changeBtnText()
          } else {
            that.btnContainer = '发送验证码'
            that.isSend = false
            this.verifyPhone(this.form.phone)

          }
        }
      }, 1000)
    },
    verifyPhone(newValue) {
      var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (this.isSend == false && pattern.test(newValue)) {
        this.btnDisabled = false;
      } else {
        this.btnDisabled = true;
      }
    }
  },
  watch: {
    'form.phone': {
      handler(newValue) {
        this.verifyPhone(newValue)
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    that = this;
  }
}
</script>

<style scoped>
.form {
  margin: 40px auto;
  width: 70%;
}

.form-item {
  margin: 55px auto 0;
}

.form-item-submit {
  margin-top: 50px;
}

.btn-login {
  margin: 0 auto;
  padding: 16px 119px;
}

.checkbox-container {
  width: 80%;
  margin-top: 20px;
  padding-left: 50%
}

.verification-input {
  width: 139px;
  margin-right: 15px;
}

.verification-btn {
  width: 112px;
}

.register-link {
  float: right;
  margin-right: 30px;
  margin-top: 15px;
}
</style>