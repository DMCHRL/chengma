<template>
	<div class="content_box">
		
		<add-meal :dialogVisible="show" @Add="addMeal" @close="show = false"></add-meal>
		
		<div class="titling">
			<span>新增/编辑考证</span>
		</div>
		<div class="clearfix backfff">

			<div class="pull-left main_box">

				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试名称：</span>
					</div>
					<div class="pull-left">
						<el-input type="text" name="" id="" value="" placeholder="" v-model="trainName" />
					</div>
				</div>
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试时间：</span>
					</div>
					<div class="pull-left">
						<el-input type="text" name="" id="" value="" placeholder="" v-model="trainTime" />
					</div>
				</div>
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试地点：</span>
					</div>
					<div class="pull-left">
						<el-input type="text" name="" id="" value="" placeholder="" v-model="address" />
					</div>
				</div>
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试类型：</span>
					</div>
					<div class="pull-left">
						<el-select v-model="type" placeholder="请选择">
						    <el-option
						      v-for="item in options"
						      :key="item.value"
						      :label="item.label"
						      :value="item.value">
						    </el-option>
						  </el-select>
					</div>
				</div>
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试海报：</span>
					</div>
					<div class="pull-left">
						<label>
						<input ref="bookFace" type="file" accept="image/*" style="display: none;" @change="imgUpload" name="" id="" value="" />
						<div class="uplond_box">
							<img v-if="bookFace" :src="bookFace" class="main_img"/>
							<img v-else src="../../assets/img/acc_1.png"/>
						</div>
					</label>
					</div>
				</div>
				
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>热门标签：</span>
					</div>
					<div class="pull-left">
						<el-input type="text" name="" id="" value="" placeholder="" v-model="hotTag" />
					</div>
				</div>
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试简介：</span>
					</div>
					<div class="pull-left">
						<el-input
						  type="textarea"
						  :rows="8"
						  placeholder="请输入内容"
						  v-model="introduction">
						</el-input>
					</div>
				</div>
				<div class="el-input_box clearfix">
					<div class="pull-left">
						<span>考试套餐：</span>
					</div>
					<div class="pull-left">
						<template v-for="(item,index) in mealList">
							<div class="">
								<span>名称：{{item.name}}</span>
								<span>价格：<span class="price">{{item.price}}</span></span>
								<span>包含：{{item.include}}</span>
								<i class="el-icon-delete" @click="deleteMeal(index,item)"></i>
							</div>
						</template>
						<span v-show="!mealList.length">空</span>
						<el-button type="primary" icon="el-icon-plus" @click="show = true">增加</el-button>
					</div>
				</div>
			</div>

			<div class="main_box pull-left over_x">
				考试详情编辑：
				<div id="editor" type="text/plain" style="width:800px; height:400px;"></div>
			</div>
		</div>

		<div class="btn_box">
			<button @click="clearContent">清空</button>
			<button class="submit" @click="submits" :disabled="isDisabled">提交</button>
		</div>

	</div>
</template>

