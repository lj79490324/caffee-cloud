<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="">{{ $t('message.common.add') }}</el-button>
        <el-popconfirm :title="$t('message.common.delTip')" @confirm="">
          <template #reference>
            <el-button type="danger" icon="el-icon-delete">{{ $t('message.common.delBat') }}</el-button>
          </template>
        </el-popconfirm>
      </div>
      <div class="layout-container-form-search">
        <el-input v-model="query.input" :placeholder="$t('message.common.searchTip')" @change=""></el-input>
        <el-button type="primary" icon="el-icon-search" class="search-btn" @click="">{{ $t('message.common.search') }}</el-button>
      </div>
    </div>
    <div class="layout-container-table">
      <div class="system-table-box">
        <el-table
            :data="tableData"
            v-loading="loading"
            class="system-table"
            height="100%"
            row-key="id"
            border
            default-expand-all
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="sysMenuName" label="菜单名" min-width="120px" />
          <el-table-column prop="sysMenuCode" label="菜单编码" />
          <el-table-column prop="sysMenuUrl" label="菜单地址" />
          <el-table-column prop="sysMenuComponent" label="菜单组件" />
          <el-table-column prop="sysMenuType" label="类型" >
            <template #default="scope">
              {{getTypeDictValue(scope.row.sysMenuType)}}
            </template>
          </el-table-column>
          <el-table-column prop="sysMenuIcon" label="图标" >
            <template #default="scope">
              <svg-icon :name="scope.row.sysMenuIcon" v-if="scope.row.sysMenuIcon"></svg-icon>
            </template>
          </el-table-column>
          <el-table-column prop="authCode" label="权限码" />
          <el-table-column prop="sysMenuDesc" label="菜单描述" />
          <el-table-column prop="path" label="系统路径" />
          <el-table-column prop="sysMenuOrder" label="排序" />
          <el-table-column prop="status" label="状态" >
            <template #default="scope">
              {{getDictValue(scope.row.status)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" >
            <template #default="scope">
              <el-button link type="primary" size="small">详情</el-button>
              <el-button link type="primary" size="small">编辑</el-button>
            </template>
          </el-table-column>

        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, reactive } from 'vue'
import SvgIcon from '@/components/SvgIcon.vue'
import { getUserMenu } from '@/api/sysMenu'
import {getTreeData} from "@/utils/system/common"
export default defineComponent ({
  name: "sysMenu",
  components:{
    SvgIcon
  },
  setup(){
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
    const loading = ref(true)
    const tableData = ref([])
    const getData = () => {
        loading.value = true
        getUserMenu().then((res)=>{
          if (res.code ===0){
            tableData.value = getTreeData(res.data)
            // console.log(tableData)
          }
          loading.value = false
        })
    }
    const getDictValue = (dictKey) => {
        if (dictKey === 1){
          return '正常'
        }else {
          return "禁用"
        }
    }
    const getTypeDictValue = (dictKey) => {
      let name = ""
     switch (dictKey) {
      case 0:
        name = "页面"
        break
      case 1:
        name = "文件夹"
        break
      case 2:
        name = "接口，按钮"
        break
      case 3:
        name = "其他"
        break
      default:
        name = "未知类型"
     }
     return name
    }
    getData()
    return {
      tableData,
      getDictValue,
      getTypeDictValue,
      query,
      loading
    }
  }
})
</script>

<style scoped>

</style>
