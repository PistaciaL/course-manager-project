<template>
  <el-result icon="success" title="注册成功" :subTitle="'正在自动登录中，请稍后' + dot" />
</template>

<script>
export default {
  data() {
    return {
      dot: '...'
    }
  },
  mounted(){
    this.changeDot()
    console.log("登录")
  },
  methods: {
    changeDot() {
      var that = this
      setTimeout(() => {
        if (this.dot.length == 5) {
          this.dot = "."
        } else {
          this.dot += '.'
        }
        that.changeDot()
      }, 500)

    }
  }
}
</script>

<style>
</style>