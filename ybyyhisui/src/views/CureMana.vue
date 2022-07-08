<template>
  <div class="background">
    <div class="curecheck">
      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>治疗管理</span>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="400px">
          <el-container>
            <el-header>
              <el-row>
                <el-col :md="16" style="margin: 11px">
                  <el-input v-model="pname" placeholder="患者名称" style="width: 95%; left: 20px"></el-input>
                </el-col>
                <el-col :md="3" style="margin: 11px">
                  <el-button type="primary" @click="getCureLogsList()" icon="el-icon-refresh" circle></el-button>
                  <!--查找-->
                </el-col>
              </el-row>
            </el-header>
            <el-main style="margin: -20px 0 0 0;">
              <div>
                <el-tag style="width: 100%">未治疗</el-tag>
                <el-table
                    :data="curelogslist1000"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220">
                  <el-table-column
                      prop="pname"
                      label="患者姓名"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="cname"
                      label="治疗项目"
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
                <el-tag type="success" style="width: 100%">治疗中</el-tag>
                <el-table
                    :data="curelogslist1001"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="curefalse">
                  <el-table-column
                      prop="pname"
                      label="患者姓名"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="cname"
                      label="治疗项目"
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
                <el-tag type="warning" style="width: 100%">已治疗</el-tag>
                <el-table
                    :data="curelogslist1002"
                    stripe
                    highlight-current-row
                    :row-style="{height: '53px'}"
                    height="220"
                    @row-click="curetrue">
                  <el-table-column
                      prop="pname"
                      label="患者姓名"
                      width="150">
                  </el-table-column>
                  <el-table-column
                      prop="cname"
                      label="检查项目"
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
          <div class="popContainer" v-if="disabled == true"></div>
          <el-card class="box-card" style="height: 98%;">
            <div slot="header" class="clearfix">
              <span>治疗结果</span>
              <el-button style="float: right; padding: 3px 0" type="primary" @click="onSubmit()">确认</el-button>
            </div>
            <div>
              <el-row>
                <el-col :span="12">
                  <div class="right">
                    <span>结果图片</span>
                    <el-upload
                        action="http://localhost:8081/updateCureLogs"
                        list-type="picture-card"
                        accept=".jpg,.png"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :on-change="handleChange"
                        :auto-upload="true"
                        :http-request="onSubmitIcon"
                        :file-list="files"
                        :before-upload="beforeAvatarUpload"
                        :limit="1">
                      <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                      <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                  </div>

                </el-col>
                <el-col :span="12">
                  <span>治疗结果</span>
                  <el-input
                      type="textarea"
                      placeholder="检查结果"
                      v-model="curelogsinfo.cureResults"
                      :rows="10"
                      resize="none"
                      @change="updateCure()"
                      show-word-limit>
                  </el-input>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "CureMana",
  data() {
    return {
      curelogslist1000: [],
      curelogslist1001: [],
      curelogslist1002: [],
      pname: '',
      disabled: true,
      curelogsinfo: {},
      cricon: '',
      clid: '',

      dialogImageUrl: '',
      dialogVisible: false,
      files: {}
    }
  },
  methods: {
    getCureLogsList() {
      var params = new URLSearchParams();
      params.append("pname", this.pname)
      axios.post("/getCureManaList", params).then((res) => {
        this.curelogslist1000 = res.data.data.curelogslist1000
        this.curelogslist1001 = res.data.data.curelogslist1001
        this.curelogslist1002 = res.data.data.curelogslist1002
      })
    },
    qy(row) {
      var params = new URLSearchParams();
      params.append("clid", row.clid)
      axios.post("/CureQy", params).then((res) => {
        this.getCureLogsList()
      })
    },
    curefalse(row) {
      this.curelogsinfo = row
      this.cricon = row.cricon
      this.disabled = false
    },
    curetrue(row) {
      this.curelogsinfo = row
      this.cricon = row.cricon
      this.disabled = true
    },
    openfile() {
      this.$refs.myfile.dispatchEvent(new MouseEvent('click'))
    },
    onSubmitIcon(val) {
      console.log(this.files)
      var params = new FormData();
      params.append("file", val.file)
      params.append('clid',this.curelogsinfo.clid);
      axios.post("/updateCureLogsIcon",params,{
        headers: {
          'Content-Type': 'false',
        }
      }).then((res) => {
        this.curelogsinfo = res.data.data
      })
    },
    updateCure() {
      axios.post("/updateCureLogs", this.curelogsinfo).then((res) => {
        this.getCureLogsList()
      })
    },
    onSubmit() {
      var params = new URLSearchParams();
      params.append("clid", this.curelogsinfo.clid)
      axios.post("/CureWc", params).then((res) => {
        this.getCureLogsList()
      })
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleChange(file, files) {
      this.files = file
    },

    beforeAvatarUpload(file) {
      var isJPG = false;
      if (file.type === 'image/png' || file.type === 'image/jpeg') {
        isJPG = true;
      } else {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      return isJPG;
    }

  },
  mounted() {
    this.getCureLogsList()
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


div.popContainer {
  position: absolute;
  top: 124px;
  left: 600px;
  right: 0;
  bottom: 8px;
  z-index: 100;
  background: rgba(0,0,0,.3);
}
</style>