<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>

        <div class="hui_content" id="hui-content">

		<label>
			<div class="item_box flex_bet flex_align_center">
				<input type="file" name="" id="" value="" style="display: none;" accept="image/*" ref="img_input"  @change="changeHeadImg"/>
				<div class="icon_box">
					<img src="../../assets/img/set_01.png"/>
				</div>
				<div class="text_box flex_one flex_bet flex_align_center">
					<span>更换头像</span>
					<div class="img_box flex_row flex_align_center">
						<img :src="userInfo.imageUrl"/>
						<div class="arrow_right"></div>
					</div>
				</div>
			</div>
		</label>
		<div class="item_box flex_bet flex_align_center" @click="show3 = true">
			<div class="icon_box">
				<img src="../../assets/img/set_02.png"/>
			</div>
			<div class="text_box flex_one flex_bet flex_align_center">
				<span>修改昵称</span>
				<span>{{userInfo.firstName}}</span>
			</div>
		</div>
	<!--	<div class="item_box flex_bet flex_align_center">
			<div class="icon_box">
				<img src="../../assets/img/4_06.png"/>
			</div>
			<div class="text_box flex_one flex_bet flex_align_center">
				<span>推荐码</span>
				<span>{{userMess.recommendation}}</span>
			</div>
		</div>-->
		<!--<div class="item_box flex_bet flex_align_center">
			<div class="icon_box">
				<img src="../../assets/img/4_02.png"/>
			</div>
			<div class="text_box flex_one flex_bet flex_align_center">
				<span>推荐人数</span>
				<span>{{userMess.recommendationTotal | nullName}}</span>
			</div>
		</div>-->

		<div class="item_box flex_bet flex_align_center" @click="Logout">
			<div class="icon_box icon_box1">
				<img src="../../assets/img/4_08.png" alt="."/>
			</div>
			<div class="text_box flex_one flex_bet flex_align_center">
				<span>注销</span>
				<!--<span>Ryan</span>-->
			</div>
		</div>

		<div v-transfer-dom>
	      	<confirm
	      		v-model="show3"
	      		ref="confirm3"
		      	show-input
		      	:title="'修改昵称'"
		      	:input-attrs="{type: 'text'}"
		     	@on-cancel="onCancel"
		     	@on-confirm="onConfirm"
		      	@on-show="onShow">
		    </confirm>
	    </div>
		<!--<div class="btn_box">
			<button class="myback">确认修改</button>
		</div>-->


        </div>
	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
	import {uploadImage} from '../../utils/util'
	import { Confirm, TransferDomDirective as TransferDom } from 'vux'
	export default {
	  	directives: {
	    	TransferDom
	  	},
		data() {
			return {
				headOption: {title: '个人设置',backText: '',showBack: true},
				mess: {
					firstName: '',
					imageUrl: ''
				},
				show3: false
			}
		},
		filters: {
			nullName (value) {
				if (value) {
					return value;
				}else {
					return 0;
				}
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess']),

		},
		components: {
			Confirm
		},
		methods: {
			...mapActions(['getUserMess']),
			Logout () {
				localStorage.clear();
				this.$router.push({path: '/login'})
			},
			changeHeadImg () {
				let _this = this;
				let imgObj = _this.$refs.img_input.files[0];
				uploadImage("/api/uploadImage",imgObj).then((res) => {
					let imgUrl = res.target.responseText;
					_this.mess.imageUrl = imgUrl;
					_this.setUserMess();
				})
			},
			onCancel () {},
			onConfirm (val) {
       /* console.log(val);*/
				this.mess.firstName = val;
				this.setUserMess();
			},
			onShow () {
				this.$refs.confirm3.setInputValue(this.mess.firstName)
			},
			setUserMess () {
				let _this = this;
				_this.mess.id = _this.userInfo.id;
				_this.$post("/api/user/edit",_this.mess).then((res) => {
					if (res.statusCode === '0000') {
						_this.$vux.toast.text('修改成功','middle');
						console.log(res.data);
            localStorage.setItem("user",JSON.stringify(res.data));
            _this.$store.commit("setUserInfo",res.data);
					}else {
						_this.$vux.toast.text(res.msgCode,'middle')
					}
				})

			}
		},
		activated () {
			this.mess = JSON.parse(JSON.stringify(this.userMess));
		}
	}
</script>

<style scoped>
	.item_box {
		height: 1.3333rem;
		background-color: #fff;
	}
	.icon_box img {
		width: 0.64rem;
		height: 0.64rem;
		margin: 0 0.4rem;
	}
	.icon_box1 img {
		width: 0.54rem;
		height: 0.54rem;
		margin: 0 0.45rem;
	}
	.img_box img {
		width: 0.9333rem;
		height: 0.9333rem;
		border-radius: 50%;
	}
	.text_box {
		align-items: center;
		height: 100%;
		border-bottom: 1px solid #ededed;
	}
	.text_box span {
		font-size: 0.3733rem;
		color: #666;
	}
	.text_box span:nth-child(2){
		margin-right: 0.4rem;
	}
	.btn_box {
		padding: 1.3333rem 0;
		text-align: center;
	}
	.btn_box button {
		color: #fff;
		font-size: 0.3466rem;
		padding: 0 1.3333rem;
		line-height: 1.0666rem;
		border-radius: 0.8rem;
	}
</style>
