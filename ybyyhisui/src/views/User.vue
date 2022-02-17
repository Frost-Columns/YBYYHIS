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
            <template slot-scope="scope">{{scope.row.sex | sexFromat}}</template>
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
              width="180">
            <template slot-scope="scope">{{scope.row.sex | dateFromat}}</template>
          </el-table-column>
          <el-table-column
              prop="uprofile"
              label="个人简介">
          </el-table-column>
          <el-table-column
              fixed="right"
              prop="sname"
              label="状态"
              width="80">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
              width="155">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
              <el-button @click="modifyUser(scope.row)" type="text" size="small">编辑</el-button>
              <el-button type="text" size="small">冻结</el-button>
              <el-button type="text" size="small">注销</el-button>
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
        <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>

        <el-drawer
            :title="title"
            :visible.sync="drawer">
          <el-form :model="userfrom" ref="userfrom">
            <el-form-item prop="name">
              <el-input v-model="userfrom.username" placeholder="账号" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item prop="name">
              <el-input v-model="userfrom.usernamezh" placeholder="姓名" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item prop="resource">
              <el-radio-group v-model="userfrom.sex">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item required>
              <el-col>
                <el-form-item prop="date1">
                  <el-date-picker type="date" placeholder="生日" :picker-options="pickerOptions" v-model="userfrom.birthday" style="width: 80%;"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-form-item>
            <el-form-item>
              <el-select v-model="userfrom.roleId" placeholder="职位" style="width: 80%;">
                <el-option
                    v-for="item in rolelist"
                    :key="item.roleid"
                    :label="item.rolenamezh"
                    :value="item.roleid">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="userfrom.deptId" placeholder="科室" style="width: 80%;">
                <el-option
                    v-for="item in deptlist"
                    :key="item.deptid"
                    :label="item.dname"
                    :value="item.deptid">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-input
                  type="textarea"
                  placeholder="个人简介"
                  v-model="userfrom.uprofile"
                  maxlength="120"
                  show-word-limit
                  style="width: 80%;">
              </el-input>
            </el-form-item>
          </el-form>
        </el-drawer>
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
      total: 0,
      drawer: false,
      title: '',
      rolelist: [],
      deptlist: [],
      userfrom: {
        userid: 0,
        username: '',
        usernamezh: '',
        sex: 1,
        birthday: '',
        roleId: '',
        deptId: '',
        uprofile: ''
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      }
    }
  },
  methods: {
    getUserList(index) {
      axios.get("/userlist/"+index).then((res) => {
        this.index = res.data.current;
        this.total = res.data.total;
        this.userlist = res.data.records;
      })
    },
    handleClick(row) {
      console.log(row);
    },
    add() {
      this.title = '添加账户';
      this.drawer = true;
    },
    modifyUser(row) {
      this.userfrom = Object.assign({},row);  //深拷贝
      this.title = '修改账户';
      this.drawer = true;
      console.log(this.userfrom)
    }
  },
  mounted() {
    this.getUserList(this.index)
    axios.get("/RoleAndDeptList").then((res) => {
      this.rolelist = res.data.role;
      this.deptlist = res.data.dept;
    })
  }
}
</script>

<style scoped>
.background {
  width: 100%;
  height: 100%;
}

.add {
  position: absolute;
  right: 40px;
  bottom: 100px;
}
</style>