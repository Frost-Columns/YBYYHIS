<template>
  <div class="background">
    <div class="recipel">
      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>接诊管理</span>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="400px">
          <el-container>
            <el-header>
              <el-row>
                <el-col :md="16" style="margin: 11px">
                  <el-input v-model="select.pname" placeholder="患者名称" style="width: 95%; left: 20px"></el-input>
                </el-col>
                <el-col :md="3" style="margin: 11px">
                  <el-button type="primary" @click="getTreatmentLogsList()" icon="el-icon-refresh" circle></el-button> <!--查找-->
                </el-col>
              </el-row>
            </el-header>
            <el-main style="margin: -20px 0 0 0;">
              <div>
                <el-tag style="width: 100%">未就诊</el-tag>
                <el-table
                    :data="treatmentlogslist500"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220">
                  <el-table-column
                      prop="caseId"
                      label="病历号"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="pname"
                      label="名字"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      fixed="right"
                      label="操作"
                      width="60">
                    <template slot-scope="scope">
                      <el-button @click="qy(scope.row)" type="text" size="mini">
                        <i class="el-icon-s-claim"/>
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div>
                <el-tag type="success" style="width: 100%">就诊中</el-tag>
                <el-table
                    :data="treatmentlogslist501"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="casefalse">
                  <el-table-column
                      prop="caseId"
                      label="病历号"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="pname"
                      label="名字"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      fixed="right"
                      label="操作"
                      width="60">
                  </el-table-column>
                </el-table>
              </div>
              <div>
                <el-tag type="warning" style="width: 100%">已就诊</el-tag>
                <el-table
                    :data="treatmentlogslist502"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="casetrue">
                  <el-table-column
                      prop="caseId"
                      label="病历号"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="pname"
                      label="名字"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      fixed="right"
                      label="操作"
                      width="60">
                  </el-table-column>
                </el-table>
              </div>
            </el-main>
          </el-container>
        </el-aside>
        <el-main style="height: 100%;">
          <div class="popContainer" v-if="disabled == true"></div>
          <el-tabs type="border-card">
            <el-tab-pane label="患者个人信息">
              <el-form ref="patientinfo" :model="patientinfo" label-width="200px" disabled style="margin: 0 150px 0 0; width: 50%;">
                <el-form-item label="患者姓名:">
                  <el-input v-model="patientinfo.pname"></el-input>
                </el-form-item>
                <el-form-item label="性别:">
                  <el-radio-group v-model="patientinfo.sex">
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="0">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期:">
                    <el-date-picker placeholder="出生日期" v-model="patientinfo.birthday" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="身份证号:">
                  <el-input v-model="patientinfo.idcard"></el-input>
                </el-form-item>
                <el-form-item label="家庭住址:">
                  <el-input v-model="patientinfo.home"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                  <el-input v-model="patientinfo.phone"></el-input>
                </el-form-item>
                <el-form-item label="紧急联系方式:">
                  <el-input v-model="patientinfo.emergencyContact"></el-input>
                </el-form-item>
                <el-form-item label="遗传史:">
                  <el-input
                      type="textarea"
                      placeholder="遗传史"
                      v-model="patientinfo.heredity"
                      :rows="4"
                      resize="none"
                      show-word-limit>
                  </el-input>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="病历信息">

              <el-form ref="caseinfo" :model="caseinfo" label-width="100px" style="margin: 0 10% 0 0; width: 90%">
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="症状:">
                      <el-input
                          type="textarea"
                          placeholder="症状"
                          v-model="caseinfo.symptom"
                          :rows="4"
                          resize="none"
                          @change="updateCase()"
                          show-word-limit>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="治疗历史:">
                      <el-input
                          type="textarea"
                          placeholder="治疗历史"
                          v-model="caseinfo.historyOfTreatment"
                          :rows="4"
                          resize="none"
                          disabled
                          show-word-limit>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="过敏原:">
                      <el-input
                          type="textarea"
                          placeholder="过敏原"
                          v-model="caseinfo.allergen"
                          :rows="4"
                          resize="none"
                          @change="updateCase()"
                          show-word-limit>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="既往病史:">
                      <el-input
                          type="textarea"
                          placeholder="既往病史"
                          v-model="caseinfo.pastHistory"
                          :rows="4"
                          resize="none"
                          disabled
                          show-word-limit>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <el-row>
                <el-col :span="24">
                  <el-card class="box-card" style="height: 440px;">
                    <div slot="header" class="clearfix">
                      <span>评估诊断</span>
                      <el-button style="float: right; padding: 3px 0" type="text" @click="casedistDialogVisible = true">添加诊断</el-button>
                    </div>
                    <div >
                      <el-table
                          :data="casedistlist"
                          stripe
                          highlight-current-row
                          height="382"
                          style="margin: -20px 0 0 0">
                        <el-table-column
                            prop="dname"
                            label="疾病名">
                        </el-table-column>
                        <el-table-column
                            prop="remarks"
                            label="备注">
                        </el-table-column>
                        <el-table-column
                            fixed="right"
                            label="操作"
                            width="60">
                          <template slot-scope="scope">
                            <el-popconfirm title="确定删除吗？" @confirm="delCaseDist(scope.row.cdid)">
                              <el-button slot="reference" type="text" size="small" :disabled="disabled">
                                <i class="el-icon-delete-solid"/>
                              </el-button>
                            </el-popconfirm>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </el-col>
              </el-row>

            </el-tab-pane>
            <el-tab-pane label="检查项目">
