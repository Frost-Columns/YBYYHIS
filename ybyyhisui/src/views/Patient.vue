<template>
  <div class="background">
    <div class="patient">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >患者查询</span>
            </el-col>
            <el-col :md="3">
              <el-input v-model="select.pname" placeholder="名称关键字" style="width: 95%"></el-input>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getPatientList(1)">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table
              :data="patientlist"
              stripe
              style="width: 100%">
            <el-table-column
                prop="pname"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="sex"
                label="性别">
              <template slot-scope="scope">{{ scope.row.sex | sexFromat }}</template>
            </el-table-column>
            <el-table-column
                prop="birthday"
                label="生日">
              <template slot-scope="scope">{{ scope.row.sex | dateFromat }}</template>
            </el-table-column>
            <el-table-column
                prop="idcard"
                label="身份证号">
            </el-table-column>
            <el-table-column
                prop="home"
                label="家庭住址">
            </el-table-column>
            <el-table-column
                prop="phone"
                label="联系电话">
            </el-table-column>
            <el-table-column
                prop="emergencyContact"
                label="紧急联系电话">
            </el-table-column>
            <el-table-column
                prop="heredity"
                label="遗传病史">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                width="120">
              <template slot-scope="scope">
                <el-button @click="modifyPatient(scope.row)"
                           v-if="scope.row.state!=101"
                           type="text"
                           size="small">
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
              background
              layout="prev, pager, next"
              :page-size="10"
              :total="total"
              @current-change="getPatientList">
          </el-pagination>
          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>
        </el-main>
      </el-container>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="patientfrom" ref="patientfrom" :rules="rules">
          <el-form-item prop="pname">
            <el-input v-model="patientfrom.pname" placeholder="姓名" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item prop="sex">
            <el-radio-group v-model="patientfrom.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item required prop="birthday">
            <el-date-picker type="date" placeholder="生日" :picker-options="pickerOptions" v-model="patientfrom.birthday"
            style="width: 80%;"></el-date-picker>
          </el-form-item>
          <el-form-item prop="idcard">
            <el-input v-model="patientfrom.idcard" placeholder="身份证号" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item prop="home">
            <el-input v-model="patientfrom.home" placeholder="家庭住址" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input v-model="patientfrom.phone" placeholder="联系方式" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item prop="emergencyContact">
            <el-input v-model="patientfrom.emergencyContact" placeholder="紧急联系方式" style="width: 80%;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
                type="textarea"
                placeholder="遗传病史"
                v-model="patientfrom.heredity"
                maxlength="120"
                show-word-limit
                style="width: 80%;">
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('patientfrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "Patient",
  data() {
    return {
      patientlist: [],
      index: 1,
      total: 0,
      drawer: false,
      title: '',
      patientfrom: {
        patientid: '',
        pname: '',
        sex: 1,
        birthday: '',
        idcard: '',
        home: '',
        phone: '',
        emergencyContact: '',
        heredity: ''
      },
      select: {
        pname: ''
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      rules: {
        pname: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        birthday: [
          {required: true, message: '请输入生日', trigger: 'blur'}
        ],
        idcard: [
          {required: true, message: '请输入身份证号', trigger: 'blur'}
        ],
        home: [
          {required: true, message: '请输入家庭住址', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入联系方式', trigger: 'blur'}
        ],
        emergencyContact: [
          {required: true, message: '请输入紧急联系方式', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getPatientList(index){
      var params = new URLSearchParams();
      params.append("index",index)
      params.append("pname",this.select.pname)
      axios.post("/patientlist",params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.patientlist = res.data.data.records;
      })
    },
    add() {
      this.title = '添加账户';
      if(this.$refs['patientfrom']!==undefined){
        this.$refs['patientfrom'].resetFields();
      }
      for (var i in this.patientfrom) {
        this.patientfrom[i] = '';
      }
      this.patientfrom.sex = 1;
      this.drawer = true;
    },
    modifyPatient(row) {
      this.patientfrom = Object.assign({}, row);  //深拷贝
      this.title = '修改账户';
      this.drawer = true;
    },
    onSubmit(pfrom) {
      this.$refs[pfrom].validate((valid) => {
        if (valid) {
          var url = '';
          if (this.patientfrom.patientid == '') {
            url = '/InsertPatient';
          } else {
            url = '/UpdatePatient';
          }
          axios.post(url, this.patientfrom).then((res) => {
            if (res.data.data) {
              this.$message.success(res.data.msg);
              this.getPatientList(this.index);
              this.drawer = false;
            } else {
              this.$message.error(res.data.msg);
            }
          })
        } else {
          return false;
        }
      });
    }
  },
  mounted() {
    this.getPatientList(this.index);
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