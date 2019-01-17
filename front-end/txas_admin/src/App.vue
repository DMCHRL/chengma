<template>
    <div id="app">
        <router-view />
    </div>
</template>

<script>
    export default {
        name: 'App',
        components: {},
        methods: {
            isUsdtSuccess() {
                // console.log(this.$route)
                let str = location.href.toString()
                let val = 'Result=0';
                if (str.indexOf(val) != -1) {
                    this.$alert('支付成功', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.$router.push({
                                path: "/into"
                            })
                        }
                    });
                }
            }
        },
        mounted() {
            this.isUsdtSuccess();
        }
    }
</script>

<style>

</style>
