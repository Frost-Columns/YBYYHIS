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
                <el-checkbox label="200">启用</el-checkbox>
                <el-checkbox label="201">停用</el-checkbox>
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
                prop="cnamzh"
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
          <!--          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>-->
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cure",
  data() {
    return {
      caselist: [],
      index: 1,
      total: 0,
      select: {
        cname: '',
        stateList: ['200']
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
        this.caselist = res.data.data.records;
      })
    }
  },
  mounted() {
    this.getCureList(index)
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