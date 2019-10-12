var orgUserPage=new Vue({
    data () {
        return {
        	tableMaxHeight:200,
        	 single: true,
        	 queryBean:{
        		 query:'',
        		 includeSubOrg:true,
        		 selectedOrg:'',
        		 page:1,
        		 size:20,
        		 totalCount:0,
        		 dataList:[]
        	 },
        	 initQueryBean:{},
        	listColumns: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '用户编号',
                    key: 'dataCode'
                },
                {
                    title: '用户名称',
                    key: 'name',
                    render: (h, params) => {
                        return h('div', [
                            h('Icon', {
                                props: {
                                    type: 'person'
                                }
                            }),
                            h('strong', params.row.name)
                        ]);
                    }
                },
               
                {
                    title: '操作',
                    key: 'action',
                    width: 150,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.show( params.row.key)
                                    }
                                }
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.remove(params.row.key)
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ]
        }
    },
		mounted : function() {
			this.initQueryBean=Object.assign(this.initQueryBean,this.queryBean);
			this.tableMaxHeight=window.top.indexFrame.getFrameHeight()-this.$refs.buttons.offsetHeight-document.querySelector(".ivu-page").offsetHeight;
			this.flushData();
		},
    methods: {
   	 changePage:function(pageNum){
   		 this.queryBean.page=pageNum;
   		 this.flushData();
   	 },
   	 doSearch:function(v){
   		 this.queryBean.query=v;
   		 this.flushData();
   	 },
   	 includeChange:function(v){
   		 this.queryBean.includeSubOrg=v;
   		 this.flushData();
   	 },
    	flushData:function(){
			var self=this;
			if(OrgPage){
				if(OrgPage.getSelectData()!=null){
					
					this.queryBean.selectedOrg=OrgPage.getSelectData().key;
				}
			}
			self.queryBean.totalCount=0;
			self.queryBean.dataList=[];
			axios.post('/org/orgUser/getOrgUserList',self.queryBean).then(response => {
				self.queryBean.totalCount=response.data.totalCount;
				self.queryBean.dataList=response.data.dataList;
				
			});
    	},
        show (index) {
        	var c={title:'编辑用户',url:'/html/org/orgUser/orgUserEdit.html?key='+index};
        	GPageModel.info(c);
        },
        add:function(){
        	
       	 if(OrgPage.getSelectData()==null){
       		
       		 this.$Message.error({content:"请先选择组织"});
       		 return;
       	 }
        	var c={title:'新增用户',url:'/html/org/orgUser/orgUserEdit.html?orgKey='+OrgPage.getSelectData().key};
        	GPageModel.info(c);
        },
        remove:function(index) {
    		this.$Modal.confirm({
					title:'',
					content:'删除后数据将无法恢复，是否继续？',
					onOk:function(){
						var self=this;
						self.$Spin.show();
		    			axios.get('/org/orgUser/deleteOrgUser?key='+index).then(response => {
		    				   if(response.data.resultType=='ok'){
		    					   orgUserPage.flushData();
		    					   this.$Message.success({content:response.data.message,onClose:function(){
		    						   
		    					   }});
		    					   
			        		   }else{
			        			   this.$Message.error({content:response.data.message});
			        		   }
		    			}).catch(function(error){
		    				
		    			}).then(function () {
		    				self.$Spin.hide();
		    			});
					}
				});

        }
    },
	el:'#orgUserPage'
});


