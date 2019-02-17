<template>
	<div class="foot_section">
		<tabbar>
			<tabbar-item
			:badge="noticeNum <= 0? '': noticeNum.toString()"
			:selected="active == 0"
			link="/"
			@on-item-click="indexChange">
				<img slot="icon-active" src="../../assets/img/h_01.png">
				<img slot="icon" src="../../assets/img/h_11.png">
				<span slot="label">首页</span>
			</tabbar-item>
			<tabbar-item :selected="active == 1" link="/strategy" @on-item-click="indexChange">
				<img slot="icon-active" src="../../assets/img/h_02.png">
				<img slot="icon" src="../../assets/img/h_12.png">
				<span slot="label">策略<i class="">·</i>跟单</span>
			</tabbar-item>
			<!--<tabbar-item :selected="active == 2" link="/exchanger" @on-item-click="indexChange">
				<img slot="icon-active" src="../../assets/img/h_03.png">
				<img slot="icon" src="../../assets/img/h_13.png">
				<span slot="label">汇商超市</span>
			</tabbar-item>-->
			<tabbar-item :selected="active == 3" link="/mine" @on-item-click="indexChange">
				<img slot="icon-active" src="../../assets/img/h_04.png">
				<img slot="icon" src="../../assets/img/h_14.png">
				<span slot="label">我的</span>
			</tabbar-item>
		</tabbar>
        <!-- <div class="x_bottom"></div> -->
	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
	import { Tabbar, TabbarItem } from 'vux'
	export default {
		props: ['routerName'],
		data() {
			return {
				active: 0,
			}
		},
		computed: {
			...mapState(['noticeNum']),
		},
		watch: {
			routerName (val) {
				let _this = this;
				switch (val) {
					case 'HomePage':
						_this.active = 0
						break;
					case 'StrategyPage':
					  	_this.active = 1
					  	break;
					case 'ExchangerPage':
					  	_this.active = 2
					  	break;
					case 'MinePage':
					  	_this.active = 3;
					  	break;
					default:
					  	_this.active = 4
				}
			}
		},
		components: {
			Tabbar,
			TabbarItem
		},
		methods: {
			...mapActions(['getNoticeNum']),
			indexChange(e) {
				this.active = e;
				this.getNoticeNum();
			},
			initActive () {
				let _this = this;
				let val = _this.routerName;
				switch (val) {
					case 'HomePage':
						_this.active = 0
						break;
					case 'StrategyPage':
					  	_this.active = 1
					  	break;
					case 'ExchangerPage':
					  	_this.active = 2
					  	break;
					case 'MinePage':
					  	_this.active = 3
					  	break;
					default:
					  	_this.active = 4
				}
			},
		},
		mounted() {
			this.initActive();
			this.getNoticeNum();
		}
	}
</script>

<style scoped>
	.foot_section {
		position: fixed;
		bottom: 0;
		width: 100%;
	}
	.i_icon {
		font-size: 20px;
	}

    .x_bottom {
        width: 100%;
        height: 30px;
    }
</style>
