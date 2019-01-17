<template>
    <div>
        <head-top></head-top>
        <div class="edit_container">
            <el-row style="margin-top: 20px;">
            	<el-col :span="20" :offset="1">
            		
            		<el-form :model="userForm" :rules="foodrules" ref="userForm" label-width="110px" class="form food_form">
            			
            			<el-form-item label="协议" prop="type">
            				<el-select v-model="userForm.type" placeholder="请选择">
            					<el-option v-for="item in departments" :key="item.value" :label="item.label" :value="item.value">
            					</el-option>
            				</el-select>
            			</el-form-item>
            			
            			<el-form-item label="名称" prop="typeDesc">
            				<el-input v-model="userForm.typeDesc"></el-input>
            			</el-form-item>
            			<el-form-item label="客服qq" prop="qq" v-if="userForm.type == 'ABOUT_US'">
            				<el-input v-model="userForm.qq"></el-input>
            			</el-form-item>
            			<el-form-item label="客服电话" prop="phone" v-if="userForm.type == 'ABOUT_US'">
            				<el-input v-model="userForm.phone"></el-input>
            			</el-form-item>
            			<el-form-item label="内容" prop="context">
            				<quill-editor v-model="userForm.context"
            					ref="myQuillEditor"
            					class="editer"
            					:options="editorOption"
            					@ready="onEditorReady($event)">
            				</quill-editor>
            			</el-form-item>
                        
                        <el-form-item>
                            <div class="submit_btn">
                        	<el-button type="primary" @click="confirm('userForm')">提交</el-button>
                            </div>
                        </el-form-item>
            		</el-form>
            		
            	</el-col>
            </el-row>
        	
        </div>
        
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import { quillEditor } from 'vue-quill-editor'
    import { addAgree,getAgreeByType } from '@/api/getData'
    export default {
        data(){
            return {
			    editorOption: {
			        
		        },
                userForm: {
                	typeDesc: '',//	是	string	名称
                    type: '',//	是	string	ABOUT_US(用户协议) ABOUT_RISK(风险协议)
                    qq: '',//	否	string	qq号
                    phone: '',//	否	string	手机号
                    context: '',
                },
                departments: [
                	{value: 'ABOUT_US',label: '关于我们'},
                	{value: 'ABOUT_RISK',label: '领航方舟合伙协议'},
                ],
                foodrules: {
                	typeDesc: [{
                		required: true,
                		message: '请输入名称',
                		trigger: 'blur'
                	},],
                	type: [{
                		required: true,
                		message: '请选择协议类型',
                		trigger: 'blur'
                	},],
                    context: [{
                    	required: true,
                    	message: '请填写协议内容',
                    	trigger: 'blur'
                    },],
                },
            }
        },
    	components: {
    		headTop,
    		quillEditor,
    	},
        computed: {
          	editor() {
	        	return this.$refs.myQuillEditor.quill
	      	},
            type () {
                return this.$route.query.type
            }
        },
        activated () {
            if (this.type) {
                this.initData();
            }else {
                this.userForm = {
                	typeDesc: '',//	是	string	名称
                	type: '',//	是	string	ABOUT_US(用户协议) ABOUT_RISK(风险协议)
                	qq: '',//	否	string	qq号
                	phone: '',//	否	string	手机号
                	context: '',
                }
            }
        },
        methods: {
            async initData() {
            	try{
                    const res = await getAgreeByType(this.type);
                    console.log(res)
                    if (res.statusCode == '0000') {
                        this.userForm = res.data;
                    }else{
                        throw new Error('获取数据失败');
                    }
            	}catch(err){
            			console.log('获取数据失败', err);
            	}
            },
		    onEditorReady(editor) {
		        console.log('editor ready!', editor)
		    },
		    confirm(userForm) {
                this.$refs[userForm].validate(async (valid) => {
                	if (valid) {
                		const params = {
                			...this.userForm
                		}
                		try {
                			const result = await addAgree(params);
                				
                			if (result.statusCode == '0000') {
                				this.$message({
                					type: 'success',
                					message: '提交成功'
                				});
                				this.userForm = {
                					typeDesc: '',//	是	string	名称
                					type: '',//	是	string	ABOUT_US(用户协议) ABOUT_RISK(风险协议)
                					qq: '',//	否	string	qq号
                					phone: '',//	否	string	手机号
                					context: '',
                				}
                			} else {
                				this.$message({
                					type: 'error',
                					message: result.msgCode
                				});
                			}
                		} catch (err) {
                			console.log(err)
                		}
                	} else {
                		this.$notify.error({
                			title: '错误',
                			message: '请检查输入是否正确',
                			offset: 100
                		});
                		return false;
                	}
                });
            }
        },
    }
</script>

<style lang="less">
	@import '../style/mixin';
	.edit_container{
		padding: 40px;
		margin-bottom: 40px;
	}
	.editer{
		height: 350px;
	}
	.submit_btn{
        margin-top: 60px;
		text-align: center;
	}
</style>
