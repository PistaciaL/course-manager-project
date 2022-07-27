<template>
  <div class="container">
    <div class="title">全校课程查询</div>
    <el-divider></el-divider>
    <div class="search-group">
      <div class="search-item">
        <span>科目名称：</span
        ><el-select
          v-model="searchForm.subjectId"
          filterable
          placeholder=""
          :remote-method="searchSubject"
          remote
          v-el-select-loadmore="loadSubject"
          :loading="subjectLoading"
          size="small"
          :clearable="true"
        >
          <el-option
            v-for="subject in subjects"
            :key="subject.id"
            :label="subject.name"
            :value="subject.id"
          >
          </el-option>
        </el-select>
      </div>
      <div class="search-item">
        <span>开设学院：</span
        ><el-select
          v-model="searchForm.collegeId"
          placeholder=""
          filterable
          size="small"
          :clearable="true"
        >
          <el-option
            v-for="college in colleges"
            :key="college.id"
            :label="college.name"
            :value="college.id"
          >
          </el-option>
        </el-select>
      </div>
      <div class="search-item">
        <span>任课教师：</span
        ><el-select
          v-model="searchForm.teacherId"
          filterable
          placeholder=""
          :remote-method="searchTeacher"
          remote
          allow-create
          default-first-option
          v-el-select-loadmore="loadTeacher"
          :loading="teacherLoading"
          size="small"
          :clearable="true"
        >
          <el-option
            v-for="teacher in teachers"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          >
          </el-option>
        </el-select>
      </div>
      <div class="search-item">
        <span>开课学期：</span
        ><el-select
          v-model="searchForm.termId"
          placeholder=""
          filterable
          size="small"
          :clearable="true"
        >
          <el-option
            v-for="term in terms"
            :key="term.id"
            :label="term.name"
            :value="term.id"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="table-container">
      <el-table :data="courses" style="width: 100%">
        <el-table-column prop="id" label="课程序号" min-width="100">
        </el-table-column>
        <el-table-column label="科目名称" min-width="210">
          <div
            slot-scope="scope"
            @click="
              searchBy('subject', {
                id: scope.row.subjectId,
                name: scope.row.subject,
              })
            "
          >
            {{ scope.row.subject }}
          </div>
        </el-table-column>
        <el-table-column label="开课学院" min-width="200">
          <div
            slot-scope="scope"
            @click="
              searchBy('college', {
                id: scope.row.collegeId,
                name: scope.row.college,
              })
            "
          >
            {{ scope.row.college }}
          </div>
        </el-table-column>
        <el-table-column label="任课教师" min-width="150">
          <div
            slot-scope="scope"
            @click="
              searchBy('teacher', {
                id: scope.row.teacherId,
                name: scope.row.teacher,
              })
            "
          >
            {{ scope.row.teacher }}
          </div>
        </el-table-column>
        <el-table-column label="开课学期" min-width="200">
          <div
            slot-scope="scope"
            @click="
              searchBy('term', { id: scope.row.termId, name: scope.row.term })
            "
          >
            {{ scope.row.term }}
          </div>
        </el-table-column>
        <el-table-column label="学生人数" min-width="100">
          <div slot-scope="scope">
            {{ scope.row.studentNumb }}/{{ scope.row.totalNumb }}
          </div>
        </el-table-column>
      </el-table>
      <div class="page-divider">
        <el-pagination
          layout="prev, pager, next"
          :page-count="pageInfo.totalPage"
          :page-size="pageInfo.pageSize"
          :current-page.sync="pageInfo.currentPage"
          :hide-on-single-page="true"
          @current-change="changePage"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
