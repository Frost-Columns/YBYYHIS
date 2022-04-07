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
        <div class="head">
          <el-row>
            <el-col :md="6" style="margin: 11px">
              <el-input v-model="select.dname" placeholder="药品名" style="width: 95%; left: 20px"></el-input>
            </el-col>
            <el-col :md="1" style="margin: 11px">
              <el-button type="primary" @click="getDrugsList(1)">查找</el-button>
            </el-col>
            <el-col :md="1" style="margin: 11px">
              <el-button @click="addDrugs()">新增</el-button>
            </el-col>
          </el-row>
        </div>
        <div class="main">
          <el-table
              :data="drugslist"
              style="width: 98%">
            <el-table-column
                prop="drugsname"
                label="疾病名">
            </el-table-column>
            <el-table-column
                prop="drugsnamezh"
                label="疾病中文名">
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
                prop="dosage_id"
                label="药品剂型">

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
              @current-change="getDrugsList">
          </el-pagination>
        </div>
      </el-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "Drugs"
}
</script>

<style scoped>

</style>