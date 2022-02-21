<template>
  <div class="background">
    <div class="user">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="3">
            <el-input v-model="select.username" placeholder="名称关键字" style="width: 95%"></el-input>
            </el-col>
            <el-col :md="2">
              <el-select v-model="select.roleId" placeholder="职位" style="width: 95%">
                <el-option
                    v-for="item in rolelist"
                    :key="item.roleid"
                    :label="item.rolenamezh"
                    :value="item.roleid">
                </el-option>
              </el-select>
            </el-col>
            <el-col :md="2">
              <el-select v-model="select.deptId" placeholder="科室" style="width: 95%">
                <el-option
                    v-for="item in deptlist"
                    :key="item.deptid"
                    :label="item.dname"
                    :value="item.deptid">
                </el-option>
              </el-select>
            </el-col>
            <el-col :md="4" style="margin: 11px">
              <el-checkbox-group v-model="select.checkList">
                <el-checkbox label="100" >正常</el-checkbox>
                <el-checkbox label="102">冻结</el-checkbox>
                <el-checkbox label="101">注销</el-checkbox>
              </el-checkbox-group>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getUserList(1)">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
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
              <template slot-scope="scope">{{ scope.row.sex | sexFromat }}</template>
            </el-table-column>
            <el-table-column
                prop="roleId"
                label="职位"
                width="180">
              <template slot-scope="scope">{{ getRoleById(scope.row.roleId) }}</template>
            </el-table-column>
            <el-table-column
                prop="deptId"
                label="科室"
                width="180">
              <template slot-scope="scope">{{ getDnameById(scope.row.deptId) }}</template>
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间"
                width="180">
              <template slot-scope="scope">{{ scope.row.sex | dateFromat }}</template>
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
                <el-button @click="modifyUser(scope.row)"
                           v-if="scope.row.state!=101"
                           type="text"
                           size="small">
                  编辑
                </el-button>
                <el-button @click="updateStateUser(scope.row,1)"
                           v-if="scope.row.state==100"
                           type="text"
                           size="small"
                           slot="reference">
                  冻结
                </el-button>
                <el-button @click="updateStateUser(scope.row,3)"
                           v-if="scope.row.state==102"
                           type="text"
                           size="small"
                           slot="reference">
                  解冻
                </el-button>
                <el-button @click="updateStateUser(scope.row,2)"
                           v-if="scope.row.state!=101"
                           type="text"
                           size="small">
                  注销
                </el-button>
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
        </el-main>
      </el-container>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="userfrom" ref="userfrom" :rules="rules">
          <el-form-item prop="username">
            <el-input v-model="userfrom.username" placeholder="账号" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item prop="usernamezh">
            <el-input v-model="userfrom.usernamezh" placeholder="姓名" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item prop="sex">
            <el-radio-group v-model="userfrom.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item required>
            <el-col>
              <el-form-item prop="birthday">
                <el-date-picker type="date" placeholder="生日" :picker-options="pickerOptions" v-model="userfrom.birthday"
                                style="width: 80%;"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item prop="role">
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
            <el-select v-model="userfrom.dtId" placeholder="科目" style="width: 80%;" @change="getDept">
              <el-option
                  v-for="item in depttype"
                  :key="item.dtid"
                  :label="item.dtname"
                  :value="item.dtid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="dept">
            <el-select v-model="userfrom.deptId" placeholder="科室" style="width: 80%;">
              <el-option
                  v-for="item in dtdept"
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
          <el-form-item>
            <el-button type="primary" @click="onSubmit('userfrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      userlist: [],
      index: 1,
      total: 0,
      drawer: false,
      visible: false,
      visible1: false,
      visible2: false,
      title: '',
      rolelist: [],
      dtlist: [],
      depttype: [],
      dtdept: [],
      deptlist: [],
      userfrom: {
        userid: 0,
        username: '',
        usernamezh: '',
        sex: 1,
        birthday: '',
        roleId: '',
        deptId: '',
        dtId: '',
        uprofile: ''
      },
      select: {
        username: '',
        roleId: '',
        deptId: '',
        checkList: ['100','102']
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        usernamezh: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        birthday: [
          {required: true, message: '请输入生日', trigger: 'blur'}
        ],
        role: [
          {required: true, message: '请选择职位', trigger: 'blur'}
        ],
        dept: [
          {required: true, message: '请选择科室', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    getUserList(index) {
      var params = new URLSearchParams();
      params.append("index",index)
      params.append("username",this.select.username)
      params.append("roleid",this.select.roleId)
      params.append("deptid",this.select.deptId)
      params.append("checklist",this.select.checkList)
      axios.post("/userlist",params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.userlist = res.data.data.records;
      })
    },
    getDnameById(deptid) {
      for (var i in this.deptlist) {
        if (this.deptlist[i].deptid == deptid) {
          return this.deptlist[i].dname;
        }
      }
      return "无";
    },
    getRoleById(roleid) {
      for (var i in this.rolelist) {
        if (this.rolelist[i].roleid == roleid) {
          return this.rolelist[i].rolenamezh;
        }
      }
      return "无";
    },
    getDept(dtId) {
      this.dtdept = [];
      for (var i in this.deptlist) {
        if (this.deptlist[i].dtId == dtId) {
          this.dtdept.push(this.deptlist[i]);
        }
      }
    },
    add() {
      this.title = '添加账户';
      for (var i in this.userfrom) {
        this.userfrom[i] = '';
      }
      this.userfrom.sex = 1;
      this.drawer = true;
    },
    modifyUser(row) {
      this.userfrom = Object.assign({}, row);  //深拷贝
      this.title = '修改账户';
      this.getDept(this.userfrom.dtId);
      this.drawer = true;
    },
    onSubmit(userfrom) {
      this.$refs[userfrom].validate = function (valid) {
        console.log('123')
        if (valid) {
          var url = '';
          if (this.userfrom.userid == '') {
            url = '/InsertUser';
          } else {
            url = '/UpdateUser';
          }
          axios.post(url, this.userfrom).then((res) => {
            if (res.data.data) {
              this.$message.success(res.data.msg);
              this.getUserList(this.index);
              this.drawer = false;
            } else {
              this.$message.error(res.data.msg);
            }
          })
        } else {
          return false;
        }
      }
    },
    updateStateUser(row, state) {
      var url = '';
      var title = '';
      if (state == 1) {
        title = '确认要冻结该账户？';
        url = '/FreezeUser';
      } else if (state == 2) {
        title = '确认要注销该账户？';
        url = '/CancellationUser';
      }else {
        title = '确认要解冻该账户？';
        url = '/ThawUser';
      }
      this.$confirm(title, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(url, row).then((res) => {
          this.getUserList(this.index);
        })
      }).catch(() => {
        return
      })
    },
    reset(){
      for (var i in this.select) {
        this.select[i] = '';
      }
      this.select.checkList = ['100','102'];
      this.getUserList(1);
    }
  },
  mounted() {
    axios.get("/RoleAndDeptList").then((res) => {
      this.depttype = res.data.data.depttype;
      this.rolelist = res.data.data.role;
      this.deptlist = res.data.data.dept;
    })
    this.getUserList(this.index);
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

.row-bg {
  padding: 10px 0px;
  background-color: #e5f2ff;
}
</style>