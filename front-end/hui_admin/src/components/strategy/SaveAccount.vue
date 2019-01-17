<template>
	<!--添加银行卡弹窗-->
	<el-dialog  width="30%"  :visible="show" @close="cancelDialog">
		<h3 slot="title" class="dialog_title">{{titleText}}</h3>
		
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">交易账号</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="tlbAccount.account"  disabled />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">交易密码</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="tlbAccount.mt4Password" disabled />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">手机号</span>
			</div>
      <div class="pull-left right_box">
        <input type="text"  v-model="tlbAccount.mobileNum" />
      </div>
		</div>

		<div slot="footer" class="dialog_footer">
			
			<el-button @click="Commit" type="primary">保存</el-button>
			<!--<el-button v-if="tlbAccount.status =='APPLYING'" @click="AudittlbAccount(tlbAccount.id,'PASSED',tlbAccount.tlbAccountName)" type="success">通过</el-button>
			<el-button v-if="tlbAccount.status =='APPLYING'" @click="AudittlbAccount(tlbAccount.id,'REJECT',tlbAccount.tlbAccountName)" type="danger">拒绝</el-button>-->
			
		</div>
	</el-dialog>
</template>

<script>
	export default {
    props: ['show','tlbAccountData','titleText'],
		data() {
			return {
				tlbAccount: {
				  id : '',
          account: '',
          mt4Password: '',
          mobileNum : '',
				}
			}
		},
		components: {

		},
    watch: {
      tlbAccountData () {
        let temp = JSON.parse(JSON.stringify(this.tlbAccountData));
        this.tlbAccount = temp;
      }
    },
		methods: {
			AuditTlbAccount (id,status,name) {
				let _this = this;
				let datas = {
					tlbAccountId: id,
					status: status
				};
				let text = status == 'REJECT'? '拒绝':'通过';
				
					_this.$confirm(text+'['+name+']策略发布, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						_this.$post("/api/hpp_tlbAccount/approve",datas).then((res) => {
							if(res.statusCode == '0000') {
								_this.$message({
									type: 'success',
									message: '操作成功!'
								});
								_this.$emit('close')
							}
						})
						
					}).catch(() => {});
				
			},
			Commit() {
				let _this = this;
// 				let datas = {
// 				  id : _this.tlbAccount.id,
// 					tlbAccountName: _this.tlbAccount.tlbAccountName,
// 					tlbAccountName2: _this.tlbAccount.tlbAccountName2,
// 					tlbAccountText: _this.tlbAccount.tlbAccountText,
// 					account: _this.tlbAccount.account,
// 					password: _this.tlbAccount.password,
//           platform:_this.tlbAccount.platform,
//           server:_this.tlbAccount.server,
//           tradeType: _this.tlbAccount.tradeType,
// 				};
				
				this.$post("/api/tlb-account/updateTlbAccount",_this.tlbAccount).then((res) => {
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "修改成功",
				          type: 'success'
				        });
				        
            _this.tlbAccount = {
              tlbAccountName: '',
              tlbAccountName2: '',
              tlbAccountText : '',
              platform : "",
              server : "",
              account: '',
              password: '',
              tradeType: ''
						};
				        
				    _this.$emit('close') //关闭弹出
				        
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'error'
				        });
					}
				})

			},
			cancelDialog() {
				this.$emit('close')
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	.dialog_title {
		text-align: center;
		font-size: 22px;
		color: #6562b6;
	}
	
	.input_box {
		margin-bottom: 25px;
		position: relative;
	}
	.left_box {
		text-align: right;
		width: 34%;
	}
	.left_box span {
		padding-right: 20px;
		color: #666;
		font-size: 14px;
		line-height: 36px;
	}
	
	span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	
	.right_box input,
	.right_box select,
	.right_box textarea {
		border: 1px solid #ccc;
		padding: 5px 10px;
		border-radius: 5px;
		font-size: 14px;
		color: #666;
		width: 250px;
	}
	
	.trip_text {
		position: absolute;
		top: 100%;
		left: 0;
		padding-left: 40%;
		font-size: 12px;
		color: #F14B3B;
	}
	
	.uplond_box {
		position: relative;
		text-align: center;
	}
	
	.uplond_box label {
		width: 200px;
		min-height: 100px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	.icon_box{
		line-height: 100px;
	}
	.icon_box img {
		width: 30px;
		height: 30px;
	}
	
	.img_box img {
		width: 100%;
		height: auto;
	}
	
	.dialog_footer {
		text-align: center;
		padding-bottom: 20px;
	}
</style>
