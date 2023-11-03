<template>
  <div class="module-container">
    <TableView v-show="false" :formColumn="formColumn" :tableData="tableData" :tableColumn="tableColumn"/>
  </div>
</template>

<script>
import TableView from '@/components/TableView.vue';

export default {
  name: "HomeView",
  data() {
    return {
      formColumn: [],
      tableData: [],
      tableColumn: [
        {
          prop: 'data',
          label: 'Date'
        }, {
          prop: 'name',
          label: 'Name'
        }, {
          prop: 'address',
          label: 'Address'
        }
      ]
    }
  },
  props: {},
  components: {
    TableView
  },
  methods: {
    init() {
      this.formColumn = [{
        formLabelName: '用户名',
        formItemName: 'username',
        formItemPlaceholder: '请输入用户名'
      }, {
        formLabelName: '手机号',
        formItemName: 'phoneNumber',
        formItemPlaceholder: '请输入手机号'
      }];
      for (let i = 0; i <= 100; i++) {
        this.tableData.push({
          data: 1,
          name: '2',
          address: '1'
        });
      }
      this.tableColumn = [
        {
          prop: 'data',
          label: 'Date'
        }, {
          prop: 'name',
          label: 'Name'
        }, {
          prop: 'address',
          label: 'Address'
        }
      ];
    },
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
    this.init();
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
.module-container {
  padding: 12px;
}
</style>