<template>
	<div class="content_box">
		<div class="titling">
			<span v-text="user.department == 'admin' || user.department == 'service'? 'CRM用户' : '下级代理'"></span>
			
			<!--<button @click="dialogVisible5 = true">申请开户</button>-->
			<router-link :to="'/regiter?id='+user.id"><button>开户</button></router-link>
			<button @click="dialogVisible = true">添加用户</button>
		</div>
		
		<!--添加用户弹窗-->
		<el-dialog :visible.sync="dialogVisible" width="25%">
		  <add-user @close="dialogClose"></add-user>
		</el-dialog>
		
		<!--修改用户弹窗-->
		<el-dialog :visible.sync="dialogVisible2" width="25%">
		  <edit-user :mess="mess" @close="dialogClose"></edit-user>
		</el-dialog>
		
		<!--新增套利宝账户-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible3" width="25%">
			<add-tlb @close="dialogClose" :userid="userid"></add-tlb>
		</el-dialog>
		
		<!--修改银行卡-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible4" width="25%">
			<set-card @close="dialogClose" :userid="userid"></set-card>
		</el-dialog>
		
		<!--申请开户-->
		<account-application :dialogFormVisible="dialogVisible5" @close="dialogClose"></account-application>
		
		<filter-work @Inquire="Inquire" :parent="1"></filter-work>
		
		<user-list :list="list" @Sort="Sort" @Edit="Edit" @CardSet="CardSet" @addAccount="addAccount" @close="dialogClose"></user-list>
		
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import UserList from "@/components/list/UserList.vue"
	import AddUser from "@/components/dialog/AddUser"
	import EditUser from "@/components/dialog/EditUser"
	import AddTlb from "@/components/dialog/AddTlb"
	import SetCard from "@/components/dialog/SetCard"
	import AccountApplication from "@/components/dialog/AccountApplication"
	export default {
		data() {
			return {
				page: {
					total: 0,
					num: 1,
					size: 15
				},
				list: [],
				dialogVisible: false,//添加用户弹窗
				dialogVisible2: false,//修改用户弹窗
				dialogVisible3: false,//新增套利宝账户
				dialogVisible4: false,//设置银行卡
				dialogVisible5: false,//申请开户
				mess: {},//用于编辑用户数据
				filter: {
					name: '',
					login: '',
					account: '',
					depart: '',
					orderByColumn: 'createdDate',
					sort: 'DESC'
				},
				userid: ''
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
			AddUser,
			UserList,
			EditUser,
			AddTlb,
			SetCard,
			AccountApplication
		},
		methods: {
			addAccount (id) {
				this.userid = id;
				this.dialogVisible3 = true;
			},
			Inquire (filter) {
//				console.log(filter);
				this.filter = filter;
				this.initPage();
			},
			Sort (data) {//列表排序
//				console.log(data)
				this.filter.orderByColumn = data.orderByColumn;
				this.filter.sort = data.sort;
				this.initPage();
			},
			dialogClose () {
				this.dialogVisible=false;
				this.dialogVisible2=false;
				this.dialogVisible3=false;
				this.dialogVisible4=false;
				this.dialogVisible5 = false;
				this.initPage();
			},
			Edit (item) {//用户信息编辑
				this.mess = item;
//				//console.log(this.mess)
				this.dialogVisible2 = true;
			},
			CardSet (id) {//银行卡设置
//				console.log(id)
				this.userid = id;
				this.dialogVisible4 = true;
			},
			currentChange (p) {//分页点击
//				////console.log(p)
				this.page.num = p;
				this.initPage();
			},
			
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
						"firstName": _this.filter.name,
						"login": _this.filter.login,
						"department": _this.filter.depart,
						"mobile": _this.filter.phone,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
						"email": _this.filter.email
					}
				};
				_this.post("/api/user/list",datas,function (res) {
//					//console.log(res)
					if (res.statusCode == "0000") {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total_count);
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
	
</style>