<template>
  <div class="container">
    <div class="title">个人资料</div>
    <el-divider></el-divider>
    <el-dialog
      title="正在修改的还未保存"
      :visible.sync="dialogVisible"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <span>是否放弃修改信息？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetChangingItem">否</el-button>
        <el-button type="primary" @click="refreshForm">是</el-button>
      </span>
    </el-dialog>
    <div class="form">
      <el-form ref="avatarform" :model="newUserInfo" label-width="160px">
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8081/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="cacheImageUrl" :src="cacheImageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div
            v-show="
              this.newUserInfo.avatar != '' &&
              this.newUserInfo.avatar != this.userInfo.avatarUrl
            "
            class="avatar-icon-group"
          >
            <el-button
              icon="el-icon-check"
              type="success"
              circle
              @click="submitAvatar"
            ></el-button>
            <el-button
              icon="el-icon-refresh-right"
              type="danger"
              circle
              @click="refreshAvatar"
            ></el-button>
          </div>
        </el-form-item>
      </el-form>
      <el-form ref="nameform" :model="newUserInfo" label-width="160px">
        <el-form-item label="姓名" class="form-item">
          <el-input
            :placeholder="userInfo.name"
            v-model="newUserInfo.name"
            :disabled="changingItem != 'name'"
          >
          </el-input>
          <div v-show="changingItem == 'name'" class="input-item-icon-group">
            <el-button
              icon="el-icon-check"
              type="success"
              circle
              @click="changingItem = ''"
            ></el-button>
            <el-button
              icon="el-icon-refresh-right"
              type="danger"
              circle
              @click="refreshName"
            ></el-button>
          </div>
          <div v-show="changingItem != 'name'" class="input-item-icon-group">
            <el-tooltip
              effect="dark"
              content="用户姓名不可以修改"
              placement="right"
              :hide-after="1000"
            >
              <el-button icon="el-icon-edit-outline" circle></el-button>
            </el-tooltip>
          </div>
        </el-form-item>
      </el-form>
      <el-form
        ref="phoneform"
        :rules="rules"
        :model="newUserInfo"
        label-width="160px"
        hide-required-asterisk
      >
        <el-form-item
          :label="changingItem == 'phone' ? '请输入原手机号' : '手机号'"
          class="form-item"
          prop="oldPhone"
        >
          <el-input
            :placeholder="userInfo.phone"
            v-model="newUserInfo.oldPhone"
            :disabled="changingItem != 'phone'"
            oninput="value=value.replace(/[^\d]/g,'')"
          >
          </el-input>
          <div v-show="changingItem == 'phone'" class="input-item-icon-group">
            <el-button
              icon="el-icon-check"
              type="success"
              circle
              @click="submitPhone"
            ></el-button>
            <el-button
              icon="el-icon-refresh-right"
              type="danger"
              circle
              @click="refreshPhone"
            ></el-button>
          </div>
          <div v-show="changingItem != 'phone'" class="input-item-icon-group">
            <el-button
              icon="el-icon-edit-outline"
              circle
              @click="changingItem = 'phone'"
            ></el-button>
          </div>
        </el-form-item>
        <el-form-item
          label="原手机号验证码"
          class="form-item-verification"
          v-show="changingItem == 'phone'"
          prop="oldVerCode"
        >
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="newUserInfo.oldVerCode"
            maxlength="6"
            show-word-limit
            oninput="value=value.replace(/[^\d]/g,'')"
          ></el-input>
          <el-button
            @click="reGetVerificationCode(0)"
            :disabled="btnDisabled[0]"
            class="verification-btn"
            >{{ btnContainer[0] }}
          </el-button>
        </el-form-item>
        <el-form-item
          label="请输入新手机号"
          class="form-item"
          v-show="changingItem == 'phone'"
          prop="newPhone"
        >
          <el-input
            v-model="newUserInfo.newPhone"
            oninput="value=value.replace(/[^\d]/g,'')"
          >
          </el-input>
        </el-form-item>
        <el-form-item
          label="新手机号验证码"
          class="form-item-verification"
          v-show="changingItem == 'phone'"
          prop="newVerCode"
        >
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="newUserInfo.newVerCode"
            maxlength="6"
            show-word-limit
            oninput="value=value.replace(/[^\d]/g,'')"
          ></el-input>
          <el-button
            @click="reGetVerificationCode(1)"
            :disabled="btnDisabled[1]"
            class="verification-btn"
            >{{ btnContainer[1] }}
          </el-button>
        </el-form-item>
      </el-form>

      <el-form
        ref="passwdform"
        :rules="rules"
        :model="newUserInfo"
        label-width="160px"
        hide-required-asterisk
      >
        <el-form-item
          :label="changingItem == 'passwd' ? '请输入原密码' : '密码'"
          class="form-item"
          prop="passwd"
        >
          <el-input
            :placeholder="userInfo.passwd"
            v-model="newUserInfo.passwd"
            :disabled="changingItem != 'passwd'"
            show-password
          >
          </el-input>
          <div v-show="changingItem == 'passwd'" class="input-item-icon-group">
            <el-button
              icon="el-icon-check"
              type="success"
              circle
              @click="submitPasswd"
            ></el-button>
            <el-button
              icon="el-icon-refresh-right"
              type="danger"
              circle
              @click="refreshPasswd"
            ></el-button>
          </div>
          <div v-show="changingItem != 'passwd'" class="input-item-icon-group">
            <el-button
              icon="el-icon-edit-outline"
              circle
              @click="changingItem = 'passwd'"
            ></el-button>
          </div>
        </el-form-item>
        <el-form-item
          label="请输入新密码"
          class="form-item"
          v-show="changingItem == 'passwd'"
          prop="newPasswd"
        >
          <el-input v-model="newUserInfo.newPasswd" show-password> </el-input>
        </el-form-item>
        <el-form-item
          label="请再次输入新密码"
          class="form-item"
          v-show="changingItem == 'passwd'"
          prop="rePasswd"
        >
          <el-input v-model="newUserInfo.rePasswd" show-password> </el-input>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    const checkPhone = (rule, value, callback) => {
      var pattern =
        /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (!pattern.test(value)) {
        callback(new Error("请输入正确手机号"));
      } else {
        callback();
      }
    };
    const checkPasswd = (rule, value, callback) => {
      var pattern = /^[a-zA-Z]\w{5,17}$/;
      if (!pattern.test(value)) {
        callback(
          new Error("以字母开头，长度在6~18之间，只能包含字母、数字和下划线")
        );
      }
      callback();
    };
    const checkNewPasswd = (rule, value, callback) => {
      if (value == this.newUserInfo.passwd) {
        callback(new Error("新密码与原密码不能相同"));
      }
      var pattern = /^[a-zA-Z]\w{5,17}$/;
      if (!pattern.test(value)) {
        callback(
          new Error("以字母开头，长度在6~18之间，只能包含字母、数字和下划线")
        );
      }
      callback();
    };
    const checkRePasswd = (rule, value, callback) => {
      if (this.newUserInfo.newPasswd != value) {
        callback(new Error("两次输入密码不相同"));
      }
      callback();
    };
    return {
      newUserInfo: {
        name: "",
        avatar: "",
        oldPhone: "",
        oldVerCode: "",
        newPhone: "",
        newVerCode: "",
        passwd: "",
        newPasswd: "",
        rePasswd: "",
        job_title: "",
      },
      rules: {
        oldPhone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { validator: checkPhone, trigger: "blur" },
        ],
        oldVerCode: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { len: 6, message: "请输入正确验证码", trigger: "blur" },
        ],
        newPhone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { validator: checkPhone, trigger: "blur" },
        ],
        newVerCode: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { len: 6, message: "请输入正确验证码", trigger: "blur" },
        ],
        passwd: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { validator: checkPasswd, trigger: "blur" },
        ],
        newPasswd: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { validator: checkNewPasswd, trigger: "blur" },
        ],
        rePasswd: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          { validator: checkRePasswd, trigger: "blur" },
        ],
      },
      cacheImageUrl: "",
      changingItem: "",
      btnContainer: ["发送验证码", "发送验证码"],
      isSend: [false, false],
      btnDisabled: [true, true],
      dialogVisible: false,
      lastItemName: "",
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log("返回保存的头像url");
      this.newUserInfo.avatar = res.imgUrl;
      this.cacheImageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const correctType =
        file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!correctType) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return correctType && isLt2M;
    },
    refreshName() {
      this.$refs["nameform"].resetFields();
      this.changingItem = "";
    },
    refreshPhone() {
      this.$refs["phoneform"].resetFields();
      this.changingItem = "";
    },
    reGetVerificationCode(key) {
      this.$set(this.btnContainer, key, 60);
      this.$set(this.isSend, key, true);
      this.$set(this.btnDisabled, key, true);
      this.changeBtnText(key);
      console.log("验证码发送");
    },
    changeBtnText(key) {
      setTimeout(() => {
        if (this.btnContainer[key] == "发送验证码") {
          this.$set(this.btnContainer, key, 59);
          this.changeBtnText(key);
        } else {
          this.$set(this.btnContainer, key, this.btnContainer[key] - 1);
          if (this.btnContainer[key] != -1) {
            this.changeBtnText(key);
          } else {
            this.$set(this.btnContainer, key, "发送验证码");
            this.$set(this.isSend, key, false);
            this.verifyPhone(this.newUserInfo.oldPhone, key);
          }
        }
      }, 1000);
    },
    verifyPhone(newValue, key) {
      var pattern =
        /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (this.isSend[key] == false && pattern.test(newValue)) {
        this.$set(this.btnDisabled, key, false);
      } else {
        this.$set(this.btnDisabled, key, true);
      }
    },
    submitPhone() {
      this.$refs.phoneform.validate((valid) => {
        if (valid) {
          console.log("提交新手机号表单");
          // if success
          console.log("刷新userInfo");
          this.refreshPhone();
        } else {
          return false;
        }
      });
    },
    submitAvatar() {
      console.log("提交新头像");
    },
    refreshAvatar() {
      this.newUserInfo.avatar = "";
      this.cacheImageUrl = this.userInfo.avatarUrl;
    },
    refreshPasswd() {
      this.$refs["passwdform"].resetFields();
      this.changingItem = "";
    },
    refreshForm() {
      this.dialogVisible = false;
      if (this.lastItemName == "phone") {
        this.refreshPhone();
      } else if (this.lastItemName == "passwd") {
        this.refreshPasswd();
      } else if (this.lastItemName == "name") {
        this.refreshName();
      } else if (this.lastItemName == "avatar") {
        this.refreshAvatar();
      }
    },
    resetChangingItem() {
      this.dialogVisible = false;
      this.changingItem = this.lastItemName;
    },
    submitPasswd() {
      this.$refs.passwdform.validate((valid) => {
        if (valid) {
          console.log("提交新密码表单");
          // if success
          this.refreshPasswd();
        } else {
          return false;
        }
      });
    },
  },
  mounted() {
    this.cacheImageUrl = this.userInfo.avatarUrl;
  },
  watch: {
    changingItem: {
      handler(newValue, oldValue) {
        if (newValue != "" && oldValue != "" && newValue != this.lastItemName) {
          this.dialogVisible = true;
          this.lastItemName = oldValue;
        }
      },
    },
    "newUserInfo.oldPhone": {
      handler(newValue) {
        this.verifyPhone(newValue, 0);
      },
      deep: true,
      immediate: true,
    },
    "newUserInfo.newPhone": {
      handler(newValue) {
        this.verifyPhone(newValue, 1);
      },
      deep: true,
      immediate: true,
    },
  },
};
</script>

<style scoped>
.title {
  font-size: 18px;
  margin-left: 10px;
}
.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}
.form {
  width: 580px;
  padding-right: 30%;
  margin: auto;
  margin-top: 50px;
}
.el-form-item {
  margin: 50px 0;
}
.form-item .el-input {
  width: 320px;
}
.form-item-verification .el-input {
  width: 248px;
}
.verification-btn {
  width: 112px;
  margin-left: 60px;
}
.input-item-icon-group {
  margin-left: 10px;
  display: inline-block;
}

/* avatar */
.avatar-uploader {
  height: 180px;
}
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
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-icon-group {
  float: right;
  margin-top: -40px;
}
</style>
