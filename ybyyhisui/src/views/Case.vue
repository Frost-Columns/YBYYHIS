<template>
  <div class="background">
    <div class="patient">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >病例查询</span>
            </el-col>
            <el-col :md="3">
              <el-input v-model="select.pname" placeholder="姓名/病历号" style="width: 95%"></el-input>
            </el-col>
            <el-col :md="4" style="margin: 11px">
              <el-checkbox-group v-model="select.stateList">
                <el-checkbox label="500">未就诊</el-checkbox>
                <el-checkbox label="501">已就诊</el-checkbox>
              </el-checkbox-group>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getCaseList(1)">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table
              :data="caselist"
              stripe
              style="width: 100%">
            <el-table-column
                prop="caseid"
                label="病历号">
            </el-table-column>
            <el-table-column
                prop="pname"
                label="患者姓名">
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间">
              <template slot-scope="scope">{{ scope.row.sex | dateFromat }}</template>
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
                <el-button @click="caseInfo(scope.row)"
                           v-if="scope.row.state!=101"
                           type="primary"
                           plain
                           size="small">
                  详细信息
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
              background
              layout="prev, pager, next"
              :page-size="10"
              :total="total"
              @current-change="getCaseList">
          </el-pagination>
          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>
        </el-main>
      </el-container>

    </div>
  </div>
</template>

<script>
export default {
  name: "Case",
  data() {
    return {
      caselist: [],
      index: 1,
      total: 0,
      caseinfo: {
        caseid: '',
        patientId: '',
        pname: '',
        symptom: '',
        historyOfTreatment: '',
        pastHistory: '',
        allergen: '',
        checkResultStr: '',
        distId: '',
        distRemarks: '',
        cureSchedule: '',
        prescriptionStr: '',
        createTime: '',
        state: '',
        sname: ''
      },
      select: {
        pname: '',
        stateList: ['500']
      }
    }
  },
  methods: {
    getCaseList(index){
      var params = new URLSearchParams();
      params.append("index", index)
      params.append("pname", this.select.pname)
      params.append("statelist", this.select.stateList)
      axios.post("/caselist", params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.caselist = res.data.data.records;
      })
    },
    add(){
      for (var i in this.caseinfo) {
        this.caseinfo[i] = '';
      }
      this.$router.push({
        path: '/caseinfo',
        name: 'CaseInfo',
        params: this.caseinfo
      })
    },
    caseInfo(row){
      this.caseinfo = row;
      this.$router.push({
        path: '/caseinfo',
        name: 'CaseInfo',
        params: this.caseinfo
      });
    },
    reset() {
      for (var i in this.select) {
        this.select[i] = '';
      }
      this.select.checkList = ['500', '501'];
      this.getCaseList(1);
    }
  },
  mounted() {
    this.getCaseList(this.index);
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