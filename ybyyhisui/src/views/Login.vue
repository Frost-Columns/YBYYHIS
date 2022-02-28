<template>
  <div class="background">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png">
      </div>
      <el-form ref="loginFormRef" :model="from" :rules="rules" label-width="0px" class="login_from">
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="from.username" prefix-icon="iconfont icon-user" maxlength="24"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="userpwd">
          <el-input v-model="from.userpwd" prefix-icon="iconfont icon-3702mima" show-password
                    maxlength="24"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login()">登录</el-button>
          <el-button type="info" @click="resetLoginFrom()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      from: {
        username: 'test',
        userpwd: '123456'
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        userpwd: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    resetLoginFrom() {
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      this.$refs.loginFormRef.validate(valid => {
        if (!valid) return;
        axios.post("/login", this.from).then((res) => {
          if(res.data.code == 200) {
            this.$message.success(res.data.msg);
            sessionStorage.setItem("token", res.data.data);
            this.$router.push("/Main");
          }else {
            this.$message.error(res.data.msg);
          }
        });
      });
    }
  }
}
</script>

<style scoped>
.background {
  background-color: #00597a;
  height: 100%;
}

.login_box {
  border-radius: 8px;
  width: 450px;
  height: 300px;
  background-color: #fff;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.avatar_box {
  width: 130px;
  height: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
}

.avatar_box > img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}

.login_from {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 40px;
  box-sizing: border-box;
}
</style>
