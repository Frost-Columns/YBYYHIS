<template>
  <div class="background">
    <div class="dist">

      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>病例查询</span>
          </el-col>
        </el-row>
      </el-header>
      <el-container style="width: 104%; margin: 0px -20px">
        <el-aside width="400px" style="border-right: 1px solid #a5a5a5">
          <div class="head">
            <el-row>
              <el-col :md="10" style="margin: 11px">
                <el-input v-model="dtname" placeholder="目录" style="width: 95%; left: 20px"></el-input>
              </el-col>
              <el-col :md="3" style="margin: 11px">
                <el-button type="primary" @click="getDistTypeList()">查找</el-button>
              </el-col>
              <el-col :md="3" style="margin: 11px">
                <el-button @click="editDistTypeShow()">编辑</el-button>
              </el-col>
            </el-row>
          </div>
          <div class="main">
            <el-table
                :data="disttypelist"
                stripe
                highlight-current-row
                style="width: 95%; left: 20px"
                :row-style="{height: '53px'}"
                height="705"
                @row-click="dist">
              <el-table-column
                  prop="dtname"
                  label="疾病类别"
                  style="width: 50%">
              </el-table-column>
              <el-table-column
                  fixed="right"
                  label=""
                  width="155">
                <template slot="header" slot-scope="scope">
                  <div class="cell">
                    <el-button @click="addDistType()" type="text" size="mini">
                      <span>
                        <i class="el-icon-circle-plus"/>
                          新增
                      </span>
                    </el-button>
                  </div>
                </template>
                <template slot-scope="scope">
                  <div class="cell" v-if="show">
                    <el-button @click="editDistType(scope.row)" type="text" size="mini">
                      <i class="el-icon-edit-outline"/>
                    </el-button>
                    <el-popconfirm title="确定删除吗？" @confirm="delDistType(scope.row.dtid)">
                      <el-button slot="reference" type="text" size="mini">
                        <i class="el-icon-delete"/>
                      </el-button>
                    </el-popconfirm>
                  </div>
                  <div v-else><span>&nbsp;</span></div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-aside>
        <el-main>
          <div class="head">
            <el-row>
              <el-col :md="4" style="margin: 11px">
                <el-button disabled>当前疾病目录：{{ disttypename }}</el-button>
              </el-col>
              <el-col :md="6" style="margin: 11px">
                <el-input v-model="select.dname" placeholder="疾病名" style="width: 95%; left: 20px"></el-input>
              </el-col>
              <el-col :md="1" style="margin: 11px">
                <el-button type="primary" @click="getDistList(1)">查找</el-button>
              </el-col>
              <el-col :md="1" style="margin: 11px">
                <el-button @click="addDist()" :disabled="disttypename === ''">新增</el-button>
              </el-col>
            </el-row>
          </div>
          <div class="main">
            <el-table
                :data="distlist"
                style="width: 98%">
              <el-table-column
                  prop="distname"
                  label="疾病名">
              </el-table-column>
              <el-table-column
                  prop="distnamezh"
                  label="疾病中文名">
              </el-table-column>
              <el-table-column
                  label="操作"
                  width="155">
                <template slot-scope="scope">
                  <el-button @click="editDist(scope.row)"
                             v-if="scope.row.state!=201"
                             type="text"
                             size="small">
                    编辑
                  </el-button>
                  <el-popconfirm title="确定删除吗？" @confirm="delDist(scope.row.distid)">
                    <el-button slot="reference" type="text" size="small">
                      删除
                    </el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
                background
                layout="prev, pager, next"
                :page-size="10"
                :total="total"
                @current-change="getDistList">
            </el-pagination>
          </div>
        </el-main>
      </el-container>

      <el-dialog
          :title="dtTitle"
          :visible.sync="dialog"
          width="30%">
        <el-input v-model="dtinfo.dtname" placeholder="请输入目录名"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog = false">取 消</el-button>
          <el-button type="primary" @click="dtSubmit()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
          :title="distTitle"
          :visible.sync="distdialog"
          width="30%">

        <el-form :model="distinfo" ref="distinfo" :rules="rules" label-position="right">
          <el-form-item prop="distname" label="疾病名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="distinfo.distname" placeholder="请输入疾病名"></el-input>
          </el-form-item>
          <el-form-item prop="distnamezh" label="疾病中文名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="distinfo.distnamezh" placeholder="请输入中文名"></el-input>
          </el-form-item>
          <el-form-item prop="distResults" label="疾病描述" style="width: 80%; margin: 20px 10%">
            <el-input type="textarea" :rows="2" v-model="distinfo.distResults" placeholder="请输入疾病描述"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="distSubmit()">提交</el-button>
            <el-button @click="distdialog = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "Dist",
  data() {
    return {
      disttypelist: [],
      dtinfo: {
        dtid: 0,
        dtname: ''
      },
      dtname: '',
      show: false,
      dialog: false,
      dtTitle: '',
      index: 1,
      total: 0,
      select: {
        dtid: '',
        dname: ''
      },
      disttypename: '',
      distlist: [],
      distinfo: {
        distid: 0,
        distname: '',
        distnamezh: '',
        distResults: '',
        dtId: 0
      },
      distTitle: '',
      distdialog: false
    }
  },
  methods: {
    getDistTypeList() {
      var params = new URLSearchParams();
      params.append("dtname", this.dtname)
      axios.post("/disttypelist", params).then((res) => {
        this.disttypelist = res.data.data
      })
    },
    editDistTypeShow() {
      this.show = !this.show
    },
    addDistType() {
      this.dtTitle = '目录添加'
      for (var i in this.dtinfo) {
        this.dtinfo[i] = '';
      }
      this.dialog = true
    },
    editDistType(row) {
      this.dtTitle = '目录修改'
      this.dtinfo = Object.assign({}, row);  //深拷贝
      this.dialog = true
    },
    dtSubmit() {
      var url = ''
      if (this.dtinfo.dtid == '') {
        url = '/addDistType'
      } else {
        url = '/editDistType'
      }
      axios.post(url, this.dtinfo).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getDistTypeList();
          this.dialog = false;
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    delDistType(dtid) {
      var params = new URLSearchParams();
      params.append("dtid", dtid)
      axios.post("/delDistType", params).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getDistTypeList();
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    dist(row) {
      this.disttypename = row.dtname
      this.select.dtid = row.dtid
      this.getDistList(1)
    },
    getDistList(index) {
      var params = new URLSearchParams();
      params.append("index",index)
      params.append("dname",this.select.dname)
      params.append("dtid",this.select.dtid)
      axios.post("/distlist",params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.distlist = res.data.data.records;
      })
    },
    addDist() {
      this.distTitle = '疾病添加'
      for (var i in this.distinfo) {
        this.distinfo[i] = '';
      }
      this.distinfo.dtId = this.select.dtid
      this.distdialog = true
    },
    editDist(row) {
      this.distTitle = '疾病修改'
      this.distinfo = Object.assign({}, row);  //深拷贝
      this.distdialog = true
    },
    distSubmit() {
      var url = ''
      if (this.distinfo.distid == '') {
        url = '/addDist'
      } else {
        url = '/editDist'
      }
      axios.post(url, this.distinfo).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getDistList(this.index);
          this.distdialog = false;
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    delDist(distid) {
      var params = new URLSearchParams();
      params.append("distid", distid)
      axios.post("/delDist", params).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getDistList(this.index);
        } else {
          this.$message.error(res.data.msg);
        }
      })
    }
  },
  mounted() {
    this.getDistTypeList()
  }
}
</script>

<style scoped>
.background {
  width: 100%;
  height: 100%;
}

.row-bg {
  padding: 10px 0px;
  right: 20px;
  width: 104%;
  background-color: #e5f2ff;
}

.cell {
  float: right;
  line-height: 23px;
  padding-left: 10px;
  padding-right: 10px;
}
</style>