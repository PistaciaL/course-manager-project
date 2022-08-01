<template>
  <div class="container">
    <div class="title">
      我的选课
      <div>
        <span>学期:{{termMsg.name}}</span>
        <span>选课时间段:{{selectChoiceMsg.startTime}} - {{selectChoiceMsg.endTime}}</span>
        <span>筹码数量:{{selectChoiceMsg.lastChip}}/{{selectChoiceMsg.totalChip}}</span>
      </div>
      <el-link
        href="/rules/courseselect"
        target="_blank"
        class="el-icon-warning-outline"
        >选课规则</el-link
      >
    </div>
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
    </div>
    <div class="table-container">
      <el-table :data="courses" style="width: 100%">
        <el-table-column prop="id" label="课程序号" min-width="60">
        </el-table-column>
        <el-table-column label="科目名称" min-width="150">
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
        <el-table-column label="开课学院" min-width="120">
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
        <el-table-column label="任课教师" min-width="100">
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
        <el-table-column label="学生人数" min-width="80">
          <div slot-scope="scope">
            {{ scope.row.studentNumb }}/{{ scope.row.totalNumb }}
          </div>
        </el-table-column>
        <el-table-column label="上课时间" min-width="200">
          <div slot-scope="scope" @click="showDrawer(scope.row)">
            <div>第{{ scope.row.startWeek }}周到第{{ scope.row.endWeek }}周</div>
            <div v-for="s in scope.row.schedule" :key="s.day">
              周{{ weekdayName(s.day) }}:第{{ s.startHour }}课时-第{{
                s.endHour
              }}课时
            </div>
          </div>
        </el-table-column>
        <el-table-column label="操作" min-width="100" align="center">
          <div slot-scope="scope">
            <el-button size="small" @click="showDrawer(scope.row)"
              >我要选课</el-button
            >
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
    <el-drawer
      :visible.sync="drawerShow"
      direction="btt"
      size="100%"
      :modal="false"
      :withHeader="false"
      :wrapperClosable="false"
      :modal-append-to-body="false"
    >
      <table class="schedule-table" cellspacing="0px">
        <thead>
          <tr>
            <th>
              <div class="drawer-btn-group">
                <div class="el-icon-check" @click="select">选课</div>
              <div class="el-icon-close" @click="hiddenDrawer">关闭</div>
              </div>
            </th>
            <th>星期一</th>
            <th>星期二</th>
            <th>星期三</th>
            <th>星期四</th>
            <th>星期五</th>
            <th>星期六</th>
            <th>星期日</th>
          </tr>
          <tr></tr>
        </thead>
        <tbody>
          <tr v-for="schedule in schedules" :key="schedule == '' ? null : null">
            <th
              :rowspan="schedule.hour"
              v-if="schedule.hour != 1"
              class="schedule-table-left-head"
            >
              {{ schedule.type }}
            </th>
            <td
              v-for="course in notnull(schedule.courses)"
              :key="course == '' ? null : null"
              :class="{ 'schedule-table-has-course': course.data != '' }"
              :rowspan="course.total"
              :style="
                course.color == 'red'
                  ? 'background:#F56C6C20'
                  : course.color == 'blue'
                  ? 'background:#409EFF20'
                  : ''
              "
            >
              <div v-for="item in course.data" :key="item.name">
                <p>{{ item.name }}</p>
                <p v-if="course != ''">
                  第{{ item.startWeek }}周到第{{ item.endWeek }}周
                </p>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </el-drawer>
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
      termMsg: {name: '2022年秋季学期'},
      selectChoiceMsg:{startTime:'2022/07/01 12:00:00', endTime:'2022/07/15 15:00:00', type:'筹码选课',totalChip:100,lastChip:100},
      drawerShow: false,
      searchForm: {
        collegeId: "",
        teacherId: "",
        subjectId: "",
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
          studentNumb: 30,
          totalNumb: 50,
          startWeek: 1,
          endWeek: 8,
          schedule: [
            { startHour: 2, endHour: 3, day: 1 },
            { startHour: 2, endHour: 3, day: 3 },
          ],
        },
        {
          id: 13121,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          studentNumb: 40,
          totalNumb: 100,
          startWeek: 9,
          endWeek: 16,
          schedule: [
            { startHour: 1, endHour: 2, day: 1 },
            { startHour: 1, endHour: 2, day: 2 },
          ],
        },
        {
          id: 1312,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          studentNumb: 100,
          totalNumb: 100,
          startWeek: 6,
          endWeek: 10,
          schedule: [
            { startHour: 1, endHour: 2, day: 1 },
            { startHour: 1, endHour: 2, day: 4 },
          ],
        },
        {
          id: 1312,
          subjectId: 12122,
          subject: "面向对象的程序与设计",
          collegeId: 2,
          college: "软件学院",
          teacherId: 12111,
          teacher: "教师二",
          studentNumb: 0,
          totalNumb: 100,
          startWeek: 1,
          endWeek: 14,
          schedule: [
            { startHour: 7, endHour: 10, day: 2 },
            { startHour: 7, endHour: 10, day: 4 },
          ],
        },
        {
          id: 1312,
          subjectId: 121,
          subject: "高等数学",
          collegeId: 1,
          college: "数学与统计学院",
          teacherId: 121,
          teacher: "教师一",
          studentNumb: 100,
          totalNumb: 100,
          startWeek: 9,
          endWeek: 10,
          schedule: [
            { startHour: 7, endHour: 8, day: 1 },
            { startHour: 7, endHour: 8, day: 3 },
          ],
        },
      ],
      pageInfo: {
        currentPage: 4,
        totalPage: 10,
        pageSize: 20,
      },
      originSchedules: [
        {
          day: 1,
          courses: [
            { start: 1, end: 2, name: "离散数学", startWeek: 1, endWeek: 8 },
            { start: 1, end: 2, name: "高等数学", startWeek: 10, endWeek: 16 },
            { start: 7, end: 8, name: "大学物理", startWeek: 1, endWeek: 8 },
          ],
        },
        {
          day: 3,
          courses: [
            { start: 3, end: 6, name: "离散数学", startWeek: 1, endWeek: 8 },
            { start: 9, end: 12, name: "大学物理", startWeek: 1, endWeek: 8 },
          ],
        },
      ],
      selectedCourse: [],
      courseHasConflict: false,
    };
  },
  mounted() {
    console.log("加载学院列表");
    console.log("加载学期列表");
    that = this;
    this.searchTeacher("");
    this.searchSubject("");
  },
  computed: {
    schedules() {
      this.courseHasConflict = false;
      var list = new Array();
      for (var i = 0; i < 13; i++) {
        var type = null;
        var hour = 1;
        if (i == 0) {
          type = "上午";
          hour = 4;
        } else if (i == 4) {
          type = "中午";
          hour = 2;
        } else if (i == 6) {
          type = "下午";
          hour = 4;
        } else if (i == 10) {
          type = "晚上";
          hour = 3;
        }
        list[i] = { type: type, hour: hour, courses: new Array() };
        for (var j = 0; j < 7; j++) {
          list[i].courses[j] = { total: 1, color: "normal", data: new Array() };
        }
      }
      for (const key1 in this.originSchedules) {
        const day = this.originSchedules[key1].day;
        for (const key2 in this.originSchedules[key1].courses) {
          const course = this.originSchedules[key1].courses[key2];
          for (var i = course.start; i <= course.end; i++) {
            list[i - 1].courses[day - 1].data.push(course);
          }
        }
        for (var i = 0; i < 12; i++) {
          for (var j = i + 1; j < 13; j++) {
            if (
              this.isEqualNotNull(
                list[i].courses[day - 1].data,
                list[j].courses[day - 1].data
              )
            ) {
              list[j].courses[day - 1].data = null;
              list[i].courses[day - 1].total++;
            }
          }
        }
      }
      //add select course
      for (const key in this.selectedCourse.schedule) {
        const s = this.selectedCourse.schedule[key];
        for (var i = s.endHour - 1; i >= s.startHour - 1; i--) {
          if (list[i].courses[s.day - 1].data == null) {
            var course = {};
            for (var j = i - 1; j >= 0; j--) {
              if (list[j].courses[s.day - 1].data != null) {
                list[j].courses[s.day - 1].total--;
                course.color = "blue";
                course.data = new Array();
                for (const courseKey in list[j].courses[s.day - 1].data) {
                  const oldCourse = list[j].courses[s.day - 1].data[courseKey];
                  course.data.push({
                    start: oldCourse.start,
                    end: oldCourse.end,
                    startWeek: oldCourse.startWeek,
                    endWeek: oldCourse.endWeek,
                    name: oldCourse.name,
                  });
                  if (
                    oldCourse.startWeek <= this.selectedCourse.endWeek &&
                    oldCourse.endWeek >= this.selectedCourse.startWeek
                  ) {
                    course.color = "red";
                    this.courseHasConflict = true;
                  }
                }
                break;
              }
            }
            course.total = 1;
            list[i].courses[s.day - 1] = course;
            if(this.isEqualNotNull(list[i+1].courses[s.day-1].data,list[i].courses[s.day-1].data)){
              list[i+1].courses[s.day-1].data=null
              list[i].courses[s.day-1].total=list[i+1].courses[s.day-1].total+1
              list[i+1].courses[s.day-1].total=1
            }
          } else if (list[i].courses[s.day - 1].data.length == 0) {
            list[i].courses[s.day - 1].color = "blue";
          } else {
            if (list[i].courses[s.day - 1].total != 1) {
              list[i + 1].courses[s.day - 1].data = new Array();
              list[i + 1].courses[s.day - 1].total =
                list[i].courses[s.day - 1].total - 1;
              for (const courseKey in list[i].courses[s.day - 1].data) {
                const oldCourse = list[i].courses[s.day - 1].data[courseKey];
                list[i + 1].courses[s.day - 1].data.push({
                  start: oldCourse.start,
                  end: oldCourse.end,
                  startWeek: oldCourse.startWeek,
                  endWeek: oldCourse.endWeek,
                  name: oldCourse.name,
                });
                if (
                  oldCourse.startWeek <= this.selectedCourse.endWeek &&
                  oldCourse.endWeek >= this.selectedCourse.startWeek
                ) {
                  list[i].courses[s.day - 1].color = "red";
                  this.courseHasConflict = true;
                }
              }
              list[i].courses[s.day - 1].total = 1;
              if ((list[i].courses[s.day - 1].color = "normal")) {
                list[i].courses[s.day - 1].color = "blue";
              }
            } else {
              for (const courseKey in list[i].courses[s.day - 1].data) {
                const oldCourse = list[i].courses[s.day - 1].data[courseKey];
                if (
                  oldCourse.startWeek <= this.selectedCourse.endWeek &&
                  oldCourse.endWeek >= this.selectedCourse.startWeek
                ) {
                  list[i].courses[s.day - 1].color = "red";
                  this.courseHasConflict = true;
                }
              }
              if (list[i].courses[s.day - 1].color == "normal") {
                list[i].courses[s.day - 1].color = "blue";
              }
              console.log('列'+i+'行'+(s.day-1))
              if(this.isEqualNotNull(list[i].courses[s.day - 1].data, list[i+1].courses[s.day - 1].data)){
                list[i].courses[s.day - 1].total=list[i+1].courses[s.day - 1].total+1
                list[i+1].courses[s.day - 1].total=1
                list[i+1].courses[s.day - 1].data=null
              }
            }
          }
        }
      }
      console.log(list)
      return list;
    },
  },
  methods: {
    weekdayName(numb){
      switch (numb) {
        case 1:
          return '一'
        case 2:
          return '二'
        case 3:
          return '三'
        case 4:
          return '四'
        case 5:
          return '五'
        case 6:
          return '六'
        case 7:
          return '日'
      
        default:
          break;
      }
    },
    isEqualNotNull(array1, array2) {
      if (array1 == null || array2 == null) {
        return false;
      }
      if (array1.length == 0 || array1.length != array2.length) {
        return false;
      } else {
        for (const key in array1) {
          if (array1[key] != array2[key]) {
            for(const k2 in array1[key]){
              if(array1[key][k2]!=array2[key][k2]){
                return false;
              }
            }
          }
        }
        return true;
      }
    },
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
      } else if (name == "subject") {
        this.subjects = [{ id: data.id, name: data.name }];
      }
      this.$set(this.searchForm, name + "Id", data.id);
    },
    changePage(newPage) {
      console.log(newPage);
    },
    showDrawer(row) {
      this.selectedCourse = row;
      this.drawerShow = true;
      this.$emit('changeHeight',1.4)
    },
    hiddenDrawer(){
      this.drawerShow=false
      this.$emit('changeHeight',1)
    },
    notnull(list) {
      var newList = new Array();
      for (var item in list) {
        if (list[item].data != null) {
          newList.push(list[item]);
        }
      }
      return newList;
    },
    select(row) {
      this.schedules
      if(this.courseHasConflict){
        this.$message({
          message: '选课时间段存在冲突',
          type: 'warning'
        });
      } else {
        if(this.selectChoiceMsg.type=='筹码选课'){
          this.$prompt('请输入所给的筹码数', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputPattern: /^\d{0,3}$/,
            inputErrorMessage: '筹码数格式不正确',
          }).then(({ value }) => {
            if(value==null){
              value=0
            }
            console.log('筹码选课, 筹码数为'+value+',课程id为'+this.selectedCourse.id)
            this.$message({
              message: '选课成功',
              type: 'success'
            });
            this.hiddenDrawer()
          }).catch(() => {});
        } else {
          console.log('抢课,课程id为'+this.selectedCourse.id)
          this.$message({
            message: '选课成功',
            type: 'success'
          });
        }
      }
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
.title div>span{
  padding: 0 20px;
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
td > div > div {
  display: inline;
}

.drawer-btn-group{
  height: 100%;
  width: 100%;
}
.drawer-btn-group div{
  width: 50%;
  height: 100%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  user-select: none;
}
.drawer-btn-group div:first-child{
  border-right: 1px solid #e6e6e6;
  width: calc(50% - 1px);
}
.drawer-btn-group div:first-child:hover{
  background-color: #409eff20;
}
.drawer-btn-group div:last-child:hover{
  background-color: #F56C6C20;
}
.schedule-table thead th:first-child {
  padding: 0;
}
.schedule-table {
  width: 100%;
  height: 100%;
}
.schedule-table th,
td {
  font-weight: 300;
  height: 50px;
}
.schedule-table thead th {
  border-top: 1px Solid #e6e6e6;
  height: 50px;
}
.schedule-table th {
  border-left: 1px Solid #e6e6e6;
  font-size: 16px;
}
.schedule-table thead th:first-child {
  border-left: 0;
  width: 199px;
}
.schedule-table tbody th,
td {
  border: 1px Solid #e6e6e6;
  border-bottom: 0;
  border-right: 0;
  font-size: 14px;
}
.schedule-table .schedule-table-left-head {
  font-size: 20px;
  border-left: 0;
}
.schedule-table-has-course {
  background: #f3f3f3;
  border-bottom: 0;
}

.el-drawer__wrapper {
  top: 50%;
  min-width: 1200px;
  overflow: visible;
}
</style>
