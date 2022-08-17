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
          :loading="loading.subjectLoading"
          size="small"
          :clearable="true"
          @clear="clearSubject"
        >
          <el-option
            v-for="subject in subjectSearchInfo.data"
            :key="subject.id"
            :label="subject.name"
            :value="subject.id"
          >
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align: center"
          >
            <div
              v-show="
                subjectSearchInfo.currentPage < subjectSearchInfo.totalPage
              "
            >
              <i class="el-icon-loading" /><span value="">加载中</span>
            </div>
            <div
              v-show="
                subjectSearchInfo.currentPage >= subjectSearchInfo.totalPage
              "
            >
              <span value="">已经全部加载完毕</span>
            </div>
          </el-option>
        </el-select>
      </div>
      <div class="search-item">
        <span>开设学院：</span
        ><el-select
          v-model="searchForm.collegeId"
          filterable
          placeholder=""
          :remote-method="searchCollege"
          remote
          v-el-select-loadmore="loadCollege"
          :loading="loading.collegeLoading"
          size="small"
          :clearable="true"
          @clear="clearCollege"
        >
          <el-option
            v-for="college in collegeSearchInfo.data"
            :key="college.id"
            :label="college.name"
            :value="college.id"
          >
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align: center"
          >
            <div
              v-show="
                collegeSearchInfo.currentPage < collegeSearchInfo.totalPage
              "
            >
              <i class="el-icon-loading" /><span value="">加载中</span>
            </div>
            <div
              v-show="
                collegeSearchInfo.currentPage >= collegeSearchInfo.totalPage
              "
            >
              <span value="">已经全部加载完毕</span>
            </div>
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
          v-el-select-loadmore="loadTeacher"
          :loading="loading.teacherLoading"
          size="small"
          :clearable="true"
          @clear="clearTeacher"
        >
          <el-option
            v-for="teacher in teacherSearchInfo.data"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          >
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align: center"
          >
            <div
              v-show="
                teacherSearchInfo.currentPage < teacherSearchInfo.totalPage
              "
            >
              <i class="el-icon-loading" /><span value="">加载中</span>
            </div>
            <div
              v-show="
                teacherSearchInfo.currentPage >= teacherSearchInfo.totalPage
              "
            >
              <span value="">已经全部加载完毕</span>
            </div>
          </el-option>
        </el-select>
      </div>
      <div class="search-item">
        <span>开课学期：</span
        ><el-select
          v-model="searchForm.termId"
          filterable
          placeholder=""
          :remote-method="searchTerm"
          remote
          v-el-select-loadmore="loadTerm"
          :loading="loading.termLoading"
          size="small"
          :clearable="true"
          @clear="clearTerm"
        >
          <el-option
            v-for="term in termSearchInfo.data"
            :key="term.id"
            :label="term.name"
            :value="term.id"
          >
            <span style="margin-right: 50px">{{ term.name }}</span>
            <span style="font-size: 8px; color: #909399"
              >{{ term.startDate }} - {{ term.endDate }}</span
            >
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align: center"
          >
            <div v-show="termSearchInfo.currentPage < termSearchInfo.totalPage">
              <i class="el-icon-loading" /><span value="">加载中</span>
            </div>
            <div
              v-show="termSearchInfo.currentPage >= termSearchInfo.totalPage"
            >
              <span value="">已经全部加载完毕</span>
            </div>
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="table-container">
      <el-table :data="courses" style="width: 100%">
        <el-table-column prop="courseId" label="课程序号" min-width="80">
        </el-table-column>
        <el-table-column label="科目名称" min-width="230" width="350">
          <div
            slot-scope="scope"
            @click="
              searchBy('subject', {
                id: scope.row.subjectId,
                name: scope.row.subjectName,
              })
            "
          >
            {{ scope.row.subjectName }}
          </div>
        </el-table-column>
        <el-table-column label="开课学院" min-width="130">
          <div
            slot-scope="scope"
            @click="
              searchBy('college', {
                id: scope.row.collegeId,
                name: scope.row.collegeName,
              })
            "
          >
            {{ scope.row.collegeName }}
          </div>
        </el-table-column>
        <el-table-column label="任课教师" min-width="100">
          <div
            slot-scope="scope"
            @click="
              searchBy('teacher', {
                id: scope.row.teacherId,
                name: scope.row.teacherName,
              })
            "
          >
            {{ scope.row.teacherName }}
          </div>
        </el-table-column>
        <el-table-column label="开课学期" min-width="140">
          <div
            slot-scope="scope"
            @click="
              searchBy('term', {
                id: scope.row.termId,
                name: scope.row.termName,
              })
            "
          >
            {{ scope.row.termName }}
          </div>
        </el-table-column>
        <el-table-column label="学生人数" width="200">
          <div slot-scope="scope" style="text-align: center; width: 160px">
            <el-progress
              :show-text="false"
              :status="
                scope.row.studentNumb / scope.row.maxNumb > 1
                  ? 'exception'
                  : scope.row.studentNumb / scope.row.maxNumb > 0.7
                  ? 'warning'
                  : scope.row.studentNumb / scope.row.maxNumb < 0.5
                  ? 'success'
                  : null
              "
              :percentage="
                scope.row.studentNumb / scope.row.maxNumb > 1
                  ? 100
                  : (scope.row.studentNumb / scope.row.maxNumb) * 100
              "
            ></el-progress>
            <span>{{ scope.row.studentNumb }}/{{ scope.row.maxNumb }}</span>
          </div>
        </el-table-column>
        <el-table-column label="课时数" min-width="80" prop="hour" align="center"></el-table-column>
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
              that.teacherSearchInfo.currentPage <
                that.teacherSearchInfo.totalPage
            ) {
              binding.value();
            } else if (
              binding.expression == "loadSubject" &&
              that.subjectSearchInfo.currentPage <
                that.subjectSearchInfo.totalPage
            ) {
              binding.value();
            } else if (
              binding.expression == "loadCollege" &&
              that.collegeSearchInfo.currentPage <
                that.collegeSearchInfo.totalPage
            ) {
              binding.value();
            } else if (
              binding.expression == "loadTerm" &&
              that.termSearchInfo.currentPage < that.termSearchInfo.totalPage
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
        collegeId: null,
        teacherId: null,
        subjectId: null,
        termId: null,
      },
      cache: {
        term: "",
        college: "",
        teacher: "",
        subject: "",
      },
      loading: {
        teacherLoading: false,
        subjectLoading: false,
        termLoading: false,
        collegeLoading: false,
      },
      teacherSearchInfo: { data: "", currentPage: 0, totalPage: 1 },
      subjectSearchInfo: { data: "", currentPage: 0, totalPage: 1 },
      termSearchInfo: { data: "", currentPage: 0, totalPage: 1 },
      collegeSearchInfo: { data: "", currentPage: 0, totalPage: 1 },
      courses: [],
      pageInfo: {
        currentPage: 1,
        totalPage: 1,
        pageSize: 10,
      },
    };
  },
  mounted() {
    this.selectCourse(1);
    that = this;
    this.searchTerm("");
    this.searchTeacher("");
    this.searchSubject("");
    this.searchCollege("");
  },
  watch: {
    searchForm: {
      handler(value) {
        this.selectCourse(1);
      },
      deep: true,
    },
  },
  methods: {
    searchTeacher(query) {
      this.teacherSearchInfo = { data: [], currentPage: 0, totalPage: 1 };
      this.cache.teacher = query;
      this.loading.teacherLoading = true;
      this.loadTeacher();
    },
    clearTeacher(){
      this.searchTeacher()
    },
    loadTeacher() {
      const name = this.cache.teacher;
      this.axios({
        method: "get",
        url: "/user/teacher/search",
        params: {
          name: name,
          page: this.teacherSearchInfo.currentPage + 1,
          pageSize: 10,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.loading.teacherLoading = false;
          this.teacherSearchInfo.data = this.teacherSearchInfo.data.concat(
            res.data.data.data
          );
          this.teacherSearchInfo.currentPage = res.data.data.currentPage;
          this.teacherSearchInfo.totalPage = res.data.data.totalPage;
        }
      });
    },
    searchSubject(query) {
      this.subjectSearchInfo = { data: [], currentPage: 0, totalPage: 1 };
      this.cache.subject = query;
      this.loading.subjectLoading = true;
      this.loadSubject();
    },
    clearSubject(){
      this.searchSubject()
    },
    loadSubject() {
      const name = this.cache.subject;
      this.axios({
        method: "get",
        url: "/subject/search",
        params: {
          name: name,
          page: this.subjectSearchInfo.currentPage + 1,
          pageSize: 10,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.loading.subjectLoading = false;
          this.subjectSearchInfo.data = this.subjectSearchInfo.data.concat(
            res.data.data.data
          );
          this.subjectSearchInfo.currentPage = res.data.data.currentPage;
          this.subjectSearchInfo.totalPage = res.data.data.totalPage;
        }
      });
    },
    searchCollege(query) {
      this.collegeSearchInfo = { data: [], currentPage: 0, totalPage: 1 };
      this.cache.college = query;
      this.loading.collegeLoading = true;
      this.loadCollege();
    },
    clearCollege(){
      this.searchCollege()
    },
    loadCollege() {
      const name = this.cache.college;
      this.axios({
        method: "get",
        url: "/college/search",
        params: {
          name: name,
          page: this.collegeSearchInfo.currentPage + 1,
          pageSize: 10,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.loading.collegeLoading = false;
          this.collegeSearchInfo.data = this.collegeSearchInfo.data.concat(
            res.data.data.data
          );
          this.collegeSearchInfo.currentPage = res.data.data.currentPage;
          this.collegeSearchInfo.totalPage = res.data.data.totalPage;
        }
      });
    },
    searchTerm(query) {
      this.termSearchInfo = { data: [], currentPage: 0, totalPage: 1 };
      this.cache.term = query;
      this.loading.termLoading = true;
      this.loadTerm();
    },
    clearTerm(){
      this.searchTerm()
    },
    loadTerm() {
      const name = this.cache.term;
      this.axios({
        method: "get",
        url: "/term/search",
        params: {
          name: name,
          page: this.termSearchInfo.currentPage + 1,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.loading.termLoading = false;
          this.termSearchInfo.data = this.termSearchInfo.data.concat(
            res.data.data.data
          );
          this.termSearchInfo.currentPage = res.data.data.currentPage;
          this.termSearchInfo.totalPage = res.data.data.totalPage;
        }
      });
    },
    searchBy(name, data) {
      if (name == "teacher") {
        this.teacherSearchInfo = {data:[{ id: data.id, name: data.name }],currentPage:1,totalPage:1};
      } else if (name == "college") {
        this.collegeSearchInfo = {data:[{ id: data.id, name: data.name }],currentPage:1,totalPage:1};
      } else if (name == "term") {
        this.termSearchInfo = {data:[{ id: data.id, name: data.name }],currentPage:1,totalPage:1};
      } else if (name == "subject") {
        this.subjectSearchInfo = {data:[{ id: data.id, name: data.name }],currentPage:1,totalPage:1};
      }
      this.$set(this.searchForm, name + "Id", data.id);
    },
    selectCourse(page) {
      this.axios({
        method: "get",
        url: "/course/searchAll",
        params: {
          subjectId: this.searchForm.subjectId,
          collegeId: this.searchForm.collegeId,
          teacherId: this.searchForm.teacherId,
          termId: this.searchForm.termId,
          page: page,
          pageSize: this.pageInfo.pageSize,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.courses = res.data.data.data;
          this.pageInfo.currentPage = res.data.data.currentPage;
          this.pageInfo.totalPage = res.data.data.totalPage;
        }
      });
    },
    changePage(newPage) {
      this.selectCourse(newPage);
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
.search-item {
  display: flex;
  align-items: center;
}
.search-item + .search-item {
  margin-left: 20px;
}
.table-container {
  margin-top: 30px;
}
</style>
