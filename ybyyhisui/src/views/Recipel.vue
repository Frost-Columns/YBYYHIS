<template>
  <div class="background">
    <div class="recipel">
      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>处方管理</span>
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
                  <el-button type="primary" @click="getRecipelList()" icon="el-icon-refresh" circle></el-button> <!--查找-->
                </el-col>
              </el-row>
            </el-header>
            <el-main style="margin: -20px 0 0 0;">
              <div>
                <el-tag style="width: 100%">未开药</el-tag>
                <el-table
                    :data="recipellist800"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="recipelfalse">
                  <el-table-column
                      prop="caseId"
                      label="病历号"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="patientName"
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
                <el-tag type="success" style="width: 100%">待取药</el-tag>
                <el-table
                    :data="recipellist801"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="recipeltrue">
                  <el-table-column
                      prop="caseId"
                      label="病历号"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="patientName"
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
                <el-tag type="warning" style="width: 100%">已取药</el-tag>
                <el-table
                    :data="recipellist802"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="recipeltrue">
                  <el-table-column
                      prop="caseId"
                      label="病历号"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="patientName"
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
        <el-main>
          <div class="head">
            <el-row>
              <el-col :md="6" style="margin: 11px">
                <el-button disabled>病历号：{{ recipelinfo.caseId }}</el-button>
              </el-col>
              <el-col :md="6" style="margin: 11px">
                <el-button disabled>患者姓名：{{ recipelinfo.patientName }}</el-button>
              </el-col>
              <el-col :md="6" style="margin: 11px">
                <el-button disabled>总价格：{{ recipelinfo.price }}￥</el-button>
              </el-col>
            </el-row>
            <el-row>
              <el-col :md="2" style="margin: 11px">
                <el-button @click="addRecipelInfo()" :disabled="disabled">添加</el-button>
              </el-col>
              <el-col :md="2" style="margin: 11px">
                <el-button @click="ky()" type="primary" :disabled="disabled">开药</el-button>
              </el-col>
            </el-row>
          </div>
          <div class="main">
            <el-table
                :data="recipelinfo.recipelinfo"
                style="width: 98%">
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
        </el-main>
      </el-container>

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
  name: "Recipel",
  data() {
    return {
      recipellist800: [],
      recipellist801: [],
      recipellist802: [],
      select: {
        pname: ''
      },
      recipelinfo: {
        recipelid: 0,
        caseId: 0,
        patientName: '',
        price: 0,
        recipelinfo: []
      },
      selectInfo: {
        recipelId: 0,
        state: 0
      },
      disabled: true,
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
    getRecipelList() {
      var params = new URLSearchParams();
      params.append("pname",this.select.pname)
      axios.post("/recipellist",params).then((res) => {
        this.recipellist800 = res.data.data.recipellist800
        this.recipellist801 = res.data.data.recipellist801
        this.recipellist802 = res.data.data.recipellist802
      })
    },
    recipelfalse(row) {
      this.selectInfo.recipelId = row.recipelid
      this.recipelinfo.recipelid = row.recipelid
      this.recipelinfo.caseId = row.caseId
      this.recipelinfo.patientName = row.patientName
      this.recipelinfo.price = row.price
      this.disabled = false
      this.getRecipelInfoList()
    },
    recipeltrue(row) {
      this.selectInfo.recipelId = row.recipelid
      this.recipelinfo.recipelid = row.recipelid
      this.recipelinfo.caseId = row.caseId
      this.recipelinfo.patientName = row.patientName
      this.recipelinfo.price = row.price
      this.disabled = true
      this.getRecipelInfoList()
    },
    getRecipelInfoList() {
      var params = new URLSearchParams();
      params.append("recipelId",this.selectInfo.recipelId)
      axios.post("/recipelinfolist",params).then((res) => {
        this.recipelinfo.recipelinfo = res.data.data;
      })
    },
    ky() {
      var params = new URLSearchParams();
      params.append("recipelid",this.recipelinfo.recipelid)
      axios.post("/recipelky",params).then((res) => {
        for (var i in this.recipelinfo) {
          this.recipelinfo[i] = '';
        }
        this.disabled = true
        this.getRecipelList()
      })
    },
    qy(row) {
      var params = new URLSearchParams();
      params.append("recipelid",row.recipelid)
      axios.post("/recipelqy",params).then((res) => {
        this.getRecipelList()
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
    editRecipelInfo(row) {
      this.title = '药物修改'
      this.drugelist = []
      this.recipelinfofrom = Object.assign({}, row);  //深拷贝
      this.drawer = true
    },
    onSubmit() {
      var url = ''
      if (this.recipelinfofrom.riid == '') {
        this.recipelinfofrom.recipelId = this.recipelinfo.recipelid
        url = '/addRecipelInfo'
      } else {
        url = '/editRecipelInfo'
      }
      axios.post(url, this.recipelinfofrom).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getPrice()
          this.getRecipelInfoList();
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
    delRecipelInfo(riid) {
      var params = new URLSearchParams();
      params.append("riid",riid)
      axios.post("/delRecipelInfo",params).then((res) => {
        this.$message.success(res.data.msg)
        this.getPrice()
        this.getRecipelInfoList();
      })
    },
    getPrice() {
      var params = new URLSearchParams();
      params.append("recipelid",this.recipelinfo.recipelid)
      axios.post("/getRecipelPrice",params).then((res) => {
        this.recipelinfo.price = res.data.data.price
      })
    }
  },
  mounted() {
    this.getRecipelList()
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