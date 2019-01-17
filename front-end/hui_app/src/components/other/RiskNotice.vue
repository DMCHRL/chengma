<template>
	<div v-transfer-dom>
      <popup v-model="show" position="bottom" height="100%" >
        <div class="text_box" v-html="context">
        	
        </div>
        <slot name="bottom_btn">
        	<div class="bottom_btn myback flex_bet">
	          <button class="cancel" @click="cancle">取消</button>
	          <button @click="readAndAgreen">已阅读并同意</button>
	        </div>
        </slot>
        
      </popup>
    </div>
</template>

<script>
	import { Popup,XHeader, Icon, TransferDomDirective as TransferDom } from 'vux'
	export default {
		props: ['show','context'],
		directives: {
	    	TransferDom
	  	},
		data() {
			return {
			}
		},
		components: {
			Popup
		},
		methods: {
			readAndAgreen() {
				this.$emit('ToBinding')
			},
			cancle() {
				this.$emit("cancle")
			}
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	
	.text_box {
		padding: 0.2666rem;
		padding-bottom: 55px;
	}
	.text_box h3 {
		text-align: center;
		padding: 0.5333rem 0;
		font-size: 0.5rem;
		color: #333;
		font-weight: normal;
	}
	.text_box p {
		text-indent: 2em;
		line-height: 0.6rem !important;
		color: #666 !important;
	}
	.text_box p.no_indent {
		text-indent: 0;
	}
	.bottom_btn button {
		width: 50%;
	}
	.bottom_btn button.cancel {
		background-color: #D9D9D9;
		border: 1px solid #D9D9D9;
		color: #999;
	}
</style>