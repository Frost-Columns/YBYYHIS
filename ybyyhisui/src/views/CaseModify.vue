<template>
  <div class="background">
    <div class="casemodify">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >病例详情</span>
            </el-col>
            <el-col :md="1" :offset="21">
              <el-button type="primary" @click="back()" size="mini">返回</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <div style="height: 718px; width: 98%; overflow: hidden;">
            <el-scrollbar style="position:relative;height:100%;">
              <h1>病历表</h1>
              <table>
                <tr>
                  <td class="title">病例号</td>
                  <td colspan="3">
                    <el-input v-model="caseinfo.caseid" placeholder="请输入内容" :disabled="true"></el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">患者ID</td>
                  <td>
                    <el-input :model="patientid" placeholder="请输入内容" :disabled="true"></el-input>
                  </td>
                  <td class="title">患者姓名</td>
                  <td>
                    <el-select v-model="patientid" placeholder="请选择">
                      <el-option
                          v-for="item in patientlist"
                          :key="item.patientid"
                          :label="item.pname"
                          :value="item.patientid">
                      </el-option>
                    </el-select>
                  </td>
                </tr>
                <tr>
                  <td class="title">既往病史</td>
                  <td class="content">
                    <el-input
                        type="textarea"
                        :rows="13"
                        placeholder="请输入内容"
                        v-model="caseinfo.historyOfTreatment">
                    </el-input>
                  </td>
                  <td class="title">治疗历史</td>
                  <td class="content">
                    <el-input
                        type="textarea"
                        :rows="13"
                        placeholder="请输入内容"
                        v-model="caseinfo.pastHistory">
                    </el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">过敏原</td>
                  <td colspan="3">
                    <el-input v-model="caseinfo.allergen" placeholder="请输入内容"></el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">症状</td>
                  <td colspan="3" style="height: 500px">
                    <el-input
                        type="textarea"
                        :rows="22"
                        placeholder="请输入内容"
                        v-model="caseinfo.symptom">
                    </el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">检查结果</td>
                  <td colspan="3" style="height: 500px">
                    <el-input
                        type="textarea"
                        :rows="22"
                        placeholder="请输入内容"
                        v-model="caseinfo.checkResultStr">
                    </el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">诊断疾病</td>
                  <td>
                    <el-cascader :props="props" @change="getDistid" v-model="initial"></el-cascader>
                  </td>
                  <td class="title">备注</td>
                  <td>
                    <el-input v-model="caseinfo.distRemarks" placeholder="请输入内容"></el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">治疗方案</td>
                  <td colspan="3" style="height: 500px">
                    <el-input
                        type="textarea"
                        :rows="22"
                        placeholder="请输入内容"
                        v-model="caseinfo.cureSchedule">
                    </el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">处方</td>
                  <td colspan="3" style="height: 500px">
                    <el-input
                        type="textarea"
                        :rows="22"
                        placeholder="请输入内容"
                        v-model="caseinfo.prescriptionStr">
                    </el-input>
                  </td>
                </tr>
                <tr>
                  <td class="title">创建时间</td>
                  <td>

                  </td>
                  <td class="title">状态</td>
                  <td>
                    <el-select v-model="caseinfo.state" placeholder="请选择状态">
                      <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                    </el-select>
                  </td>
                </tr>
              </table>
            </el-scrollbar>
          </div>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
let id = 0;
export default {
  name: "CaseModify",
  data() {
    return {
      caseinfo: this.$route.params,
      caseinfocopy: Object.assign({}, this.$route.params),
      initial: [],
      patientid: 0,
      patientlist: [],
      props: {
        lazy: true,
        lazyLoad (node, resolve) {
          const { level } = node;
          if(level == 0){
            var disttypelist = [];
            axios.get("/disttypelist").then((res) => {
              for(var i in res.data.data){
                disttypelist.push({
                  value: res.data.data[i].dtid,
                  label: res.data.data[i].dtname,
                  leaf: false
                })
              }
              resolve(disttypelist);
            })
          }else{
            var distlist = [];
            var dtid = node.value;
            var params = new URLSearchParams();
            params.append("dtid", dtid)
            axios.post("/distlist", params).then((res) => {
              for(var i in res.data.data){
                distlist.push({
                  value: res.data.data[i].distid,
                  label: res.data.data[i].distnamezh,
                  leaf: true
                })
              }
              resolve(distlist);
            })
          }
        }
      },
      options: [{
        value: 500,
        label: '未就诊'
      }, {
        value: 501,
        label: '已就诊'
      }]
    }
  },
  methods: {
    getPatientList(){
      axios.post("/patientall").then((res) => {
        this.patientlist = res.data.data;
      })
    },
    getDistid(res){
      this.caseinfo.distId = res[1];
    },
    back(){
      this.$router.push({
        path: '/case/info',
        name: 'CaseInfo',
        params: this.caseinfocopy
      })
    }
  },
  mounted() {
    this.getPatientList();
    var params = new URLSearchParams();
    params.append("distid", this.caseinfo.distId)
    axios.post("/distname",params).then((res) => {
      this.initial.push(res.data.data.dtId);
      this.initial.push(this.caseinfo.distId);
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

table {
  position:relative;
  border-collapse:collapse;
  left: 5%;
  width: 90%;
}

th, td {
  height: 60px;
  width: 500px;
  border: 1px solid #E9ECF3;
}

.title {
  width: 100px;
}

.content{
  height: 300px;
}
</style>