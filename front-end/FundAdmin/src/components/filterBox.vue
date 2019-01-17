<template>
    <div class="filter_container">
        <el-select v-model="datas.type" placeholder="请选择" @change="change">
        	<el-option v-for="item in departments" :key="item.value" :label="item.label" :value="item.value">
        	</el-option>
        </el-select>
    </div>
</template>

<script>
    export default {
    	data(){
    		return {
                departments: [
                	{value: '1',label: '融投页'},
                	{value: '2',label: '入伙页'},
                ],
                datas: {
                    type: '1'
                    
                }
    		}
    	},
    	created(){
    	},
    	computed: {
    	},
		methods: {
            change () {
                this.$emit('change',this.datas)
            }
		},
        mounted () {
        }
    }
</script>

<style lang="less">
	@import '../style/mixin';
	.filter_container{
		background-color: #fff;
		height: 60px;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		padding: 0 20px;
	}
	
</style>
