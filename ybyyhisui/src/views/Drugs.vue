<template>
  <div class="background">
    <div class="drugs">
      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>药品管理</span>
          </el-col>
        </el-row>
      </el-header>
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row>
            <el-col :md="6" style="margin: 11px">
              <el-input v-model="select.dname" placeholder="药品名/生产厂家" style="width: 95%; left: 20px"></el-input>
            </el-col>
            <el-col :md="1" style="margin: 11px">
              <el-button type="primary" @click="getDrugsList(1)">查找</el-button>
            </el-col>
            <el-col :md="1" style="margin: 11px">
              <el-button @click="addDrugs()">新增</el-button>
            </el-col>
            <el-col :md="1" style="margin: 11px">
              <el-popconfirm title="确定删除吗？" @confirm="delDrugsList()">
                <el-button slot="reference" type="danger">批量删除</el-button>
              </el-popconfirm>
            </el-col>
          </el-row>
        </el-header>
        <el-main style="padding-top: 0px">
          <el-table
              :data="drugslist"
              height="650"
              style="width: 98%"
              @selection-change="multipleSelection">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column
                prop="drugsname"
                label="药品名">
            </el-table-column>
            <el-table-column
                prop="drugsnamezh"
                label="药品中文名">
            </el-table-column>
            <el-table-column
                prop="count"
                label="剩余计量">
            </el-table-column>
            <el-table-column
                prop="unit"
                label="包装单位">
            </el-table-column>
            <el-table-column
                prop="dosageId"
                label="药品剂型">
              <template slot-scope="scope">
                {{getDosage(scope.row.dosageId)}}
              </template>
            </el-table-column>
            <el-table-column
                prop="price"
                label="药品单价">
            </el-table-column>
            <el-table-column
                prop="manufacturer"
                label="生产厂家">
            </el-table-column>
            <el-table-column
                prop="state"
                label="状态">
            </el-table-column>
            <el-table-column
                label="操作"
                width="155">
              <template slot-scope="scope">
                <el-button @click="editDrugs(scope.row)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  编辑
                </el-button>
                <el-popconfirm title="确定删除吗？" @confirm="delDrugs(scope.row.drugsid)">
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
              :current-page="index"
              @current-change="getDrugsList">
          </el-pagination>
        </el-main>
      </el-container>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="drugsfrom" ref="drugsfrom" :rules="rules" label-position="right">
          <el-form-item prop="drugsname" label="药品名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="drugsfrom.drugsname" placeholder="药品名"></el-input>
          </el-form-item>
          <el-form-item prop="drugsnamezh" label="药品中文名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="drugsfrom.drugsnamezh" placeholder="药品中文名"></el-input>
          </el-form-item>
          <el-form-item prop="count" label="库存计量" style="width: 80%; margin: 20px 10%">
            <el-input type="number" min="0" v-model="drugsfrom.count" placeholder="库存计量">
              <el-select v-model="drugsfrom.unit" slot="append" placeholder="" style="width: 100px">
                <el-option value="支"></el-option>
                <el-option value="瓶"></el-option>
                <el-option value="盒"></el-option>
                <el-option value="袋"></el-option>
                <el-option value="克"></el-option>
                <el-option value="板"></el-option>
                <el-option value="粒"></el-option>
                <el-option value="包"></el-option>
                <el-option value="条"></el-option>
                <el-option value="片"></el-option>
                <el-option value="枚"></el-option>
                <el-option value="只"></el-option>
              </el-select>
            </el-input>
          </el-form-item>
          <el-form-item prop="dosageId" label="药品剂型" style="width: 80%; margin: 20px 10%">
            <el-select v-model="drugsfrom.dosageId" placeholder="药品剂型" style="width: 100%">
              <el-option
                  v-for="item in dosagelist"
                  :key="item.dosageid"
                  :label="item.dosagename"
                  :value="item.dosageid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="format" label="包装规格" style="width: 80%; margin: 20px 10%">
            <el-input v-model="drugsfrom.format" placeholder="包装规格"></el-input>
          </el-form-item>
          <el-form-item prop="price" label="价格" style="width: 80%; margin: 20px 10%">
            <el-input type="number" min="0" v-model="drugsfrom.price" placeholder="价格"></el-input>
          </el-form-item>
          <el-form-item prop="manufacturer" label="生产商" style="width: 80%; margin: 20px 10%">
            <el-input v-model="drugsfrom.manufacturer" placeholder="生产商"></el-input>
          </el-form-item>
          <el-form-item prop="drugeRemarks" label="药品描述" style="width: 80%; margin: 20px 10%">
            <el-input
                type="textarea"
                placeholder="药品描述"
                v-model="drugsfrom.drugeRemarks"
                maxlength="120"
                show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('drugsfrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "Drugs",
  data() {
    return {
      index: 1,
      total: 0,
      drawer: false,
      title: '',
      select: {
        dname: ''
      },
      drugslist: [],
      dosagelist:[],
      drugsfrom: {
        drugsid: 0,
        drugsname: '',
        drugsnamezh: '',
        count: 0,
        unit: '支',
        dosageId: 0,
        format: '',
        price: 0,
        drugeRemarks: '',
        manufacturer: ''
      },
      dels: [],
      rules: {
        drugsname: [
          {required: true, message: '请输入药品名', trigger: 'blur'}
        ],
        drugsnamezh: [
          {required: true, message: '请输入药品中文名', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请输入库存计量', trigger: 'blur'}
        ],
        format: [
          {required: true, message: '请输入包装规格', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入药品价格', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getDrugsList(index) {
      var params = new URLSearchParams();
      params.append("index",index)
      params.append("dname",this.select.dname)
      axios.post("/drugslist",params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.drugslist = res.data.data.records;
      })
    },
    getDosage(dosageid) {
      for (var i in this.dosagelist) {
        if (this.dosagelist[i].dosageid == dosageid) {
          return this.dosagelist[i].dosagename
        }
      }
    },
    addDrugs() {
      this.title = '药品添加';
      for (var i in this.drugsfrom) {
        this.drugsfrom[i] = '';
      }
      this.drugsfrom.unit = '支'
      this.drugsfrom.dosageId = 110
      this.drawer = true;
    },
    editDrugs(row) {
      this.title = '药品信息修改';
      this.drugsfrom = Object.assign({}, row);  //深拷贝
      this.drawer = true;
    },
    onSubmit(dfrom) {
      this.$refs[dfrom].validate((valid) => {
        if (valid) {
          var url = '';
          if (this.drugsfrom.drugsid == '') {
            url = '/InsertDrugs';
          } else {
            url = '/UpdateDrugs';
          }
          axios.post(url, this.drugsfrom).then((res) => {
            if (res.data.data) {
              this.$message.success(res.data.msg);
              this.getDrugsList(this.index);
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
    delDrugs(drugsid) {
      var params = new URLSearchParams();
      params.append("drugsid", drugsid)
      axios.post("/delDrugs", params).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getDrugsList(this.index);
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    multipleSelection(dels) {
      this.dels = dels
      console.log(this.dels)
    },
    delDrugsList() {
      axios.post("/delDrugsList", this.dels).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          this.getDrugsList(this.index);
        } else {
          this.$message.error(res.data.msg);
        }
      })
    }
  },
  mounted() {
    this.getDrugsList(this.index)
    axios.get("/dosagelist").then((res) => {
      this.dosagelist = res.data.data
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

.cell {
  float: right;
  line-height: 23px;
  padding-left: 10px;
  padding-right: 10px;
}

</style>