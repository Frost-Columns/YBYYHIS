<template>
  <div class="background">
    <div class="cure">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="2" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >检查治疗项目管理</span>
            </el-col>
            <el-col :md="3">
              <el-input v-model="select.cname" placeholder="项目名关键字" style="width: 95%"></el-input>
            </el-col>
            <el-col :md="3" style="margin: 11px">
              <el-checkbox-group v-model="select.stateList">
                <el-checkbox label="600">检查项目</el-checkbox>
                <el-checkbox label="1000">治疗项目</el-checkbox>
              </el-checkbox-group>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getCureList(1)">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table
              :data="curelist"
              stripe
              style="width: 100%">
            <el-table-column
                prop="cureid"
                label="项目ID">
            </el-table-column>
            <el-table-column
                prop="cname"
                label="项目名">
            </el-table-column>
            <el-table-column
                prop="cnamezh"
                label="项目中文名">
            </el-table-column>
            <el-table-column
                prop="price"
                label="项目费用">
            </el-table-column>
            <el-table-column
                prop="loc"
                label="项目地址">
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
                <el-button @click="modifyCure(scope.row)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  编辑
                </el-button>
                <el-button @click="updateStateCure(scope.row,2)"
                           v-if="scope.row.state==201"
                           type="text"
                           size="small"
                           slot="reference">
                  启用
                </el-button>
                <el-button @click="updateStateCure(scope.row,1)"
                           v-if="scope.row.state!=201"
                           type="text"
                           size="small">
                  停用
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
              background
              layout="prev, pager, next"
              :page-size="10"
              :total="total"
              :current-page="index"
              @current-change="getCureList">
          </el-pagination>
          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>
        </el-main>
      </el-container>

      <el-drawer
          :title="title"
          :visible.sync="drawer">
        <el-form :model="curefrom" ref="curefrom" :rules="rules" label-position="right">
          <el-form-item prop="cname" label="项目名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="curefrom.cname" placeholder="项目名"></el-input>
          </el-form-item>
          <el-form-item prop="cnamezh" label="项目中文名" style="width: 80%; margin: 20px 10%">
            <el-input v-model="curefrom.cnamezh" placeholder="项目中文名"></el-input>
          </el-form-item>
          <el-form-item prop="price" label="价格" style="width: 80%; margin: 20px 10%">
            <el-input type="number" min="0" v-model="curefrom.price" placeholder="价格"></el-input>
          </el-form-item>
          <el-form-item prop="loc" label="地址" style="width: 80%; margin: 20px 10%">
            <el-input v-model="curefrom.loc" placeholder="地址"></el-input>
          </el-form-item>
          <el-form-item prop="state" label="项目类型：" style="width: 80%; margin: 20px 10%">
            <el-radio-group v-model="curefrom.state">
              <el-radio :label="600">检查项目</el-radio>
              <el-radio :label="1000">治疗项目</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('curefrom')">提交</el-button>
            <el-button @click="drawer = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cure",
  data() {
    return {
      curelist: [],
      index: 1,
      total: 0,
      title: '',
      drawer: false,
      curefrom: {
        cureid: 0,
        cname: '',
        cnamezh: '',
        price: 0,
        loc: '',
        state: 600
      },
      select: {
        cureid: '',
        cname: '',
        stateList: []
      },
      rules: {
        cname: [
          {required: true, message: '请输入项目名', trigger: 'blur'}
        ],
        cnamezh: [
          {required: true, message: '请输入项目中文名名', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入价格', trigger: 'blur'}
        ],
        loc: [
          {required: true, message: '请输入地址', trigger: 'blur'}
        ],
        state: [
          {required: true, message: '请选择项目类型', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getCureList(index) {
      var params = new URLSearchParams();
      params.append("index", index)
      params.append("cname", this.select.cname)
      params.append("statelist", this.select.stateList)
      axios.post("/curelist", params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.curelist = res.data.data.records;
      })
    },
    add() {
      this.title = '项目添加';
      if(this.$refs['curefrom']!==undefined){
        this.$refs['curefrom'].resetFields();
      }
      for (var i in this.curefrom) {
        this.curefrom[i] = '';
      }
      this.curefrom.price = 0;
      this.drawer = true;
    },
    modifyCure(row) {
      this.title = '项目信息修改';
      if(this.$refs['curefrom']!==undefined){
        this.$refs['curefrom'].resetFields();
      }
      this.curefrom = Object.assign({}, row);  //深拷贝
      this.drawer = true;
    },
    onSubmit(dfrom) {
      this.$refs[dfrom].validate((valid) => {
        if (valid) {
          var url = '';
          if (this.curefrom.cureid == '') {
            url = '/InsertCure';
          } else {
            url = '/UpdateCure';
          }
          axios.post(url, this.curefrom).then((res) => {
            if (res.data.data) {
              this.$message.success(res.data.msg);
              this.getCureList(this.index);
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
    updateStateCure(row, state) {
      var url = '';
      var title = '';
      if (state == 1) {
        title = '确认要停用该项目？';
        url = '/CancellationCure';
      } else if (state == 2) {
        title = '确认要启用该项目？';
        url = '/ThawCure';
      }
      this.$confirm(title, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(url, row).then((res) => {
          if(res.data.code == 200) {
            this.$message.success(res.data.msg);
            this.getCureList(this.index);
          }else {
            this.$message.error(res.data.msg);
          }
        })
      }).catch(() => {
        return
      })
    },
    reset() {
      for (var i in this.select) {
        this.select[i] = '';
      }
      this.select.stateList = [];
      this.getCureList(1);
    }
  },
  mounted() {
    this.getCureList(this.index)
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