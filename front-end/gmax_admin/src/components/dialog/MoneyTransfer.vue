<template>
	<div class="dialog_box">
		<h3 slot="title" class="dialog_title">资金内转</h3>
		<div class="input_box">
			<span>账户</span>
			<select v-model="account">
				<template v-for="item in acclist" >
					<option :value="item.account">{{item.account}}</option>
					
				</template>
			</select>
		</div>
		<div class="input_box">
			<span>金额</span>
			<input type="number" name="" id="" value="" v-model="cny" />
		</div>
		
		<div slot="footer" class="dialog_footer">
			<button @click="Confirm">确认转账</button>
		</div>
	</div>
</template>

<script>
	export default {
		props: ["userid"],
		data() {
			return {
				acclist: [],
				account: '',
				cny: ''
			}
		},
		components: {

		},
		methods: {
			Confirm() {
				let _this = this;
				let datas = {
				    "account":_this.account,
    				"amount": _this.cny
				};
				_this.post("/api/tlb-fund-apply/inner",datas,function (res) {
//					//console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '申请成功',
				          type: 'success'
				        });
					}else{
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
					_this.cny = '';
					_this.$emit("close");
					
				})
			},
			getAccountList () {
				let list = localStorage.getItem("accList");
				if (list) {
					list = JSON.parse(list);
					this.acclist = list;
					this.account = list[0].account;
				}
			}
			
		},
		mounted() {
			this.getAccountList();
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>