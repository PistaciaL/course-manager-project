<template>
    <el-dialog title="注册新的课程" :visible.sync="addCourseFormShow">
      <el-form ref="form" :model="form" label-width="250px" :rules="rules" 
          :hide-required-asterisk="true">
      <el-form-item label="请选择开课学期" prop="termId" class="term-form">
        <el-select
          v-model="form.termId"
          :remote-method="searchTerm"
          placeholder="请选择开课学期"
          :loading="termLoading"
          filterable
          remote
          v-el-select-loadmore="loadTerm"
          :clearable="true"
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
            style="text-align:center"
          >
          <div v-show="terms.currentPage<terms.totalPage"><i class="el-icon-loading"/><span value="">加载中</span></div>
          <div v-show="terms.currentPage>=terms.totalPage"><span value="">已经全部加载完毕</span></div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="请选择开设课程" prop="subjectId">
        <el-select
          v-model="form.collegeId"
          :remote-method="searchCollege"
          placeholder="全部学院"
          :loading="collegeLoading"
          filterable
          remote
          v-el-select-loadmore="loadCollege"
          :clearable="true"
        >
          <el-option
            v-for="college in colleges.data"
            :key="college.id"
            :label="college.name"
            :value="college.id"
          >
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align:center"
          >
          <div v-show="colleges.currentPage<colleges.totalPage"><i class="el-icon-loading"/><span value="">加载中</span></div>
          <div v-show="colleges.currentPage>=colleges.totalPage"><span value="">已经全部加载完毕</span></div>
          </el-option>
        </el-select>
        <el-divider direction="vertical"></el-divider>
        <el-select
          v-model="form.subjectId"
          :remote-method="searchSubject"
          placeholder="请选择科目名称"
          :loading="subjectLoading"
          filterable
          remote
          v-el-select-loadmore="loadSubject"
          :clearable="true"
        >
          <el-option
            v-for="subject in subjects.data"
            :key="subject.id"
            :label="subject.name"
            :value="subject.id"
          >
          </el-option>
          <el-option
            :disabled="true"
            value="-1"
            key="-1"
            style="text-align:center"
          >
          <div v-show="subjects.currentPage<subjects.totalPage"><i class="el-icon-loading"/><span value="">加载中</span></div>
          <div v-show="subjects.currentPage>=subjects.totalPage"><span value="">已经全部加载完毕</span></div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="请输入最大上课人数">
        <el-input-number v-model="form.maxStudent" controls-position="right" :min="20" :max="160" :step="5"></el-input-number>
      </el-form-item>
    </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCourseFormShow = false">取 消</el-button>
        <el-button type="primary" @click="submit"
          >确 定</el-button
        >
      </div>
    </el-dialog>
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
              that.terms.currentPage <
                that.terms.totalPage
            ) {
              binding.value();
            } else if (
              binding.expression == "loadSubject" &&
              that.subjects.currentPage <
                that.subjects.totalPage
            ) {
              binding.value();
            } else if(
              binding.expression == "loadCollege" &&
              that.colleges.currentPage <
                that.colleges.totalPage
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
      addCourseFormShow:false,
      form: {
        termId: "",
        subjectId: "",
        collegeId: "",
        maxStudent: 80,
      },
      rules: {
        termId: [
          { required: true, message: '请选择开课学期', trigger: 'change' }
        ],
        subjectId: [
          { required: true, message: '请选择开设的课程', trigger: 'change' }
        ],
      },
      cacheValues:{
        term:'',
        college:'',
        subject:''
      },
      terms:{data:[],currentPage:0,totalPage:1},
      subjects:{data:[],currentPage:0,totalPage:1},
      colleges:{data:[],currentPage:0,totalPage:1},
      subjectLoading:false,
      termLoading:false,
      collegeLoading:false
    };
  },
  mounted(){
    that = this;
    this.searchTerm('') 
    this.searchCollege('')
  },
  watch: {
    "form.collegeId": {
      handler(newValue) {
        this.searchSubject('')
      },
      deep: true,
      immediate: true,
    },
  },
  methods:{
    submit(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios({
            method:'post',
            url:'/course/add',
            data:{
              termId:this.form.termId,
              subjectId:this.form.subjectId,
              maxStudent:this.form.maxStudent
            }
          }).then(res=>{
            if(res.data.code==200){
              this.$message.success("注册成功")
              this.addCourseFormShow=false
              setTimeout(() => {
                this.$router.go(0)
              }, 1000);
            } else {
              this.$message.warning("注册失败")
            }
          })
        } else {
          return false;
        }
      });
    },
    searchTerm(query){
      this.terms={data:[],currentPage:0,totalPage:1}
      this.cacheValues.term=query
      this.termLoading=true
      this.loadTerm(query)
    },
    searchSubject(query){
      this.subjects={data:[],currentPage:0,totalPage:1}
      this.cacheValues.subject=query
      this.subjectLoading=true
      this.loadSubject(query)
    },
    searchCollege(query){
      this.colleges={data:[],currentPage:0,totalPage:1}
      this.cacheValues.college=query
      this.collegeLoading=true
      this.loadCollege(query)
    },
    loadTerm(){
      const name=this.cacheValues.terms
      this.axios({
        method:'get',
        url:'/term/search',
        params:{
          'name':name,
          page:this.terms.currentPage+1,
          pageSize:10
        }
      }).then(res=>{
        if(res.data.code==200){
          this.termLoading=false
          this.terms.data=this.terms.data.concat(res.data.data.data)
          this.terms.currentPage=res.data.data.currentPage
          this.terms.totalPage=res.data.data.totalPage
        }
      })
    },
    loadSubject(){
      const name=this.cacheValues.subject
      this.axios({
        method:'get',
        url:'/subject/search',
        params:{
          'name':name,
          page:this.subjects.currentPage+1,
          collegeId:this.form.collegeId
        }
      }).then(res=>{
        if(res.data.code==200){
          this.subjectLoading=false
          let that = this;
          res.data.data.data.forEach(function(x){
          that.subjects.data.push(x)
          });

          // this.subjects.data=this.subjects.data.concat(res.data.data.data)
          this.subjects.currentPage=res.data.data.currentPage
          this.subjects.totalPage=res.data.data.totalPage
        }
      })
    },
    loadCollege(){
      const name=this.cacheValues.college
      this.axios({
        method:'get',
        url:'/college/search',
        params:{
          'name':name,
          page:this.colleges.currentPage+1,
        }
      }).then(res=>{
        if(res.data.code==200){
          this.collegeLoading=false
          this.colleges.data=this.colleges.data.concat(res.data.data.data)
          this.colleges.currentPage=res.data.data.currentPage
          this.colleges.totalPage=res.data.data.totalPage
        }
      })
    }
  }
};
</script>

<style scoped>
</style>
