<template>
	<div class="content_box">
		<div class="titling">
			<span>管理员设置</span>
			<button @click="newUser">添加管理员</button>
			<span class="dao pull-right" @click="outExe">导出excel</span>
		</div>
		
		<!--添加编辑用户弹窗-->
		<el-dialog :visible.sync="dialogVisible" width="25%">
		  <save-user @close="dialogClose" :mess="mess"></save-user>
		</el-dialog>

		
		<!--新增套利宝账户-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible3" width="20%">
			<add-tlb @close="dialogClose" :userid="userid"></add-tlb>
		</el-dialog>
		
		<!--修改银行卡-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible4" width="20%">
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
	import UserList from "../list/UserList.vue"
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import SaveUser from "../dialog/SaveUser"
	import AddTlb from "../dialog/AddTlb"
	import SetCard from "../dialog/SetCard"
	import AccountApplication from "../dialog/AccountApplication"
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
				mess: {
					firstName: "",     //名称
					email: "",          //邮箱
					mobile: "", // 电话号码
					eara: "",      // 区域
					department: 'service',
					id: ''
				},//用于编辑用户数据
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
			FilterWork,
			SaveUser,
			Pagination,
			UserList,
			AddTlb,
			SetCard,
			AccountApplication
		},
		methods: {
			outExe() {
				let _this = this;
				this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.excelData = this.list //你要导出的数据list。
					this.export2Excel()
				}).catch(() => {

				});
			},
			export2Excel() {
				var that = this;
				require.ensure([], () => {
					const {
						export_json_to_excel
					} = require('../../excel/Export2Excel'); //这里必须使用绝对路径
					const filterVal = ['login', 'firstName', 'mobile', 'email', 'createdDate']; // 导出的表头名
					const tHeader = ['登录名', '名称', '联系电话', '邮箱', '注册时间']; // 导出的表头字段名
					const list = that.excelData;
					const data = that.formatJson(filterVal, list);
					var myDate = new Date();
					var time = myDate.getFullYear() +'-'+ (myDate.getMonth()+1) +'-'+ myDate.getDate();
					export_json_to_excel(tHeader, data, `[${time}]汇添溢后台用户excel`); // 导出的表格名称，根据需要自己命名
				})
			},
			formatJson(filterVal, jsonData) {
				return jsonData.map(v => filterVal.map(j => v[j]))
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
			Sort (data) {//列表排序
			// console.log(data)
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
			newUser () {
				this.mess = {};
				this.dialogVisible = true;
			},
			Edit (item) {//用户信息编辑
				this.mess = item;
				this.dialogVisible = true;
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
						"firstName": _this.filter.name,
						"login": _this.filter.login,
						"department": _this.filter.depart,
						"mobile": _this.filter.phone,
						"email": _this.filter.email,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
					}
				};
				_this.$post("/api/user/list",datas).then((res) => {
					// console.log(res)
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