<template>
  <div class="container">
    <el-row>
      <el-col :span="6">
        <MyInfo/>
      </el-col>
      <el-col :span="12">
        <Schedule/>
      </el-col>
      <el-col :span="6">
        <QuickOperation/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import MyInfo from './home/MyInfo.vue';
import QuickOperation from './home/QuickOperation.vue';
import Schedule from './home/Schedule.vue';
export default {
  components: { Schedule, MyInfo, QuickOperation },
};
</script>

<style scoped>
.container {
  background: #f9f9f9;
  padding: 20px;
}
.content {
  margin: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  min-height: 190px;
  border-radius: 15px;
  background: #fff;
}
</style>