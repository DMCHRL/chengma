<template>
	<!--添加银行卡弹窗-->
	<el-dialog width="50%" :visible="show" @close="cancelDialog">
		<h3 slot="title" class="dialog_title">{{titleName}}</h3>

		<div class="dialog_body clearfix">
			<div class="pull-left part_body">
				<h4>平台介绍</h4>
				<div class="input_box clearfix" v-for="item in leftInputList">
					<div class="pull-left left_box">
						<span :class="{import:item.isimport}">{{item.label}}</span>
					</div>
					<div class="pull-left right_box">
						<input type="text" v-model="item.value" />
					</div>
				</div>
				<div class="input_box clearfix">
					<div class="pull-left left_box">
						<span class="import">平台介绍</span>
					</div>
					<div class="pull-left right_box">
						<textarea name="" rows="4" cols="" v-model="remark"></textarea>
					</div>
				</div>
				<div class="input_box clearfix">
					<div class="pull-left left_box">
						<span class="import">列表图标</span>
					</div>
					<div class="pull-left right_box">
						<label>
							<input ref="listImg" type="file" accept="image/*" style="display: none;" @change="listImgUpload" name="" id="" value="" />
							<div class="uplond_box">
								<img v-if="listImg" :src="listImg"  class="main_img"/>
								<img v-else src="../../assets/img/acc_1.png"/>
							</div>
						</label>
					</div>
				</div>
				<div class="input_box clearfix">
					<div class="pull-left left_box">
						<span class="import">详情背景</span>
					</div>
					<div class="pull-left right_box">
						<label>
							<input ref="background" type="file" accept="image/*" style="display: none;" @change="backgroundUpload" name="" id="" value="" />
							<div class="uplond_box">
								<img v-if="background" :src="background" class="main_img"/>
								<img v-else src="../../assets/img/acc_1.png"/>
							</div>
						</label>
					</div>
				</div>
			</div>
			<div class="pull-right part_body">
				<h4>基本信息</h4>
				<div class="input_box clearfix" v-for="item in rightInputList">
					<div class="pull-left left_box">
						<span :class="{import:item.isimport}">{{item.label}}</span>
					</div>
					<div class="pull-left right_box">
						<input type="text" v-model="item.value" />
					</div>
				</div>
				<div class="input_box clearfix">
					<div class="pull-left left_box">
						<span class="import">评价</span>
					</div>
					<div class="pull-left right_box">
						<textarea name="" rows="4" cols="" v-model="evaluate"></textarea>
					</div>
				</div>
			</div>
		</div>


		<div slot="footer" class="dialog_footer">

			<el-button @click="Commit" type="primary">提交</el-button>

		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show','exid'],
		data() {
			return {
				titleName: '新增汇商',
				leftInputList: [
					{label:'名称',model: 'sinksName',isimport:true,value:''},
					{label:'英文名',model: 'sinksName2',isimport:true,value:''},
					{label:'摘要',model: 'explain',isimport:true,value:''},
//					{label:'图标',model: 'listImg',isimport:true,value:''},
//					{label:'详情背景',model: 'background',isimport:true,value:''},
					{label:'成立时间',model: 'openTime',isimport:true,value:''},
					{label:'所属国家',model: 'country',isimport:true,value:''},
					{label:'最小手数',model: 'lots',isimport:true,value:''},
					{label:'最大杠杆',model: 'lever',isimport:true,value:''},
					{label:'点差类型',model: 'pointType',isimport:true,value:''},
					{label:'主要点差',model: 'mainPoint',isimport:true,value:''},
					{label:'交易品种',model: 'tradeType',isimport:true,value:''}
				],
				rightInputList: [
					{label:'平台类型',model: 'platformType',isimport:true,value:''},
					{label:'开设账户类型',model: 'accountType',isimport:true,value:''},
					{label:'剥头皮',model: 'peel',isimport:true,value:''},
					{label:'对冲 ',model: 'hedging',isimport:true,value:''},
					{label:'最大交易量',model: 'maxTrade',isimport:true,value:''},
					{label:'入金方式',model: 'fundIn',isimport:true,value:''},
					{label:'出金方式',model: 'fundOut',isimport:true,value:''},
					{label:'出金手续费',model: 'fundOutCost',isimport:true,value:''},
					{label:'入金手续费',model: 'fundInCost',isimport:true,value:''},
					{label:'隔夜利息',model: 'rollovers',isimport:true,value:''},
					{label:'爆仓比例',model: 'explosionRatio',isimport:true,value:''},
					{label:'是否支持人民币入金 ',model: 'rmb',isimport:true,value:''},
					{label:'评价分数 ',model: 'evaluateScore',isimport:true,value:''},
				],
				remark: '',
				evaluate: '',
				listImg: null,
				background: null,
				id: null
			}
		},
		watch: {
			exid (val) {
				let _this = this;
				_this.id = val;
				if (_this.id) {
					_this.titleName = "编辑汇商";
					_this.getExById();
				}else {
					_this.titleName = "新增汇商";
				}
			}
		},
		components: {

		},
		methods: {
			setValue (data) {
				this.remark=data.remark;
				this.evaluate=data.evaluate;
				this.listImg=data.listImg;
				this.background=data.background;
				let list = this.leftInputList.concat(this.rightInputList);
				list.forEach((item) => {
					for(let it in data) {
						if (item.model == it) {
							item.value = data[it];
						}
					}
				})
			},
			getExById () {
				let _this = this;
				_this.$fetch("/api/hpp_sinks/get/"+_this.id).then((res) => {
					console.log(res)
					_this.setValue(res.data)
				})
			},
			listImgUpload () {
				let _this = this;
				let obj = _this.$refs.listImg.files[0];
				_this.$until.uploadImage("/api/uploadImage",obj).then((res) => {
					let imgUrl = res.target.responseText;
					_this.listImg = imgUrl;
				})
			},
			backgroundUpload () {
				let _this = this;
				let obj = _this.$refs.background.files[0];
				_this.$until.uploadImage("/api/uploadImage",obj).then((res) => {
					let imgUrl = res.target.responseText;
					_this.background = imgUrl;
				})
			},
			getValue (models) {
				let _this = this;
				let list = _this.leftInputList.concat(_this.rightInputList);
				for (let i = 0;i<list.length;i++) {
					if (list[i].model == models) {
						return list[i].value;
					}
				}
			},
			Commit() {
				let _this = this;
				
				let datas = {
					sinksName: _this.getValue('sinksName'),//汇商名称   
					sinksName2: _this.getValue('sinksName2'),// 汇商次名    
					explain: _this.getValue('explain'),  // 列表摘要    
					listImg:  _this.listImg,// 列表显示图标    
					background: _this.background,// 背景图    
					openTime: _this.getValue('openTime'), // Date  成立时间    
					country:  _this.getValue('country'),// 所属国家    
					lots: _this.getValue('lots'), //Double  最小手数    
					lever: _this.getValue('lever'),// 列表摘要    
					pointType    : _this.getValue('pointType'),// 点数类型    
					mainPoint    : _this.getValue('mainPoint'),// 主要点差    
					tradeType    : _this.getValue('tradeType'),// 交易品种    
					remark    : _this.remark,// 平台介绍    
					platformType    : _this.getValue('platformType'),// 平台类型    
					accountType    : _this.getValue('accountType'),// 开设账户类型    
					peel    : _this.getValue('peel'),// 剥头皮 Y or N (支持 or 不支持)    
					hedging    : _this.getValue('hedging'),////对冲 Y or N (支持 or 不支持)    
					maxTrade    : _this.getValue('maxTrade'),// 最大交易量    
					fundIn    : _this.getValue('fundIn'),// 入金方式    
					fundOut    : _this.getValue('fundOut'),// 出金方式    
					fundOutCost    : _this.getValue('fundOutCost'),// 出金手续费    
					fundInCost    : _this.getValue('fundInCost'),// 入金手续费    
					rollovers    : _this.getValue('rollovers'),// 隔夜利息    
					explosionRatio    : _this.getValue('explosionRatio'),// 爆仓比例    
					rmb    : _this.getValue('rmb'),//是否支持人民币入金 (Y or N) (支持 or 不支持)    
					evaluate    : _this.evaluate,// 评价    
					evaluateScore    : _this.getValue('evaluateScore'),// 评价分数    
					id    : _this.id,// 修改时用到    
				}
				
				this.$post("/api/hpp_sinks/saveHppSinks", datas).then((res) => {
					console.log(res)
					if(res.statusCode == "0000") {
						_this.$message({
							message: "新增成功",
							type: 'success'
						});
						
						_this.$emit('close') //关闭弹出
						window.location.reload();//刷新页面，初始化data
					} else {
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
	
	.part_body {
		width: 50%;
	}
	.part_body h4 {
		padding-left: 20px;
		color: #666;
		padding-bottom: 10px;
	}
	.input_box {
		margin-bottom: 15px;
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
	
	
	
	.dialog_footer {
		text-align: center;
		padding-bottom: 20px;
	}
</style>