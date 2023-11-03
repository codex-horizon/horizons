<template>
  <div class="container">
    <el-form class="login" status-icon :model="form" ref="formRef">
      <el-form-item>
        <div class="form-item-avatar">
          <el-avatar :size="68" :src="avatarURL" fit="cover" @error="() => true"/>
        </div>
      </el-form-item>
      <el-form-item prop="username" :rules="[{required: true, message: '用户名 空', trigger: 'blur'}]">
        <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" clearable/>
      </el-form-item>
      <el-form-item prop="password" :rules="[{required: true, message: '密码 空', trigger: 'blur'}]">
        <el-input v-model="form.password" placeholder="密码" prefix-icon="Key" show-password clearable/>
      </el-form-item>
      <el-form-item prop="code" :rules="[{required: true, message: '验证码 空', trigger: 'blur'}]">
        <el-input v-model="form.code" placeholder="验证码" prefix-icon="Promotion" clearable>
          <template #suffix>
            <el-avatar class="captcha" :size="20" style="" shape="square" :src="captchaURL" @click="handlerCaptcha"/>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <div class="form-item-authentication">
          <el-button icon="Right" circle @click="handlerAuthentication('formRef')"
                     v-loading.fullscreen.lock="fullscreenLoading"/>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {captchaApi, userApi} from '@/api';

export default {
  name: 'LoginView',
  data() {
    return {
      avatarURL: require('@/assets/avatar.jpeg'),
      form: {
        username: '',
        password: '',
        code: '',
      },
      captchaURL: '',
      fullscreenLoading: false,
    }
  },
  props: {},
  components: {},
  watch: {
    'form.username'(newUsername, oldUsername) {
      if (newUsername && newUsername.length === 11) {
        this.handlerCaptcha();
      } else if (oldUsername && oldUsername.length === 11) {
        this.form.code = '';
      }
    }
  },
  methods: {
    handlerCaptcha: async function () {
      const {data} = await captchaApi.fetchCaptcha(this.form.username);
      this.captchaURL = data;
    },
    handlerAuthentication(formName) {
      this.fullscreenLoading = true;
      this.$refs[formName].validate(async (valid/*, object*/) => {
        if (valid) {
          const {code, data, message} = await userApi.fetchAuthentication(this.form);
          console.info(data);
          if (code === 'Biz_Ok_Response') {
            setTimeout(() => {
              this.$router.push('/index');
            }, 1000)
            return true;
          }
          this.fullscreenLoading = false;
          this.$message.error(message);
          return false;
        } else {
          this.fullscreenLoading = false;
          return false;
        }
      });
    },
    initializer() {
    }
  },
  setup(props, context) {
    console.info(props, context);
  },
  beforeCreate() {
    console.log('1.在组件实例初始化完成之后立即调用。');
  },
  created() {
    console.log('2.在组件实例处理完所有与状态相关的选项后调用。');
  },
  beforeMount() {
    console.log('3.在组件被挂载之前调用。');
  },
  mounted() {
    console.log('4.在组件被挂载之后调用。');
    this.initializer();
  },
  beforeUpdate() {
    console.log('5.在组件即将因为一个响应式状态变更而更新其 DOM 树之前调用。');
  },
  updated() {
    console.log('6.在组件因为一个响应式状态变更而更新其 DOM 树之后调用。');
  },
  beforeUnmount() {
    console.log('7.在一个组件实例被卸载之前调用。');
  },
  unmounted() {
    console.log('8.在一个组件实例被卸载之后调用。');
  },
  errorCaptured() {
    console.log('9.在捕获了后代组件传递的错误时调用。');
  },
  renderTracked() {
    console.log('10.在一个响应式依赖被组件的渲染作用追踪后调用。');
  },
  renderTriggered() {
    console.log('11.在一个响应式依赖被组件触发了重新渲染之后调用。');
  },
  activated() {
    console.log(
        "12.若组件实例是 </KeepAlive>/ 缓存树的一部分，当组件被插入到 DOM 中时调用。"
    );
  },
  deactivated() {
    console.log(
        "13.若组件实例是 </KeepAlive>/ 缓存树的一部分，当组件从 DOM 中被移除时调用。"
    );
  },
  serverPrefetch() {
    console.log('14.当组件实例在服务器上被渲染之前要完成的异步函数。');
  }
};
</script>

<style scoped lang="scss">
.container {
  background-image: url(@/assets/animation.svg);
  background-size: 100% 100%;
  background-repeat: no-repeat;

  display: flex;
  align-items: center;
  justify-content: center;

  .login {
    width: 360px;
    padding: 36px 36px 18px;
    box-shadow: 0 11px 34px 0 #0000001a;
    border: 1px solid #e4e4e4;
    border-radius: 12px;

    .avatar, .captcha {
      width: 100px;
      background-color: transparent;
      cursor: pointer;
    }

    .form-item-avatar, .form-item-authentication {
      width: 100%;
      height: 100%;

      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}

</style>