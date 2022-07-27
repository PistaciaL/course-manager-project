<template>
  <div class="aside" ref="aside">
    <el-menu
      class="el-menu-vertical-demo"
      router
      @open="handleOpen"
      @close="handleClose"
      :default-active="defaultSelect"
    >
      <template v-for="item in operationItems">
        <el-menu-item
          :index="item.url"
          :key="item.name"
          v-if="item.children == null"
          >{{ item.name }}</el-menu-item
        >
        <el-submenu
          :index="item.url"
          :key="item.name"
          v-else-if="item.children != null"
        >
          <template v-slot:title>
            <span>{{ item.name }}</span>
          </template>

          <el-menu-item
            :index="item.url + child.url"
            v-for="child in item.children"
            :key="child.name"
            >{{ child.name }}</el-menu-item
          >
        </el-submenu>
      </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      allOperationItems: [
        {
          name: "我的通知",
          identity: ["学生", "教师"],
          permission: ["院级管理员", "校级管理员"],
          url: "/back/notice",
        },
        {
          name: "个人资料",
          identity: ["学生", "教师"],
          permission: ["院级管理员", "校级管理员"],
          url: "/back/info",
        },
        {
          name: "我的课程",
          identity: ["学生", "教师"],
          url: "/back/course",
        },
        {
          name: "选课系统",
          identity: ["学生"],
          permission: ["校级管理员"],
          url: "/back/select",
          children: [
            {
              name: "全校课程查询",
              identity: ["学生"],
              permission: ["校级管理员"],
              url: "/totalcourse",
            },
            { name: "我的选课", identity: ["学生"], url: "/myselect" },
            { name: "我的退课", identity: ["学生"], url: "/mywithdraw" },
            { name: "我的补选", identity: ["学生"], url: "/mysupplementary" },
            {
              name: "选课系统管理",
              permission: ["校级管理员"],
              url: "/manager",
            },
          ],
        },
        {
          name: "我的考试",
          identity: ["学生", "教师"],
          url: "/back/exam",
        },
        {
          name: "学业指导计划",
          identity: ["学生"],
          permission: ["院级管理员"],
          src: "",
          url: "/back/guide",
          children: [
            { name: "我的学业指导计划", identity: ["学生"], url: "/myguide" },
            {
              name: "学业指导计划管理",
              permission: ["院级管理员"],
              url: "/manager",
            },
          ],
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
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/usermng",
        },
        {
          name: "教室管理",
          identity: [],
          permission: ["校级管理员"],
          src: "",
          url: "/back/roommng",
        },
        {
          name: "课程管理",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/coursemng",
        },
        {
          name: "学期管理",
          identity: [],
          permission: ["校级管理员"],
          src: "",
          url: "/back/termmng",
        },
        {
          name: "科目管理",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/subjectmng",
        },
        {
          name: "学院及专业",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/collegemng",
        },
        {
          name: "统计日志",
          identity: [],
          permission: ["院级管理员", "校级管理员"],
          src: "",
          url: "/back/totalmng",
        },
      ],
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
    operationItems() {
      var items = new Array();
      for (const key in this.allOperationItems) {
        const item = this.allOperationItems[key];
        var isAdd = false;
        for (const key2 in item.identity) {
          if (item.identity[key2] == this.userInfo.identity) {
            if(item.children!=null){
              const children = item.children;
              var newItem = {};
              for(const itemKey in item){
                newItem[itemKey]=item[itemKey]
              }
              newItem.children=new Array()
              for(const key3 in children){
                const item2 = children[key3]
                var isAdd2 = false
                for(const key4 in item2.identity){
                  if(item2.identity[key4]==this.userInfo.identity){
                    newItem.children.push(item2);
                    isAdd2 = true
                    break;
                  }
                }
                if(!isAdd2){
                  for(const key4 in item2.permission){
                    if(item2.permission[key4]==this.userInfo.permission){
                      newItem.children.push(item2);
                      break;
                    }
                  }
                }
              }
              items.push(newItem)
            } else {
            items.push(item)
            }
            isAdd = true;
            break;
          }
        }
        if (!isAdd) {
          for (const key2 in item.permission) {
            if (item.permission[key2] == this.userInfo.permission) {
              if(item.children!=null){
              const children = item.children;
              var newItem = {};
              for(const itemKey in item){
                newItem[itemKey]=item[itemKey]
              }
              newItem.children=new Array()
              for(const key3 in children){
                const item2 = children[key3]
                var isAdd2 = false
                for(const key4 in item2.identity){
                  if(item2.identity[key4]==this.userInfo.identity){
                    newItem.children.push(item2);
                    isAdd2 = true
                    break;
                  }
                }
                if(!isAdd2){
                  for(const key4 in item2.permission){
                    if(item2.permission[key4]==this.userInfo.permission){
                      newItem.children.push(item2);
                      break;
                    }
                  }
                }
              }
              items.push(newItem)
            } else {
              items.push(item);
            }
              break;
            }
          }
        }
      }
      return items;
    },
    defaultSelect(){
      return this.$router.currentRoute.fullPath;
    }
  },
  mounted() {
    this.$emit("changeAsideHeight", this.$refs.aside.offsetHeight);
  },
  methods: {
    handleOpen() {
      var that = this;
      setTimeout(function () {
        that.$emit("changeAsideHeight", that.$refs.aside.offsetHeight);
      }, 350);
    },
    handleClose() {
      var that = this;
      setTimeout(function () {
        that.$emit("changeAsideHeight", that.$refs.aside.offsetHeight);
      }, 350);
    },
  },
};
</script>

<style scoped>
.aside {
  overflow: hidden;
}
</style>