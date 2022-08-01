<template>
  <div class="container">
    <div class="title">我的申请</div>
    <el-divider></el-divider>
    <div class="table-container">
      <el-table :data="applies" style="width: 100%">
        <el-table-column width="100" align="center">
          <template slot-scope="scope">
            <el-tag class="el-icon-right" v-if="scope.row.fromName!=userInfo.name"></el-tag>
            <el-tag type="info" class="el-icon-back" v-else></el-tag>
          </template>
        </el-table-column>
        <el-table-column label="申请类型" width="100" align="center">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.type=='选课申请'">{{scope.row.type}}</el-tag>
            <el-tag type="warning" v-else-if="scope.row.type=='退课申请'">{{scope.row.type}}</el-tag>
            <el-tag type="danger" v-else-if="scope.row.type=='成绩申请'">{{scope.row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fromName" label="申请用户" width="150" align="center">
        </el-table-column>
        <el-table-column prop="toName" label="接收用户或学院" width="150" align="center">
        </el-table-column>
        <el-table-column label="申请内容" min-width="300">
          <template slot-scope="scope">
            <el-popover v-if="scope.row.content.length>30"
              placement="top-start"
              width="500"
              trigger="hover"
              :content="scope.row.content">
              <span slot="reference">{{scope.row.content.slice(0,30)}}... </span>
            </el-popover>
            <span v-else>{{scope.row.content}}</span>
          </template>  
        </el-table-column>
        <el-table-column prop="applyTime" label="申请时间" width="150" align="center">
        </el-table-column>
        <el-table-column label="处理时间" width="150" align="center">
          <template slot-scope="scope">
            {{scope.row.handleTime==null?'——':scope.row.handleTime}}
          </template>
        </el-table-column>
        <el-table-column label="处理意见" width="150" align="center">
          <template slot-scope="scope">
            <template v-if="scope.row.fromName==userInfo.name">
              {{scope.row.isAgree==null?'——':scope.row.isAgree==true?'同意':'拒绝'}}
            </template>
            <template v-else>
              <el-button :disabled="scope.row.isAgree!=null" size="small">{{scope.row.isAgree==null?'&#12288;&#12288;':scope.row.isAgree==true?'同意':'拒绝'}}</el-button>
            </template>
          </template>
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
  data(){
    return{
      applies:[
        {type:'选课申请',fromName:'张三',toName:'李四',content:"我的家乡是美丽的泉城济南。济南风景优美，景色宜人。但济南夜晚更是让人眼花缭乱。每当夜幕降临，泉城广场灯火辉煌，光彩夺目。广场四周，彩灯勾画出一座座高楼大厦的雄伟轮廓。音乐喷泉随着灯光的变幻，各种造型的水柱喷涌而出。泉城路两旁时而银装素裹，时而火树银花。绚丽多彩的广告、五光十色的霓虹灯把大街装扮得更加繁华、明亮。顺河高架桥犹如一条奔腾的巨龙盘旋在半空中。经十路上流光溢彩，川流不息的汽车灯光闪烁，像银河从天而降。大明湖畔，满天的繁星和湖边的明灯互相倒映在水中，令人心旷神怡。微风吹过波光粼粼，好似千万条银鱼在游动。站在千佛山上俯瞰济南的夜色，万家灯光和天上的星光连成一片，魅力无比。",applyTime:'2022/07/05 14:00:00',handleTime:'2022/07/25 15:00:00',isAgree:true},
        {type:'成绩申请',fromName:'王五',toName:'张三',content:'希望修改成绩',applyTime:'2022/07/20 17:00:00',handleTime:null,isAgree:null}
      ],
      pageInfo: {
        currentPage: 4,
        totalPage: 10,
        pageSize: 20,
      },
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  methods:{
    changePage(newPage) {
      console.log(newPage);
    },
  }
};
</script>

<style scoped>
.title {
  font-size: 18px;
  margin-left: 10px;
  display: flex;
  justify-content: space-between;
}
.title span {
  margin-right: 100px;
}
.page-divider {
  float: right;
  margin-top: 10px;
  margin-right: 30px;
}
</style>
