<template>
	<div class="content_box">
		
		<div class="titling">
			<span v-text="user.department == 'admin' || user.department == 'service'? '账户管理' : '归属账户'"></span>
			<!--<button @click="dialogFormVisible = true">开户</button>-->
			<!--<button @click="toNewAccount">开户</button>-->
			<button v-if="user.department == 'admin' || user.department == 'service'" @click="dialogVisible = true">新增客户</button>
			<!--<button v-if="user.department == 'admin'" @click="dialogVisible2 = true">新增套利宝账号</button>-->
		</div>
		
		<!--新增客户-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible" width="20%">
			<add-account @close="dialogClose"></add-account>
		</el-dialog>
		
		<!--新增套利宝账户-->
		<!--<el-dialog custom-class=""  :visible.sync="dialogVisible2" width="20%">
			<add-tlb @close="dialogClose"></add-tlb>
		</el-dialog>-->
		
		<!--编辑套利宝账户-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible3" width="20%">
			<edit-acc :mess="mess" @close="dialogClose"></edit-acc>
		</el-dialog>
		
		<!--开户弹窗-->
		<!--<el-dialog title="开立账户" width="60%" :visible.sync="dialogFormVisible">
			<new-account></new-account>
		</el-dialog>-->
		
		<filter-work @Inquire="Inquire" :parent="2"></filter-work>
		
		<acc-list :list="list" @Sort="Sort" @Edit="Edit" @close="dialogClose"></acc-list>
		
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import AccList from "@/components/list/AccList.vue"
	import NewAccount from '@/components/common/NewAccount'
	import AddAccount from "@/components/dialog/AddAccount"
	import EditAcc from "@/components/dialog/EditAcc"
//	import AddTlb from "@/components/dialog/AddTlb"
	export default {
		data() {
			return {
				page: {
					total: 0,
					num: 1,
					size: 15
				},
				list: [],
				dialogFormVisible: false, //开户弹窗
				dialogVisible: false, //新增账户
				dialogVisible2: false,
				dialogVisible3: false,
				mess: {},
				filter: {
					name: '',
					login: '',
					account: '',
					depart: '',
					orderByColumn: 'createAt',
					sort: 'DESC'
				}
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
			NewAccount,
			AddAccount,
			AccList,
//			AddTlb,
			EditAcc
		},
		methods: {
			Inquire (filter) {//搜索
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
				this.initPage();
			},
			Edit (item) {
				this.mess = item;
				this.dialogVisible3 = true;
			},
			currentChange (p) {//分页点击
//				//console.log(p)
				this.page.num = p;
				this.initPage();
			},
			toNewAccount () {
				let id = this.user.id;
				window.open("http://tlb.txasfx.com/application2?id="+id);
			},
			
			initPage () {
				let _this = this;
				let datas = {      
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
						"account": _this.filter.account,
						"accountName": _this.filter.name,  // 帐号名称
						"group": _this.filter.group,
						"parentId": _this.filter.pid,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
					}
					
				};
				
				let url = "/api/user/loadBelongAccount";
				if (_this.user.department == 'admin' || _this.user.department == 'service') {
					url = "/api/tlb-account/pageList";
				}
				
				_this.$until.superPost(url,datas,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						
						_this.list = res.data.list;
						_this.page.total = res.data.total;
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