<template>
	<div class="foot_section">
		<tabbar class="tabbar_box">
			<tabbar-item :selected="active == 0"  link="/"  @on-item-click="indexChange">
				<img class="img" slot="icon-active" src="../../assets/img/f_tab_01_active.png">
				<img class="img" slot="icon" src="../../assets/img/f_tab_01.png">
				<span class="text" slot="label">融投服务</span>
			</tabbar-item>
			<tabbar-item :selected="active == 1" link="/fund" @on-item-click="indexChange">
				<img class="img" slot="icon-active" src="../../assets/img/f_tab_02_active.png">
				<img class="img" slot="icon" src="../../assets/img/f_tab_02.png">
				<span class="text" slot="label">基金入伙</span>
			</tabbar-item>
			<tabbar-item :badge="noticeNum <= 0? '': noticeNum.toString()"  :selected="active == 2" link="/mine" @on-item-click="indexChange">
				<img class="img" slot="icon-active" src="../../assets/img/f_tab_03_active.png">
				<img class="img" slot="icon" src="../../assets/img/f_tab_03.png">
				<span class="text" slot="label">我的资产</span>
			</tabbar-item>
			<!-- <tabbar-item :selected="active == 3" link="/mine" @on-item-click="indexChange">
				<img slot="icon-active" src="../../assets/img/h_04.png">
				<img slot="icon" src="../../assets/img/h_14.png">
				<span slot="label">我的</span>
			</tabbar-item> -->
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
                this.initActive(val)
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
				// this.getNoticeNum();
			},
			initActive (val) {
				let _this = this;
                if (!val) val = _this.routerName;
				switch (val) {
					case 'Home':
						_this.active = 0
						break;
					case 'JoinFund':
					  	_this.active = 1
					  	break;
					
					case 'Mine':
					  	_this.active = 2
					  	break;
					default:
					  	_this.active = 0
				}
			},
		},
		mounted() {
			this.initActive();
			// this.getNoticeNum();
		}
	}
</script>

<style scoped>
	.foot_section {
		position: fixed;
		bottom: 0;
		width: 100%;
        
	}
    .tabbar_box {
        background-color: #202a67;
    }
    .img {
        width: 0.666rem;
        height: 0.506rem;
        margin-top: 4px;
    }
    .weui-bar__item_on .text {
        color: #ea9908;
    }
    .text {
        color: #8e9ae3;
    }
</style>