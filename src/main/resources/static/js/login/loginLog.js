var pageList = new Vue({
 	el : '#pageList',
     data () {
         return {
         	tableMaxHeight:200,
         	 single: true,
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
                     title: '用户账号',
                     key: 'account',
                     align: 'center'
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
                     },
                     align: 'center'
                 },
                 {
                	 title:'登录IP',
                	 key:'ip',
                	 align: 'center'
                 },
                 {
                	 title:'登录时间',
                	 key:'time',
                	 align: 'center'
                 }
             ],
             loginLogs:{
            	 totalCount:0,
            	 dataList:[]
             }
         }
     },
     mounted : function() {
    	 this.tableMaxHeight=window.top.indexFrame.getFrameHeight()-this.$refs.offsetHeight-document.querySelector(".ivu-page").offsetHeight;
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
 			 var selfLog=this;
 			 axios.post('/auth/login/loginLog',this.queryBean).then(response => {
 				 selfLog.loginLogs=response.data;
 			 });
     	 },
      }
});