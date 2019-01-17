<template>
	<div class="content_box">
		
		<save-exchanger :show="saveShow" :exid="exId" @close="closeInit"></save-exchanger>
		
		<div class="titling">
			<span>协议管理</span>
			<router-link to="/saveprotocol">
				<button>新增协议</button>
			</router-link>
		</div>
		
		<!--<filter-work @Inquire="Inquire" :parent="3"></filter-work>-->
		
		
		<div class="list_section">
			<div class="headlist clearfix">
					<span class="pull-left">协议类型</span>
					<span class="pull-left">协议标识</span>
					<span class="pull-left">创建时间</span>
					<span class="pull-left">更新时间</span>
					<span class="pull-right">操作</span>
			</div>
            <ul class="bodylist">
                <li v-for="item in list" class="item_box clearfix">
                        <span class="pull-left">{{item.typeDesc}}</span>
                        <span class="pull-left">{{item.type}}</span>
                        <span class="pull-left">{{item.createTime}}</span>
                        <span class="pull-left">{{item.updateTime}}</span>
                    <div class="pull-right">
                        <el-button @click="Edit(item.id)" type="primary" icon="el-icon-edit" circle></el-button>
                        <el-button @click="Delete(item.type,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
                        
                    </div>
                </li>
            </ul>
			
			
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
	import SaveExchanger from '@/components/dialog/SaveExchanger'
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 10
				},
				filter: {},
				saveShow: false,
				exId: null
			}
		},
		components: {
			SaveExchanger,
		},
		methods: {
			Edit (id) {
				this.$router.push({
					path: '/addProtocol?id='+id
				})
			},
			AddEx() {
				this.exId = null;
				this.saveShow = true;
			},
			Delete (name,id) {
				let _this = this;
				
				_this.$confirm('删除['+name+']协议, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.fetch("/api/protocol/delete/"+id,function (res) {
//						console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '操作成功!'
							});
							_this.initPage();
						}else {
							_this.$message({
								type: 'error',
								message: res.msgCode
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
				this.fetch("/api/protocol/findList",function (res) {
//					console.log(res)
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
	.headlist span,
	.bodylist li span {
		width: 14%;
	}
	
	.headlist span.time,
	.bodylist li span.time {
		width: 11%;
	}
	
	.headlist span:last-of-type,
	.bodylist li>div {
		width: 14%;
	}
</style>