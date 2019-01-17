<template>
	<div class="content_box">
		
		<div class="titling">
			<span>交易师考证</span>
			<router-link to="/saveexam">
				<button>新增考证</button>
			</router-link>
		</div>
		
		<!--<filter-work @Inquire="Inquire" :parent="3"></filter-work>-->
		
		
		<div class="list_body">
			<div v-for="item in list" class="item_box clearfix">
				<div class="img_box pull-left">
					<img :src="item.img"/>
				</div>
				<div class="text_box pull-left">
					<h3>{{item.trainName}}</h3>
					<p>
						<span>考试地点：</span>
						{{item.address}}
					</p>
					<p>
						<span>考试时间：</span>
						{{item.trainTime}}
					</p>
				</div>
				
				<div class="text_box text_box2 pull-left">
					<p>
						<span>考试简介：</span>
						{{item.introduction}}
					</p>
					
				</div>
				<div class="set_box pull-right">
					<router-link :to="'/saveexam?id='+item.id">
						<el-button type="primary" icon="el-icon-edit" circle></el-button>
					</router-link>
					<!--<el-button @click="deleteType(item.trainName,item.id)" type="danger" icon="el-icon-delete" circle></el-button>-->
				</div>
			</div>
			
			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/nodata_2.png" />
				<p>暂无数据</p>
			</div>
		</div>
		
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 6
				},
				filter: {},
				saveShow: false
			}
		},
		components: {
			Pagination,
			FilterWork
		},
		methods: {
			deleteType (title,id) {
				let _this = this;
				
				_this.$confirm("删除["+title+"]视频集合，不可恢复。是否继续?", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_train/delete/"+id).then((res) => {
//						console.log(res)
						if (res.statusCode == "0000") {
							_this.$message({
						          message: '删除成功',
						          type: 'success'
						    });
						    _this.initPage();
						}else {
							_this.$message({
						          message: res.msgCode,
						          type: 'warning'
						    });
						}
					})
				}).catch(() => {});
				
				
			},
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire (filter) {
				this.filter = filter;
				this.initPage();
			},
			closeInit () {
				this.saveShow = false;
				this.initPage();
			},
			initPage () {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						userId: '',
						videoTypeName: ''
					}
				}
				this.$post("/api/hpp_exam/pageList",datas).then((res) => {
					if (res.data) {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total);
					}
				})
			}
		},
		mounted () {
			this.initPage();
		}
	}
</script>

<style scoped>
	.item_box {
		padding: 20px;
		border-bottom: 1px solid #999;
	}
	.img_box {
		width: 180px;
		height: 100px;
		overflow: hidden;
	}
	.img_box img {
		width: 100%;
	}
	.text_box {
		width: 250px;
		margin-left: 20px;
	}
	.text_box2 {
		width: 600px;
	}
	.text_box h3 {
		color: #111111;
		font-size: 18px;
		margin-bottom: 10px;
	}
	.text_box p span {
		color: #666;
	}
	.text_box p {
		color: #999;
		font-size: 14px;
	}
	.set_box {
		line-height: 100px;
		padding-right: 50px;
	}
	
</style>