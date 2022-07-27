<template>
  <div
    class="container"
    v-loading.fullscreen.lock="loadingWithdraw"
    element-loading-background="rgba(0, 0, 0, 0.5)"
  >
    <div class="title">我的退课</div>
    <el-divider></el-divider>
    <div class="term-select">
      <span>选择学期：</span>
      <el-select
        v-model="term"
        filterable
        remote
        reserve-keyword
        :remote-method="searchItems"
        :loading="termLoading"
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
    <div class="table-container">
      <el-table :data="courses" style="width: 100%">
        <el-table-column prop="id" label="课程代码" min-width="150">
        </el-table-column>
        <el-table-column prop="name" label="名称" min-width="220">
        </el-table-column>
        <el-table-column prop="college" label="开课学院" min-width="210">
        </el-table-column>
        <el-table-column prop="teacher" label="任课教师" min-width="180"> </el-table-column>
        <el-table-column label="选课人数" min-width="100">
          <template slot-scope="scope">
            {{ scope.row.studentNumb }}/{{ scope.row.totalNumb }}
          </template>
        </el-table-column>
        <el-table-column label="我要退课" width="100">
          <template slot-scope="scope">
            <el-popconfirm
              title="退课操作无法撤回。确认是否选择退课？"
              @confirm="withdraw(scope.row.id)"
            >
              <el-button size="small" slot="reference">我要退课</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      title="该课程无法立即退课，请申请退课"
      :visible.sync="withdrawFormShow"
    >
      <el-form
        :model="withdrawForm"
        :rules="withdrawFormRules"
        ref="withdrawForm"
      >
        <el-form-item label="申请理由" prop="content">
          <el-input
            type="textarea"
            autosize
            placeholder="请输入内容"
            v-model="withdrawForm.content"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="withdrawFormShow = false">取 消</el-button>
        <el-button type="primary" @click="withdrawFormSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      courses: [
        {
          id: 5088,
          name: "面向对象的程序与设计",
          college: "软件学院",
          teacher: "教师0",
          studentNumb: 26,
          totalNumb: 63,
        },
        {
          id: 2772,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师1",
          studentNumb: 18,
          totalNumb: 52,
        },
        {
          id: 6835,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师2",
          studentNumb: 17,
          totalNumb: 50,
        },
        {
          id: 6723,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师3",
          studentNumb: 17,
          totalNumb: 71,
        },
        {
          id: 1483,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师4",
          studentNumb: 48,
          totalNumb: 68,
        },
        {
          id: 6174,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师5",
          studentNumb: 0,
          totalNumb: 74,
        },
        {
          id: 869,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师6",
          studentNumb: 8,
          totalNumb: 51,
        },
        {
          id: 7009,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师7",
          studentNumb: 32,
          totalNumb: 59,
        },
        {
          id: 9597,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师8",
          studentNumb: 5,
          totalNumb: 89,
        },
        {
          id: 6664,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师9",
          studentNumb: 33,
          totalNumb: 80,
        },
        {
          id: 7693,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师10",
          studentNumb: 24,
          totalNumb: 88,
        },
        {
          id: 7726,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师11",
          studentNumb: 48,
          totalNumb: 85,
        },
        {
          id: 4614,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师12",
          studentNumb: 8,
          totalNumb: 66,
        },
        {
          id: 1252,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师13",
          studentNumb: 5,
          totalNumb: 59,
        },
        {
          id: 2114,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师14",
          studentNumb: 8,
          totalNumb: 55,
        },
        {
          id: 6401,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师15",
          studentNumb: 15,
          totalNumb: 94,
        },
        {
          id: 4824,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师16",
          studentNumb: 47,
          totalNumb: 55,
        },
        {
          id: 7858,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师17",
          studentNumb: 31,
          totalNumb: 52,
        },
        {
          id: 4504,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师18",
          studentNumb: 4,
          totalNumb: 89,
        },
        {
          id: 5641,
          name: "离散数学",
          college: "数学与统计学院",
          teacher: "教师19",
          studentNumb: 32,
          totalNumb: 60,
        },
      ],
      term: "",
      terms: [
        { id: 1, name: "2020年春学期" },
        { id: 2, name: "2020年秋学期" },
        { id: 3, name: "2021年春学期" },
        { id: 4, name: "2021年秋学期" },
        { id: 5, name: "2022年春学期" },
        { id: 6, name: "2022年秋学期" },
      ],
      termLoading: false,
      loadingWithdraw: false,
      withdrawFormShow: false,
      withdrawForm: {
        courseId: "",
        content: "",
      },
      withdrawFormRules: {
        content: [
          { required: true, message: "申请理由不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    searchItems(query) {
      this.termLoading = true;
      console.log("根据" + query + "搜索学期");
    },
    withdraw(id) {
      this.loadingWithdraw = true;
      console.log("提交退课申请");
      setTimeout(() => {
        //如果不能立即退课
        if (Math.random() < 1) {
          this.withdrawFormShow = true;
          this.loadingWithdraw = false;
        } else {
          //可以立即退课,已退课
          this.loadingWithdraw = false;
          this.successWithdraw();
        }
      }, 1000);
    },
    successWithdraw() {
      this.$message({
        message: "退课成功",
        type: "success",
      });
    },
    successApplyWithdraw() {
      this.$message({
        message: "退课申请提交成功",
        type: "success",
      });
    },
    withdrawFormSubmit() {
      //提交表单
      this.withdrawFormShow = false
      this.successApplyWithdraw()
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
</style>
