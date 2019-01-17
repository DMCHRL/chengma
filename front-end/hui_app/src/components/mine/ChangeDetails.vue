<template>
    <!-- 积分明细 -->
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		
        <div class="hui_content" id="hui-content">
            
		<div class="list_body">
			<template v-for="item in list">
				<!-- <router-link :to="'/vlist?id='+ item.id"> -->
					
					<div class="item_box flex_bet" @click="pushLink(item)">
						<div class="img_box ">
							<img src="../../assets/img/9_04.png"/>
						</div>
						<div class="text_box flex_bet flex_one">
							<div>
								<p>{{item.type | typeOfName}}</p>
								<p>{{item.createTime}}</p>
							</div>
							<div>
								<span v-text="item.state == 'OUT'?'积分购买':'奖励积分'"></span>
							</div>
							<div class="text_right">
								<span :class="item.state"><i v-text="item.state == 'OUT'?'-':'+'"></i> {{item.score}}</span>
							</div>
						</div>
					</div>
				<!-- </router-link> -->
				
			</template>
			
            <p class="trip_p">暂无更多记录~~</p>
            
		</div>
        
        </div>
        
	</div>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		data() {
			return {
				headOption: {title: '积分明细',backText: '',showBack: true},
				list: []
			}
		},
		filters: {
			typeOfName: function(value) {
				switch (value){
					case 'VIDEO':  return '交易战法视频';
					case 'COURSE': return '交易面对面课程';
					case "EXAM": return '交易师考证' ;
					case "COMMUNITY": return '分享朋友圈赠送' ;
					case "LOGIN": return '登陆积分赠送' ;
					case "FRIEND": return '分享好友赠送' ;
					case "SYSTEM": return '系统赠送' ;
					default: return '系统赠送' ;
				}
			}
		},
		computed: {
			...mapState(['userInfo']),
		},
		components: {
		},
		methods: {
			pushLink (item) {
				// console.log(item)
				let _this = this;
				switch (item.type){
					case 'video':
						_this.$router.push({path: '/vlist?id='+ item.id})
						break;
					case 'course':
						_this.$router.push({path: '/tdetails?id='+ item.id})
						break;
					case 'exam':
						_this.$router.push({path: '/examdetails?id='+ item.id})
						break;
					default:
						break;
				}
			},
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_integral/findDetailByMobile/"+_this.userInfo.mobile).then((res) => {
					// console.log(res)
					if (res.data) {
						_this.list = res.data.list;
					}
				})
			}
		},
		activated () {
			this.initPage();
		}
	}
</script>

<style scoped>
	.content_box {
		background-color: #fff;
		min-height: 100%;
	}
	.list_body {
		padding: 0 0.32rem;
	}
	.item_box {
		position: relative;
		padding: 0.4rem 0;
		border-bottom: 1px solid #ccc;
	}
	.item_box img {
		width: 0.8rem;
		height: 0.8rem;
		min-height: 0.8rem;
		margin-right: 0.2666rem;
	}
	.item_box p {
		font-size: 0.3rem;
		color: #333;
	}
	.item_box h3 {
		font-size: 0.4rem;
		color: #333;
	}
	.text_box>span {
		font-size: 0.3466rem;
		color: #e51434;
	}
	.text_right {
		width: 1.5rem;
		text-align: right;
	}
	.text_right span {
		color: #28c76f;
		font-size: 0.3466rem;
	}
	.text_right span.OUT{
		color: #FF5053;
	}
	
	
</style>