<template>
	<div class="content_box">

		<save-live :show="saveShow" :titleText="titleText" :videoType="videoType" @close="closeInit"></save-live>

		<div class="titling">
			<span>直播管理</span>
			<button @click="newVideoType">新增直播</button>
		</div>

		<!-- <filter-work @Inquire="Inquire" :parent="'live'"></filter-work> -->

		<div class="list_section">
			<div class="headlist clearfix myback2">
				
				<template v-for="(item,index) in headlist">
					<span :key="index"
						:class="['pull-left', item.istime? 'time': '', item.sort?'pointer':'']" 
						@click="Sort(item.sort)">{{item.name}}
						<i v-if="item.sort" class="el-icon-d-caret"></i>
					</span>
				</template>
				
				<span class="pull-right time">操作</span>
			
			</div>
			
			<ul class="bodylist">
			<template v-for="(item,index) in list">
				<li class="item_box clearfix" :key="index">
					<div class="text_box pull-left">
						<p>{{item.sortNum}}</p>
					</div>
					<div class="pull-left time">
						<div class="img_box">
							<img :src="item.img" />
						</div>
					</div>
					<div class="text_box pull-left">
						<p>{{item.name}}</p>
					</div>
					<div class="text_box pull-left">
						<p>{{item.text}}</p>
					</div>
					<div class="text_box pull-left time">
						<p>{{item.url}}</p>
					</div>
					<div class="text_box pull-left time">
						<p>{{item.createTime}}</p>
					</div>
					<div class="set_box pull-right time">
						<el-button @click="editVideoType(item)" type="primary" icon="el-icon-edit" circle></el-button>
						<el-button @click="deleteType(item.text,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
					</div>
				</li>
			</template>
			</ul>
			
		</div>
		<div class="trip_box" v-show="!list.length">
			<img src="../../assets/img/nodata_2.png" />
			<p>暂无数据</p>
		</div>
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import SaveLive from './SaveLive'
	export default {
		data() {
			return {
				headlist: [
					{name: '序列',sort: 'sortNum',istime: false},
					{name: '图片',sort: null,istime: true},
					{name: '名字',sort: null,istime: false},
					{name: '介绍',sort: null,istime: false},
					{name: '直播地址',sort: null,istime: true},
					{name: '新增时间',sort: 'createTime',istime: true}
				],
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 6
				},
				filter: {
					banner: 'HOME',
          orderByColumn: 'sortNum',
          sort: 'ASC'
				},
				saveShow: false,
				videoType: {
					img: '',
					text: '',
					type: '',
					url: ''
				},
				titleText: "新增直播"
			}
		},
		filters: {
			typeInCN (val) {
				switch(val)
				{
					case 'HOME':
							return '首页'
							break;
					case 'VIDEO':
							return '交易战法视频'
							break;
					case 'SINKS':
							return '汇商超市'
							break;
					case 'STRATEGY':
							return '策略入驻'
							break;
					case 'LIVE':
							return '视频直播'
							break;
					default:
							return '其他'
				}
			}
		},
		components: {
			Pagination,
			FilterWork,
			SaveLive,
		},
		methods: {
			newVideoType() {
				this.titleText = "新增直播";
				this.videoType = {
					img: '',
					text: '',
					type: '',
					url: ''
				};
				this.saveShow = true;
			},
			editVideoType(item) {
				this.titleText = "编辑直播";
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
					_this.$fetch("/api/hpp_live/delete/" + id).then((res) => {
						// console.log(res)
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
			Sort (param) {//列表排序
				if (!param) return;
					this.filter.orderByColumn = param;
					this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
					this.initPage();
			},
			initPage() {
				let _this = this;
				let datas = {
					page_number: _this.page.num,
					page_size: _this.page.size,
					formParams: {
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
					}
				};
				this.$post("/api/hpp_live/pageList", datas).then((res) => {
					if(res.data.list) {
						_this.list = res.data.list;
						_this.page.total = res.data.total;
// 						let list = res.data.list;
// 						_this.list = list.filter((item) => {
// 							return item.type === _this.filter.banner;
// 						});
// 						_this.page.total = _this.list.length;
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
	.headlist span,
	.bodylist li>div {
		width: 170px;
	}
	
	.headlist span.time,
	.bodylist li>div.time {
		width: 240px;
	}
	.img_box img {
		width: 140px;
	}
	.text_box p {
		max-height: 90px;
		overflow: hidden;
		text-overflow: ellipsis;
		-ms-word-wrap: break-word;
		word-wrap:break-word
	}
</style>
