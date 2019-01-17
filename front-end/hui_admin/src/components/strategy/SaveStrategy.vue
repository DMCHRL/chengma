<template>
	<!--添加银行卡弹窗-->
	<el-dialog  width="30%"  :visible="show" @close="cancelDialog">
		<h3 slot="title" class="dialog_title">{{titleText}}</h3>
		
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">策略名称</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="strategy.strategyName" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">策略类型</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="strategy.strategyName2" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">策略介绍</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="6" cols="" v-model="strategy.strategyText"></textarea>
			</div>
		</div>
    <div class="input_box clearfix">
      <div class="pull-left left_box">
        <span class="import">交易方式</span>
      </div>
      <div class="pull-left right_box">
        <input type="text"  v-model="strategy.tradeType" />
      </div>
    </div>
    <div class="input_box clearfix">
      <div class="pull-left left_box">
        <span class="import">交易平台</span>
      </div>
      <div class="pull-left right_box">
        <input type="text"  v-model="strategy.platform" />
      </div>
    </div>
    <div class="input_box clearfix">
      <div class="pull-left left_box">
        <span class="import">服务器</span>
      </div>
      <div class="pull-left right_box">
        <input type="text"  v-model="strategy.server" />
      </div>
    </div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import" >账号</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" disabled  v-model="strategy.account" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">密码</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"   v-model="strategy.password" />
			</div>
		</div>
		
		<div slot="footer" class="dialog_footer">
			
			<el-button @click="Commit" type="primary">保存</el-button>
			<el-button v-if="strategy.status =='APPLYING'" @click="AuditStrategy(strategy.id,'PASSED',strategy.strategyName)" type="success">通过</el-button>
			<el-button v-if="strategy.status =='APPLYING'" @click="AuditStrategy(strategy.id,'REJECT',strategy.strategyName)" type="danger">拒绝</el-button>
			
		</div>
	</el-dialog>
</template>

<script>
	export default {
    props: ['show','strategyData','titleText'],
		data() {
			return {
				strategy: {
				  id : '',
          strategyName: '',
          strategyName2: '',
          strategyText : '',
          platform : "",
          server : "",
          account: '',
					password: '',
          tradeType: ''
				}
			}
		},
		components: {

		},
    watch: {
      strategyData () {
        let temp = JSON.parse(JSON.stringify(this.strategyData));
        this.strategy = temp;
      }
    },
		methods: {
			AuditStrategy (id,status,name) {
				let _this = this;
				let datas = {
					strategyId: id,
					status: status
				};
				let text = status == 'REJECT'? '拒绝':'通过';
				
					_this.$confirm(text+'['+name+']策略发布, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						_this.$post("/api/hpp_strategy/approve",datas).then((res) => {
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
// 				  id : _this.strategy.id,
// 					strategyName: _this.strategy.strategyName,
// 					strategyName2: _this.strategy.strategyName2,
// 					strategyText: _this.strategy.strategyText,
// 					account: _this.strategy.account,
// 					password: _this.strategy.password,
//           platform:_this.strategy.platform,
//           server:_this.strategy.server,
//           tradeType: _this.strategy.tradeType,
// 				};
				
				this.$post("/api/hpp_strategy/edit",_this.strategy).then((res) => {
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "修改成功",
				          type: 'success'
				        });
				        
            _this.strategy = {
              strategyName: '',
              strategyName2: '',
              strategyText : '',
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
