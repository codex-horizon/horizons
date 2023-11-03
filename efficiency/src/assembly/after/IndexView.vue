<template>
  <div class="container">
    <div class="fixed-unit">
      <div class="logo-unit">
        <span class="title">Efficiency</span>
      </div>
      <div class="menu-unit">
        <el-scrollbar>
          <el-menu style="border-right: none" :unique-opened="true" :default-active="$route.path" :router="true">
            <MenuView :menus="this.$store.getters.getMenus"/>
          </el-menu>
        </el-scrollbar>
      </div>
    </div>
    <div class="trend-unit">
      <div class="head-unit">
        <div class="head-left-unit">
          <el-button class="child" circle :icon="menuOnOff ? 'Expand' : 'Fold'" @click="handleMenuOnOff" title="切换菜单"/>
        </div>
        <div class="head-right-unit">
          <el-dropdown class="child" trigger="click" style="line-height: 0;">
            <span class="el-dropdown-link">
              <el-avatar :src="avatarURL" :size="44" v-loading.fullscreen.lock="fullscreenLoading"/>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item icon="User" @click="handleUpdatePassword">修改密码</el-dropdown-item>
                <el-dropdown-item icon="SwitchButton" @click="handleLogout">注销登出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <el-button class="child" circle icon="Setting" @click="handleOpenDrawer" title="工作台设置"/>
          <el-button class="child" circle icon="Bell" @click="handleOpenNotice" title="消息通知"/>
        </div>
      </div>
      <div class="fast-unit">
        <el-breadcrumb separator-icon="ArrowRight">
          <el-breadcrumb-item :to="{path: item.path}" v-for="(item, index) in this.$store.getters.getBreadcrumbList" :key="index">
            <span style="font-size: 10px;">{{ item.meta.title }}</span>
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="main-unit">
        <router-view/>
      </div>
    </div>
  </div>
  <!-- 快捷设置 -->
  <ShortcutSettingsView ref="shortcutSettings"/>
  <!-- 快捷通知 -->
  <ShortcutNoticeView ref="shortcutNotice"/>
</template>

<script>
import ShortcutSettingsView from "@/assembly/after/components/ShortcutSettingsView.vue";
import ShortcutNoticeView from "@/assembly/after/components/ShortcutNoticeView.vue";
import MenuView from '@/assembly/after/components/MenuView.vue';
import {h} from "@/utils";

export default {
  name: "IndexView",
  data() {
    return {
      avatarURL: require('@/assets/avatar.jpeg'),
      fullscreenLoading: false,
      menuOnOff: false
    }
  },
  props: {},
  components: {
    ShortcutSettingsView,
    ShortcutNoticeView,
    MenuView
  },
  methods: {
    handleOpenDrawer() {
      this.$refs.shortcutSettings.handleOpen();
    },
    handleLogout() {
      this.$messageBox.confirm(h("p", {style: "color: #000000;"}, [
        h("span", {style: "color: #000000;"}, "确认"),
        h("strong", {style: "color: teal;"}, " 登出 "),
        h("span", {style: "color: #000000;"}, "吗？"),
      ]), "警告提示", {
        confirmButtonText: "确 认",
        cancelButtonText: "取 消",
        type: "warning",
        autofocus: false
      }).then(() => {
        setTimeout(() => {
          this.fullscreenLoading = true;
        }, 0)
        setTimeout(() => {
          this.$router.push("/login.html");
        }, 1000)
      }).catch(() => {
        this.$message.info("已取消");
      });
    },
    handleMenuOnOff() {
      this.$message.info("切换菜单");
      this.menuOnOff = !this.menuOnOff;
    },
    handleOpenNotice() {
      this.$refs.shortcutNotice.handleOpen();
    },
    handleUpdatePassword() {
      this.$message.info("修改密码");
    }
  },
  beforeCreate() {
    console.log("1.在组件实例初始化完成之后立即调用。");
  },
  created() {
    console.log("2.在组件实例处理完所有与状态相关的选项后调用。");
  },
  beforeMount() {
    console.log("3.在组件被挂载之前调用。");
  },
  mounted() {
    console.log("4.在组件被挂载之后调用。");
  },
  beforeUpdate() {
    console.log("5.在组件即将因为一个响应式状态变更而更新其 DOM 树之前调用。");
  },
  updated() {
    console.log("6.在组件因为一个响应式状态变更而更新其 DOM 树之后调用。");
  },
  beforeUnmount() {
    console.log("7.在一个组件实例被卸载之前调用。");
  },
  unmounted() {
    console.log("8.在一个组件实例被卸载之后调用。");
  },
  errorCaptured() {
    console.log("9.在捕获了后代组件传递的错误时调用。");
  },
  renderTracked() {
    console.log("10.在一个响应式依赖被组件的渲染作用追踪后调用。");
  },
  renderTriggered() {
    console.log("11.在一个响应式依赖被组件触发了重新渲染之后调用。");
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
    console.log("14.当组件实例在服务器上被渲染之前要完成的异步函数。");
  }
};
</script>

<style scoped lang="scss">
.container {
  display: flex;

  .fixed-unit {
    flex: 0 0 256px;

    .logo-unit {
      width: 100%;
      height: 82px;
      display: flex;
      align-items: center;
      justify-content: center;
      box-sizing: border-box;
      border-right: 1px solid #e0e0e0;

      .title {
        font-size: 28px;
      }
    }

    .menu-unit {
      width: 100%;
      height: calc(100% - 82px);
      box-sizing: border-box;
      border-right: 1px solid #e0e0e0;
    }
  }

  .trend-unit {
    flex: 1 1 auto;

    .head-unit {
      width: 100%;
      height: 52px;
      display: flex;
      align-items: center;
      box-sizing: border-box;
      border-bottom: 1px solid #e0e0e0;

      .head-left-unit, .head-right-unit {
        width: 50%;
        height: 100%;
        display: flex;
        align-items: center;

        .child {
          cursor: pointer;
        }
      }

      .head-left-unit {
        flex-direction: row;

        .child {
          margin: 0 0 0 16px;
        }
      }

      .head-right-unit {
        flex-direction: row-reverse;

        .child {
          margin: 0 16px 0 0;
        }
      }

    }

    .fast-unit {
      height: 30px;
      display: flex;
      align-items: center;
      padding: 0 0 0 16px;
    }

    .main-unit {
      width: 100%;
      height: calc(100% - 52px - 30px);
      background-color: #f2f2f2;
    }
  }
}

</style>