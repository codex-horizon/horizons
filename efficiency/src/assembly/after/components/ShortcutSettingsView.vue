<template>
  <el-drawer v-model="visible" :direction="direction" :size="size" :before-close="handleClose">
    <template #header>
      <h4>{{ title }}</h4>
    </template>
    <template #default>
      <el-scrollbar>
        <div class="container">
          <el-divider content-position="left" class="divider">
            <span class="plain-theme">系统主题</span>
            <el-divider direction="vertical" border-style="dashed"/>
            <el-switch v-model="form.theme" inline-prompt active-icon="Sunny" active-color="#ededed" inactive-color="#181818" inactive-icon="Moon"/>
          </el-divider>
          <el-divider content-position="left" class="divider">
            <span class="plain-theme">主题色系</span>
            <el-divider direction="vertical" border-style="dashed"/>
            <el-color-picker v-model="predefineColor" show-alpha :predefine="predefineColors"/>
          </el-divider>
          <el-divider content-position="left" class="divider">
            <span class="plain-theme">元素大小</span>
            <el-divider direction="vertical" border-style="dashed"/>
            <el-radio-group v-model="currentSize">
              <el-radio v-for="(element, index) in elementSize" :key="index" @change="handleResize(element.value)" :label="element.size" :size="element.value">{{element.name}}</el-radio>
            </el-radio-group>
          </el-divider>
        </div>
      </el-scrollbar>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="cancelClick">取 消</el-button>
        <el-button type="primary" @click="confirmClick">确 认</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script>
import {h} from "@/utils";

export default {
  name: "ShortcutSettingsView",
  data() {
    return {
      visible: false,
      direction: "rtl", // rtl / ltr / ttb / btt
      size: '30%',
      title: "工作主题",
      form: {
        theme: true
      },
      predefineColor: 'rgba(255, 69, 0, 0.68)',
      predefineColors: [
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577',
      ],
      currentSize: 18,
      elementSize: [
        {name:'大',value: 'large', size: 22},
        {name:'中',value: 'default', size: 18},
        {name:'小',value: 'small', size: 14}
      ]
    }
  },
  props: {},
  components: {},
  methods: {
    handleOpen() {
      this.$message.info("系统设置");
      // this.visible = true;
    },
    handleClose(done) {
      this.$messageBox.confirm(h("p", {style: "color: #000000;"}, [
        h("span", {style: "color: #000000;"}, "确认"),
        h("strong", {style: "color: teal;"}, " 关闭 "),
        h("span", {style: "color: #000000;"}, "工作主题设置吗？"),
      ]), "警告提示", {
        confirmButtonText: "确 认",
        cancelButtonText: "取 消",
        type: "warning",
        autofocus: false
      }).then(() => {
        done()
        this.$message.success("已关闭");
      }).catch(() => {
        this.$message.info("已取消");
      });
    },
    handleResize(size) {
      this.$store.commit('setFormComponentSize', size);
    },
    cancelClick() {
      this.visible = false;
    },
    confirmClick() {
      this.$messageBox.confirm(h("p", {style: "color: #000000;"}, [
        h("span", {style: "color: #000000;"}, "确认"),
        h("strong", {style: "color: teal;"}, " 保存 "),
        h("span", {style: "color: #000000;"}, "工作主题设置吗？"),
      ]), "警告提示", {
        confirmButtonText: "确 认",
        cancelButtonText: "取 消",
        type: "warning",
        autofocus: false
      }).then(() => {
        this.visible = false;
        this.$message.success("已保存");
      }).catch(() => {
        this.$message.info("已取消");
      });
    }
  },
  setup(props, context) {
    console.info(props, context);
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
  .plain-theme {
    //color: teal;
  }

  .divider {
    padding: 0 0 15px 0;
  }
}

.slider-demo-block {
  display: flex;
  align-items: center;
}

.slider-demo-block .el-slider {
  margin-top: 0;
  margin-left: 12px;
}
</style>