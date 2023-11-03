<template>
  <el-scrollbar>
    <el-form :inline="true" :model="dynamicForm" label-width="80px">
      <el-form-item prop="email" label="邮箱" :rules="[{ required: true, message: '请输入邮箱地址', trigger: 'blur'}]">
        <el-input v-model="dynamicForm.email" placeholder="请输入邮箱地址"/>
      </el-form-item>
      <el-form-item :label="dynamicOtherForm[index]" :prop="item" :rules="{required: true, message: '请输入...', trigger: 'blur'}" v-for="(item, index) in dynamicForm.formOBJ" :key="index">
        <el-input v-model="dynamicForm.formOBJ" :placeholder="dynamicOtherForm[index]" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">Query</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData">
      <el-table-column :prop="item.prop" :label="item.label" :width="item.width ? item.width : ''" v-for="(item, index) in tableColumn" :key="index"/>
    </el-table>
  </el-scrollbar>
</template>

<script>
export default {
  name: "TableView",
  data() {
    return {
      dynamicForm: {
        email: '',
        formOBJ: []
      },
      dynamicOtherForm: []
    }
  },
  computed: {
    monitorFormColumn: function () {
      return this.formColumn;
    }
  },
  watch: {
    monitorFormColumn: function (newVal) {
      let obj = {};
      let arr = [];
      for (let item of newVal) {
        obj[item.formItemName] = '';
        arr.push({
          'formLabelName': item.formLabelName,
          'formItemProp': item.formLabelName,
          'formItemPlaceholder': item.formItemPlaceholder
        })
      }
      this.onAddForm(obj, arr);
    }
  },
  props: {
    formColumn: {
      required: true,
      type: Array,
      default() {
        return [];
      }
    },
    tableData: {
      required: true,
      type: Array,
      default() {
        return [];
      }
    },
    tableColumn: {
      required: true,
      type: Array,
      default() {
        return [];
      }
    }
  },
  components: {},
  methods: {
    onQuery() {
      this.$message.info("搜索");
      console.info(this.dynamicForm);
    },
    onrRemoveForm(item) {
      let index = this.dynamicForm.indexOf(item)
      if (index !== -1) {
        this.dynamicForm.splice(index, 1)
      }
    },
    onAddForm(obj, arr) {
      this.dynamicForm['formOBJ'] = obj;
      this.dynamicOtherForm = arr;
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

</style>