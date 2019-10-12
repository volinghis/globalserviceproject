
    var pageList = new Vue({
    	el : '#pageList',
        data () {
            return {
            	tableMaxHeight:200,
            	queryBean:{
            		 query:'',
            		 page:1,
            		 size:20
            	 },
            	listColumns: [
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '角色编号',
                        key: 'dataCode'
                    },
                    {
                        title: '角色名称',
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
                        title: '备注',
                        key: 'remark'
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
                ],
                roles:{
               	 totalCount:0,
               	 dataList:[]
                }
            }
        },
		mounted : function() {
			this.tableMaxHeight=window.top.indexFrame.getFrameHeight()-this.$refs.addButton.offsetHeight;
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
        	flushData:function(){
    			var s=this;
    			axios.post('/auth/role/getRoleInfoList',this.queryBean).then(response => {
    				s.roles=response.data;
    			});
        	},
        	
            show (index) {
               	var c={title:'编辑角色',url:'/html/auth/role/roleEdit.html?key='+index,height:230};
            	GPageModel.info(c);
            },
            add:function(){
            	var c={title:'新增角色',url:'/html/auth/role/roleEdit.html',height:230};
            	GPageModel.info(c);
    		
            },
            remove:function(index) {
        		this.$Modal.confirm({
					title:'',
					content:'删除后数据将无法恢复，是否继续？',
					onOk:function(){
						var self=this;
						self.$Spin.show();
		    			axios.get('/auth/role/deleteRoleInfo?key='+index).then(response => {
		    	    		
		    				   if(response.data.resultType=='ok'){
		    					   pageList.flushData();
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

            }
        }
    });