<template>
  <div class="header">
    <div class="left-operation word-v-middle">
      <div class="logo" @click="gotoHome">
        THIS IS LOGO.
      </div>
    </div>
    <div class="right-operation word-v-middle">
      <div v-if="userInfo.isLogin">
      <el-dropdown @command="handleCommand">
        <div
          class="dropdown-header"
          :class="{ 'dropdown-head-mouseover': mouseOver == 'avatar' }"
          @mouseenter="setMouseOver('avatar')"
          @mouseleave="setMouseOver()"
        >
          <el-avatar :size="28" ref="avatar" :src="userInfo.avatarUrl" class="avatar">
            {{userInfo.userName.slice(0,2)}}
          </el-avatar>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>操作1</el-dropdown-item>
          <el-dropdown-item>操作2</el-dropdown-item>
          <el-dropdown-item>操作3</el-dropdown-item>
          <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div v-else>
      <a href="/login">登录</a>
      <el-divider direction="vertical"></el-divider>
      <a href="/register">注册</a>
    </div>
    </div>
    
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      mouseOver: "",
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  methods: {
    setMouseOver(str) {
      this.mouseOver = str;
    },
    gotoHome(){
      this.$router.push('/')
    },
    logout(){
      console.log('logout')
      this.$store.state.userInfo = {isLogin: false}
    },
    handleCommand(command){
      if(command=='logout'){
        this.logout()
      } else {
        console.log(command)
      }
    }
  },
};
</script>

<style scoped>
.left-operation{
  float: left;
}
.right-operation {
  float: right;
}
.header {
  margin-left: 50px;
  margin-right: 50px;
}
.avatar-instead {
  font-size: 28px;
}
.dropdown-header {
  height: 38px;
  padding: 0 20px;
}
.dropdown-head-mouseover {
  background-color: whitesmoke;
}
.avatar{
  display: inline-block;
  margin-top: 5px;
  font-size: 14px;
  text-align: center;
}
.logo{
  user-select: none;
}
</style>