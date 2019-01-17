<template>
	<div class="content_box">

		<!-- <save-advertising :show="saveShow" :titleText="titleText" :videoType="videoType" @close="closeInit"></save-advertising> -->

		<div class="titling">
			<span>首页导航</span>
		</div>

		<!--<filter-work @Inquire="Inquire" :parent="3"></filter-work>-->

		<div class="list_body clearfix">
			<template v-for="item in list">
					<div class="item_box clearfix pull-left">
						
						<div class="img_box pull-left">
							<img :src="item.img" />
						</div>
						<div class="text_box pull-left">
							<h3>{{item.name}}</h3>
							<p>累计点击量：{{item.num}}</p>
							<p>当天点击量：{{item.day}}</p>
						</div>
					</div>
			</template>
			
			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/nodata_2.png" />
				<p>暂无数据</p>
			</div>
		</div>

		<!--分页-->
		<!--<pagination @currentChange="currentChange" :page="page"></pagination>-->
	</div>
</template>

<script>
	import Pagination from "../common/Pagination"
	import FilterWork from "../common/FilterWork"
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
				saveShow: false,
				videoType: {
					img: '',
					text: '',
					type: '',
					url: ''
				},
				titleText: "新增广告"
			}
		},
		components: {
			Pagination,
			FilterWork
		},
		methods: {
			newVideoType() {
				this.titleText = "新增广告";
				this.videoType = {
					img: '',
					text: '',
					type: '',
					url: ''
				};
				this.saveShow = true;
			},
			editVideoType(item) {
				this.titleText = "编辑广告";
				this.videoType = item;
				this.saveShow = true;
			},
			deleteType(title, id) {
				let _this = this;

				_this.$confirm("删除[" + title + "]，不可恢复。是否继续?", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_advertisement/delete/" + id).then((res) => {
						console.log(res)
						if(res.statusCode == "0000") {
							_this.$message({
								message: '删除成功',
								type: 'success'
							});
							_this.initPage();
						} else {
							_this.$message({
								message: res.msgCode,
								type: 'warning'
							});
						}
					})
				}).catch(() => {});

			},
			currentChange(p) { //分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire(filter) {
				this.filter = filter;
				this.initPage();
			},
			closeInit() {
				this.saveShow = false;
				this.initPage();
			},
			initPage() {
				let _this = this;
				
				this.$fetch("/api/hpp_navigation/findAll").then((res) => {
//					console.log(res)
					if(res.data) {
						_this.list = res.data.list;
					}
				})
			}
		},
		mounted() {
			this.initPage();
		}
	}
</script>

<style scoped>
	.item_box {
		width: 33.33%;
		padding: 20px;
		border-bottom: 1px solid #999;
	}
	
	.img_box {
		width: 80px;
		height: 80px;
		overflow: hidden;
		margin-right: 20px;
	}
	
	.img_box img {
		width: 100%;
		height: 100%;
	}
	.text_box h3 {
		font-size: 18px;
		color: #666;
	}
	.text_box p {
		color: #999;
		font-size: 14px;
		line-height: 30px;
	}
	
</style>