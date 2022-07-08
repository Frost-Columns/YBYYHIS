<template>
  <div class="background">
    <el-header style="text-align: right; font-size: 12px">
      <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>王小虎</span>
    </el-header>
    <el-container style="height: 100%; border: 1px solid #eee">

      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu router :default-openeds="['2']">
          <el-menu-item v-for="item in menu" :index="item.url" :class="$route.path==item.url?'is-active':''">
            <template slot="title"><i :class="item.icon"></i>{{ item.menunamezh }}</template>
            <!--            <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path" :class="$route.path==item2.path?'is-active':''">{{item2.name}}-{{index2}}</el-menu-item>-->
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <router-view></router-view>
      </el-container>

    </el-container>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    return {
      menu: []
    }
  },
  methods: {},
  mounted() {
    axios.post('/menu').then((res) => {
      this.menu = res.data.data;
    });
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.background {
  height: 93%;
}
</style>