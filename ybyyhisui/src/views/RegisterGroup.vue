<template>
  <div class="background">
    <div class="registerGroup">
      <el-header>
        <el-row class="row-bg">
          <el-col :md="1" style="margin: 11px;">
            <span style='color: white ; font-size: 16px'>排班管理</span>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <div style="width: 100%">
          <el-row style="position: relative">
            <el-col :span="24">
              <el-table
                  :data="registerRuleList"
                  height="670"
                  border
                  style="width: 98%; margin: 20px 20px 20px 20px;">
                <el-table-column
                    prop="username"
                    label="医生姓名">
                </el-table-column>
                <el-table-column
                    prop="mondayAm"
                    label="星期一上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.mondayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="mondayPm"
                    label="星期一下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.mondayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="tuesdayAm"
                    label="星期二上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.tuesdayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="tuesdayPm"
                    label="星期二下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.tuesdayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="wednesdayAm"
                    label="星期三上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.wednesdayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="wednesdayPm"
                    label="星期三下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.wednesdayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="thursdayAm"
                    label="星期四上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.thursdayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="thursdayPm"
                    label="星期四下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.thursdayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="fridayAm"
                    label="星期五上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.fridayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="fridayPm"
                    label="星期五下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.fridayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="saturdayAm"
                    label="星期六上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.saturdayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="saturdayPm"
                    label="星期六下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.saturdayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="sundayAm"
                    label="星期日上午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.sundayAm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="sundayPm"
                    label="星期日下午"
                    width="70">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.sundayPm"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="rlId"
                    label="挂号等级"
                    width="100">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.rlId">
                      <el-option label="普通" value="1"></el-option>
                      <el-option label="专家" value="2"></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column
                    prop="count"
                    label="号数"
                    width="150">
                  <template slot-scope="scope">
                    <el-input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"
                              v-model="scope.row.count" placeholder="库存计量"></el-input>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
            <el-col :span="6" :offset="6">
              <span>规则组名：{{registerGroupInfo.rgname}}</span>
            </el-col>
            <el-col :span="6">
              <span>备注：{{registerGroupInfo.remarks}}</span>
            </el-col>
            <el-col :span="24">
              <div style="margin: 10px auto;">
                <el-button type="primary" @click="returnRegister()">取消</el-button>
                <el-button type="success" @click="onSubmit()">确认</el-button>
                <el-button type="warning" @click="clear()">重置</el-button>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-container>
    </div>
  </div>

</template>

<script>
export default {
  name: "RegisterGroup",
  data() {
    return {
      registerGroupInfo: this.$route.params,
      registerRuleList: [],
    }
  },
  methods: {
    getUserList() {
      var params = new URLSearchParams();
      params.append("dtId", this.registerGroupInfo.dtId)
      axios.post("/UserGroupList",params).then((res) => {
        for (var i in res.data.data) {
          var rule = {
            rgId: this.registerGroupInfo.rgid,
            userId: res.data.data[i].userid,
            username: res.data.data[i].usernamezh,
            rlId: 1,
            count: 30
          }
          this.registerRuleList.push(rule)
        }
        this.getRegisterRule()
      })
    },
    getRegisterRule() {
      axios.post("/RegisterRuleList",this.registerGroupInfo).then((res) => {
        for (var i in res.data.data){
          for (var j in this.registerRuleList) {
            if (this.registerRuleList[j].rgId === res.data.data[i].rgId && this.registerRuleList[j].userId === res.data.data[i].userId) {
              this.$set(this.registerRuleList, j,res.data.data[i])
              break
            }
          }
        }
        this.$forceUpdate()
      })
    },
    returnRegister() {
      this.$router.push("/register")
    },
    onSubmit() {
      axios.post("/updateGroup", this.registerRuleList).then((res) => {
        this.$message.success(res.data.msg);
        this.getRegisterRule();
      })
    },
    clear() {
      this.registerRuleList = []
      this.getUserList()
      this.$message.success("重置成功！");
    }
  },mounted() {
    if (this.registerGroupInfo.rgid != 0 && this.registerGroupInfo.rgid != null) {
      this.getUserList()
    } else {
      this.$router.push("/register")
    }
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
</style>