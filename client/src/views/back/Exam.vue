<template>
  <div class="container">
    <div class="title">我的考试</div>
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
    <div class="table-container">
      <el-table :data="exams">
        <el-table-column prop="subjectName" label="科目名称" min-width="300">
        </el-table-column>
        <el-table-column prop="roomName" label="考试教室" min-width="300"> </el-table-column>
        <el-table-column prop="startTime" label="考试时间" min-width="360">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
      exams:[
        {subjectName:'离散数学', startTime:'2022/07/05 14:00:00',roomName:'教学西楼A201'}
      ]
    };
  },
  mounted() {
    console.log("加载全部学期");
  },
  methods: {
    searchItems(query) {
      this.termLoading = true;
      console.log("根据" + query + "搜索学期");
    },
  },
};
</script>

<style>
.title {
  font-size: 18px;
  margin-left: 10px;
}
.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}
.el-table{
  min-width: 960px;
  width: 100%;
  margin: auto;
}

.term-select{
  margin-left: 50px;
}
</style>
