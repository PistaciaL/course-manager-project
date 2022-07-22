<template>
  <div class="operation-content content">
    <div class="content-header">
      <span>快速入口</span>
    </div>
    <el-divider></el-divider>
    <div class="operation-content-body">
      <div
        class="operation-item"
        v-for="item in showOperationItems"
        :key="item.name"
      >
        <el-image :src="item.url"></el-image>
        <span>{{ item.name }}</span>
      </div>
    </div>
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
          url: "",
        },
        {
          name: "个人信息",
          identity: ["学生", "教师"],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
        },

        {
          name: "我的学业",
          identity: ["学生"],
          permission: [],
          src: "",
          url: "",
        },
        {
          name: "我的选课",
          identity: ["学生"],
          permission: [],
          src: "",
          url: "",
        },
        {
          name: "我的考试",
          identity: ["学生"],
          permission: [],
          src: "",
          url: "",
        },
        {
          name: "学业指导计划",
          identity: ["学生"],
          permission: ["院级管理员"],
          src: "",
          url: "",
        },

        {
          name: "我的课程",
          identity: ["教师"],
          permission: [],
          src: "",
          url: "",
        },
        {
          name: "申请处理",
          identity: ["教师"],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
        },

        {
          name: "用户管理",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "学工号管理",
          identity: [],
          permission: ["校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "教室管理",
          identity: [],
          permission: ["校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "课程管理",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "学期管理",
          identity: [],
          permission: ["校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "科目管理",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "选课系统管理",
          identity: [],
          permission: ["校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "学院及专业",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
        },
        {
          name: "统计日志",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "",
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
        var item = this.allOperationItems[key];
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
        items.length % 6 == 0 ? items.length / 6 : items.length / 6 + 1;
      this.showOperationItems = items.slice(
        (page - 1) * pageSize,
        page * pageSize
      );
      this.page = page;
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