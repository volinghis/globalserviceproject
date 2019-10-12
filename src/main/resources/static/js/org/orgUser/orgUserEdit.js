var pageEdit = {
        data () {
            return {
            	editModel: {
            		name: '',
            		dataCode: ''
                },
                editRuleValidate: {
                	name: [
                        { required: true, message: '用户名称不能为空', trigger: 'blur' }
                    ],
                    dataCode: [
                        { required: true, message: '用户编号不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        mounted : function() {
			var self=this;
			var key=getQueryString(location.href,"key");
			if(key&&key!=null&&key!=''){
				axios.get('/org/orgUser/getOrgUser?key='+key).then(response => {
					self.editModel=Object.assign(self.editModel,response.data);
				});
			}

		},
        methods: {
            handleSubmit () {
			      this.$refs.editForm.validate((valid) => {
				        if (valid) {
				        	
				        	var self=this;
				        	self.$Spin.show();
				         	var orgKey=getQueryString(location.href,"orgKey");
				        	if(orgKey&&orgKey!=null&&orgKey!=''){
									self.editModel.orgKey=orgKey;
							}
				        	   axios.post('/org/orgUser/saveOrgUser',self.editModel).then(response => {
				        		   if(response.data.resultType=='ok'){
				        			   parent.orgUserPage.flushData();
				        			   this.$Message.success({content:response.data.message,onClose:function(){
		        						   GPageModel.close();
		        					   }});
		        					 
				        		   }else{
				        			   this.$Message.error({content:response.data.message});
				        		   }
			    			}).catch(function(error){
			    				
			    			}).then(function () {
			    				self.$Spin.hide();
			    			});
				        	   
				        }
				      })
    
            },
            handleReset () {
                this.$refs['editForm'].resetFields();
            }
        }
    }
var PageEdit = Vue.extend(pageEdit)
new PageEdit().$mount('#pageEdit')