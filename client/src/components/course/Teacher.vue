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
        size="small"
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
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.name }}</td>
          <td style="text-align: center">{{ course.id }}</td>
          <td style="text-align: center">{{ course.hour }}</td>
          <td style="text-align: center">
            {{ course.studentNumb }}/{{ course.totalNumb }}
          </td>
          <td style="text-align: center">
            <el-button
              icon="el-icon-search"
              circle
              @click="showSchedule(course.id)"
            ></el-button>
          </td>
          <td style="text-align: center">
            <el-popover
              placement="right"
              trigger="hover"
              v-if="course.exams != null"
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
            size='small'
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
            size='small'
          ></el-input-number>
          分钟
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addExam"
          >添加考试</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeNames: [],
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
          id: 12351,
          name: "高等数学",
          hour: 64,
          studentNumb: 45,
          totalNumb: 100,
          mark: "已发布",
          exams: [
            {
              id: 1,
              startTime: "2022/07/26 16:00",
              room: ["教学西楼A201", "教学西楼A203"],
            },
            {
              id: 2,
              startTime: "2022/05/04 14:00",
              room: ["教学西楼A201", "教学西楼A203"],
            },
          ],
        },
        {
          id: 46512,
          name: "大学物理",
          hour: 48,
          studentNumb: 80,
          totalNumb: 120,
          mark: "已发布",
          exams: [
            {
              id: 3,
              startTime: "2022/05/04 14:00",
              room: ["教学西楼A201", "教学西楼A203"],
            },
          ],
        },
        {
          id: 12315,
          name: "离散数学",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 89456,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 12311,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 43241,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 46534,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 31245,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 98156,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 89523,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 17895,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
        },
        {
          id: 81569,
          name: "面向对象的程序与设计",
          hour: 48,
          studentNumb: 45,
          totalNumb: 100,
          mark: "未发布",
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
      addExamShow: false,
      addExamForm: {
        id: "",
        date: "",
        stillHour: "",
        stillMinute: "",
      },
    };
  },
  methods: {
    addExam(){
      console.log('添加考试')
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
    addExamChangeHour(currentValue){
      if(currentValue ==4){
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
    searchItems(query) {
      this.loading = true;
      console.log("根据" + query + "搜索学期");
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

.term-select{
  margin-left: 50px;
}
.el-input-number{
  width: 100px;
}
</style>
