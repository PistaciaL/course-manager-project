<template>
  <div class="form">
    <div class="term-select">
      <span>选择学期：</span>
      <el-select
        v-model="term"
        filterable
        remote
        reserve-keyword
        :remote-method="searchItems"
        :loading="loading"
      >
        <el-option
          v-for="item in terms"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>
    </div>
    <table cellspacing="0px" class="outer-table">
      <thead>
        <tr class="outer-head">
          <th style="width: 200px">课程名</th>
          <th style="width: 130px">任课教师</th>
          <th style="width: 50px">学分</th>
          <th style="width: 510px">课程分数</th>
          <th style="width: 70px">排课时间</th>
        </tr>
      </thead>
      <tbody class="outer-body">
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.name }}</td>
          <td>{{ course.tName }}</td>
          <td>{{ course.credit }}</td>
          <td>
            <div v-if="course.marks != null" class="inner-table-container">
              <table cellspacing="0px" class="inner-table">
                <tr>
                  <th style="width: 100px"></th>
                  <th v-for="mark in course.marks" :key="mark.name">
                    {{ mark.name }}
                  </th>
                  <th style="width: 100px">总成绩</th>
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
                  <td>100%</td>
                </tr>
              </table>
            </div>
            <div v-else class="no-mark">成绩暂未发布</div>
          </td>
          <td>
            <el-button
              icon="el-icon-search"
              circle
              @click="showSchedule(course.id)"
            ></el-button>
          </td>
        </tr>
      </tbody>
    </table>
    <el-dialog title="排课时间" :visible.sync="scheduleIsShow">
      <el-calendar>
        <template slot="dateCell" slot-scope="data">
          <span>{{ data.data.day.split("-").slice(1).join("/") }}</span>
          <p style="background:#90939920">{{ hasCourse(data.data.day) }}</p>
        </template>
      </el-calendar>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      term: "",
      terms: [
        { id: 1, name: "2020年春学期" },
        { id: 2, name: "2020年秋学期" },
        { id: 3, name: "2021年春学期" },
        { id: 4, name: "2021年秋学期" },
        { id: 5, name: "2022年春学期" },
        { id: 6, name: "2022年秋学期" },
      ],
        loading: false,
      courses: [
        {
          id: 1,
          name: "高等数学",
          tName: "教师一",
          credit: 3,
          marks: [
            { name: "平时成绩", mark: 90, percent: 0.4 },
            { name: "期中考试", mark: 80, percent: 0.2 },
            { name: "期末考试", mark: 90, percent: 0.4 },
          ],
        },
        {
          id: 2,
          name: "大学物理",
          tName: "教师二",
          credit: 2.5,
          marks: [
            { name: "平时成绩", mark: 85, percent: 0.2 },
            { name: "期中考试", mark: 78, percent: 0.4 },
            { name: "期末考试", mark: 95, percent: 0.4 },
          ],
        },
        {
          id: 3,
          name: "离散数学",
          tName: "教师一",
          credit: 3,
          marks: [
            { name: "平时成绩", mark: 90, percent: 0.2 },
            { name: "期中考试", mark: 95, percent: 0.8 },
          ],
        },
        {
          id: 4,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 5,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 6,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 7,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 8,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 9,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 10,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 11,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
        {
          id: 12,
          name: "面向对象的程序与设计",
          tName: "教师一",
          credit: 4,
          mark: null,
        },
      ],
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
    };
  },
  methods: {
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
    searchItems(query){
      this.loading = true
      console.log("根据"+query+"搜索学期")
    }
  },
};
</script>

<style scoped>
.term-select span{
  padding: 0 10px;
}

.outer-table {
  margin: auto;
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
  margin-left: 19%;
}
td{
  padding: 0 10px;
}
</style>