<template>
	<div class="join-record">
		<div class="sec_1 sec_box box_shadow">
			<div class="flex_bet">
                <span>已入伙</span>
                <span class="num">{{data.num}}人</span>
            </div>
            <div class="flex_bet">
            	<span>募集金额</span>
            	<span class="price">{{data.total}}元</span>
            </div>
		</div>

		<div class="sec_2 sec_box box_shadow">
            <div class="flex_around">
                <span class="flex_one">用户名</span>
                <span>入伙金额</span>
                <span>入伙时间</span>
            </div>
            
            <template v-for="item in data.list">
                
                <div class="flex_around">
                    <span class="flex_one">{{item.userName}}</span>
                    <span>￥{{item.money}}</span>
                    <span>{{item.createAt}}</span>
                </div>
                
            </template>
            <p class="trip_p" v-show="!data.list.length">暂无入伙记录</p>
            
		</div>

	</div>
</template>

<script>
	export default {
		props: {
			data: {
				type: Object
			},
		},
		data() {
			return {
                isShow: true
            }
		},
		computed: {},
		components: {},
		methods: {},
		mounted() {}
	}
</script>

<style scoped>
	.sec_box {
		background-color: rgba(255, 255, 255, .9);
	}

	.sec_1 {
		padding: 0.666rem 0.533rem;
        margin-bottom: 0.2rem;
	}
    .sec_1>div:first-of-type {
        margin-bottom: 0.533rem;
    }
    .sec_1 span {
        font-size: 0.4rem;
        color: #202a67;
    }
    .sec_1 span.num {
        font-weight: bold;
    }
    .sec_1 span.price {
        color: #FF0000;
    }
    
    .sec_2 span {
        width: 40%;
        text-align: center;
        font-size: 0.346rem;
        color: #202a67;
        padding: 0.2rem 0;
    }
</style>
