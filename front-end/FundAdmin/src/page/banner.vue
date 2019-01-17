<template>
	<div class="fillcontain">
		<head-top></head-top>
    <filter-box @change="handleChange"></filter-box>
		<div class="table_container">
			<el-carousel type="card" height="400px" :autoplay="false">
        <el-carousel-item v-for="(item,index) in list" :key="index">
          <div class="ban_box">
            <div class="delete_box" @click="handleDelete(item.id,index)">
              <i class="iconfont icon-shanchu"></i>
            </div>
            <div class="img_box">
              <img :src="item.img" alt="">
            </div>
            <div class="text_box">
              <p>备注：{{item.text}}</p>
              <p>序号：{{item.sortNum}}</p>
            </div>
          </div>
          
        </el-carousel-item>
      </el-carousel>
      
      <div class="Pagination" style="text-align: left;margin-top: 10px;">
      	<el-pagination :current-page="page.currentPage" :page-size="page.size" layout="total, prev, pager, next"
      	:total="page.count">
      	</el-pagination>
      </div>
		</div>
	</div>
</template>

<script>
  import filterBox from '../components/filterBox'
	import headTop from '../components/headTop'
	import {
		baseUrl,
		baseImgPath
	} from '@/config/env'
  import {
  	whatType,
    whatStatus
  } from '@/config/mUtils'
	import {
		getBanners,
    deleteBanner
	} from '@/api/getData'
	export default {
		data() {
			return {
				list: [],
				selectTable: {},
				page: {
					size: 200,
					count: 0,
					currentPage: 1,
				},

				baseUrl,
				baseImgPath,

				dialogFormVisible: false,
				
        type: '1'
			}
		},
		created() {
			this.initData();
		},
		components: {
			headTop,
      filterBox
		},
		methods: {
      
			async initData() {
				try {
          this.list = [];
					const res = await getBanners(this.type);
					console.log(res)
					if (res.statusCode == '0000') {
            let list = res.data.list;
            list.sort(function(a,b){
            	return a.sortNum - b.sortNum;
            })
            this.list = list;
            this.page.count = res.data.total;
					} else {
						throw new Error('获取数据失败');
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
      handleChange(obj) {
        console.log(obj)
        this.type = obj.type;
        this.initData();
      },
			async handleSelect(id, status) {
				try {
					const res = await handleSelect({
						id: id, //	是	string	处理id
						status: status
					});
					console.log(res)
					if (res.statusCode == '0000') {
						this.$message({
							type: 'success',
							message: '操作成功'
						});
						this.selectTable.status = 'PASSED';
						this.dialogFormVisible = false;
					} else {
						this.$message({
							type: 'error',
							message: res.msgCode
						});
					}
				} catch (e) {
					console.log('操作失败', e);
				}
			},
			
			
			handleCurrentChange(val) {
				this.page.currentPage = val;
				this.initData()
			},
			handleEdit(index, row) {
				this.selectTable = row;
				this.dialogFormVisible = true;
			},
			
			async handleDelete(id,index) {
				try {
					const res = await deleteBanner(id);
					if (res.statusCode == '0000') {
						this.$message({
							type: 'success',
							message: '删除成功'
						});
						this.list.splice(index, 1);
					} else {
						this.$message({
							type: 'error',
							message: res.msgCode
						});
					}
				} catch (err) {
					console.log('操作失败', e);
				}
			},
			
			
		},
	}
</script>

<style lang="less">
	@import '../style/mixin';

	.table_container {
		padding: 20px;
	}
  
  .ban_box {
    background-color: #f1f1f1;
    width: 100%;
    height: 100%;
    overflow: hidden;
    position: relative;
  }
  .img_box {
    width: 100%;
  }
  .img_box img {
    width: 100%;
    max-height: 100%;
  }
  .text_box {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(1,1,1,.4);
  }
  .text_box p {
    color: #fff;
    font-size: 20px;
    padding: 4px 10px;
  }
  .delete_box {
    position: absolute;
    right: 5px;
    top: 5px;
    width: 40px;
    height: 40px;
    border-radius: 40px;
    line-height: 40px;
    background-color: #FF0000;
    text-align: center;
    z-index: 9;
    box-shadow: 2px 2px 3px 1px #999;
  }
  .delete_box i {
    font-size: 20px;
    color: #fff;
  }
  
</style>
