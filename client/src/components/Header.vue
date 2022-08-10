<template>
  <div class="header">
    <div class="left-operation word-v-middle">
      <div class="logo" @click="gotoHomeOrBack">
        <h1 style="color: #409eff">LOGO</h1>
      </div>
    </div>
    <div class="right-flex">
      <div
        class="operation-item"
        :class="{ 'operation-item-mouseover': mouseOver == 'notice' }"
        @mouseenter="setMouseOver('notice')"
        @mouseleave="setMouseOver()"
      >
        <el-dropdown @command="handleNoticeCommand" placement="bottom">
          <div class="operation-item-hover">
            <el-badge
              :value="noticeNumb"
              class="notice-dot"
              :max="99"
              :hidden="noticeNumb == 0"
            >
              <i class="el-icon-bell notice-icon"></i>
            </el-badge>
          </div>
          <el-dropdown-menu class="notice-dropdown-menu">
            <el-dropdown-item
              v-for="notice in notices"
              :key="notice.id"
              class="primary-dropdown-item"
              :command="notice.id"
            >
              <span class="el-icon-warning-outline">{{ notice.type }}</span
              ><el-divider direction="vertical"></el-divider
              ><span>{{ notice.content }}</span>
            </el-dropdown-item>
            <el-dropdown-item
              divided
              command="moreNotice"
              class="divided-dropdown-item"
              >更多通知</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div
        class="operation-item"
        :class="{ 'operation-item-mouseover': mouseOver == 'avatar' }"
        @mouseenter="setMouseOver('avatar')"
        @mouseleave="setMouseOver()"
      >
        <el-dropdown @command="handleAvatarCommand" placement="bottom-start">
          <div class="operation-item-hover">
            <el-avatar
              :size="28"
              ref="avatar"
              :src="MyUtils.avatarUrl(userInfo('avatarUrl'))"
              class="avatar"
            >
              {{ userInfo('name').slice(0, 2) }}
            </el-avatar>
          </div>

          <el-dropdown-menu>
            <el-dropdown-item command="myInfo">我的资料</el-dropdown-item>
            <el-dropdown-item divided command="logout"
              >退出登录</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
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
      noticeNumb: 100,
      notices: [
        { id: 1, type: "选课", content: "xxx希望选择您的xx课程" },
        { id: 2, type: "退课", content: "xxx希望退选您的xx课程" },
        { id: 3, type: "退课", content: "xxx希望退选您的xx课程" },
        { id: 4, type: "选课", content: "xxx希望选择您的xx课程" },
      ],
    };
  },
  mounted() {
    this.$webSocket.initWebSocket(this.userInfo("token"))
  },
  methods: {
    userInfo(str) {
      return localStorage.getItem(str);
    },
    setMouseOver(str) {
      this.mouseOver = str;
    },
    gotoHomeOrBack() {
      if (this.$router.currentRoute.fullPath == "/") {
        this.$router.push("/back");
      } else {
        this.$router.push("/");
      }
    },
    logout() {
      let phone = localStorage.getItem("phone")
      let id = localStorage.getItem('workNumb')
      localStorage.clear();
      localStorage.setItem('workNumb', id)
      localStorage.setItem('phone', phone.substring(0, 3) + '****' + phone.substring(7))
      this.$router.push("/login");
    },
    handleAvatarCommand(command) {
      if (command == "logout") {
        this.logout();
      } else if (command == "myInfo") {
        this.$router.push("/back/info");
      } else {
        console.log(command);
      }
    },
    handleNoticeCommand(command) {
      if (command == "moreNotice") {
        this.$router.push("/back/notice");
      }
      console.log(command);
    },
  },
};
</script>

<style scoped>
.left-operation {
  float: left;
}

.header {
  padding-right: 40px;
  height: 100%;
}

.avatar-instead {
  font-size: 24px;
}

.dropdown-header {
  height: 38px;
  padding: 0 25px;
}

.operation-item-mouseover {
  background-color: #f4f4f4;
}

.avatar {
  display: inline-block;
  font-size: 14px;
  text-align: center;
}

.logo {
  user-select: none;
  width: 200px;
  text-align: center;
}
.notice-icon {
  font-size: 24px;
}
.right-flex {
  display: flex;
  justify-content: flex-end;
  height: 100%;
}
.operation-item {
  width: 80px;
  margin: 0 3px;
  height: 100%;
}
.notice-dot {
  font-size: 10px;
  margin-top: 1px;
  padding-right: 5px;
}
.notice-dropdown-menu {
  width: 300px;
}
.primary-dropdown-item span:first-child {
  color: #c0c4cc;
}
.operation-item-hover {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.el-dropdown {
  height: 100%;
  width: 100%;
}
.divided-dropdown-item {
  text-align: center;
}
</style>