<!--              检查项目 + 检查结果-->
              <el-row>
                <el-col :span="24">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span>检查项目备选</span>
                      <el-button style="float: right; padding: 3px 0" type="text" @click="cureAltDialogVisible = true">
                        <i class="el-icon-circle-plus" />
                      </el-button>
                      <el-button style="float: left; padding: 3px 0" type="text" @click="addCureSch()">
                        <i class="el-icon-success" />
                      </el-button>
                    </div>
                    <div >
                      <el-table
                          :data="curealtlist"
                          stripe
                          highlight-current-row
                          height="270"
                          style="margin: -20px 0 0 0">
                        <el-table-column
                            prop="cnamezh"
                            label="项目名">
                        </el-table-column>
                        <el-table-column
                            prop="loc"
                            label="地址">
                        </el-table-column>
                        <el-table-column
                            fixed="right"
                            label="操作"
                            width="60">
                          <template slot-scope="scope">
                            <el-popconfirm title="确定删除吗？" @confirm="delCureAlt(scope.$index, curealtlist)">
                              <el-button slot="reference" type="text" size="small" :disabled="disabled">
                                <i class="el-icon-delete-solid"/>
                              </el-button>
                            </el-popconfirm>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </el-col>
                <el-col :span="24">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span>检查项目</span>
                      <el-button style="float: right; padding: 3px 0" type="text" @click="getCureSch()">
                        <i class="el-icon-refresh" />
                      </el-button>
                    </div>
                    <div >
                      <el-table
                          :data="cureschlist"
                          stripe
                          highlight-current-row
                          height="270"
                          style="margin: -20px 0 0 0">
                        <el-table-column
                            prop="cname"
                            label="项目名">
                        </el-table-column>
                        <el-table-column
                            prop="sname"
                            label="状态">
                        </el-table-column>
                        <el-table-column
                            fixed="right"
                            label="操作"
                            width="60">
                          <template slot-scope="scope">
                            <el-button slot="reference" type="text" size="small" v-if="scope.row.state == 602" @click="queryCureLogs(scope.row)">
                              <i class="el-icon-s-order"/>
                            </el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="治疗方案">
              <el-row>
                <el-col :span="24">
                  <div>
                    <span>治疗方案</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="cureTreDialogVisible = true">
                      <i class="el-icon-circle-plus" />
                    </el-button>
                    <el-button style="float: right; padding: 3px 5px" type="text" @click="getCureTre()">
                      <i class="el-icon-refresh" />
                    </el-button>
                  </div>
                  <div >
                    <el-table
                        :data="curetrelist"
                        stripe
                        highlight-current-row
                        height="360"
                        style="margin: -20px 0 0 0">
                      <el-table-column
                          prop="cname"
                          label="项目名">
                      </el-table-column>
                      <el-table-column
                          prop="sname"
                          label="状态">
                      </el-table-column>
                      <el-table-column
                          fixed="right"
                          label="操作"
                          width="80">
                        <template slot-scope="scope">
                          <el-button slot="reference" type="text" size="small" v-if="scope.row.state == 1001" @click="queryCureLogs(scope.row)">
                            <span>查看结果</span>
                          </el-button>
                          <el-popconfirm title="确定删除吗？" @confirm="delCureLogs(scope.row.clid)">
                            <el-button slot="reference" type="text" size="small" v-if="scope.row.state == 1000">
                              <span style="color: red">删除项目</span>
                            </el-button>
                          </el-popconfirm>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-col>
                <el-col :span="24" style="margin: 20px 0">
                  <div>
                    <div>
                      <span>治疗方案备注</span>
                    </div>
                  </div>
                  <div>
                    <el-input
                        type="textarea"
                        placeholder="治疗方案备注"
                        v-model="caseinfo.cureSchedule"
                        :rows="8"
                        resize="none"
                        @change="updateCase()"
                        show-word-limit>
                    </el-input>
                  </div>
                </el-col>
              </el-row>
              <el-button style="float: right; padding: 3px 0" type="primary" @click="wc()">确认</el-button>
            </el-tab-pane>

            <el-tab-pane label="处方">
              <el-row>
                <el-col :span="24">
                  <div>
                    <span>处方</span>
                    <el-button style="float: left; padding: 3px 0" type="text" @click="addRecipel()">
                      开处方
                    </el-button>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="addRecipelInfo()">
                      <i class="el-icon-circle-plus" />
                    </el-button>
                  </div>
                  <div >
                    <div class="recipelContainer" v-if="recipel === undefined"></div>
                    <el-table
                        :data="recipelinfo"
                        stripe
                        highlight-current-row
                        height="680"
                        style="margin: -20px 0 0 0">
                      <el-table-column
                          prop="dname"
                          label="药品名称">
                      </el-table-column>
                      <el-table-column
                          prop="count"
                          label="剂量">
                      </el-table-column>
                      <el-table-column
                          prop="unitPrice"
                          label="单价">
                      </el-table-column>
                      <el-table-column
                          prop="price"
                          label="总价">
                      </el-table-column>
                      <el-table-column
                          prop="takeRemarks"
                          label="服用说明">
                      </el-table-column>
                      <el-table-column
                          label="操作"
                          width="155">
                        <template slot-scope="scope">
                          <el-button @click="editRecipelInfo(scope.row)"
                                     v-if="scope.row.state!=201"
                                     type="text"
                                     size="small"
                                     :disabled="disabled">
                            编辑
                          </el-button>
                          <el-popconfirm title="确定删除吗？" @confirm="delRecipelInfo(scope.row.riid)">
                            <el-button slot="reference" type="text" size="small" :disabled="disabled">
                              删除
                            </el-button>
                          </el-popconfirm>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </el-main>
      </el-container>

      <el-dialog
          title="添加诊断"
          :visible.sync="casedistDialogVisible"
          width="30%"
          center>
        <span>疾病：</span>
        <el-select v-model="casedist.distId" filterable remote :remote-method="getDist" placeholder="疾病名" style="width: 100%">
          <el-option
              v-for="item in distlist"
              :key="item.distid"
              :label="item.distnamezh"
              :value="item.distid">
          </el-option>
        </el-select>
        <span>备注：</span>
        <el-input v-model="casedist.remarks" placeholder="备注" style="width: 100%"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="casedistDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCaseDist()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
          title="添加检查项目"
          :visible.sync="cureAltDialogVisible"
          width="30%"
          center>
        <span>项目：</span>
        <el-select v-model="cureId" filterable remote :remote-method="getCureS" placeholder="项目名" style="width: 100%">
          <el-option
              v-for="item in curelist"
              :key="item.cureid"
              :label="item.cnamezh"
              :value="item.cureid">
          </el-option>
        </el-select>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cureAltDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCureAlt()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
          title="添加治疗项目"
          :visible.sync="cureTreDialogVisible"
          width="30%"
          center>
        <span>项目：</span>
        <el-select v-model="cureId" filterable remote :remote-method="getCureT" placeholder="项目名" style="width: 100%">
          <el-option
              v-for="item in curelist"
              :key="item.cureid"
              :label="item.cnamezh"
              :value="item.cureid">
          </el-option>
        </el-select>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cureTreDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCureTre()">确 定</el-button>
        </span>
      </el-dialog>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="recipelinfofrom" ref="recipelinfofrom" :rules="rules" label-position="right">
          <el-form-item prop="drugsId" label="药品" style="width: 80%; margin: 20px 10%">
            <el-select v-model="recipelinfofrom.drugsId" filterable remote :remote-method="getDruge" placeholder="药品" style="width: 100%">
              <el-option
                  v-for="item in drugelist"
                  :key="item.drugsid"
                  :label="item.drugsnamezh"
                  :value="item.drugsid">
                <span style="float: left">{{ item.drugsnamezh }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.price }}￥/{{ item.unit }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="count" label="剂量" style="width: 80%; margin: 20px 10%">
            <el-input
                v-model="recipelinfofrom.count"
                type="number"
                onkeyup="this.value=this.value.replace(/\D/g,'')"
                placeholder="剂量"
            ></el-input>
          </el-form-item>
          <el-form-item prop="takeRemarks" label="使用说明" style="width: 80%; margin: 20px 10%">
            <el-input v-model="recipelinfofrom.takeRemarks" placeholder="使用说明"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('recipelinfofrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "Medicals",
  data() {
    return {
      treatmentlogslist500: [],
      treatmentlogslist501: [],
      treatmentlogslist502: [],
      treatmentlogsinfo: {},
      select: {
        pname: ''
      },
      caseinfo: {},
      patientinfo: {},
      disabled: true,
      casedistlist: [],
      casedistDialogVisible: false,
      cureAltDialogVisible: false,
      cureTreDialogVisible: false,
      recipelDialogVisible: true,
      distlist: [],
      casedist: {
        distId: '',
        remarks: ''
      },
      cureId: '',
      curelist: [],
      curealtlist: [],
      cureschlist: [],
      curetrelist:[],
      recipel: undefined,
      recipelinfo: [],

      recipelinfofrom: {
        riid: 0,
        recipelId: 0,
        drugsId: 0,
        dname: '',
        count: 0,
        price: 0,
        takeRemarks: ''
      },
      title: '',
      drawer: false,
      drugelist: [],
      rules: {
        drugsId: [
          {required: true, message: '请选择药品', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请输入剂量', trigger: 'blur'}
        ],
        takeRemarks: [
          {required: true, message: '请输入服用说明', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getTreatmentLogsList() {
      var params = new URLSearchParams();
      params.append("pname",this.select.pname)
      axios.post("/treatmentlogslist",params).then((res) => {
        this.treatmentlogslist500 = res.data.data.treatmentlogslist500
        this.treatmentlogslist501 = res.data.data.treatmentlogslist501
        this.treatmentlogslist502 = res.data.data.treatmentlogslist502
      })
    },
    qy(row) {
      var params = new URLSearchParams();
      params.append("caseid",row.caseId)
      params.append("tlid",row.tlid)
      params.append("state",501)
      axios.post("/updateTreatmentlogsQy",params).then((res) => {
        this.getTreatmentLogsList()
      })
    },
    wc() {
      var params = new URLSearchParams();
      params.append("caseid",this.treatmentlogsinfo.caseId)
      params.append("tlid",this.treatmentlogsinfo.tlid)
      params.append("state",502)
      axios.post("/updateTreatmentlogsQy",params).then((res) => {
        this.getTreatmentLogsList()
      })
    },
    casefalse(row) {
      this.treatmentlogsinfo = row
      this.recipel = undefined
      this.recipelinfo = []
      var params = new URLSearchParams();
      params.append("caseid",row.caseId)
      axios.post("/getTreatmentInfo",params).then((res) => {
        this.caseinfo = res.data.data.caseinfo
        this.patientinfo = res.data.data.patientinfo
        this.getCaseDistList(this.caseinfo.caseid)
        this.getCureSch()
        this.getCureTre()
        this.disabled = false
        this.getRecipel()
      })
    },
    casetrue(row) {
      this.treatmentlogsinfo = row
      this.recipel = undefined
      this.recipelinfo = []
      var params = new URLSearchParams();
      params.append("caseid",row.caseId)
      axios.post("/getTreatmentInfo",params).then((res) => {
        this.caseinfo = res.data.data.caseinfo
        this.patientinfo = res.data.data.patientinfo
        this.getCaseDistList(this.caseinfo.caseid)
        this.getCureSch()
        this.getCureTre()
        this.disabled = true
        this.getRecipel()
      })
    },
    updateCase() {
      console.log(this.caseinfo)
      axios.post("/updateCase",this.caseinfo).then((res) => {

      })
    },
    getCaseDistList(caseid) {
      var params = new URLSearchParams();
      params.append("caseid",caseid)
      axios.post("/casedistlist",params).then((res) => {
        this.casedistlist = res.data.data
      })
    },
    getDist(dname) {
      var params = new URLSearchParams();
      params.append("dname",dname)
      axios.post("/getDistList",params).then((res) => {
        this.distlist = res.data.data
      })
    },
    getCureS(cname) {
      var params = new URLSearchParams();
      params.append("cname",cname)
      params.append("state",600)
      axios.post("/getCureList",params).then((res) => {
        this.curelist = res.data.data
      })
    },
    getCureT(cname) {
      var params = new URLSearchParams();
      params.append("cname",cname)
      params.append("state",1000)
      axios.post("/getCureList",params).then((res) => {
        this.curelist = res.data.data
      })
    },
    getRecipel() {
      var params = new URLSearchParams();
      params.append("caseId",this.caseinfo.caseid)
      axios.post("/getRecipel",params).then((res) => {
        if (res.data.data != null) {
          this.recipel = res.data.data
          this.getRecipelInfo()
        } else {
          this.recipel = undefined
        }
      })
    },
    addCaseDist() {
      var params = new URLSearchParams();
      params.append("caseId",this.caseinfo.caseid)
      params.append("distId",this.casedist.distId)
      params.append("remarks",this.casedist.remarks)
      axios.post("/addCaseDist",params).then((res) => {
        this.$message.success(res.data.msg)
        this.getCaseDistList(this.caseinfo.caseid)
        this.casedistDialogVisible = false
      })
      for (var i in this.casedist) {
        this.casedist[i] = '';
      }
      this.distlist = []
    },
    delCaseDist(cdid) {
      var params = new URLSearchParams();
      params.append("cdid",cdid)
      axios.post("/delCaseDist",params).then((res) => {
        this.$message.success(res.data.msg)
        this.getCaseDistList(this.caseinfo.caseid)
      })
    },
    addCureAlt() {
      var params = new URLSearchParams();
      params.append("cureid",this.cureId)
      if (this.cureId != '') {
        axios.post("/getCaseById",params).then((res) => {
          this.curealtlist.push(res.data.data)
          this.cureAltDialogVisible = false
        })
        this.cureId = ''
        this.curelist = []
      }
    },
    delCureAlt(index, rows) {
      rows.splice(index, 1)
    },
    addCureSch() {
      var cureidlist = []
      for (var i in this.curealtlist) {
        cureidlist.push(this.curealtlist[i].cureid)
      }
      var params = new URLSearchParams();
      params.append("cureids",cureidlist)
      params.append("caseid",this.caseinfo.caseid)
      params.append("patientid",this.patientinfo.patientid)
      params.append("state",600)
      axios.post("/addCureLogs",params).then((res) => {
        this.$message.success(res.data.msg)
        this.getCureSch()
        this.curealtlist = []
      })
    },
    delCureLogs(clid) {
      var params = new URLSearchParams();
      params.append("clid",clid)
      axios.post("/delCureLogs",params).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg)
        } else {
          this.$message.error(res.data.msg)
        }
        this.getCureSch()
        this.getCureTre()
      })
    },
    getCureSch() {
      var params = new URLSearchParams();
      params.append("caseid",this.caseinfo.caseid)
      axios.post("/getCureLogs1",params).then((res) => {
        this.cureschlist = res.data.data
      })
    },
    getCureTre() {
      var params = new URLSearchParams();
      params.append("caseid",this.caseinfo.caseid)
      axios.post("/getCureLogs2",params).then((res) => {
        this.curetrelist = res.data.data
      })
    },
    queryCureLogs(row) {

    },
    addCureTre() {
      if (this.cureId != '') {
        var cureidlist = []
        cureidlist.push(this.cureId)
        var params = new URLSearchParams();
        params.append("cureids",cureidlist)
        params.append("caseid",this.caseinfo.caseid)
        params.append("patientid",this.patientinfo.patientid)
        params.append("state",1000)
        axios.post("/addCureLogs",params).then((res) => {
          this.$message.success(res.data.msg)
          this.getCureTre()
          this.cureId = ''
          this.cureTreDialogVisible = false
        })
      }
    },
    addRecipel() {
      var params = new URLSearchParams();
      params.append("caseid",this.caseinfo.caseid)
      params.append("patientName",this.caseinfo.pname)
      axios.post("/addRecipel",params).then((res) => {
        this.recipel = res.data.data
      })
    },
    getRecipelInfo() {
      var params = new URLSearchParams();
      params.append("recipelId",this.recipel.recipelid)
      axios.post("/recipelinfolist",params).then((res) => {
        this.recipelinfo = res.data.data
      })
    },
    addRecipelInfo() {
      this.title = '药物添加'
      for (var i in this.recipelinfofrom) {
        this.recipelinfofrom[i] = '';
      }
      this.drugelist = []
      this.drawer = true
    },
    onSubmit() {
      var url = ''
      if (this.recipelinfofrom.riid == '') {
        this.recipelinfofrom.recipelId = this.recipel.recipelid
        url = '/addRecipelInfo'
      } else {
        url = '/editRecipelInfo'
      }
      axios.post(url, this.recipelinfofrom).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getRecipelInfo()
          this.drawer = false;
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    getDruge(dname) {
      var params = new URLSearchParams();
      params.append("dname",dname)
      axios.post("/getDrugeList",params).then((res) => {
        this.drugelist = res.data.data
      })
    },
  },
  mounted() {
    this.getTreatmentLogsList()
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

.el-form-item {
  margin-bottom: 40px;
}

.el-card {
  margin: 0 0 10px 100px;
  width: 81%;
}

.el-tab-pane {
  height: 725px;
}

div.popContainer {
  position: absolute;
  top: 124px;
  left: 600px;
  right: 0;
  bottom: 8px;
  z-index: 100;
  background: rgba(0,0,0,.3);
}

div.recipelContainer {
  position: absolute;
  top: 0px;
  left: 1180px;
  right: 0;
  bottom: 680px;
  z-index: 100;
  background: rgba(0,0,0,.3);
}
</style>