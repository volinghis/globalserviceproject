var RolePage=new Vue({
    data () {
        return {
        	tableMaxHeight:200,
        	 queryBean:{
        		 selectedMenu:'',
        		 dataList:[]
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
                    title: '操作',
                    key: 'action',
                    width: 150,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.remove(params.row)
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
			this.tableMaxHeight=window.top.indexFrame.getFrameHeight()-this.$refs.buttons.offsetHeight;
		},
    methods: {
    	flushData:function(){
			var self=this;
			if(MenuPage){
				if(MenuPage.getSelectData()!=null){
					this.queryBean.selectedMenu=MenuPage.getSelectData().name;
				}
			}
			axios.get('/auth/menu/findMenuRoles?menuKey='+this.queryBean.selectedMenu).then(response => {
				self.queryBean.dataList=response.data;
				
			});
    	},
        add:function(){
          	 if(MenuPage.getSelectData()==null){
           		 this.$Message.error({content:"请先选择菜单"});
           		 return;
           	 }
          	var c={title:'授权角色',url:'/html/auth/menu/roleSelect.html?menuKey='+this.queryBean.selectedMenu,height:300,width:700};
        	GPageModel.info(c);
        },
        remove:function(data) {
        	var mine=this;
    		this.$Modal.confirm({
					title:'',
					content:'删除后数据将无法恢复，是否继续？',
					onOk:function(){
						mine.$Spin.show();
						var param={
				     			menuKey:mine.queryBean.selectedMenu,
				     			roleList:[data]
				     	};
		    			axios.post('/auth/menu/deleteMenuRole',param).then(response => {
		    				   if(response.data.resultType=='ok'){
		    					   RolePage.flushData();
		    					   this.$Message.success({content:response.data.message});
			        		   }else{
			        			   this.$Message.error({content:response.data.message});
			        		   }
		    			}).catch(function(error){
		    				
		    			}).then(function () {
		    				mine.$Spin.hide();
		    			});
					}
				});

        }
    },
	el:'#rolePage'
});
var MenuPage = new Vue({
		el:'#menuPage',
        data () {
            return {
            	menuTreeData:[]
            }
        },
        mounted : function() {
    		axios.post('/auth/menu/findAllMenus',{}).then(response => {
    			this.menuTreeData=response.data;
			});
		} ,
		methods: {
			treeSelect:function(data,node){
				RolePage.flushData();
			},
			getSelectData:function(){
				if(this.$refs.menuTree.getSelectedNodes()&&this.$refs.menuTree.getSelectedNodes().length>0){
					return this.$refs.menuTree.getSelectedNodes()[0];
				}else{
					return null;
				}
			}
		}
});



