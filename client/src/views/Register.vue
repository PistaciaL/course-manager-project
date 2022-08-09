<template>
  <div class="container">
    <div class="step-line">
      <el-steps :active="stepActive" finish-status="success" align-center>
        <el-step title="验证学工号"></el-step>
        <el-step title="完善信息"></el-step>
        <el-step title="注册完成"></el-step>
      </el-steps>
    </div>
    <div class="form-body">
      <div v-if="stepActive==0"><Step1/></div>
      <div v-else-if="stepActive==1"><Step2/></div>
      <div v-else><Success/></div>
    </div>
  </div>
</template>

<script>
import Step1 from '@/components/register/VerifyInfo.vue';
import Step2 from '@/components/register/FinishInfo.vue';
import Success from '@/components/register/Success.vue';
export default {
  components: { Step1, Step2, Success },
  data() {
    return {
      stepActive: 0
    };
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log('验证表单')
          this.axios({
            method: 'get',
            url: '/register',
            data: this.form
          }).then(function(res){
            console.log(res)
          })
        } else {
          return false;
        }
      });
    },
    setStepActive(newStepAvtive){
      this.stepActive = newStepAvtive
    }
  },
};
</script>

<style scoped>
.container {
  width: 800px;
  margin: 100px auto;
}
.form-body {
  margin: 50px auto;
  width: 450px;
}
</style>