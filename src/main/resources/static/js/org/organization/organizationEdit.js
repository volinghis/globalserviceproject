var pageEdit = {
        data () {
            return {
            	editModel: {
            		name: '',
            		dataCode: ''
                },
                formDisabled:false,
                editRuleValidate: {
                	name: [
                        { required: true, message: '组织名称不能为空', trigger: 'blur' }
                    ],
                    dataCode: [
                        { required: true, message: '组织编号不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        mounted : function() {
        	var self=this;
			var key=getQueryString(location.href,"key");
			if(key&&key!=null&&key!=''){
				axios.get('/org/organization/getOrganizationInfo?key='+key).then(response => {
					self.editModel=Object.assign(self.editModel,response.data);
				});
			}
			var view=getQueryString(location.href,"view");
			if(view&&view!=null&&view!=''){
				this.formDisabled=true;
			}
		},
        methods: {
            handleSubmit () {
			      this.$refs.editForm.validate((valid) => {
				        if (valid) {
				        	
				        	var self=this;
				        	self.$Spin.show();
				        	var parentKey=getQueryString(location.href,"parentKey");
				        	if(parentKey&&parentKey!=null&&parentKey!=''){
									self.editModel.parentKey=parentKey;
							}
				        	axios.post('/org/organization/saveOrganizationInfo',self.editModel).then(response => {
				        		   if(response.data.resultType=='ok'){
				        			   parent.OrgPage.flushTreeData();
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