<template>
	<div class="sub_btn">
		<x-button type="primary" @click.native="ClickMe" :disabled="disabled">{{text}}</x-button>
	</div>
</template>

<script>
    import { XButton } from 'vux'
	export default {
		name: 'SubBtn',
		props: {
			text: {
				type: String
			},
            disabled: {
                type: Boolean
            }
		},
		data() {
			return {
				
			}
		},
		computed: {},
		components: {
            XButton
        },
		methods: {
            ClickMe () {
                this.$emit("clickme")
            }
		},

	}
</script>

<style scoped>
	.sub_btn{
		padding: 20px 15px;
        padding-bottom: 60px;
	}
</style>
