<template>
  <div class="background">
    <div class="curelogs">
      <el-container style="width: 104%; margin: 0px -20px">
        <el-header>
          <el-row class="row-bg">
            <el-col :md="1" style="margin: 11px">
              <span style='color: white ; font-size: 16px' >项目记录</span>
            </el-col>
            <el-col :md="3">
              <el-input v-model="name" placeholder="姓名/项目名" style="width: 95%"></el-input>
            </el-col>
            <el-col :md="3">
              <el-button type="primary" @click="getCurelogsList(1)">查找</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table
              :data="curelogslist"
              stripe
              style="width: 100%">
            <el-table-column
                prop="clid"
                label="ID">
            </el-table-column>
            <el-table-column
                prop="pname"
                label="患者姓名">
            </el-table-column>
            <el-table-column
                prop="cname"
                label="项目名称">
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间">
              <template slot-scope="scope">{{ scope.row.sex | dateFromat }}</template>
            </el-table-column>
            <el-table-column
                prop="cureTime"
                label="检查/治疗时间">
              <template slot-scope="scope">{{ scope.row.sex | dateFromat }}</template>
            </el-table-column>
            <el-table-column
                prop="sname"
                label="状态">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                width="155">
              <template slot-scope="scope">
                <el-button @click="curelogsInfo(scope.row)"
                           v-if="scope.row.state == 602 || scope.row.state == 1002"
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
              :current-page="index"
              @current-change="getCurelogsList">
          </el-pagination>
          <!--          <el-button @click="add()" fixed="right" class="add" type="success" icon="el-icon-plus" circle></el-button>-->
        </el-main>
      </el-container>

      <el-dialog
          :title="title"
          :visible.sync="drawer">
        <el-row>
          <el-col :span="12">
            <div class="right">
              <img class="mx-auto d-block img-thumbnail" style="width: 300px; height:300px;" :src='cricon' />
            </div>

          </el-col>
          <el-col :span="12">
            <span>治疗结果</span>
            <el-input
                type="textarea"
                placeholder="检查结果"
                v-model="curelogsinfo.cureResults"
                disabled
                :rows="10"
                resize="none"
                show-word-limit>
            </el-input>
          </el-col>
        </el-row>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "CureLogs",
  data() {
    return {
      curelogslist: [],
      index: 1,
      total: 0,
      curelogsinfo: {},
      cricon: '',
      name: '',
      drawer: false
    }
  },
  methods: {
    getCurelogsList(index) {
      var params = new URLSearchParams();
      params.append("index", index)
      params.append("name", this.name)
      axios.post("/curelogslist", params).then((res) => {
        this.index = res.data.data.current;
        this.total = res.data.data.total;
        this.curelogslist = res.data.data.records;
      })
    },
    curelogsInfo(row){
      this.curelogsinfo = row
      this.cricon = this.curelogsinfo.cricon.replaceAll("\\", "/");
      this.drawer = true
      // this.$router.push({
      //   path: '/curelogsinfo',
      //   name: 'CureLogsInfo',
      //   params: row
      // });
    },
    reset() {
      this.name = ''
      this.getCureList(1);
    }
  },mounted() {
    this.getCurelogsList(this.index)
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
  background-color: #e5f2ff;
}
</style>