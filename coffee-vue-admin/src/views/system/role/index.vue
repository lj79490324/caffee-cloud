<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleAdd">{{ $t('message.common.add') }}</el-button>
        <el-popconfirm :title="$t('message.common.delTip')" @confirm="handleDel(chooseData)">
          <template #reference>
            <el-button type="danger" icon="el-icon-delete" :disabled="chooseData.length === 0">{{ $t('message.common.delBat') }}</el-button>
          </template>
        </el-popconfirm>
      </div>
      <div class="layout-container-form-search">
        <el-input v-model="query.input" :placeholder="$t('message.common.searchTip')" @change="getTableData(true)"></el-input>
        <el-button type="primary" icon="el-icon-search" class="search-btn" @click="getTableData(true)">{{ $t('message.common.search') }}</el-button>
      </div>
    </div>
    <div class="layout-container-table">
      <el-table
          :data="tableData"
          style="width: 100%; margin-bottom: 20px"
          row-key="id"
          border
          default-expand-all
      >
<!--        <el-table-column type="selection" width="55" />-->
<!--        <el-table-column prop="id" label="id" sortable />-->
        <el-table-column prop="sysRoleName" label="角色名" align="center" sortable />
        <el-table-column prop="sysRoleCode" label="角色编码" align="center" sortable />
        <el-table-column prop="path" label="路径" align="center" sortable />
        <el-table-column prop="sysRoleDesc" label="角色描述" align="center" sortable />
        <el-table-column :label="$t('message.common.handle')" align="center" fixed="right">
          <template #default="scope">
            <el-button @click="">{{ $t('message.common.update') }}</el-button>
            <el-button type="primary" @click="handleAuth(scope.row)">{{ $t('message.common.auth') }}</el-button>
            <el-popconfirm :title="$t('message.common.delTip')" @confirm="">
              <template #reference>
                <el-button type="danger">{{ $t('message.common.del') }}</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <Layer :layer="layer" @getTableData="getTableData" v-if="layer.show" />
      <auth-layer :layer="layer2"  v-if="layer2.show" />
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, reactive } from 'vue'
import { del } from '@/api/table'
import {getAllRole} from '@/api/sysRole'
import {getTreeData} from "@/utils/system/common"
import Layer from './layer.vue'
import AuthLayer from "./authLayer.vue";
import { ElMessage } from 'element-plus'
export default defineComponent({
  name: 'sysRole',
  components: {
    Layer,
    AuthLayer
  },
  setup() {
    // 存储搜索用的数据
    const query = reactive({
      input: ''
    })
    // 弹窗控制器
    const layer = reactive({
      show: false,
      title: '新增',
      showButton: true
    })
    const layer2 = reactive({
      show: false,
      title: '授权',
      showButton: true
    })
    // 分页参数, 供table使用
    const page = reactive({
      index: 1,
      size: 20,
      total: 0
    })
    const loading = ref(true)
    const tableData = ref([])
    const chooseData = ref([])
    const handleSelectionChange = (val) => {
      chooseData.value = val
    }
    // 获取表格数据
    // params <init> Boolean ，默认为false，用于判断是否需要初始化分页
    const getTableData = (init) => {
      loading.value = true
      if (init) {
        page.index = 1
      }
      let params = {
        page: page.index,
        pageSize: page.size,
        ...query
      }
      getAllRole()
      .then(res =>{
        console.log(res)
        // let data = res.data.list
        tableData.value = getTreeData(res.data)
        console.log(getTreeData(res.data))
      }).catch(error => {
        tableData.value = []
        page.index = 1
        page.total = 0
      }).finally(() => {
            loading.value = false
          })
    }

    const handleAuth = (data)=>{
      console.log(data)
      layer2.title = data.sysRoleName+'的授权操作'
      layer2.show = true
    }
    // 删除功能
    const handleDel = (data) => {
      let params = {
        ids: data.map((e)=> {
          return e.id
        }).join(',')
      }
      del(params)
      .then(res => {
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        getTableData(tableData.value.length === 1 ? true : false)
      })
    }
    // 新增弹窗功能
    const handleAdd = () => {
      layer.title = '新增数据'
      layer.show = true
      delete layer.row
    }
    // 编辑弹窗功能
    const handleEdit = (row) => {
      layer.title = '编辑数据'
      layer.row = row
      layer.show = true
    }
    getTableData(true)
    return {
      query,
      tableData,
      chooseData,
      loading,
      page,
      layer,
      layer2,
      handleSelectionChange,
      handleAdd,
      handleEdit,
      handleAuth,
      handleDel,
      getTableData
    }
  }
})
</script>

<style lang="scss" scoped>

</style>
