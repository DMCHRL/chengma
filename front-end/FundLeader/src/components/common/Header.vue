<template>
    <div class="head_sec" :class="headShow?'back':''">
        <x-header :left-options="leftOptions">{{leftOptions.title}}
            <div slot="right"><slot name="right-box"></slot></div>
        </x-header>
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
						title: '领航方舟'
					}
				}
			},

		},
		components: {
			XHeader,
		},
		data() {
			return {
                headShow: false
			}
		},
        methods: {
        	handleScroll() {
        		var scrollTop = document.getElementById('my-box').pageYOffset || document.getElementById(
        			'my-box').scrollTop || document.getElementById('my-box').scrollTop
        		// console.log(scrollTop)
        		if (scrollTop > 50) {
        			this.headShow = true;
        		} else {
        			this.headShow = false;
        		}
        	},
        },
        mounted () {
        	if (document.getElementById('my-box')) {
        		document.getElementById('my-box').addEventListener('scroll', this.handleScroll)
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
    .head_sec.back {
    	transition: all .5s;
    	background-color: #28347e;
    }
</style>