<template>
	<!--联系客服-->
	<actionsheet
			v-model="show"
			:menus="menus"
			:theme="'android'"
			:close-on-clicking-mask="false"
			:close-on-clicking-menu="false"
			@on-click-menu="actionClick"
			@on-click-mask="Close"
		>
	</actionsheet>
</template>

<script>
	import { Actionsheet} from 'vux'
	export default {
		props: {
			show: {
				type: Boolean,
				default: false
			},
		},
		data() {
			return {
				menus: [],
			}
		},
		components: {
			Actionsheet
		},
		methods: {
			actionClick(item) {
				let arr = item.split(',');
				if (arr[0] == 'qq') {
					location.href = "mqqwpa://im/chat?chat_type=wpa&uin="+arr[1]+"&version=1&src_type=web&web_src=oicqzone.com"
				}else if (arr[0] == 'phone' || arr[0] == 'mobile') {
					location.href = "tel:"+arr[1]
				}else {
					return;
				}
			},
			Close () {
				this.$emit('close');
			},
			initPage () {
				this.$fetch('/api/hpp_server/findAll').then((res) => {
					if (res.data) {
						let list = res.data.list;
						let items = {};
						this.menus = [];
						list.forEach(item => {
							items = {};
							items.label = item.label+' ： '+item.text;
							items.value = item.type+','+item.text;
							this.menus.push(items)
						})
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
	
</style>