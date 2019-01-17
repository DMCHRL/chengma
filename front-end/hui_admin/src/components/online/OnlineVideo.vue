<template>
	<div class="content_box">

		<save-video-type :show="saveShow" :titleText="titleText" :videoType="videoType" @close="closeInit"></save-video-type>

		<div class="titling">
			<span>线上视频</span>
			<button @click="newVideoType">新增视频集</button>
		</div>

		<!--<filter-work @Inquire="Inquire" :parent="3"></filter-work>-->

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
				<template v-for="item in list">
					<li class="item_box clearfix">
							<div class="text_box pull-left ">
								<span>{{item.sortNum}}</span>
							</div>
							<div class="text_box pull-left">
								<div class="img_box">
									<img :src="item.img" />
								</div>
							</div>
							<div class="text_box pull-left time">
								<span>{{item.videoTypeName}}</span>
							</div>
							<div class="text_box pull-left time">
								<span v-html="item.text"></span>
							</div>
							<div class="text_box pull-left time">
								<span>{{item.createAt}}</span>
							</div>
							<div class="text_box pull-left ">
								<el-switch
								  v-model="item.flager"
								  active-color="#13ce66"
								  inactive-color="#ff4949"
								  @change="changeDayPush(item.flager,item.id)">
								</el-switch>
							</div>
						<div class="set_box pull-right time">
							<router-link :to="'/videolist?typeid='+ item.id">
								<el-button type="success" icon="el-icon-view" circle></el-button>
							</router-link>
							<el-button @click="editVideoType(item)" type="primary" icon="el-icon-edit" circle></el-button>
							<el-button @click="deleteType(item.videoTypeName,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
						</div>
					</li>
				</template>
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
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import SaveVideoType from '../dialog/SaveVideoType'
	export default {
		data() {
			return {
				headlist: [
					{name: '排序',sort: 'sortNum',istime: false},
					{name: '封面',sort: null,istime: false},
					{name: '标题',sort: null,istime: true},
					{name: '视频介绍',sort: null,istime: true},
					{name: '时间',sort: 'createAt',istime: true},
					{name: '每日推荐',sort: null,istime: false}
				],
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 5
				},
				filter: {
          orderByColumn: 'sortNum',
          sort: 'ASC'
        },
				saveShow: false,
				videoType: {
					img: '',
					text: '',
					videoTypeName: ''
				},
				titleText: "新增视频集",
				show: true,
				
			}
		},
		components: {
			SaveVideoType,
			Pagination,
			FilterWork
		},
		methods: {
			changeDayPush (flag,id) {
				//设置每日推荐
				// console.log(flag,id)
				let _this = this;
				let _url = "";
				if (flag) {
					_url = "/api/hpp_video_type/editDayPush/"
				}else {
					_url = "/api/hpp_video_type/cancelDayPush/"
				}
				_this.$fetch(_url+id).then((res) => {
					if(res.statusCode == "0000") {
						_this.$message({
							message: '操作成功',
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
				
			},
			newVideoType() {
				this.titleText = "新增视频集";
				this.videoType = {
					img: '',
					text: '',
					videoTypeName: ''
				};
				this.saveShow = true;
			},
			editVideoType(item) {
				this.titleText = "编辑视频集";
				this.videoType = item;
				this.saveShow = true;
			},
			deleteType(title, id) {
				let _this = this;

				_this.$confirm("删除[" + title + "]视频集合，不可恢复。是否继续?", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_video_type/delete/" + id).then((res) => {
//						console.log(res)
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
      Sort (data) {//列表排序
        if (!data) return;
        this.filter.orderByColumn = data;
        this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
        this.initPage();
      },
			initPage() {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						userId: '',
						videoTypeName: '',
            "orderByColumn": _this.filter.orderByColumn,
            "sort": _this.filter.sort,
					}
				};
				this.$post("/api/hpp_video_type/pageList", datas).then((res) => {
					let list = res.data.list;
					if (list) {
						list.forEach(function (item) {
							item.flager = (item.dayPush == 'NO' ? false:true);
						})
					}
					_this.list = res.data.list;
					_this.page.total = parseInt(res.data.total);
					
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
		width: 160px;
	}
	.headlist span.time,
	.bodylist li>div.time {
		width: 240px;
	}
	
	.item_box {
		padding: 20px 0;
		border-bottom: 1px solid #999;
	}
	
	.img_box {
		width: 80px;
		margin: 0 auto;
	}
	.img_box img {
		width: 100%;
	}
	
	.text_box {
		box-sizing: border-box;
		width: 600px;
		min-height: 1px;
		padding: 0 5px;
	}
	.el-button {
		margin-left: 10px;
	}
</style>