var OrgPage = new Vue({
		el:'#orgPage',
        data () {
            return {
            	orgListData:[],
            	sData:{},
            	 buttonProps: {
                     type: 'default',
                     size: 'small',
                 }
            }
        },
        mounted : function() {
        	this.flushTreeData();
		} ,
		methods: {
			flushTreeData:function(){
			   	var self=this;
				axios.get('/org/organization/getOrgTreeData').then(response => {
					self.orgListData=response.data;
				}); 
			},
			setSelectData:function(data){
				this.sData=data;
				orgUserPage.queryBean=Object.assign(orgUserPage.queryBean,orgUserPage.initQueryBean);
				orgUserPage.flushData();
				
			},
			getSelectData:function(){
				if(this.sData&&this.sData.key){
					return this.sData;
				}else{
					return null;
				}
			},
			viewPage:function(data){
				var c={title:'查看组织',url:'/html/org/organization/organizationEdit.html?view=v&key='+data.key};
            	GPageModel.info(c);
			},
			editPage:function(data){
		      	var c={title:'编辑组织',url:'/html/org/organization/organizationEdit.html?key='+data.key};
            	GPageModel.info(c);
			},
			addPage:function(data){
			 	var c={title:'新增组织',url:'/html/org/organization/organizationEdit.html?parentKey='+data.key};
            	GPageModel.info(c);
			},
			remove:function(data){
        		this.$Modal.confirm({
					title:'',
					content:'删除后数据将无法恢复，是否继续？',
					onOk:function(){
						var self=this;
						self.$Spin.show();
		    			axios.get('/org/organization/deleteOrganizationInfo?key='+data.key).then(response => {
		    				   if(response.data.resultType=='ok'){
		    					   OrgPage.flushTreeData();
				        		   this.$Message.success({content:response.data.message});
			        		   }else{
			        			   this.$Message.error({content:response.data.message});
			        		   }
		    			}).catch(function(error){
		    				
		    			}).then(function () {
		    				self.$Spin.hide();
		    			});
					}
				});
			},
			btnAppend:function(h,data){
				var btnArray=[];
				var viewBtn=  h('Button', {
                    props: Object.assign({}, this.buttonProps, {
                        icon: 'md-text'
                    }), style: {
                        marginRight: '4px'
                    },
                    on: {
                        click: () => {this.viewPage(data)  }
                    }
                });
				btnArray.push(viewBtn);
				var editBtn=h('Button', {
                    props: Object.assign({}, this.buttonProps, {
                        icon: 'md-create'
                    }),
                    style: {
                        marginRight: '4px'
                    },
                    on: {
                        click: () => { this.editPage(data) }
                    }
                });
				btnArray.push(editBtn);
				if(data.parentKey==null||data.parentKey==''){
					btnArray.push(h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'md-add',
                            type: 'primary'
                        }),
                        style: {
                            width: '60px'
                        },
                        on: {
                            click: () => { this.addPage(data) }
                        }
                    }));
					return btnArray;
				}else{
					btnArray.push(h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'md-add'
                        }),
                        style: {
                            marginRight: '4px'
                        },
                        on: {
                            click: () => {  this.addPage(data)}
                        }
                    }));
					btnArray.push(    h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'md-remove'
                        }),
                        on: {
                            click: () => { this.remove(data) }
                        }
                    }));
					return btnArray;
				}

			},
            renderContent (h, { root, node, data }) {
                return h('span',{
                	style: {	cursor: 'pointer'}
           
                } ,[
                    h('span', [
                        h('Icon', {
                            props: {
                                type: (data.children!=null&&data.children.length>0)?'ios-folder-outline':'ios-paper-outline',
                                color:'rgb(135, 208, 104)',
                                size:18
                            },
                            style: {
                                marginRight: '8px'
                            }
                        }),
                        h('span', {     	on:{
                    		click:function(e){
                    			OrgPage.setSelectData(data);
                    			var secs=document.querySelector("[name='secs']");
                    			if(secs){
                    				secs.style.backgroundColor="white";
                    				secs.removeAttribute("name");
                    			}
                    			e.target.setAttribute("name","secs");
                    			e.target.style.backgroundColor="rgb(219,218,217)";
                    		}
                    	}},data.title)
                    ]),

                    h('span', {
                        style: {
                        	display:'inline-block',
                            float: 'right',
                            marginRight: '10px'
                        }
                    }, this.btnAppend(h,data))

                ]);
            }
		}
    });