var that;
export default {
  directives: {
    "el-select-loadmore": {
      bind(el, binding) {
        // 获取element-ui定义好的scroll盒子
        const SELECTWRAP_DOM = el.querySelector(
          ".el-select-dropdown .el-select-dropdown__wrap"
        );
        SELECTWRAP_DOM.addEventListener("scroll", function () {
          /**
           * scrollHeight 获取元素内容高度(只读)
           * scrollTop 获取或者设置元素的偏移值,常用于, 计算滚动条的位置, 当一个元素的容器没有产生垂直方向的滚动条, 那它的scrollTop的值默认为0.
           * clientHeight 读取元素的可见高度(只读)
           * 如果元素滚动到底, 下面等式返回true, 没有则返回false:
           * ele.scrollHeight - ele.scrollTop === ele.clientHeight;
           */
          const condition =
            this.scrollHeight - this.scrollTop <= this.clientHeight;
          if (condition) {
            if (
              binding.expression == "loadTeacher" &&
              that.teacherSearchInfo.currentPage <=
                that.teacherSearchInfo.totalPage
            ) {
              binding.value();
            } else if (
              binding.expression == "loadSubject" &&
              that.subjectSearchInfo.currentPage <=
                that.subjectSearchInfo.totalPage
            ) {
              binding.value();
            }
          }
        });
      },
    },
  },
  data() {
    return {
      searchForm: {
        collegeId: "",
        teacherId: "",
        subjectId: "",
        termId: "",
      },
      teacherSearchInfo: { input: "", currentPage: 1, totalPage: 10 },
      subjectSearchInfo: { input: "", currentPage: 1, totalPage: 10 },
      colleges: [
        { id: 1, name: "计算机与科学学院" },
        { id: 2, name: "软件学院" },
        { id: 3, name: "微电子学院" },
        { id: 4, name: "数学与统计学院" },
      ],
      teachers: [],
      staticTeachers: [
        { id: 123, name: "教师一" },
        { id: 321, name: "教师二" },
        { id: 312, name: "教师三" },
        { id: 1123, name: "教师一" },
        { id: 321231, name: "教师二" },
        { id: 24, name: "教师三" },
        { id: 12342323, name: "教师一" },
        { id: 23423, name: "教师二" },
        { id: 35435312, name: "教师三" },
      ],
      subjects: [],
      staticSubjects: [
        { id: 1, name: "科目1" },
        { id: 2, name: "科目2" },
        { id: 3, name: "科目3" },
        { id: 4, name: "科目4" },
        { id: 5, name: "科目5" },
        { id: 6, name: "科目6" },
        { id: 11, name: "科目11" },
        { id: 12, name: "科目12" },
        { id: 13, name: "科目13" },
        { id: 14, name: "科目14" },
        { id: 15, name: "科目15" },
        { id: 16, name: "科目16" },
      ],
      terms: [
        { id: 1, name: "2020年春学期" },
        { id: 2, name: "2020年秋学期" },
        { id: 3, name: "2021年春学期" },
        { id: 4, name: "2021年秋学期" },
        { id: 5, name: "2022年春学期" },
        { id: 6, name: "2022年秋学期" },
      ],
      teacherLoading: false,
      subjectLoading: false,
      courses: [
        {
          id: 1312,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          termId: 1,
          term: "2022年秋学期",
          studentNumb: 30,
          totalNumb: 50,
        },
        {
          id: 13121,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          termId: 1,
          term: "2022年秋学期",
          studentNumb: 40,
          totalNumb: 100,
        },
        {
          id: 1312,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          termId: 1,
          term: "2022年秋学期",
          studentNumb: 100,
          totalNumb: 100,
        },
        {
          id: 1312,
          subjectId: 12122,
          subject: "面向对象的程序与设计",
          collegeId: 2,
          college: "软件学院",
          teacherId: 12111,
          teacher: "教师二",
          termId: 2,
          term: "2022年春学期",
          studentNumb: 0,
          totalNumb: 100,
        },
        {
          id: 1312,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          termId: 1,
          term: "2022年秋学期",
          studentNumb: 100,
          totalNumb: 100,
        },
      ],
      pageInfo: {
        currentPage: 4,
        totalPage: 10,
        pageSize: 20,
      },
    };
  },
  mounted() {
    console.log("加载学院列表");
    console.log("加载学期列表");
    that = this;
    this.searchTeacher("");
    this.searchSubject("");
  },
  methods: {
    searchTeacher(query) {
      this.teacherLoading = true;
      this.teacherSearchInfo = { input: "", currentPage: 0, totalPage: 10 };
      this.teacherSearchInfo.input = query;
      console.log("根据选择的学院远程搜索教师");
      setTimeout(() => {
        this.teacherLoading = false;
        this.teachers = this.staticTeachers.filter((item) => {
          return item.name.indexOf(query) > -1;
        });
      }, 1000);
    },
    loadTeacher() {
      console.log("输入的关键字" + this.teacherSearchInfo.input);
      this.teachers.push({
        id: "000" + this.teacherSearchInfo.currentPage,
        name: this.teacherSearchInfo.input + this.teacherSearchInfo.currentPage,
      });
      this.teacherSearchInfo.currentPage += 1;
    },
    searchSubject(query) {
      this.subjectLoading = true;
      this.subjectSearchInfo = { input: "", currentPage: 0, totalPage: 10 };
      this.subjectSearchInfo.input = query;
      console.log("根据选择的学院远程搜索教师");
      setTimeout(() => {
        this.subjectLoading = false;
        this.subjects = this.staticSubjects.filter((item) => {
          return item.name.indexOf(query) > -1;
        });
      }, 1000);
    },
    loadSubject() {
      console.log("输入的关键字" + this.subjectSearchInfo.input);
      this.subjects.push({
        id: "000" + this.subjectSearchInfo.currentPage,
        name: this.subjectSearchInfo.input + this.subjectSearchInfo.currentPage,
      });
      this.subjectSearchInfo.currentPage += 1;
    },
    searchBy(name, data) {
      for (const key in this.searchForm) {
        this.searchForm[key] = "";
      }
      if (name == "teacher") {
        this.teachers = [{ id: data.id, name: data.name }];
      } else if (name == "college") {
        this.colleges = [{ id: data.id, name: data.name }];
      } else if (name == "term") {
        this.terms = [{ id: data.id, name: data.name }];
      } else if (name == "subject") {
        this.subjects = [{ id: data.id, name: data.name }];
      }
      this.$set(this.searchForm, name + "Id", data.id);
    },
    changePage(newPage) {
      console.log(newPage);
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
.search-group {
  padding: 0 20px;
  display: flex;
  justify-content: space-around;
}
.search-group span {
  padding: 0 10px;
}
.search-item + .search-item {
  margin-left: 20px;
  display: flex;
  align-items: center;
}

.table-container {
  margin-top: 30px;
}
</style>
