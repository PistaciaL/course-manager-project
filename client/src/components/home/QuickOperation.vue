<template>
  <div class="operation-content content">
    <div class="content-header">
      <span>快速入口</span>
    </div>
    <el-divider></el-divider>
    <transition name="el-zoom-in-top">
      <div class="operation-content-body">
        <div
          class="operation-item"
          v-for="item in showOperationItems"
          :key="item.name"
          @click="goto(item.url)"
        >
          <el-image :src="item.src"></el-image>
          <span>{{ item.name }}</span>
        </div>
        <div class="operation-item" v-for="i in 6-showOperationItems.length" :key="i"></div>
      </div>
    </transition>
    <div class="operation-content-footer btn-group">
      <el-button
        circle
        size="mini"
        :type="n == page ? 'primary' : ''"
        v-for="n in totalPage"
        :key="n"
        @click="changePage(n)"
      ></el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      allOperationItems: [
        {
          name: "我的通知",
          identity: ["学生", "教师"],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/notice",
        },
        {
          name: "个人资料",
          identity: ["学生", "教师"],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/info",
        },
        {
          name: "我的课程",
          identity: ["学生", "教师"],
          src: "",
          url: "/back/course",
        },
        {
          name: "我的选课",
          identity: ["学生"],
          src: "",
          url: "/back/select",
        },
        {
          name: "我的考试",
          identity: ["学生", "教师"],
          src: "",
          url: "/back/exam",
        },
        {
          name: "学业指导计划",
          identity: ["学生"],
          permission: ["院级管理员"],
          src: "",
          url: "/back/guide",
        },
        {
          name: "我的申请",
          identity: ["教师"],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/apply",
        },
        {
          name: "用户管理",
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/usermng",
        },
        {
          name: "教室管理",
          permission: ["校级管理员"],
          src: "",
          url: "/back/roommng",
        },
        {
          name: "课程管理",
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/coursemng",
        },
        {
          name: "学期管理",
          permission: ["校级管理员"],
          src: "",
          url: "/back/termmng",
        },
        {
          name: "科目管理",
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/subjectmng",
        },
        {
          name: "选课系统管理",
          permission: ["校级管理员"],
          src: "",
          url: "/back/selectmng",
        },
        {
          name: "学院及专业",
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/collegemng",
        },
        {
          name: "统计日志",
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/totalmng",
        },
      ],
      showOperationItems: [],
      totalPage: 1,
      page: 1,
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  mounted() {
    this.changePage(1);
  },
  methods: {
    changePage(page) {
      const pageSize = 6;
      var items = new Array();
      for (const key in this.allOperationItems) {
        const item = this.allOperationItems[key];
        var isAdd = false;
        for (const key2 in item.identity) {
          if (item.identity[key2] == this.userInfo.identity) {
            items.push(item);
            isAdd = true;
            break;
          }
        }
        if (!isAdd) {
          for (const key2 in item.permission) {
            if (item.permission[key2] == this.userInfo.permission) {
              items.push(item);
              break;
            }
          }
        }
      }
      this.totalPage =
        items.length % 6 == 0
          ? items.length / 6
          : parseInt(items.length / 6) + 1;
      this.showOperationItems = items.slice(
        (page - 1) * pageSize,
        page * pageSize
      );
      this.page = page;
    },
    goto(url) {
      this.$router.push(url);
    },
  },
};
</script>

<style scoped>
.content-header {
  height: 29px;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  font-size: 24px;
}
.el-divider {
  margin: 0;
}

.el-image {
  width: 60px;
  height: 60px;
}
.operation-content-body {
  display: flex;
  padding: 20px;
  flex-wrap: wrap;
  justify-content: space-between;
}
.operation-item {
  width: 85px;
  height: 80px;
  margin: 15px 15px;
  font-size: 14px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.operation-content-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30px;
}
</style>
