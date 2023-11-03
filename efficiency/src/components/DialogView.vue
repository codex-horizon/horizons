<template>
  <el-dialog v-model="visible" :title="title" width="50%" :before-close="handleClose">
    <slot/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelClick">取 消</el-button>
        <el-button type="primary" @click="confirmClick">确 认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {h} from "@/utils";

export default {
  name: "DialogView",
  data() {
    return {
      visible: false
    }
  },
  props: {
    title: {
      type: String,
      default: "对话框",
      required: true
    }
  },
  components: {},
  methods: {
    handleOpen() {
      this.$message.info("通知信息")
      // this.visible = true;
    },
    handleClose(done) {
      this.$messageBox.confirm(h("p", {style: "color: #000000;"}, [
        h("span", {style: "color: #000000;"}, "确认"),
        h("strong", {style: "color: teal;"}, " 关闭 "),
        h("span", {style: "color: #000000;"}, "处理内容项吗？"),
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
    cancelClick() {
      this.visible = false;
    },
    confirmClick() {
      this.$messageBox.confirm(h("p", {style: "color: #000000;"}, [
        h("span", {style: "color: #000000;"}, "确认"),
        h("strong", {style: "color: teal;"}, " 保存 "),
        h("span", {style: "color: #000000;"}, "处理内容项吗？"),
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

</style>