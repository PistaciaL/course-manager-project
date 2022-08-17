<template>
  <div class="form">
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
    <table cellspacing="0px" class="outer-table">
      <thead>
        <tr class="outer-head">
          <th style="min-width: 128px">课程名</th>
          <th style="min-width: 98px">任课教师</th>
          <th style="min-width: 58px">学分</th>
          <th style="min-width: 158px">选课人数</th>
          <th style="min-width: 418px;width:518px">课程分数</th>
          <th style="min-width: 88px">排课时间</th>
        </tr>
      </thead>
      <tbody class="outer-body">
        <tr v-for="course in courses.data" :key="course.courseId">
          <td style="text-align:center">{{ course.subjectName }}</td>
          <td style="text-align:center">{{ course.teacherName }}</td>
          <td style="text-align:center">{{ course.credit }}</td>
          <td style="text-align:center">
            <p style="width:70%;margin:10px auto">
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
          <td>
            <div v-if="course.marks != null" class="inner-table-container">
              <table cellspacing="0px" class="inner-table">
                <tr>
                  <th style="width: 78px"></th>
                  <th v-for="mark in course.marks" :key="mark.name">
                    {{ mark.name }}
                  </th>
                  <th style="width: 98px">总成绩</th>
                </tr>
                <tr>
                  <th>成绩</th>
                  <td v-for="mark in course.marks" :key="mark.name">
                    {{ mark.mark }}
                  </td>
                  <td>{{ totalMark(course.marks) }}</td>
                </tr>
                <tr>
                  <th>占比</th>
                  <td v-for="mark in course.marks" :key="mark.name">
                    {{ mark.percent * 100 }}%
                  </td>
                  <td></td>
                </tr>
              </table>
            </div>
            <div v-else class="no-mark">成绩暂未发布</div>
          </td>
          <td style="text-align:center">
            <el-tooltip class="item" effect="dark" content="该课程暂未排课" placement="bottom" :disabled="course.schedule!=''">
              <div style="width:min-content;margin:auto">
                <el-button
                  :disabled="course.schedule==''"
                  :ref="'scheduleBtn'+course.courseId"
                  :icon="scheduleIsLoading==course.courseId?'el-icon-loading':'el-icon-search'"
                  circle
                  @click="showSchedule(course)"
                ></el-button>
              </div>
            </el-tooltip>
          </td>
        </tr>
      </tbody>
    </table>
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
      <el-calendar v-model="schedules.startDate">
        <template slot="dateCell" slot-scope="data">
          <span>{{ data.data.day.split("-").slice(1).join("-") }}</span>
          <span style="display:block;margin-top:5px" v-for="info in hasCourse(data.data.day)" :key="info==null?null:null">
            {{info}}
          </span>
        </template>
      </el-calendar>
    </el-dialog>
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
      scheduleIsLoading:null,
      courses: [],
      schedules: {
        startDate: null,
        data:[],
      },
      scheduleIsShow: false,
    };
  },
  mounted(){
    that = this;
    this.searchTerm("");
    this.selectCourse(1);
  },
  methods: {
    totalMark(marks) {
      var sum = 0;
      for (const key in marks) {
        sum += marks[key].mark * marks[key].percent;
      }
      return sum.toFixed(1);
    },
    showSchedule(course) {
      const id = course.courseId
      this.scheduleIsLoading=id
      if(course.schedule==null){
        this.axios({
          method:'get',
          url:'/schedule/course/search',
          params:{
            courseId:id
          }
        }).then(res=>{
          if(res.data.code==200){
            course.schedule=this.formatSchedule(res.data.data.schedules)
            this.schedules=course.schedule
            this.scheduleIsShow = true;
          } else {
            course.schedule=''
          }
          this.scheduleIsLoading=null
        })
      } else {
        this.schedules=course.schedule
        this.scheduleIsShow = true;
        this.scheduleIsLoading=null
      }
    },
    formatSchedule(schedules){
      var scheduleList = {startDate:new Date("2099-01-01"),data:{}}
      schedules.forEach((value)=>{
        if(new Date(value.classDate)<scheduleList.startDate){
          scheduleList.startDate = new Date(value.classDate)
        }
        scheduleList.data[value.classDate]={roomName:value.roomName,startHour:value.startHour,endHour:value.endHour}
      })
      return scheduleList
    },
    hasCourse(date) {
      if(this.schedules.data[date]!=null){
        console.log(this.schedules.data[date])
        return [this.schedules.data[date].startHour+"-"+this.schedules.data[date].endHour+"节",this.schedules.data[date].roomName]
      }
    },
    selectCourse(page) {
      this.axios({
        method: "get",
        url: "/course/search",
        params: {
          termId: this.termId,
          page: page
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
          page: this.terms.currentPage + 1
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
  },
};
</script>

<style scoped>
.term-select span{
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
.inner-table>tr:first-child>th:first-child{
  border-bottom: 0;
}
.inner-table>tr:first-child>th{
  border-bottom: 1px Solid #e6e6e6;
  border-right: 0;
}
.inner-table>tr>th{
  border-right: 1px Solid #e6e6e6;
}
.inner-table td {
  text-align: center;
}

.term-select{
  margin-left: 50px;
}
td{
  padding: 0 10px;
}

.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}
</style>