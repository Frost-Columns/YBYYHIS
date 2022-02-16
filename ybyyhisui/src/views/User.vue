<template>
  <div class="background">
    <div class="user">
      <el-table
          :data="userlist"
          stripe
          style="width: 100%">
        <el-table-column
            prop="username"
            label="账号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="usernamezh"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="sex"
            label="性别"
            width="180">
        </el-table-column>
        <el-table-column
            prop="rname"
            label="职位"
            width="180">
        </el-table-column>
        <el-table-column
            prop="dname"
            label="科室"
            width="180">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            :formatter="dataFormat(scope.row.createTime)"
            width="180">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="prev, pager, next"
          :page-size="10"
          :total="total"
          @current-change="getUserList()">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: "User",
  data() {
    return {
      userlist: [],
      index: 1,
      total: 0
    }
  },
  methods: {
    getUserList(index) {
      axios.get("/userlist/"+index).then((res) => {
        for (var i in res.data.records) {
          res.data.records[i].sex = res.data.records[i].sex == 1 ? '男' : '女';
          if (res.data.records[i].dname == '') {
            res.data.records[i].dname = '无';
          }
        }
        this.index = res.data.current;
        this.total = res.data.total;
        this.userlist = res.data.records;
      })
    },
    handleClick(row) {
      console.log(row);
    },
    dateFormat(date){
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    }
  },
  mounted() {
    this.getUserList(this.index)
  }
}
</script>

<style scoped>

</style>