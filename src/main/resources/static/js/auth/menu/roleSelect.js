var RoleSelect=new Vue({
    data () {
        return {
        	tableMaxHeight:200,
        	dataList:[],
        	selection:[],
        	listColumns: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    type: 'selection',
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
                }
            ]
        }
    },
		mounted : function() {
			this.tableMaxHeight=document.body.offsetHeight-this.$refs.buttons.offsetHeight;
			axios.get('/auth/menu/findAllRolesByMenuKey?menuKey='+getQueryString(location.href,"menuKey")).then(response => {
				this.dataList=response.data;
			});
		},
    methods: {
    	selectChange:function(se){
    		this.selection=se;
    	},
        add:function(){
	       	if(this.selection.length==0){
	       		this.$Message.error({content:"请先选择角色"});
	       		return;
	       	}
	     	var mine=this;
	     	mine.$Spin.show();
	     	var param={
	     			menuKey:getQueryString(location.href,"menuKey"),
	     			roleList:this.selection
	     	};
	    	axios.post('/auth/menu/saveMenuRole',param).then(response => {
	    		   if(response.data.resultType=='ok'){
	    			   parent.RolePage.flushData();
	    			   this.$Message.success({content:response.data.message,onClose:function(){
						   GPageModel.close();
					   }});
	    		   }else{
	    			   this.$Message.error({content:response.data.message});
	    		   }
			}).catch(function(error){
				
			}).then(function () {
				mine.$Spin.hide();
			});
       	 
        },
        cancel:function(index) {
        	GPageModel.close();
        }
    },
	el:'#roleSelect'
});