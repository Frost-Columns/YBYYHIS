<template>
  <div class="background">
    <div class="casemodify">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >病例详情</span>
            </el-col>
            <el-col :md="1" :offset="20">
              <el-button type="primary" @click="back()" size="mini">提交</el-button>
            </el-col>
            <el-col :md="1">
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
                    <el-input v-model="caseinfo.patientId" placeholder="请输入内容" :disabled="true"></el-input>
                  </td>
                  <td class="title">患者姓名</td>
                  <td>
                    <el-select v-model="caseinfo.patientId" placeholder="请选择" :disabled="patientstate">
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
                  <td colspan="3" style="height: 500px; vertical-align: top;">
<!--                    <el-input-->
<!--                        type="textarea"-->
<!--                        :rows="22"-->
<!--                        placeholder="请输入内容"-->
<!--                        v-model="caseinfo.prescriptionStr">-->
<!--                    </el-input>-->
                    <el-row>
                      <el-col :md="4">
                        <el-select v-model="drugsid"
                                   filterable
                                   remote
                                   placeholder="请输入药品名称关键字"
                                   :remote-method="getDrugslist"
                                   style="width: 100%">
                          <el-option
                              v-for="item in drugslist"
                              :key="item.drugsid"
                              :label="item.drugsnamezh"
                              :value="item.drugsid">
                            <span style="float: left">{{ item.drugsnamezh }}-库存:{{ item.count }}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.format }}</span>
                          </el-option>
                        </el-select>
                      </el-col>
                      <el-col :md="4">
                        <el-input type="Number" min="1" v-model="drugscount" placeholder="剂量"></el-input>
                      </el-col>
                      <el-col :md="8">
                        <el-input v-model="takeRemarks" placeholder="服用说明"></el-input>
                      </el-col>
                      <el-col :md="1">
                        <el-button type="primary" @click="addDrugs()">确认</el-button>
                      </el-col>
                    </el-row>

                    <el-table
                        :data="prescription"
                        style="width: 100%; height: 500px"
                        height="250">
                      <el-table-column
                          label="药品">
                        <template slot-scope="scope">
                          <span style="margin-left: 10px">{{ scope.row.drugs.drugsnamezh }}</span>
                        </template>
                      </el-table-column>
                      <el-table-column
                          prop="count"
                          label="计量">
                      </el-table-column>
                      <el-table-column
                          prop="takeRemarks"
                          label="说明">
                      </el-table-column>
                      <el-table-column
                          label="操作">
                        <template slot-scope="scope">
                          <el-button @click="modifyUser(scope.row)"
                                     v-if="scope.row.state!=101"
                                     type="text"
                                     size="small">
                            删除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
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
      patientstate: false,
      caseinfocopy: Object.assign({}, this.$route.params),
      initial: [],
      patientid: '',
      patientlist: [],
      drugslist: [],
      drugsid: '',
      drugscount: 1,
      takeRemarks: '',
      prescription: [],
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
    },
    getDrugslist(query){
      var params = new URLSearchParams();
      params.append("drugsname", query)
      axios.post("/DrugsListByName",params).then((res) => {
        this.drugslist = res.data.data
      })
    },
    addDrugs(){
      for(var i in this.prescription){
        if(this.prescription!=[] && this.prescription[i].drugs.drugsid == this.drugsid){
          this.prescription[i].count = parseInt(this.prescription[i].count) + parseInt(this.drugscount);
          return ;
        }
      }
      var params = new URLSearchParams();
      params.append("drugsid", this.drugsid)
      axios.post("/getDrugs",params).then((res) => {
        this.prescription.push({
          drugs: res.data.data,
          count: this.drugscount,
          takeRemarks: this.takeRemarks
        })
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
    if(this.$route.params.patientId != undefined){
      this.patientstate = true;
    }
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