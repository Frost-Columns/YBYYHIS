<template>
  <div class="background">
    <div class="dept">
      <el-container style="width: 102.5%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="3">
              <el-input v-model="select.dname" placeholder="名称关键字" style="width: 95%"></el-input>
            </el-col>
            <el-col :md="2">
              <el-select v-model="select.dtId" placeholder="科目" style="width: 95%">
                <el-option
                    v-for="item in depttype"
                    :key="item.dtid"
                    :label="item.dtname"
                    :value="item.dtid">
                </el-option>
              </el-select>
            </el-col>
            <el-col :md="4" style="margin: 11px">
              <el-checkbox-group v-model="select.stateList">
                <el-checkbox label="200" >启用</el-checkbox>
                <el-checkbox label="202">暂停</el-checkbox>
                <el-checkbox label="201">停用</el-checkbox>
              </el-checkbox-group>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getDeptList(1)">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table
              :data="deptlist"
              stripe
              style="width: 100%">
            <el-table-column
                prop="dname"
                label="科室名">
            </el-table-column>
            <el-table-column
                prop="deptProfile"
                label="科室介绍">
            </el-table-column>
            <el-table-column
                prop="loc"
                label="地址">
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
                <el-button @click="modifyDept(scope.row)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  编辑
                </el-button>
                <el-button @click="updateStateDept(scope.row,1)"
                           v-if="scope.row.state==200"
                           type="text"
                           size="small"
                           slot="reference">
                  暂停
                </el-button>
                <el-button @click="updateStateDept(scope.row,3)"
                           v-if="scope.row.state==202"
                           type="text"
                           size="small"
                           slot="reference">
                  启用
                </el-button>
                <el-button @click="updateStateDept(scope.row,2)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  停用
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
              background
              layout="prev, pager, next"
              :page-size="10"
              :total="total"
              @current-change="getDeptList">
          </el-pagination>
          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>
        </el-main>
      </el-container>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="deptfrom" ref="deptfrom" :rules="rules" label-position="right">
          <el-form-item prop="dname" label="科室名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="deptfrom.dname" placeholder="科室名"></el-input>
          </el-form-item>
          <el-form-item prop="dtId" label="科目" style="width: 80%; margin: 20px 10%">
            <el-select v-model="deptfrom.dtId" placeholder="科目" style="width: 100%">
              <el-option
                  v-for="item in depttype"
                  :key="item.dtid"
                  :label="item.dtname"
                  :value="item.dtid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="loc" label="科室地址" style="width: 80%; margin: 20px 10%">
            <el-input v-model="deptfrom.loc" placeholder="科室地址"></el-input>
          </el-form-item>
          <el-form-item prop="deptProfile" label="科室简介" style="width: 80%; margin: 20px 10%">
            <el-input
                type="textarea"
                placeholder="科室简介"
                v-model="deptfrom.deptProfile"
                maxlength="120"
                show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('deptfrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>


<script>
export default {
  name: "Dept",
  data() {
    return {
      index: 1,
      total: 0,
      drawer: false,
      title: '',
      depttype: [],
      deptlist: [],
      deptfrom: {
        deptid: 0,
        dname: '',
        dtId: '',
        deptProfile: '',
        loc: ''
      },
      select: {
        dname: '',
        dtId: '',
        stateList: ['200']
      },
      rules: {
        dname: [
          {required: true, message: '请输入科室名', trigger: 'blur'}
        ],
        dtId: [
          {required: true, message: '请选择科目', trigger: 'blur'}
        ],
        deptProfile: [
          {required: true, message: '请输入科室简介', trigger: 'blur'}
        ],
        loc: [
          {required: true, message: '请输入科室地址', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getDeptList(index){
      var params = new URLSearchParams();
      params.append("index",index)
      params.append("dname",this.select.dname)
      params.append("dtId",this.select.dtId)
      params.append("statelist",this.select.stateList)
      axios.post("/deptlist",params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.deptlist = res.data.data.records;
      })
    },
    add() {
      this.title = '科室添加';
      if(this.$refs['deptfrom']!==undefined){
        this.$refs['deptfrom'].resetFields();
      }
      for (var i in this.deptfrom) {
        this.deptfrom[i] = '';
      }
      this.deptfrom.dtId = this.select.dtId;
      this.drawer = true;
    },
    modifyDept(row) {
      this.title = '科室信息修改';
      if(this.$refs['deptfrom']!==undefined){
        this.$refs['deptfrom'].resetFields();
      }
      this.deptfrom = Object.assign({}, row);  //深拷贝
      this.drawer = true;
    },
    onSubmit(dfrom) {
      this.$refs[dfrom].validate((valid) => {
        if (valid) {
          var url = '';
          if (this.deptfrom.deptid == '') {
            url = '/InsertDept';
          } else {
            url = '/UpdateDept';
          }
          axios.post(url, this.deptfrom).then((res) => {
            if (res.data.data) {
              this.$message.success(res.data.msg);
              this.getDeptList(this.index);
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
    updateStateDept(row, state) {
      var url = '';
      var title = '';
      if (state == 1) {
        title = '确认要暂停该科室？';
        url = '/FreezeDept';
      } else if (state == 2) {
        title = '确认要停用该科室？';
        url = '/CancellationDept';
      }else {
        title = '确认要启用该科室？';
        url = '/ThawDept';
      }
      this.$confirm(title, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(url, row).then((res) => {
          if(res.data.code == 200) {
            this.$message.success(res.data.msg);
            this.getDeptList(this.index);
          }else {
            this.$message.error(res.data.msg);
          }
        })
      }).catch(() => {
        return
      })
    },
    reset(){
      for (var i in this.select) {
        this.select[i] = '';
      }
      this.select.stateList = ['200'];
      this.getDeptList(1);
    }
  },
  mounted() {
    axios.get("/DeptTypeList").then((res) => {
      this.depttype = res.data.data;
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

.row-bg {
  padding: 10px 0px;
  background-color: #e5f2ff;
}
</style>