<template>
	<div class="content_box">
		<div class="titling">
			<span>APP注册用户管理</span>
			<span class="dao pull-right" @click="outExe">导出excel</span>
		</div>
		
		<filter-work @Inquire="Inquire" :parent="'appuser'"></filter-work>
		
		<!-- 显示推荐人数 -->
		<recom-list :show="followShow" :url="'/api/hpp_mobile_user/findByRecommendation'" :id="code" @close="dialogClose"></recom-list>
		
		<!--积分赠送弹窗-->
		<el-dialog :visible.sync="showBonus" width="25%">
			<bonus-gift @close="dialogClose" :mess="mess"></bonus-gift>
		</el-dialog>
		
		
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
			<li class="item_box clearfix" v-for="(item,index) in list">
				<!--<div class="pull-left">
					<el-checkbox v-model="item.ischeck" @change="handleCheckedChange(item)"></el-checkbox>
				</div>-->
				<div class="pull-left">
					<img :src="item.headImg"/>
				</div>
				<div class="pull-left">
					<span>{{item.userName}}</span>
				</div>
				<div class="pull-left time">
					<span>{{item.phone}}</span>
				</div>
				<div class="pull-left time">
					<span>{{item.createAt}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.recommendation}}</span>
				</div>
				<div class="pull-left">
					<span class="pointer"  @click="showFollow(item.recommendation)">{{item.recommendationTotal}}</span>
				</div>
        <div class="pull-left">
          <span>{{item.shareTotal}}</span>
        </div>
				<div class="pull-left">
					<span>{{item.integral}}</span>
				</div>
				<div class="pull-left">
					<span v-text="item.followFlag=='Y'?'是':'否'"></span>
				</div>
				<div class="pull-left">
					<span v-text="item.buyFlag=='Y'?'是':'否'"></span>
				</div>
				<div class="pull-left">
					<span v-text="item.openFlag=='Y'?'是':'否'"></span>
				</div>
        <div class="pull-right time">
          <el-popover placement="bottom" width="100" trigger="click" popper-class="popover_box">
           <!-- <button
              v-if="user.department == 'admin' || user.department == 'service'"
              @click="Edit(item)">
              编辑用户
            </button>-->
            <button @click="addIntegral(item)">
              积分赠送
            </button>
            <el-button type="primary" icon="el-icon-setting" slot="reference"></el-button>
          </el-popover>
        </div>


			</li>
		</ul>
		</div>
		<!--<user-list :list="list" @Sort="Sort" @Edit="Edit" @CardSet="CardSet" @addAccount="addAccount" @close="dialogClose"></user-list>-->
		
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import UserList from "../list/UserList.vue"
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import RecomList from '../list/RecomList'
	import BonusGift from '../dialog/BonusGift'
	export default {
		data() {
			return {
				headlist: [
					{name: '头像',sort: null,istime: false},
					{name: '昵称',sort: null,istime: false},
					{name: '手机号',sort: null,istime: true},
					{name: '注册时间',sort: 'createAt',istime: true},
					{name: '推荐码',sort: null,istime: false},
					{name: '推荐人数',sort: null,istime: false},
          {name: '分享次数',sort: null,istime: false},
					{name: '当前积分',sort: null,istime: false},
					{name: '是否跟单',sort: null,istime: false},
					{name: '是否消费',sort: null,istime: false},
					{name: '是否开户',sort: null,istime: false}
				],
				page: {
					total: 0,
					num: 1,
					size: 13
				},
				list: [],
				showBonus: false,
				mess: {},//用于编辑用户数据
				filter: {
					name: '',
					login: '',
					account: '',
					depart: '',
					orderByColumn: 'createAt',
					sort: 'DESC',
          date:''
				},
				userid: '',
				followShow: false,
				code: null
			}
		},
		computed: {
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return {department: 'user'}
				}
			}
		},
		components: {
			FilterWork,
			Pagination,
			UserList,
			RecomList,
			BonusGift
		},
		methods: {
			addIntegral (item) {
				this.showBonus = true;
				this.mess = item;
			},
			showFollow(id) {
				this.code = id;
				this.followShow = true;
			},
			outExe() {
				let _this = this;
				_this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						let datas = {
							page_number: 1, //	是	int	当前页
							page_size: 1000,//	是	int	每页大小
							formParams: {
                "mobile": _this.filter.phone,
                "type": _this.filter.type,
								"orderByColumn": _this.filter.orderByColumn,
								"sort": _this.filter.sort,
                "startTime": _this.filter.date[0],
                "endTime": _this.filter.date[1]
              }
						};
						
						_this.$postForExcel('/api/export/mobileUser',datas,'APP注册用户').then();
							
				}).catch(() => {
		
				});
			},
			addAccount (id) {
				this.userid = id;
				this.dialogVisible3 = true;
			},
      currentChange (p) {//分页点击
      	this.page.num = p;
      	this.initPage();
      },
			Inquire (filter) {
				this.filter = filter;
				this.currentChange(1);
			},
			Sort (param) {//列表排序
				if (!param) return;
				this.filter.orderByColumn = param;
				this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
				this.initPage();
			},
			dialogClose () {
				this.showBonus=false;
				this.followShow = false;
				this.initPage();
			},
			Edit (item) {//用户信息编辑
				this.mess = item;
				this.dialogVisible2 = true;
			},
			CardSet (id) {//银行卡设置
				this.userid = id;
				this.dialogVisible4 = true;
			},
			
			
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
						"mobile": _this.filter.phone,
						"type": _this.filter.type,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
            "startTime": _this.filter.date[0],
            "endTime": _this.filter.date[1]
					}
				};
				_this.$post("/api/hpp_mobile_user/pageList",datas).then((res) => {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total);
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
  .bodylist li>div button:last-of-type {
    background-color: transparent;
    color: #409eff;
    border: none
  }
  .popover_box button {
    font-size: 14px;
    color: #6562b6;
    width: 100%;
    height: 30px;
    margin-bottom: 10px;
    background-color: transparent;
    border: 1px solid #6562b6;
    border-radius: 20px;
  }

	.headlist span,
	.bodylist li>div {
		width: 100px;
	}
	
	.headlist span.time,
	.bodylist li>div.time {
		width: 190px;
	}
	.item_box img {
		width: 30px;
		height: 30px;
		border-radius: 50%;
	}
	.bodylist li span.pointer {
		color: #0000FF;
	}


</style>
