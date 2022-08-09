<template>
  <div class="my-info-content content">
          <div class="content-header">
            <span>我的信息</span>
          </div>
          <el-divider></el-divider>
          <div class="my-info-content-body">
            <div class="my-info-content-item">
              <el-avatar
                :size="128"
                ref="avatar"
                :src="MyUtils.avatarUrl(userInfo('avatarUrl'))"
                class="my-info-avatar"
                shape="square"
              >
                {{ userInfo('name').slice(0, 2) }}
              </el-avatar>
            </div>
            <div class="my-info-content-item">
              <div class="my-info-tag-content">
                <el-tag>{{ userInfo('identity') }}</el-tag>
                <el-tag>{{ userInfo('permission') }}</el-tag>
              </div>
            </div>
            <div class="my-info-content-item my-info-detail-content">
              <el-collapse v-model="activeName" accordion>
                <el-collapse-item
                  title="近期考试"
                  name="exam"
                  class="collapse-item"
                >
                  <el-table
                    :data="exams"
                    :show-header="false"
                    empty-text="未来一段时间内暂无考试"
                  >
                    <el-table-column
                      prop="subjectName"
                      label="科目"
                      :min-width="78"
                      align="left"
                    ></el-table-column>
                    <el-table-column
                      prop="startDate"
                      label="日期"
                      :min-width="94"
                    ></el-table-column>
                    <el-table-column
                      prop="room"
                      label="教室"
                      :min-width="110"
                    ></el-table-column>
                  </el-table>
                </el-collapse-item>
                <el-collapse-item
                  title="近期成绩"
                  name="mark"
                  class="collapse-item"
                  v-if="userInfo('identity') == '学生'"
                >
                  <el-table
                    :data="marks"
                    :show-header="false"
                    empty-text="暂无成绩"
                  >
                    <el-table-column
                      prop="subjectName"
                      label="科目"
                      :min-width="138"
                      align="left"
                    ></el-table-column>
                    <el-table-column
                      prop="mark"
                      label="总分"
                      :min-width="80"
                      align="center"
                    ></el-table-column>
                  </el-table>
                </el-collapse-item>
              </el-collapse>
            </div>
          </div>
        </div>
</template>

<script>
export default {
  data(){
    return{
      activeName: 0,
      exams: [
        {
          id: 1,
          subjectName: "高等数学",
          startDate: "08/10 18:00",
          room: "教学东楼A302",
        },
        {
          id: 2,
          subjectName: "大学物理",
          startDate: "08/14 16:00",
          room: "教学西楼B101",
        },
        {
          id: 3,
          subjectName: "面向对象的程序与设计",
          startDate: "08/10 17:00",
          room: "实验大楼B412",
        },
        {
          id: 4,
          subjectName: "电磁学",
          startDate: "09/15 08:00",
          room: "理学院B101",
        },
        {
          id: 5,
          subjectName: "模拟电路",
          startDate: "10/08 10:00",
          room: "实验大楼A406",
        },
      ],
      marks: [
        { id: 1, subjectName: "离散数学", mark: "91" },
        { id: 2, subjectName: "军事理论", mark: "90" },
        { id: 3, subjectName: "软件开发综合能力训练", mark: "83" },
      ],
    }
  },
  methods:{
    userInfo(str) {
      return localStorage.getItem(str);
    },
  }
}
</script>

<style scoped>
.content-header {
  height: 29px;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  font-size: 24px;
}
.my-info-content-item {
  margin: 20px 0;
}
.el-divider {
  margin: 0;
}

.my-info-content-body {
  min-height: 500px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px 0;
}
.my-info-avatar {
  font-size: 32px;
}
.my-info-tag-content span {
  margin: 0 10px;
}
.my-info-detail-content {
  width: 100%;
}
.collapse-item {
  padding-left: 10%;
}
</style>