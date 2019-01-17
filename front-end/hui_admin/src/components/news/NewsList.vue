<template>
	<div class="content_box">
		<div class="titling">
			<span>财经资讯</span>
			<router-link to="/addnews">
				<button>新增资讯</button>
			</router-link>
		</div>
		<div class="list_section">
			<div class="headlist clearfix myback2">
				
				<template v-for="(item,index) in headlist">
					<span :key="index"
						:class="['pull-left', item.istime? 'time': '', item.sort?'pointer':'']" 
						@click="Sort(item.sort)">{{item.name}}
						<i v-if="item.sort" class="el-icon-d-caret"></i>
					</span>
				</template>
				
				<span class="pull-right">操作</span>
			</div>
			<ul class="bodylist">
				<template v-for="(item,index) in list">
					<li :key="index" class="clearfix">
						<div class="pull-left time">
							<router-link :to="'/addnews?id='+ item.id">
								<div class="img_box">
									<img :src="item.listImg" />
								</div>
							</router-link>
						</div>
						<div class="pull-left">
							<span>{{item.title}}</span>
						</div>
						<div class="pull-left">
							<span>{{item.type}}</span>
						</div>
						<div class="pull-left time">
							<span>{{item.createTime}}</span>
						</div>
						<div class="pull-left">
							<el-switch v-model="item.flager" active-color="#13ce66" inactive-color="#ff4949" @change="changeHomePush(item.flager,item.id)">
							</el-switch>
						</div>
						<div class="pull-right btn_box">
							<!--<button @click="editVideoType(item)">编辑视频集</button>
							<button class="delete" @click="deleteType(item.videoTypeName,item.id)">删除视频集</button>-->
							<el-button @click="sendNews(item.title,item.id)" type="info" icon="el-icon-bell" circle></el-button>
								<router-link :to="'/addnews?id='+ item.id">
							<el-button type="primary" icon="el-icon-edit" circle></el-button>
							</router-link>
              <el-button @click="Delete(item.title,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
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
	export default {
		data() {
			return {
				headlist: [
					{name: '封面',sort: null,istime: true},
					{name: '标题',sort: null,istime: false},
					{name: '栏目',sort: null,istime: false},
					{name: '时间',sort: 'createTime',istime: true},
					{name: '首页推荐',sort: null,istime: false}
				],
				page: {
					total: 0,
					num: 1,
					size: 6
        },
        filter: {
          orderByColumn: 'createTime',
					sort: 'DESC'
				},
				list: []
			}
		},
		components: {
			Pagination
		},
		methods: {
			Delete(title, id) {
				let _this = this;

				_this.$confirm("删除[" + title + "]，不可恢复。是否继续?", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_news/delete/" + id).then((res) => {
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
      sendNews(title, id) {
        let _this = this;

        _this.$confirm("发送[" + title + "]", '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.$fetch("/api/hpp_news/sendNews/" + id).then((res) => {
            if(res.statusCode == "0000") {
              _this.$message({
                message: '发送成功',
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
			changeHomePush(flag, id) {
				let _this = this;
				_this.$fetch("/api/hpp_news/showOnApp/" + id).then((res) => {
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
			currentChange(p) { //分页点击
				this.page.num = p;
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
					"page_number": _this.page.num, //页码
					"page_size": _this.page.size, // 每页条数
					"formParams": {
            "orderByColumn": _this.filter.orderByColumn,
            "sort": _this.filter.sort,
          }
				};
				_this.$post("/api/hpp_news/pageList", datas).then((res) => {
					let list = res.data.list;
					if(list) {
						list.forEach(function(item) {
							item.flager = (item.status == 'N' ? false : true);
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
		width: 220px;
	}
	
	.headlist span.time,
	.bodylist li>div.time {
		width: 260px;
	}
	
	.bodylist li {
		padding: 10px 0;
	}
	
	.img_box {
		width: 140px;
		margin: 0 auto;
	}
	
	.img_box img {
		width: 100%;
	}
	.el-button {
		margin-left: 10px;
	}
</style>
