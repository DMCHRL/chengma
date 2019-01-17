<template>
	<div class="money_input">
        <sup>￥</sup>
        <div class="input_box">
            <input type="number" v-model="value" @focus="inputFocus" @blur="inputBlur"/>
            <span>{{value | format}} <i v-show="isInput">|</i></span>
        </div>
	</div>
</template>

<script>
	export default {
		name: 'MoneyInput',
		props: {
			money: {
				type: Number
			}
		},
		data() {
			return {
				value: '',
                isInput: false,
			}
		},
        filters: {
        	format: function(s) {
        		if (/[^0-9\.]/.test(s))
        			return "";
        		if (s == null || s == "null" || s == "")
        			return "";
        		s = s.toString().replace(/^(\d*)$/, "$1.");
        		s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
        		s = s.replace(".", ",");
        		var re = /(\d)(\d{3},)/;
        		while (re.test(s))
        			s = s.replace(re, "$1,$2");
        		s = s.replace(/,(\d\d)$/, ".$1");
        		var a = s.split(".");
        		if (a[1] == "00") {
        			s = a[0];
        		}
        		return s;
        	}
        },
        watch: {
        	money(newValue, oldValue) {
                this.value = newValue
        	},
            value (newValue, oldValue) {
                this.$emit("change",this.value)
            }
        },
		computed: {},
		components: {},
		methods: {
            inputFocus () {
                this.isInput = true;
            },
            inputBlur () {
                this.isInput = false;
            },
            
		},

	}
</script>

<style scoped>
    .money_input {
        width: 100%;
        position: relative;
        padding-right: 0.266rem;
        padding-top: 15px;
    }
    .money_input sup {
        position: absolute;
        left: 0;
        top: 0;
        font-size: 0.466rem;
        color: #202a67;
        z-index: 9;
    }
    .input_box {
        position: relative;
        width: 100%;
        height: 40px;
    }
	.input_box input,
    .input_box span{
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        width: 100%;
        font-size: 0.68rem;
        color: #202a67;
        border-bottom: 1px solid #202A67;
        padding: 0 18px;
        box-sizing: border-box;
	}
    .input_box input {
        z-index: 4;
        opacity: 0;
        
    }
    .input_box span {
        z-index: 3;
    }
</style>
