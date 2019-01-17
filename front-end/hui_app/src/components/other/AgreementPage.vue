<template>
	<div class="content_box">
        
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content" id="hui-content">
            <div class="text_box" v-html="context"></div>
        </div>
		
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '用户协议',backText: '',showBack: true},
				context: ''
			}
		},
        computed: {
        	type() {
        		return this.$route.query.type;
        	},
            name () {
                return this.$route.query.name;
            }
        },
		components: {
		},
		methods: {
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_protocol/getType/"+_this.type).then((res) => {
//					console.log(res)
					_this.context = res.data.context;
				})
			}
		},
		mounted () {
            this.headOption.title = this.$route.query.name;
			this.initPage();
		}
	}
</script>

<style scoped>
	.text_box {
		padding: 0.2666rem;
	}
</style>