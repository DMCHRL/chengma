<template>
	<div class="item_box" :class="item.type == 'FUNDIN'? 'in':''" @click="item.showBottom = !item.showBottom">
		<div class="item_top flex_bet flex_align_center">
            <div class="line_box"></div>
            <div class="flex_one mid_box">
            	<span>{{item.type | formattType}}</span>
                <span>{{item.status | formattStatus}}</span>
            </div>
            <span class="price">{{item.money}}</span>
        </div>
        <div class="item_bottom flex_wrap" v-show="item.showBottom">
            <span>申请时间：{{item.createAt}}</span>
            <span>审核时间：{{item.approveAt}}</span>
        </div>
	</div>
</template>

<script>
    import {whatStatus,whatType} from '@/utils/util'
	export default {
		name: 'FundDetailsItem',
		props: {
			item: {
				// type: Object
			},
		},
		data() {
			return {
				progressValue: 33
			}
		},
        filters: {
        	formattStatus: function(value) {
        		return whatStatus(value);
        	},
            formattType: function(value) {
            	return whatType(value);
            },
        },
		computed: {},
		components: {},
		methods: {

		},

	}
</script>

<style scoped>
	.item_box {
        background-color: #fff;
        padding: 0.133rem 0;
        margin-bottom: 0.106rem;
        box-shadow: 0 4px 4px #eeeff2;
    }
    .line_box {
        width: 0.1rem;
        height: 1.04rem;
        background-color: #ce0217;
        border-radius: 0 0.1rem 0.1rem 0;
    }
    .mid_box {
        padding-left: 0.64rem;
    }
    .mid_box span {
        font-size: 0.373rem;
        color: #666;
    }
    .mid_box span:last-of-type {
        font-size: .3rem;
        color: #666;
        margin-left: 0.533rem;
    }
    .item_top span.price {
        font-size: 0.373rem;
        color: #ce0217;
        margin: 0 0.266rem;
    }
    
    .item_box.in .line_box{
        background-color: #70d10b;
    }
    .item_box.in span.price {
        color: #70d10b;
    }
    .item_bottom {
        padding-left: 20px;
    }
    .item_bottom span{
        width: 50%;
        line-height: 0.666rem;
        font-size: 0.3rem;
        color: #999;
    }
</style>
