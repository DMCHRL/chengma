<template>
	<!--对话框-->
		<el-dialog :visible="show"  width="60%" @close="diaclose">
			<h3 slot="title" class="title_box">跟单列表</h3>
			<div class="dialog_body">
				<div class="filter_box clearfix">
					
					<div class="pull-left">
						<el-input
						  placeholder="手机号"
						  v-model="filter.phone"
						  clearable
						  @keyup.enter.native="Inquire" >
						</el-input>
					</div>
          <div class="pull-left">
            <div class="block">
              <el-date-picker
                v-model="filter.date"
                type="datetimerange"
                start-placeholder="交易开始日期"
                end-placeholder="交易结束日期"
                :unlink-panels="true"
                value-format="yyyy-MM-dd"
                :default-time="['00:00:00']">
              </el-date-picker>
            </div>
          </div>
					<div class="pull-left">
						<el-button type="primary" icon="el-icon-search" @click="Inquire">搜索</el-button>
					</div>
				</div>
				<div class="table_head item_box clearfix">
					<div class="pull-left">
						<span>账号</span>
					</div>
					<div class="pull-left">
						<span>手机号</span>
					</div>
					<div class="pull-left">
						<span>风险参数</span>
					</div>
					<div class="pull-left time">
						<span>跟单时间</span>
					</div>
					<div class="pull-left time">
						<span>交易总手数（手）</span>
					</div>
					<div class="pull-right time">
						<span>操作</span>
					</div>
				</div>
				<ul class="table_body">
					<template v-for="(item,index) in list">
					
					<li class="item_box clearfix" :key="index">
						<div class="pull-left">
							<span>{{item.account}}</span>
						</div>
						<div class="pull-left">
							<span>{{item.mobileNum}}</span>
						</div>
						<div class="pull-left">
							<span>{{item.STEADY}}</span>
						</div>
						<div class="pull-left time">
							<span>{{item.approveAt}}</span>
						</div>
						<div class="pull-left time">
							<span>{{item.lots}}</span>
						</div>
						<div class="pull-right time">
							<el-button @click="ForcedRelease(item)" type="danger" round>强制解除</el-button>
						</div>
					</li>
						
					</template>
				</ul>
				<!--分页-->
				<pagination @currentChange="currentChange" :page="page"></pagination>
			</div>
		</el-dialog>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	export default {
		props: ["show","id","url"],
		data() {
			return {
				list: [],
				filter: {
					phone: '',
          date:''
				},
				page: {
					total: 0,
					num: 1,
					size: 10
				},
				checkAll: false,
				isIndeterminate: true,
				selectList: []
			}
		},
		watch: {
			id () {
				this.initPage();
			}
		},
		computed: {
			
		},
		components: {
			Pagination
		},
		methods: {
			ForcedRelease (item) {
        let _this = this;
        _this.$confirm('强制解除[' + item.account + ']账号, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let datas = {
            strategyId: item.strategyId,
            mobileNum: item.mobileNum,
            account: item.account,
            password: item.password,
          };
          _this.$post('/api/hpp_strategy_order/relieve',datas).then((res) => {
            //						console.log(res)
            if (res.statusCode == '0000') {
              _this.$message({
                type: 'success',
                message: '操作成功!'
              });
              _this.initPage();
            } else {
              _this.$message({
                type: 'error',
                message: res.msgCode
              });
            }
          })

        }).catch(() => {});
      },
			handleCheckAllChange(val) {
				let _this = this;
				_this.isIndeterminate = false;
				_this.list.forEach((item) => {
					item.ischeck = val;
				})
		   	},
		    handleCheckedChange(item) {
		      	let _this = this;
		      	let delIndex = null;
		      	if (item.ischeck) {
		      		_this.selectList.push(item)
		      	}else {
		      		let arr = _this.selectList;
		      		for (let i =0;i<arr.length;i++) {
		      			if (arr[i] == item) {
		      				delIndex = i;
		      			}
		      		}
		      		_this.selectList.splice(delIndex,1);
		      	}
		      	
		    },
		    currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire () {
        if(this.filter.date == null){
          this.filter.date='';
        }
				this.initPage();
			},
			diaclose () {
				this.$emit("close")
			},
			
			Confirm() {
				let _this = this;
				let datas = {
					'noticeId': _this.noticeId,
					'type': 'MULTIPLE',
					'hppMobileUserDTOList': _this.selectList
				}
				_this.$post('/api/hpp_notice/sendNotice',datas).then((res) => {
					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "发送成功",
				          type: 'success'
				       });
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
				})
			},
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
						strategyId: _this.id,
						mobileNum: _this.filter.phone,
            startTime: _this.filter.date[0],
            endTime: _this.filter.date[1],
					}
				};
				_this.$post(_this.url,datas).then((res) => {
//					console.log(res)
					_this.list = res.data.list;
					_this.page.total = parseInt(res.data.total);
				})
			}
			
		},
		mounted (){
			this.initPage();
		}
	}
</script>

<style scoped>
	.el-button.is-round {
			padding: 8px 20px;
	}
	.title_box {
		text-align: center;
	}
	
	.dialog_body {
		margin-top: -30px;
		padding-bottom: 60px;
	}
	
	.dialog_body h4 {
		font-size: 16px;
		border-left: 3px solid #19183E;
		margin: 20px 0;
		padding: 2px 20px;
	}
	
	.dialog_body .input_box {
		width: 50%;
		line-height: 40px;
		margin-bottom: 10px;
	}
	
	.dialog_body .input_box input,
	.dialog_body .input_box textarea {
		height: 35px;
		width: 50%;
		border: 1px solid #dcdfe6;
		padding: 0 10px;
	}
	
	.dialog_body .input_box textarea {
		height: 60px;
		line-height: 20px;
		padding: 5px 10px;
	}
	
	.dialog_body .input_box span {
		float: left;
		width: 26%;
		text-align: right;
		margin-right: 10px;
	}
	
	.dialog_body .img_box {
		float: left;
		width: 50%;
		height: 160px;
		overflow: hidden;
	}
	
	.dialog_body .img_box img {
		width: auto;
		max-width: 100%;
	}
	
	._dialog-footer {
		text-align: center;
		padding: 20px 0;
	}
	
	._dialog-footer button {
		font-size: 16px;
		color: #fff;
		padding: 3px 30px;
		border-radius: 20px;
		box-shadow: 1px 1px 1px #999;
	}
	
	._dialog-footer button:last-of-type {
		color: #191A41;
		margin-left: 30px;
	}
	
	.filter_box {
		padding: 20px;
	}
	.filter_box>div {
		margin-right: 20px;
	}
	
	/* .table_body button{
		border: 1px solid #0000CC;
		color: #999;
		font-size: 16px;
		border-radius: 10px;
		padding: 2px 20px;
	} */
	
	.item_box {
		border-bottom: 1px solid #ededed;
		padding: 5px 20px;
	}
	.item_box>div {
		width: 120px;
		min-height: 1px;
		text-align: center;
	}
	.item_box>div.time {
		width: 200px;
	}
	.item_box img {
		width: 40px;
		max-height: 40px;
		border-radius: 50%;
	}
	
	
</style>
