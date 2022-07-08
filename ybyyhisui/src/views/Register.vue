<template>
  <div class="background">
    <div class="register">
      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>排班管理</span>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-header>
          <el-row>
            <el-col :md="6" style="margin: 11px">
              <el-select v-model="dtId" placeholder="科目" style="width: 95%" @change="getRegisterGroup(1)">
                <el-option
                    v-for="item in depttypelist"
                    :key="item.dtid"
                    :label="item.dtname"
                    :value="item.dtid">
                </el-option>
              </el-select>
            </el-col>
            <el-col :md="1" style="margin: 11px">
              <el-button @click="addRegisterGroup()">新增</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main style="padding-top: 0px">
          <el-table
              :data="registerGroupList"
              height="650"
              style="width: 98%">
            <el-table-column
                prop="rgname"
                label="规则组名">
            </el-table-column>
            <el-table-column
                prop="remarks"
                label="备注">
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间">
              <template slot-scope="scope">{{ scope.row.createTime | dateFromat }}</template>
            </el-table-column>
            <el-table-column
                label="操作"
                width="300">
              <template slot-scope="scope">
                <el-button @click="editRegisterGroup(scope.row)"
                           type="primary"
                           size="small">
                  编辑
                </el-button>
                <el-button @click="addRegister(scope.row)"
                           type="success"
                           size="small">
                  生成排班信息
                </el-button>
                <el-popconfirm title="确定删除吗？" @confirm="delRegisterGroup(scope.row.rgid)" style="padding: 9px 10px;">
                  <el-button slot="reference" type="danger" size="small">
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
              :current-page="index"
              @current-change="getRegisterGroup">
          </el-pagination>
        </el-main>
      </el-container>

      <el-dialog
          title="生成排班信息"
          :visible.sync="DialogVisible"
          width="30%"
          style="“margin: 0 auto; text-align: center">
        <el-row>
          <el-col :span="24">
            <span class="demonstration">排班时间</span>
          </el-col>
          <el-col :span="24">
            <el-date-picker
                v-model="registerTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="nowDate">
            </el-date-picker>
          </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
          <el-button @click="DialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addRegisterSubmit()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
          title="新建排班规则"
          :visible.sync="groupDialogVisible"
          width="30%"
          style="margin: 0 auto; text-align: center">
        <el-form :model="groupfrom" ref="groupfrom" :rules="rules" label-position="right">
          <el-form-item prop="dtId" label="科目" style="width: 80%; margin: 20px 10%">
            <el-select v-model="groupfrom.dtId" placeholder="科目" style="width: 100%">
              <el-option
                  v-for="item in depttypelist"
                  :key="item.dtid"
                  :label="item.dtname"
                  :value="item.dtid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="rgname" label="规则组名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="groupfrom.rgname" placeholder="规则组名"></el-input>
          </el-form-item>
          <el-form-item prop="remarks" label="备注" style="width: 80%; margin: 20px 10%">
            <el-input v-model="groupfrom.remarks" placeholder="备注"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('groupfrom')">提交</el-button>
            <el-button @click="groupDialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      index: 1,
      total: 0,
      dtId: 1,
      depttypelist: [],
      registerGroupList: [],
      registerGroup: {},
      DialogVisible: false,
      registerTime: '',
      groupDialogVisible: false,
      groupfrom: {
        dtId: '',
        rgname: '',
        remarks: ''
      },
      nowDate: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      rules: {
        rgname: [
          {required: true, message: '请输入规则组名', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getRegisterGroup(index) {
      var params = new URLSearchParams();
      params.append("index",index)
      params.append("dtId",this.dtId)
      axios.post("/registerGroup",params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.registerGroupList = res.data.data.records;
      })
    },
    addRegisterGroup() {
      this.groupfrom.dtId = this.dtId
      this.rgname = ''
      this.remarks = ''
      this.groupDialogVisible = true
    },
    editRegisterGroup(row) {
      this.$router.push({
        path: '/registerGroup',
        name: 'RegisterGroup',
        params: row
      });
    },
    delRegisterGroup(rgid) {

    },
    addRegister(row) {
      this.registerGroup = row
      this.DialogVisible = true
    },
    onSubmit(group) {
      axios.post("/addGroup", this.groupfrom).then((res) => {
        if (res.data.data) {
          this.$message.success(res.data.msg);
          this.getRegisterGroup(this.index);
          this.groupDialogVisible = false;
        } else {
          this.$message.error(res.data.msg);
        }
      })
      // axios.post("/getWeek",this.registerWeek).then((res) => {
      //   this.$message.success(res.data.msg)
      // })
    },
    addRegisterSubmit() {
      console.log(this.registerGroup.rgid)
      var params = new URLSearchParams();
      params.append("rgid",this.registerGroup.rgid)
      params.append("time1",this.registerTime[1])
      params.append("time2",this.registerTime[0])
      axios.post("/addRegister", params).then((res) => {
          this.$message.success(res.data.msg);
          this.getRegisterGroup(this.index);
          this.DialogVisible = false;
      })
    }
  },
  mounted() {
    axios.get("/DeptTypeList").then((res) => {
      this.depttypelist = res.data.data
    })
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
</style>