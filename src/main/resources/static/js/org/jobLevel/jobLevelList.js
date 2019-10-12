
    var pageList = new Vue({
    	el : '#pageList',
        data () {
            return {
            	tableMaxHeight:200,
            	listColumns: [
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '职级编号',
                        key: 'dataCode'
                    },
                    {
                        title: '职级名称',
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
                ],
                listData: []
            }
        },
		mounted : function() {
			this.tableMaxHeight=window.top.indexFrame.getFrameHeight()-this.$refs.addButton.offsetHeight;
			this.flushData();
		},
        methods: {
        	flushData:function(){
    			var self=this;
    			axios.get('/org/orgJobLevel/getOrgJobLevelInfoList').then(response => {
    				self.listData=response.data;
    			});
        	},
            show (index) {
            	var c={title:'编辑职级',url:'/html/org/jobLevel/jobLevelEdit.html?key='+index};
            	GPageModel.info(c);
            },
            add:function(){
              	var c={title:'新增职级',url:'/html/org/jobLevel/jobLevelEdit.html'};
            	GPageModel.info(c);
            },
            remove:function(index) {
        		this.$Modal.confirm({
					title:'',
					content:'删除后数据将无法恢复，是否继续？',
					onOk:function(){
						var self=this;
						self.$Spin.show();
		    			axios.get('/org/orgJobLevel/deleteOrgJobLevelInfo?key='+index).then(response => {
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