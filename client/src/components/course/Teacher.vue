<template>
  <div class="form">
    <div class="form-header">
      <div class="term-select">
        <span>选择学期：</span>
        <el-select
          v-model="termId"
          :remote-method="searchTerm"
          placeholder=""
          :loading="loading"
          filterable
          remote
          v-el-select-loadmore="loadTerm"
          :clearable="true"
          size="small"
          @change="selectCourse(1)"
          @clear="clearTerm"
        >
          <el-option
            v-for="term in terms.data"
            :key="term.id"
            :label="term.name"
            :value="term.id"
          >
          <span style="margin-right:50px">{{term.name}}</span>
          <span style="float:right;font-size:8px;color:#909399;">{{term.startDate}} - {{term.endDate}}</span>
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align: center"
          >
            <div v-show="terms.currentPage < terms.totalPage">
              <i class="el-icon-loading" /><span value="">加载中</span>
            </div>
            <div v-show="terms.currentPage >= terms.totalPage">
              <span value="">已经全部加载完毕</span>
            </div>
          </el-option>
        </el-select>
      </div>
      <div><el-button @click="addCourseBtn">注册课程</el-button></div>
    </div>
    <CourseAddForm ref="addForm" /> 
    <table cellspacing="0px" class="outer-table" v-if="courses.data.length!=0">
      <thead>
        <tr class="outer-head">
          <th style="min-width: 206px">科目名称</th>
          <th style="min-width: 120px">课程代码</th>
          <th style="min-width: 100px">课时数</th>
          <th style="min-width: 120px">课程人数</th>
          <th style="min-width: 100px">排课时间</th>
          <th style="min-width: 170px">考试情况</th>
          <th style="min-width: 130px">课程成绩</th>
        </tr>
      </thead>
      <tbody class="outer-body">
        <tr v-for="course in courses.data" :key="course.id">
          <td>{{ course.name }}</td>
          <td style="text-align: center">{{ course.id }}</td>
          <td style="text-align: center">{{ course.hour }}</td>
          <td style="text-align: center">
            <p>
              <el-progress
                :show-text="false"
                :status="
                  course.studentNumb / course.totalNumb > 1 ? 'warning' : null
                "
                :percentage="
                  course.studentNumb / course.totalNumb > 1
                    ? 100
                    : (course.studentNumb / course.totalNumb) * 100
                "
              ></el-progress>
            </p>
            <span>{{ course.studentNumb }}/{{ course.totalNumb }}</span>
          </td>
          <td style="text-align: center">
            <el-button
              icon="el-icon-search"
              circle
              @click="showSchedule(course.id)"
              v-if="schedules != null"
            ></el-button>
            <el-button
              icon="el-icon-search"
              circle
              @click="showSchedule(course.id)"
              v-else
              disabled
            ></el-button>
          </td>
          <td style="text-align: center">
            <el-popover
              placement="right"
              trigger="hover"
              v-if="course.exams.length!=0"
            >
              <el-table :data="course.exams">
                <el-table-column
                  width="150"
                  property="startTime"
                  label="时间"
                ></el-table-column>
                <el-table-column
                  width="300"
                  property="room"
                  label="教室"
                ></el-table-column>
              </el-table>
              <el-button slot="reference" @click="setExam(course.id)"
                >已设置{{ course.exams.length }}次考试</el-button
              >
            </el-popover>
            <el-button v-else slot="reference" @click="setExam(course.id)"
              >未进行考试</el-button
            >
          </td>
          <td>
            <el-button @click="searchMark(course.id)"
              >{{ course.mark }}<i class="el-icon-arrow-right"></i
            ></el-button>
          </td>
        </tr>
      </tbody>
    </table>
    <el-empty description="这学期没有开设课程" v-else></el-empty>
    <div class="page-divider">
      <el-pagination
        layout="prev, pager, next"
        :page-count="courses.totalPage"
        :page-size="courses.pageSize"
        :current-page.sync="courses.currentPage"
        :hide-on-single-page="true"
        @current-change="changePage"
      >
      </el-pagination>
    </div>
    <el-dialog title="排课时间" :visible.sync="scheduleIsShow">
      <el-calendar>
        <template slot="dateCell" slot-scope="data">
          <span>{{ data.data.day.split("-").slice(1).join("/") }}</span>
          <p style="background: #90939920">{{ hasCourse(data.data.day) }}</p>
        </template>
      </el-calendar>
    </el-dialog>

    <el-dialog title="添加考试" :visible.sync="addExamShow" width="600px">
      <el-form :model="addExamForm">
        <el-form-item label="考试日期">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="addExamForm.date"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="考试时长">
          <el-input-number
            v-model="addExamForm.stillHour"
            @change="addExamChangeHour"
            :min="0"
            :max="4"
            controls-position="right"
            size="small"
          ></el-input-number>
          小时
          <el-input-number
            v-model="addExamForm.stillMinute"
            @change="addExamChangeMinute"
            :min="this.addExamForm.stillHour == 0 ? 10 : -10"
            :max="this.addExamForm.stillHour == 4 ? 0 : 60"
            :step="10"
            step-strictly
            controls-position="right"
            size="small"
          ></el-input-number>
          分钟
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addExam">添加考试</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import CourseAddForm from "./CourseAddForm.vue";
var that;
export default {
  components: { CourseAddForm },
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
              binding.expression == "loadTerm" &&
              that.terms.currentPage < that.terms.totalPage
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
      termId: null,
      cacheTerm: "",
      terms: { data: [], currentPage: 0, totalPage: 1 },
      loading: false,
      courses: { data: [], currentPage: 0, totalPage: 1 },
      schedules: [
        [],
        [],
        [],
        [],
        [],
        [
          { day: 25, startHour: 1, endHour: 2 },
          { day: 26, startHour: 1, endHour: 2 },
        ],
        [
          { day: 1, startHour: 1, endHour: 2 },
          { day: 3, startHour: 1, endHour: 2 },
          { day: 5, startHour: 1, endHour: 2 },
          { day: 6, startHour: 1, endHour: 2 },
          { day: 11, startHour: 1, endHour: 2 },
        ],
        [],
        [],
        [],
        [],
        [],
      ],
      scheduleIsShow: false,
      addExamShow: false,
      addExamForm: {
        id: "",
        date: "",
        stillHour: "",
        stillMinute: "",
      },
    };
  },
  mounted() {
    that = this;
    this.searchTerm("");
    this.selectCourse(1);
  },
  methods: {
    selectCourse(page) {
      this.axios({
        method: "get",
        url: "/course/search",
        params: {
          termId: this.termId,
          page: page,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          if (this.termId == null) {
            //根据term分组(待定)
            this.courses = res.data.data;
          } else {
            this.courses = res.data.data;
          }
        }
      });
    },
    changePage(newPage) {
      this.selectCourse(newPage)
    },
    searchTerm(query) {
      this.terms = { data: [], currentPage: 0, totalPage: 1 };
      this.cacheTerm = query;
      this.loading = true;
      this.loadTerm();
    },
    clearTerm(){
      this.searchTerm()
    },
    loadTerm() {
      const name = this.cacheTerm;
      this.axios({
        method: "get",
        url: "/term/search",
        params: {
          name: name,
          page: this.terms.currentPage + 1,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.loading = false;
          this.terms.data = this.terms.data.concat(res.data.data.data);
          this.terms.currentPage = res.data.data.currentPage;
          this.terms.totalPage = res.data.data.totalPage;
        }
      });
    },
    addExam() {
      console.log("添加考试");
    },
    addCourseBtn() {
      this.$refs.addForm.addCourseFormShow = true;
    },
    addExamChangeMinute(currentValue) {
      if (currentValue < 0) {
        this.addExamForm.stillHour -= 1;
        this.addExamForm.stillMinute = 50;
      } else if (currentValue > 50) {
        this.addExamForm.stillHour += 1;
        this.addExamForm.stillMinute = 0;
      }
    },
    addExamChangeHour(currentValue) {
      if (currentValue == 4) {
        this.addExamForm.stillMinute = 0;
      }
    },
    totalMark(marks) {
      var sum = 0;
      for (const key in marks) {
        sum += marks[key].mark * marks[key].percent;
      }
      return sum;
    },
    showSchedule(id) {
      console.log("请求排课表,格式化");
      this.scheduleIsShow = true;
    },
    hasCourse(date) {
      const month = parseInt(date.split("-")[1]);
      const day = parseInt(date.split("-")[2]);
      for (const key in this.schedules[month - 1]) {
        const daySchedule = this.schedules[month - 1][key];
        if (daySchedule.day == day) {
          return daySchedule.startHour + "-" + daySchedule.endHour + "节";
        }
      }
    },
    searchMark(id) {
      console.log("跳转到成绩详情页面,课程id" + id);
    },
    setExam(id) {
      this.addExamForm.id = id;
      this.addExamShow = true;
    },
  },
};
</script>

<style scoped>
.term-select span {
  padding: 0 10px;
}

.outer-table {
  margin: auto;
  width: 100%;
}
.outer-body > tr > td {
  font-weight: 400;
  font-size: 14px;
  color: #606266;
  border-bottom: 1px Solid #e6e6e6;
  height: 80px;
}
.outer-head th {
  font-size: 14px;
  color: #909399;
  font-weight: bold;
  border-bottom: 1px Solid #e6e6e6;
  height: 80px;
}
.outer-body > tr > td:last-child {
  text-align: center;
}
.no-mark {
  text-align: center;
}
.inner-table {
  width: 100%;
}
.inner-table th {
  color: #909399;
  font-weight: bold;
}
.inner-table > tr:first-child > th:first-child {
  border-bottom: 0;
}
.inner-table > tr:first-child > th {
  border-bottom: 1px Solid #e6e6e6;
  border-right: 0;
}
.inner-table > tr > th {
  border-right: 1px Solid #e6e6e6;
}
.inner-table td {
  text-align: center;
}
td {
  padding: 0 10px;
}
.el-input-number {
  width: 100px;
}

.form-header {
  display: flex;
  margin: 0 150px;
  justify-content: space-between;
  align-items: center;
}

.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}
</style>
