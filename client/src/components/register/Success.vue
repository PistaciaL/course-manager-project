<template>
  <el-result icon="success" title="注册成功" :subTitle="prefixStr + dot" />
</template>

<script>
export default {
  data() {
    return {
      prefixStr:'注册成功,正在跳转至登陆页面',
      dot: '...'
    }
  },
  mounted(){
    this.changeDot()
    setTimeout(() => {
      this.$router.push("/")
    }, 3000);
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