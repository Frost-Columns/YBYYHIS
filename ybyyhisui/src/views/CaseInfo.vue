<template>
  <div class="background">
    <div class="caseinfo">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >病例详情</span>
            </el-col>
<!--            <el-col :md="1" :offset="19">-->
<!--              <el-button type="primary" @click="modify()" size="mini">编辑</el-button>-->
<!--            </el-col>-->
            <el-col :md="1" :offset="19">
              <el-button type="primary" @click="back()" size="medium">返回</el-button>
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
                  <td colspan="3"><span>{{ caseinfo.caseid }}</span></td>
                </tr>
                <tr>
                  <td class="title">患者ID</td>
                  <td><span>{{ caseinfo.patientId }}</span></td>
                  <td class="title">患者姓名</td>
                  <td><span>{{ caseinfo.pname }}</span></td>
                </tr>
                <tr>
                  <td class="title">既往病史</td>
                  <td class="content"><span>{{ caseinfo.pastHistory }}</span></td>
                  <td class="title">治疗历史</td>
                  <td class="content"><span>{{ caseinfo.historyOfTreatment }}</span></td>
                </tr>
                <tr>
                  <td class="title">过敏原</td>
                  <td colspan="3"><span>{{ caseinfo.allergen }}</span></td>
                </tr>
                <tr>
                  <td class="title">症状</td>
                  <td colspan="3" style="height: 500px"><span>{{ caseinfo.symptom }}</span></td>
                </tr>
                <tr>
                  <td class="title">检查结果</td>
                  <td colspan="3" style="height: 500px"><span>{{ caseinfo.checkResultStr }}</span></td>
                </tr>
                <tr>
                  <td class="title">诊断疾病</td>
                  <td><span>{{ distname }}</span></td>
                  <td class="title">备注</td>
                  <td><span>{{ caseinfo.distRemarks }}</span></td>
                </tr>
                <tr>
                  <td class="title">治疗方案</td>
                  <td colspan="3" style="height: 500px"><span>{{ caseinfo.cureSchedule }}</span></td>
                </tr>
                <tr>
                  <td class="title">处方</td>
                  <td colspan="3" style="height: 500px"><span>{{ caseinfo.prescriptionStr }}</span></td>
                </tr>
                <tr>
                  <td class="title">创建时间</td>
                  <td><span>{{ caseinfo.createTime | dateFromat }}</span></td>
                  <td class="title">状态</td>
                  <td><span>{{ caseinfo.sname }}</span></td>
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

export default {
  name: "CaseInfo",
  data() {
    return {
      caseinfo: this.$route.params,
      distname: ''
    }
  },
  methods: {
    getDistName(){
      var params = new URLSearchParams();
      params.append("distid", this.caseinfo.distId)
      axios.post("/distname",params).then((res) => {
        this.distname = res.data.data.distnamezh;
      });
    },
    // modify(){
    //   this.$router.push({
    //     path: '/case/modify',
    //     name: 'CaseModify',
    //     params: this.caseinfo
    //   })
    // },
    back(){
     this.$router.push("/case")
    }
  },
  mounted() {
    this.getDistName();
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