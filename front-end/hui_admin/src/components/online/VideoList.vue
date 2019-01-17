<template>
	<div class="content_box">

		<upload-video :show="upShow" @close="closeInit" @uploadSuccess="uploadSuccess"></upload-video>

		<save-video :show="saveShow" :editVideoMess="editVideoMess" @close="closeInit"></save-video>

		<video-details :show="videoShow" :VideoMess="editVideoMess" @close="closeInit"></video-details>

		<div class="titling">
			<span>{{typeTitle}}</span>
			<button @click="upLoadVideo">上传视频</button>
		</div>

		<!-- <filter-work @Inquire="Inquire" :parent="3"></filter-work> -->


		<ul class="list_body clearfix">
			<li v-for="item in list" class="li_box pull-left">

				<div class="item_box">
					<div class="img_box">
						<img :src="item.img" />
					</div>
					<div class="cover_box">
						<div class="text_box pointer"  @click="watchVideo(item)">
							<h3>{{item.videoName}}</h3>
							<div>
								<p>{{item.videoText}}</p>
							</div>
							<div class="clearfix">
								<span class="pull-left">价格：</span>
								<span class="pull-right red">{{item.price}} 元</span>
							</div>
							<!-- <div class="clearfix">
								<span class="pull-left">兑换积分：</span>
								<span class="pull-right">{{item.integralPrice}}</span>
							</div> -->
							<div class="clearfix">
								<span class="pull-left">更新时间：</span>
								<span class="pull-right">{{item.updateAt}}</span>
							</div>
						</div>
						<div class="btn_box clearfix">
							<!-- <button @click="editVideo(item)">修改视频</button>
							<button class="delete" @click="deleteType(item.videoName,item.id)">删除视频</button> -->
							
							<el-button @click="editVideo(item)" type="primary" icon="el-icon-edit" circle></el-button>
							<el-button @click="deleteType(item.videoName,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
						</div>
					
					</div>
				</div>
			</li>

		</ul>
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
	import SaveVideo from '../dialog/SaveVideo'
	import UploadVideo from '../dialog/UploadVideo'
	import VideoDetails from '../dialog/VideoDetails'
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 12
				},
				filter: {
					orderByColumn: 'sortNum',
					sort: 'ASC'
				},
				saveShow: false,
				typeTitle: '视频列表',
				editVideoMess: {
					videoUrl: ''
				},
				videoShow: false,
				upShow: false
			}
		},
		computed: {
			typeid() {
				return this.$route.query.typeid;
			}
		},
		components: {
			SaveVideo,
			Pagination,
			FilterWork,
			VideoDetails,
			UploadVideo
		},
		methods: {
			uploadSuccess(obj) {
				let _this = this;
				this.editVideoMess = obj;
				this.saveShow = true;
			},
			upLoadVideo() {
				let _this = this;
				this.upShow = true;
			},
			watchVideo(item) {
				this.editVideoMess = item;
				this.videoShow = true;
			},
			editVideo(item) {
				this.editVideoMess = item;
				this.saveShow = true;
			},
			deleteType(title, id) {
				let _this = this;
				_this.$confirm("删除[" + title + "]，不可恢复。是否继续?", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_video/delete/" + id).then((res) => {
						if (res.statusCode == "0000") {
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
				this.upShow = false;
				this.saveShow = false;
				this.videoShow = false;
				this.initPage();
			},
			initPage() {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						typeId: this.typeid,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
					}
				}
				this.$post("/api/hpp_video/pageList", datas).then((res) => {
					//					console.log(res)
					_this.list = res.data.list;
					_this.page.total = parseInt(res.data.total);
					if (res.data.list.length) {
						_this.typeTitle = res.data.list[0].videoTypeName;
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
	.li_box {
		width: 16.6%;
		box-sizing: border-box;
		padding: 8px 5px;
	}
	.item_box {
		position: relative;
		border: 1px solid #999;
	}

	.img_box {
		width: 100%;
	}

	.img_box img {
		width: 100%;
	}
	.cover_box {
		position: absolute;
		width: 100%;
		height: 100%;
		left: 0;
		top: 0;
		background-color: rgba(0,0,0,.4);
		z-index: 1;
	}
	.text_box {
		box-sizing: border-box;
		/*height: 160px;*/
		position: absolute;
		bottom: 0;
		width: 100%;
		padding: 5px;
		z-index: 3;
	}
	.text_box>div {
		margin-bottom: 10px;
	}
	.text_box h3 {
		font-size: 16px;
		color: #fff;
		margin-bottom: 10px;
	}

	.text_box p {
		color: #fff;
		font-size: 14px;
	}
	.text_box span {
		color: #fff;
	}
	.text_box span.red {
		color: #FF7800;
		font-size: 18px;
	}
	
	.btn_box {
		display: none;
		position: absolute;
		right: 10px;
		bottom: 0;
		height: 50px;
		z-index: 99;
	}
	.cover_box:hover .btn_box{
		display: block;
	}
	.cover_box:hover .text_box{
		bottom: 50px;
		transition: .5s;
	}
	/* .btn_box button {
		margin-bottom: 10px;
		margin-left: 5px;
		background-color: #3269FF;
		border-radius: 20px;
		padding: 3px 10px;
		color: #fff;
		font-size: 12px;
	}

	.btn_box button.delete {
		background-color: #FF0000;
	} */
</style>
