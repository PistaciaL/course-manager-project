<template>
  <div class="container">
    <div class="title">我的通知</div>
    <el-divider></el-divider>
    <div class="table">
      <el-table
        :data="notices"
        style="width: 100%"
        :row-class-name="rowColor"
        @cell-click="gotoDetail"
      >
        <el-table-column
          prop="createTime"
          label="通知时间"
          min-width="200"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="type" label="通知类型" min-width="120">
        </el-table-column>
        <el-table-column prop="content" label="通知内容" min-width="400">
        </el-table-column>
        <el-table-column label="操作" min-width="180" align="center">
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
export default {
  data() {
    return {
      notices: [
        {
          id: 1,
          type: "发布考试",
          content: "您的课程《大学物理》已经发布考试",
          createTime: "2022/07/25 14:40:00",
          isRead: false,
        },
        {
          id: 2,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: false,
        },
        {
          id: 3,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: false,
        },
        {
          id: 4,
          type: "发布考试",
          content: "您的课程《大学物理》已经发布考试",
          createTime: "2022/07/25 14:40:00",
          isRead: true,
        },
        {
          id: 5,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: true,
        },
        {
          id: 6,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: true,
        },
        {
          id: 7,
          type: "发布考试",
          content: "您的课程《大学物理》已经发布考试",
          createTime: "2022/07/25 14:40:00",
          isRead: false,
        },
        {
          id: 8,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: false,
        },
        {
          id: 9,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: false,
        },
        {
          id: 10,
          type: "发布考试",
          content: "您的课程《大学物理》已经发布考试",
          createTime: "2022/07/25 14:40:00",
          isRead: true,
        },
        {
          id: 11,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: true,
        },
        {
          id: 12,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: true,
        },
        {
          id: 13,
          type: "发布考试",
          content: "您的课程《大学物理》已经发布考试",
          createTime: "2022/07/25 14:40:00",
          isRead: false,
        },
        {
          id: 14,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: false,
        },
        {
          id: 15,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: false,
        },
        {
          id: 16,
          type: "发布考试",
          content: "您的课程《大学物理》已经发布考试",
          createTime: "2022/07/25 14:40:00",
          isRead: true,
        },
        {
          id: 17,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: true,
        },
        {
          id: 18,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: true,
        },
        {
          id: 119,
          type: "退课结果",
          content: "您对《离散数学》的退课申请已经得到审核",
          createTime: "2022/06/20 15:00:00",
          isRead: true,
        },
        {
          id: 20,
          type: "发布成绩",
          content: "您修读的课程《大学物理》成绩已经发布",
          createTime: "2022/09/01 10:00:00",
          isRead: true,
        },
      ],
      pageInfo:{
        totalPage: 24,
      currentPage: 3,
      pageSize: 20,
      }
    };
  },
  methods: {
    rowColor({ row, rowIndex }) {
      if (row.isRead) {
        return "is-read-row";
      }
    },
    gotoDetail(row) {},
    changePage(newPage){
      console.log(newPage)
    }
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
.table {
  min-width: 960px;
  margin: 0 auto;
}
</style>

<style>
.el-table .is-read-row {
  background: #f3f3f3;
}
</style>