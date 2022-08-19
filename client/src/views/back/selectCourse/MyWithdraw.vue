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
          v-model="termId"
          :remote-method="searchTerm"
          placeholder=""
          :loading="termLoading"
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
    <div class="table-container">
      <el-table :data="courses.data" style="width: 100%">
        <el-table-column prop="courseId" label="课程代码" min-width="120">
        </el-table-column>
        <el-table-column prop="subjectName" label="名称" min-width="200">
        </el-table-column>
        <el-table-column prop="collegeName" label="开课学院" min-width="150">
        </el-table-column>
        <el-table-column prop="teacherName" label="任课教师" min-width="130"> </el-table-column>
        <el-table-column label="选课人数" min-width="200" align="center">
          <template slot-scope="scope">
            <p style="width:70%;margin:10px auto">
              <el-progress
                :show-text="false"
                :status="
                  scope.row.studentNumb / scope.row.totalNumb > 1 ? 'warning' : null
                "
                :percentage="
                  scope.row.studentNumb / scope.row.totalNumb > 1
                    ? 100
                    : (scope.row.studentNumb / scope.row.totalNumb) * 100
                "
              ></el-progress>
            </p>
            <span>{{ scope.row.studentNumb }}/{{ scope.row.totalNumb }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-popconfirm
              title="退课操作无法撤回。确认是否选择退课？"
              @confirm="withdraw(scope.row.courseId)"
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
            :autosize="{ minRows: 4, maxRows: 12}"
            type="textarea"
            placeholder="请输入内容"
            v-model="withdrawForm.content"
          >
          </el-input>
          <span style="float:right;margin-right:10px">当前字数：{{withdrawForm.content.length}}</span>
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
      courses: [],
      termId: null,
      cacheTerm: "",
      terms: { data: [], currentPage: 0, totalPage: 1 },
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
  mounted() {
    that = this;
    this.searchTerm("");
    this.selectCourse(1);
  },
  methods: {
    withdraw(id) {
      this.loadingWithdraw = true;
      this.axios({
        method:'get',
        url:'/course/withdraw/judge',
        params:{
          courseId:id
        }
      }).then(res=>{
        if(res.data.code==200){
          this.loadingWithdraw = false;
          this.successWithdraw();
          this.$router.go(0)
        } else {
          this.withdrawForm.courseId=id
          this.withdrawFormShow = true;
          this.loadingWithdraw = false;
        }
      })
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
      this.axios({
        method:'post',
        url:'/apply/add',
        data:{
          type:'Withdraw',
          courseId:this.withdrawForm.courseId,
          content:this.withdrawForm.content
        }
      }).then(res=>{
        this.successApplyWithdraw()
      })
      this.withdrawFormShow = false
    },
    changePage(newPage) {
      this.selectCourse(newPage)
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
    searchTerm(query) {
      this.terms = { data: [], currentPage: 0, totalPage: 1 };
      this.cacheTerm = query;
      this.termLoading = true;
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
          this.termLoading = false;
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
.title {
  font-size: 18px;
  margin-left: 10px;
}
.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}
.table-container {
  margin-top: 30px;
}
</style>