<script>
	import '../../../static/utf8-jsp/ueditor.config'
	import '../../../static/utf8-jsp/ueditor.all'
	import '../../../static/utf8-jsp/lang/zh-cn/zh-cn'
	import AddMeal from './AddMeal'
	export default {
		data() {
			return {
				ue: null,
				isDisabled: false,
				old: '',
				options: [{
		          value: '1',
		          label: '系统考试'
		        }],
		        show: false,
				trainName: '',
				bookFace: null, //	是	string	配图
				trainTime: '', //	是	String	培训时长
				type: '1', //	是	string	考试类别
				teacher: '', //	是	string	授课老师
				hotTag: '', //	否	string	热门标签
				introduction: '', //	否	string	介绍
				context: '', //	否	string	百度编辑器内容
				mealList: [], //	否	json数组	套餐
				address: ''
			}
		},
		computed: {
			id() {
				return this.$route.query.id;
			}
		},
		components: {
			AddMeal
		},
		methods: {
			addMeal (obj) {
				console.log(obj)
				this.mealList.push(obj)
				this.show= false;
			},
			deleteMeal (index,item) {
        let _this = this;
        _this.$confirm("删除["+item.name+"]，不可恢复。是否继续?", '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.$fetch("/api/hpp_exam/deleteCourseMeal/"+item.id).then((res) => {
            if (res.statusCode === "0000") {
              _this.$message({
                message: '删除成功',
                type: 'success'
              });
              this.mealList.splice(index,1);
            }else {
              _this.$message({
                message: res.msgCode,
                type: 'warning'
              });
            }
          })
        }).catch(() => {});
			},
			getCourses() {
				let _this = this;
				_this.$fetch("/api/hpp_exam/get/" + _this.id).then((res) => {
					console.log(res)
					if(res.data) {
						_this.trainName= res.data.trainName;
						_this.bookFace= res.data.img;
						_this.trainTime= res.data.trainTime;
						_this.type= res.data.type;
						_this.address= res.data.address;
						_this.hotTag= res.data.hotTag;
						_this.introduction= res.data.introduction;
						_this.mealList= res.data.mealList;
						
						_this.old = res.data.context;

						setTimeout(function() {
							_this.xieru();
						}, 1000)
					}
				})
			},
			initUeditor() {
				this.ue = UE.getEditor('editor', {
					BaseUrl: '',
					UEDITOR_HOME_URL: 'static/utf8-jsp/',
					// toolbars:[] 
				});
			},
			imgUpload() {
				let _this = this;
				let obj = _this.$refs.bookFace.files[0];
				_this.$until.uploadImage("/api/uploadImage", obj).then((res) => {
					let imgUrl = res.target.responseText;
					_this.bookFace = imgUrl;
				})
			},
			clearContent() {
				this.ue.execCommand('cleardoc');
			},
			submits() {
				let _this = this;
				let content = this.ue.getContent();
				let datas = {
					trainName: _this.trainName, //是	string	培训名称
					img: _this.bookFace, //	是	string	配图
					trainTime: _this.trainTime, //	是	String	培训时长
					type: _this.type, //	是	string	考试类别
					address: _this.address, //	是	string	授课老师
					hotTag: _this.hotTag, //	否	string	热门标签
					introduction: _this.introduction, //	否	string	介绍
					context: content, //	否	string	百度编辑器内容
					mealList: _this.mealList, //	否	json数组	套餐
					id: _this.id //	否	string	修改时加上
				}
				
				_this.$post("/api/hpp_exam/saveHppExam", datas).then((res) => {
					console.log(res)
					if(res.statusCode == "0000") {
						_this.$message({
							message: "操作成功",
							type: 'success'
						});
						_this.$router.go(-1)
					} else {
						_this.$message({
							message: res.msgCode,
							type: 'error'
						});
					}
				})
			},
			xieru() {
				UE.getEditor('editor').setContent(this.old);
			}
		},
		mounted() {
			this.initUeditor();
			if(this.id != undefined) {
				this.getCourses();
			}
		},
		destroyed() {
			this.ue.destroy();
		}
	}
</script>

<style scoped>
	.backfff {
		background-color: #fff;
		margin: 0 -20px;
	}
	.main_box {
		width: 50%;
		overflow: hidden;
		padding: 20px 0;
	}
	.over_x {
		overflow-x: scroll;
		
	}
	
	.el-input{
		width: 200px;
	}
	
	.el-textarea {
		width: 400px;
	}
	.el-input_box {
		margin-bottom: 10px;
		margin-left: 30px;
	}
	
	.el-input_box span {
		line-height: 30px;
		font-size: 14px;
		color: #666;
		margin-right: 10px;
	}
	.el-input_box span.price {
		color: #FF0000;
	}
	
	.uplond_box {
		width: 200px;
		height: 100px;
		line-height: 100px;
		text-align: center;
		border: 1px solid #dcdfe6;
		cursor: pointer;
	}
	
	.uplond_box img {
		width: 30px;
	}
	
	.uplond_box img.main_img {
		width: auto;
		height: 100%;
	}
	
	.btn_box {
		text-align: center;
		padding: 30px 0;
		background-color: #fff;
		margin: 0 -20px;
	}
	
	.btn_box button {
		background-color: transparent;
		color: #666;
		padding: 5px 40px;
		border-radius: 20px;
		background-color: #fff;
	}
	
	.btn_box button.submit {
		font-size: 14px;
		background-color: #f2825b;
		color: #fff;
		margin: 0 10px;
	}
	
	.edit_box button {
		font-size: 14px;
		background-color: #007AFF;
		color: #fff;
		margin-bottom: 10px;
		padding: 4px 20px;
		border-radius: 20px;
	}
</style>
