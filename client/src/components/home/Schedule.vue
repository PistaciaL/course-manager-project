<template>
  <div class="schedule-content content">
    <div class="content-header schedule-content-header">
      <span>我的课表</span>
      <div class="btn-group">
        <el-button icon="el-icon-arrow-left" circle></el-button>
        <el-button icon="el-icon-arrow-right" circle></el-button>
      </div>
    </div>
    <el-divider></el-divider>
    <div class="schedule-content-body">
      <table class="schedule-table" cellspacing="0px">
        <thead>
          <tr>
            <th></th>
            <th>星期一</th>
            <th>星期二</th>
            <th>星期三</th>
            <th>星期四</th>
            <th>星期五</th>
            <th>星期六</th>
            <th>星期日</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="schedule in schedules" :key="schedule==''?null:null">
            <th :rowspan="schedule.hour" v-if="schedule.hour!=1" class="schedule-table-left-head">{{ schedule.type}}</th>
            <th
              v-for="course in notnull(schedule.courses)"
              :key="course == '' ? null : null"
              :class="{ 'schedule-table-has-course': course != '' }"
              :rowspan="course.total"
            >
              <p>{{ course.name }}</p>
              <p>{{ course.room }}</p>
            </th>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      originSchedules: [
        {
          day: 1,
          courses: [
            { start: 1, end: 2, name: "离散数学", room: "教学西楼A204" },
            { start: 7, end: 8, name: "大学物理", room: "教学西楼B302" },
          ],
        },
        {
          day: 3,
          courses: [
            { start: 3, end: 4, name: "离散数学", room: "教学西楼A204" },
            { start: 9, end: 12, name: "大学物理", room: "教学西楼B302" },
          ],
        },
      ],
    };
  },
  computed: {
    schedules() {
      var list = new Array();
      for (var i = 0; i < 13; i++) {
        var type = null
        var hour = 1
        if(i==0){
          type='上午'
          hour = 4
        }else if(i==4){
          type='中午'
          hour = 2
        }else if(i==6){
          type='下午'
          hour = 4
        }else if(i==10){
          type='晚上'
          hour = 3
        }
        list[i] = {'type': type, 'hour':hour, courses: new Array() };
        for (var j = 0; j < 7; j++) {
          list[i].courses[j] = "";
        }
      }
      for (const key1 in this.originSchedules) {
        for (const key2 in this.originSchedules[key1].courses) {
          const course = this.originSchedules[key1].courses[key2];
          for (var i = course.start - 1; i < course.end; i++) {
            list[i].courses[this.originSchedules[key1].day - 1] =
              i == course.start - 1
                ? {
                    total: course.end - course.start + 1,
                    name: course.name,
                    room: course.room,
                  }
                : null;
          }
        }
      }
      return list;
    },
  },
  methods: {
    notnull(list) {
      var newList = new Array();
      for (var item in list) {
        if (list[item] != null) {
          newList.push(list[item]);
        }
      }
      return newList;
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

.schedule-content-header {
  display: flex;
  justify-content: space-between;
}
.schedule-table {
  width: 100%;
}
.schedule-table th {
  height: 50px;
  font-weight: 300;
}
.schedule-table thead th{
  border-left: 1px Solid #e6e6e6;
  font-size: 20px;
}
.schedule-table thead th:first-child{
  border-left: 0;
}
.schedule-table tbody th {
  border: 1px Solid #e6e6e6;
  border-bottom: 0;
  border-right: 0;
  width: 13%;
  font-size: 16px;
}
.schedule-table .schedule-table-left-head {
  width: 9%;
  font-size: 20px;
  border-left: 0;
}
.schedule-table-has-course {
  background: #f3f3f3;
  border-bottom: 0;
}
</style>