var pageEdit = {
        data () {
            return {
            	editModel: {
            		name: '',
            		dataCode: ''
                },
                editRuleValidate: {
                	name: [
                        { required: true, message: '职务名称不能为空', trigger: 'blur' }
                    ],
                    dataCode: [
                        { required: true, message: '职务编号不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        mounted : function() {
			var self=this;
			var key=getQueryString(location.href,"key");
			if(key&&key!=null&&key!=''){
				axios.get('/org/orgJob/getOrgJobInfo?key='+key).then(response => {
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
				        	axios.post('/org/orgJob/saveOrgJobInfo',this.editModel).then(response => {
				        		   if(response.data.resultType=='ok'){
				          			   parent.pageList.flushData();
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