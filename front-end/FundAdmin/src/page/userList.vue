<template>
	<div class="fillcontain">
		<head-top></head-top>
		<div class="table_container">
			<el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="创建时间">
                <span>{{ props.row.createAt }}</span>
              </el-form-item>
              <el-form-item label="更新时间">
                <span>{{ props.row.updateAt }}</span>
              </el-form-item>
              <el-form-item label="身份证明">
                <div class="img_boxs">
                  <img :src="props.row.idNegative" alt="" @click="lookBig(props.row.idNegative)">
                  <img :src="props.row.idPositive" alt="" @click="lookBig(props.row.idPositive)">
                </div>
              </el-form-item>
              <el-form-item label="头像">
              	<div class="img_tou">
              		<img :src="props.row.headImg" alt="">
              	</div>
              </el-form-item>

            </el-form>
          </template>
        </el-table-column>
				<el-table-column prop="login" label="登录名">
				</el-table-column>
				<el-table-column prop="firstName" label="名称">
				</el-table-column>
				<el-table-column prop="mobile" label="手机号" >
				</el-table-column>
				<el-table-column prop="email" label="邮箱" >
				</el-table-column>
        <el-table-column prop="Cdepartment" label="身份">
        </el-table-column>
        
        <el-table-column label="操作" width="200">
        	<template slot-scope="scope">
        		<!-- <el-button
        			size="mini"
        			@click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
        		
        		<el-button
        			size="mini"
        			type="danger"
        			@click="handleDelete(scope.$index, scope.row)">删除</el-button>
        	</template>
        </el-table-column>
			</el-table>
			<div class="Pagination" style="text-align: left;margin-top: 10px;">
				<el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage"
				 :page-size="page.size" layout="total, prev, pager, next" :total="page.count">
				</el-pagination>
			</div>
      
      <el-dialog v-model="dialogFormVisible">
      	<div class="dialog_box">
      		<img :src="bigImg" alt="">
      	</div>
      	<div slot="footer" class="dialog-footer">
      	</div>
      </el-dialog>
      
		</div>
	</div>
</template>

<script>
	import headTop from '../components/headTop'
	import {
		userList,
    deleteUser
	} from '@/api/getData'
  import {whatDepart} from '@/config/mUtils'
	export default {
		data() {
			return {
				tableData: [],
				currentRow: null,
				page: {
					size: 20,
					count: 0,
					currentPage: 1,
				},
        dialogFormVisible: false,
        bigImg: ''
			}
		},
		components: {
			headTop,
		},
		created() {
			this.initData();
		},
		methods: {
      lookBig (url) {
        this.bigImg = url;
        this.dialogFormVisible = true;
      },
			async initData() {
				try {
          const res = await userList({
            page_number: this.page.currentPage, //	是	string	页码
            page_size: this.page.size, //	是	string	每页大小
            formParams: {}
          });

          console.log(res)
          
          if (res.statusCode == '0000') {
            let list = res.data.list;
            list.forEach(item => {
              item.Cdepartment = whatDepart(item.department)
            })
            
            this.tableData = list;
            this.page.count = res.data.total_count
          } else {
            throw new Error(res.message)
          }
        } catch (err) {
          console.log('获取数据失败', err);
        }
			},
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
			},
			handleCurrentChange(val) {
				this.page.currentPage = val;
				this.initData()
			},
			
      async handleDelete(index, row) {
      		try{
      				const res = await deleteUser(row.id);
      				if (res.statusCode == '0000') {
      						this.$message({
      								type: 'success',
      								message: '删除成功'
      						});
      						this.tableData.splice(index, 1);
      				}else{
      						this.$message({
                    type: 'error',
                    message: res.message
                  });
      				}
      		}catch(err){
      				console.log('删除失败',err)
      		}
      },
		},
	}
</script>

<style lang="less">
	@import '../style/mixin';
  .demo-table-expand {
		font-size: 0;
	}

	.demo-table-expand label {
		width: 90px;
		color: #99a9bf;
	}

	.demo-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 50%;
	}

	.table_container {
		padding: 20px;
	}
	.table_container {
		padding: 20px;
	}
  .img_boxs img{
    width: 150px;
    margin-right: 10px;
  }
  .img_tou img {
    width: 50px;
    height: 50px;
    border-radius: 5px;
  }
  .dialog_box img{
    max-width: 100%;
  }
</style>
