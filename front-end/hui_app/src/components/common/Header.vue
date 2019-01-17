<template>
    <div class="head_sec myback" v-show="!leftOptions.ishide">
        <x-header :left-options="leftOptions">{{leftOptions.title}}</x-header>
    </div>
</template>

<script>
	import { XHeader } from 'vux'

	export default {
		props: {
			leftOptions: {
				type: Object,
				default() {
					return {
						backText: '',
						showBack: false,
						title: '汇添溢',
						ishide: false
					}
				}
			},

		},
		components: {
			XHeader,
		},
		data() {
			return {

			}
		}
	}
</script>

<style scoped>
	.head_sec {
		box-sizing: border-box;
		padding-top: 30px;
		height: 75px;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 9;
	}
</style>