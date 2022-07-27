<template>
  <el-form ref="form" :model="form" label-width="150px" :rules="rules">
    <el-form-item label="请上传头像" class="form-item">
      <el-button class="cancel_upload" @click="cancelUpload">取消上传</el-button>
      <el-upload class="avatar-uploader" action="http://localhost:8081/" :show-file-list="false"
        :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
        <img v-if="cacheImageUrl" :src="cacheImageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-form-item>
    <el-form-item label="请输入手机号" prop="phone" class="form-item">
      <el-input v-model="form.phone" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
    </el-form-item>
    <el-form-item label="请输入验证码" prop="vCode" class="form-item">
      <el-input v-model="form.vCode" class="verification-input"></el-input>
      <el-button @click="reGetVerificationCode" :disabled="btnDisabled" class="verification-btn">{{ btnContainer }}
      </el-button>
    </el-form-item>
    <el-form-item label="请输入密码" class="form-item" prop="passwd">
      <el-input v-model="form.passwd" show-password></el-input>
    </el-form-item>
    <el-form-item label="请再次输入密码" class="form-item" prop="rePasswd">
      <el-input v-model="form.rePasswd" show-password></el-input>
    </el-form-item>
    <el-form-item class="form-item">
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
  data() {
    const checkPhone = (rule, value, callback) => {
      var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
      if (!pattern.test(value)) {
        callback(new Error('请输入正确手机号'))
      } else {
        callback()
      }
    }
    const checkPasswd = (rule, value, callback) => {
      var pattern = /^[a-zA-Z]\w{5,17}$/
      if (!pattern.test(value)) {
        callback(new Error('以字母开头，长度在6~18之间，只能包含字母、数字和下划线'))
      }
      callback()
    }
    const checkRePasswd = (rule, value, callback) => {
      if (this.form.passwd != value) {
        callback(new Error('两次输入密码不相同'))
      }
      callback()
    }
    return {
      form: {
        phone: '',
        vCode: '',
        passwd: '',
        rePasswd: '',
        imageUrl: ''
      },
      rules: {
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' }
        ],
        vCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { len: 6, message: '请输入正确验证码', trigger: 'blur' }
        ],
        passwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: checkPasswd, trigger: 'blur' }
        ],
        rePasswd: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: checkRePasswd, trigger: 'blur' }
        ]
      },
      btnContainer: '发送验证码',
      isSend: false,
      btnDisabled: true,
      cacheImageUrl: ''
    }
  },
  methods: {
    nextStep() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$parent.stepActive = 3;
        } else {
          return false;
        }
      });
    },
    reGetVerificationCode() {
      this.btnContainer = '60'
      this.isSend = true,
        this.btnDisabled = true
      this.changeBtnText()
      console.log("发送手机验证码")
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
    },
    handleAvatarSuccess(res, file) {
      console.log("返回保存的头像url")
      this.form.imageUrl = res.imgUrl;
      this.cacheImageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const correctType = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!correctType) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return correctType && isLt2M;
    },
    cancelUpload(){
      this.form.imageUrl = ''
      this.cacheImageUrl = ''
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
.form-item {
  margin: 30px auto;
}

.other-option {
  margin-left: 40px;
}

.verification-btn {
  width: 112px;
}

.verification-input {
  width: 173px;
  margin-right: 15px;
}

/* avatar */
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
}
.avatar-uploader{
  height: 180px;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.cancel_upload{
  float: right;
}
</style>