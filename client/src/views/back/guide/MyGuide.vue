<template>
  <div class="container">
    <div class="title">
      我的学业指导计划
      <div>
        <span>已修读学分： {{ totalCredit }} / {{ needCredit }}</span
        ><span>计划外学分： {{ extraCredit }}</span>
        <el-link
          href="/rules/guide"
          target="_blank"
          class="el-icon-warning-outline"
          >学业指导计划说明</el-link
        >
      </div>
    </div>
    <el-divider></el-divider>
    <div class="table-container">
      <div class="fold-btn-group">
        <el-tooltip class="item" effect="dark" content="展开全部" placement="top" v-show="allFold">
          <el-button icon="el-icon-s-unfold" circle @click="foldAll(true)"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="折叠全部" placement="top" v-show="!allFold">
          <el-button icon="el-icon-s-fold" circle @click="foldAll(false)"></el-button>
        </el-tooltip>
      </div>
      <table class="outer-table" cellspacing="0px">
        <thead>
          <th style="width: 240px">开课学院</th>
          <th>已修读学分</th>
          <th>计划外学分</th>
          <th>状态</th>
        </thead>
        <tbody>
          <template v-for="college in items">
            <tr
              :key="college.collegeName"
              @click="college.isShow = !college.isShow"
              class="header-tr"
            >
              <td>
                <i
                  :class="
                    college.isShow
                      ? 'el-icon-arrow-down'
                      : 'el-icon-arrow-right'
                  "
                ></i
                >{{ college.collegeName }}
              </td>
              <td style="text-align: center">
                {{ college.totalCredit }} / {{ college.needCredit }}
              </td>
              <td style="text-align: center">{{ college.extraCredit }}</td>
              <td style="text-align: center">
                <i
                  class="el-icon-check"
                  style="color: #67c23a"
                  v-if="
                    college.needCredit == college.totalCredit &&
                    college.needCredit != 0
                  "
                ></i>
                <i
                  class="el-icon-close"
                  style="color: #f56c6c"
                  v-else-if="college.needCredit != 0"
                ></i>
                <i class="el-icon-minus" style="color: #e6a23c" v-else></i>
              </td>
            </tr>
            <tr :key="'div' + college.collegeName" v-show="college.isShow">
              <td :colspan="999" class="inner-table-container">
                <table class="inner-table" cellspacing="0px">
                  <thead>
                    <th style="width: 200px">科目名称</th>
                    <th style="width: 120px">学分</th>
                    <th style="width: 120px">总成绩</th>
                    <th style="width: 120px">状态</th>
                    <th style="width: 200px"></th>
                  </thead>
                  <tbody>
                    <tr
                      v-for="course in college.courses"
                      :key="course.subjectName"
                    >
                      <td>{{ course.subjectName }}</td>
                      <td style="text-align: center">{{ course.credit }}</td>
                      <td style="text-align: center">{{ course.mark!=''?course.mark:'——' }}</td>
                      <td style="text-align: center">
                        <el-tag type="info" v-if="course.mark == null"
                          >未修读</el-tag
                        >
                        <el-tag type="warning" v-else-if="course.mark == ''"
                          >正在修读中</el-tag
                        >
                        <el-tag type="danger" v-else-if="course.mark < 60"
                          >未通过</el-tag
                        >
                        <el-tag type="success" v-else>已修读</el-tag>
                      </td>
                      <td style="text-align: center"><el-tag v-if="course.isExtra">计划外课程</el-tag></td>
                    </tr>
                  </tbody>
                </table>
              </td>
            </tr>
          </template>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      totalCredit: 0,
      needCredit: 0,
      extraCredit: 0,
      guides: [
        { subjectName: "离散数学", credit: 3, collegeName: "数学与统计学院" },
        { subjectName: "高等数学", credit: 4, collegeName: "数学与统计学院" },
        { subjectName: "大学物理", credit: 3.5, collegeName: "物理学院" },
        {
          subjectName: "马克思主义哲学原理",
          credit: 4,
          collegeName: "人文学院",
        },
      ],
      courses: [
        {
          subjectName: "离散数学",
          mark: 50,
          credit: 3,
          collegeName: "数学与统计学院",
        },
        {
          subjectName: "线性代数",
          mark: '',
          credit: 3,
          collegeName: "数学与统计学院",
        },
        {
          subjectName: "马克思主义哲学原理",
          credit: 4,
          collegeName: "人文学院",
          mark:'98'
        },
        {
          subjectName: "高等数学",
          mark: 90,
          credit: 4,
          collegeName: "数学与统计学院",
        },
        {
          subjectName: "面向对象的程序与设计",
          mark: 92,
          credit: 4,
          collegeName: "软件学院",
        },
        {
          subjectName: "C语言程序与基础",
          mark: "",
          credit: 3,
          collegeName: "计算机科学与技术学院",
        },
      ],
      items: [],
    };
  },
  computed:{
    allFold(){
      for(const key in this.items){
        if(this.items[key].isShow==true){
          return false
        }
      }
      return true
    }
  },
  mounted() {
    console.log("获取指导计划表");
    console.log("获取已修读课程表");
    this.items = this.calItems();
    for (const key in this.items) {
      this.totalCredit += this.items[key].totalCredit;
      this.needCredit += this.items[key].needCredit;
      this.extraCredit += this.items[key].extraCredit;
    }
  },
  methods: {
    foldAll(b){
      for(const key in this.items){
        this.items[key].isShow=b
      }
    },
    calItems() {
      var items = new Array();
      for (const gKey in this.guides) {
        var item = this.getCollegeItem(items, this.guides[gKey].collegeName);
        item.needCredit += this.guides[gKey].credit;
        item.courses.push({
          subjectName: this.guides[gKey].subjectName,
          credit: this.guides[gKey].credit,
          isGuide: true,
        });
      }
      for (const cKey in this.courses) {
        var college = this.getCollegeItem(
          items,
          this.courses[cKey].collegeName
        );
        var course = this.getCourseItem(
          college.courses,
          this.courses[cKey].subjectName
        );
        if (course == null) {
          college.extraCredit += this.courses[cKey].credit;
          college.courses.push({
            subjectName: this.courses[cKey].subjectName,
            credit: this.courses[cKey].credit,
            mark: this.courses[cKey].mark,
            isGuide: false,
            isExtra: true
          });
        } else {
          course.mark = this.courses[cKey].mark;
          if (course.mark >= 60) {
            college.totalCredit += this.courses[cKey].credit;
          }
        }
      }
      return items;
    },
    getCollegeItem(array, name) {
      for (const key in array) {
        if (array[key].collegeName == name) {
          return array[key];
        }
      }
      array.push({
        collegeName: name,
        totalCredit: 0,
        needCredit: 0,
        extraCredit: 0,
        courses: new Array(),
        isShow: false,
      });
      return array[array.length - 1];
    },
    getCourseItem(array, name) {
      for (const key in array) {
        if (array[key].subjectName == name) {
          return array[key];
        }
      }
      return null;
    },
  },
};
</script>

<style scoped>
.title {
  font-size: 18px;
  margin-left: 10px;
  display: flex;
  justify-content: space-between;
}
.title span {
  margin-right: 100px;
}
.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}

.table-container {
  min-width: 960px;
  margin: auto;
  position: relative;
}
.fold-btn-group {
  position: absolute;
  right: 0;
  top: 20px;
}
.fold-btn-group button{
  font-size: 18px;
}

.outer-table {
  width: 100%;
}
.outer-table th {
  font-size: 14px;
  font-weight: bold;
  color: #909399;
}
.outer-table > thead th {
  height: 80px;
}
.outer-table tr td:first-child i {
  margin: 0 50px 0 20px;
}
.outer-table tr td:last-child i {
  font-size: 20px;
  font-weight: bold;
}
.header-tr {
  background: #409eff10;
}
td {
  font-weight: 400;
  font-size: 14px;
  color: #606266;
  height: 50px;
}
.outer-table > tbody > tr > td {
  border-bottom: 1px solid #e6e6e6;
}
.inner-table th {
  height: 30px;
}
.inner-table-container {
  padding: 0;
}
.inner-table {
  margin-left: 100px;
}
.inner-table th:first-child {
  text-align: left;
}
</style>
