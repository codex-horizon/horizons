<template>
  <DialogView ref="dialog" title="快捷通知">
    <el-scrollbar height="300px">
      <el-table :data="tableData">
        <el-table-column fixed prop="date" label="日期" width="150"/>
        <el-table-column prop="name" label="姓名" width="120"/>
        <el-table-column prop="state" label="状态" width="120"/>
        <el-table-column prop="city" label="地区" width="120"/>
        <el-table-column prop="address" label="地址" width="600"/>
        <el-table-column prop="zip" label="Zip" width="120"/>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default>
            <el-button link type="primary" size="small" icon="View"/>
            <el-button link type="primary" size="small" icon="Edit"/>
            <el-button link type="warning" size="small" icon="Delete"/>
          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </DialogView>
</template>

<script>
import DialogView from "@/components/DialogView.vue";
import {h} from "@/utils";

export default {
  name: "ShortcutSettingsView",
  data() {
    return {
      tableData: [
        {
          date: '2016-05-03',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-02',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-04',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-01',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-03',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-02',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-04',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-01',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-03',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-02',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-04',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-01',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-03',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-02',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
        {
          date: '2016-05-04',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
        {
          date: '2016-05-01',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Office',
        },
      ]
    }
  },
  props: {},
  components: {
    DialogView
  },
  methods: {
    handleOpen() {
      this.$refs.dialog.handleOpen()
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
</style>