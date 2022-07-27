<template>
  <div class="container">
    <div class="title">我的课程</div>
    <el-divider></el-divider>
    <template v-if="userInfo.identity=='学生'">
      <Student/>
    </template>
    <template v-if="userInfo.identity=='教师'">
      <Teacher/>
    </template>
  </div>
</template>

<script>
import Teacher from '@/components/course/Teacher.vue'
import Student from '@/components/course/Student.vue'
export default {
  components: { Teacher, Student },
  computed:{
    userInfo(){
      return this.$store.state.userInfo
    }
  }
}
</script>

<style scoped>
.title {
  font-size: 18px;
  margin-left: 10px;
}

</style>