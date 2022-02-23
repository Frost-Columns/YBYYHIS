<template>
  <div class="background">
    <div class="perm">
      <el-container style="width: 102.5%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >权限管理</span>
            </el-col>
            <el-col :md="2" style="margin: 11px">
              <el-checkbox-group v-model="select.stateList">
                <el-checkbox label="200">启用</el-checkbox>
                <el-checkbox label="201">停用</el-checkbox>
              </el-checkbox-group>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getRoleList()">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table
              :data="rolelist"
              stripe
              style="width: 100%">
            <el-table-column
                prop="rolename"
                label="职位名">
            </el-table-column>
            <el-table-column
                prop="rolenamezh"
                label="职位名(ZH)">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="权限管理">
              <template slot-scope="scope">
                <el-button @click="rolePerm(scope.row)" v-if="scope.row.state!=201" type="text" size="small">
                  权限管理
                </el-button>
                <el-button @click="rolePerm(scope.row)" v-if="scope.row.state==201" type="text" size="small" disabled>
                  权限管理
                </el-button>
              </template>
            </el-table-column>
            <el-table-column
                fixed="right"
                label="菜单管理">
              <template slot-scope="scope">
                <el-button @click="roleMenu(scope.row)" v-if="scope.row.state!=201" type="text" size="small">
                  菜单管理
                </el-button>
                <el-button @click="roleMenu(scope.row)" v-if="scope.row.state==201" type="text" size="small" disabled>
                  菜单管理
                </el-button>
              </template>
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
                <el-button @click="modifyRole(scope.row)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  编辑
                </el-button>
                <el-button @click="updateStateRole(scope.row)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  停用
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>
        </el-main>
      </el-container>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="rolefrom" ref="rolefrom" :rules="rules" label-position="right">
          <el-form-item prop="rolename" label="职位名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="rolefrom.rolename" placeholder="职位名"></el-input>
          </el-form-item>
          <el-form-item prop="rolenamezh" label="职位名(ZH)" style="width: 80%; margin: 20px 10%">
            <el-input v-model="rolefrom.rolenamezh" placeholder="职位名(ZH)"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('rolefrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>

      <el-dialog
          title="权限管理"
          :visible.sync="RolePermDialogVisible"
          width="635px"
          center>
        <el-transfer :titles="['所有权限','拥有权限']" v-model="rolePermList" :data="permlist"></el-transfer>
        <span slot="footer" class="dialog-footer">
          <el-button @click="RolePermDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="rolePermSubmit()">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
          title="菜单管理"
          :visible.sync="RoleMenuDialogVisible"
          width="635px"
          center>
        <el-transfer :titles="['所有菜单','可视菜单']" v-model="roleMenuList" :data="menulist"></el-transfer>
        <span slot="footer" class="dialog-footer">
          <el-button @click="RoleMenuDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="roleMenuSubmit()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "Perm",
  data() {
    return {
      title: '',
      drawer: false,
      RolePermDialogVisible: false,
      RoleMenuDialogVisible: false,
      rolelist: [],
      permlist: [],
      menulist: [],
      roleid: '',
      rolePermList: [],
      roleMenuList: [],
      rolefrom: {
        roleid: 0,
        rolename: '',
        rolenamezh: ''
      },
      select: {
        stateList: ['200']
      },
      rules: {
        rolename: [
          {required: true, message: '请输入职位名', trigger: 'blur'}
        ],
        rolenamezh: [
          {required: true, message: '请输入职位名(ZH)', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getRoleList() {
      var params = new URLSearchParams();
      params.append("statelist", this.select.stateList)
      axios.post("/RoleList", params).then((res) => {
        this.rolelist = res.data.data;
      })
    },
    add() {
      this.title = '角色添加';
      if (this.$refs['rolefrom'] !== undefined) {
        this.$refs['rolefrom'].resetFields();
      }
      for (var i in this.rolefrom) {
        this.rolefrom[i] = '';
      }
      this.drawer = true;
    },
    modifyRole(row) {
      this.title = '角色信息修改';
      if (this.$refs['rolefrom'] !== undefined) {
        this.$refs['rolefrom'].resetFields();
      }
      this.rolefrom = Object.assign({}, row);  //深拷贝
      this.drawer = true;
    },
    updateStateRole(row) {
      this.$confirm('确认要停用该角色？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post("/CancellationRole", row).then((res) => {
          if (res.data.code == 200) {
            this.$message.success(res.data.msg);
            this.getRoleList();
          } else {
            this.$message.error(res.data.msg);
          }
        })
      }).catch(() => {
        return
      })
    },
    onSubmit(rfrom) {
      this.$refs[rfrom].validate((valid) => {
        if (valid) {
          var url = '';
          if (this.rolefrom.roleid == '') {
            url = '/InsertRole';
          } else {
            url = '/UpdateRole';
          }
          axios.post(url, this.rolefrom).then((res) => {
            if (res.data.data) {
              this.$message.success(res.data.msg);
              this.getRoleList();
              this.drawer = false;
            } else {
              this.$message.error(res.data.msg);
            }
          })
        } else {
          return false;
        }
      });
    },
    reset() {
      this.select.stateList = ['200'];
      this.getRoleList();
    },
    rolePerm(row){
      this.rolePermList = [];
      axios.post("/RolePerm",row).then((res) => {
        for(var i in res.data.data){
          this.rolePermList.push(res.data.data[i].permId)
        }
      })
      this.roleid = row.roleid;
      this.RolePermDialogVisible = true;
    },
    rolePermSubmit(){
      var params = new URLSearchParams();
      params.append("rpList",this.rolePermList)
      params.append("roleid",this.roleid)
      axios.post("/UpdateRolePerm",params).then((res) => {
        if (res.data.data) {
          this.$message.success(res.data.msg);
          this.RolePermDialogVisible = false;
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    roleMenu(row){
      this.roleMenuList = [];
      axios.post("/RoleMenu",row).then((res) => {
        for(var i in res.data.data){
          this.roleMenuList.push(res.data.data[i].menuId)
        }
      })
      this.roleid = row.roleid;
      this.RoleMenuDialogVisible = true;
    },
    roleMenuSubmit(){
      var params = new URLSearchParams();
      params.append("rmList",this.roleMenuList)
      params.append("roleid",this.roleid)
      axios.post("/UpdateRoleMenu",params).then((res) => {
        if (res.data.data) {
          this.$message.success(res.data.msg);
          this.RoleMenuDialogVisible = false;
        } else {
          this.$message.error(res.data.msg);
        }
      })
    }
  },
  mounted() {
    axios.get("/PermAndMenuList").then((res) => {
      for(var i in res.data.data.perm){
        this.permlist.push({
          key: res.data.data.perm[i].permid,
          label: res.data.data.perm[i].permnamezh
        })
      }
      for(var i in res.data.data.menu){
        this.menulist.push({
          key: res.data.data.menu[i].menuid,
          label: res.data.data.menu[i].menunamezh
        })
      }
    })
    this.getRoleList()
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