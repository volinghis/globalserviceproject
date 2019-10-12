var Login = {
		  data () {
			    return {
			    	message:'',
			    	show:false,
			    	btnLoading:false,
			      form: {
			        userName: '',
			        password: ''
			      },
			      rules: {
			    	  userName: [
	                        { required: true, message: '用户名不能为空', trigger: 'blur' }
	                    ],
	                    password: [
	                        { required: true, message: '密码不能为空', trigger: 'blur' }
	                    ]
			      }
			    }
			  },
			  methods: {
			    handleSubmit () {
			    	var self=this;
			    	self.show=false;
			    	
			    	self.$refs.loginForm.validate((valid) => {
			        if (valid) {
			        	self.btnLoading=true;
			        	   axios.post('/auth/login/doLogin',this.form).then(response => {
			        		   if(response.data.resultType=='ok'){
			        			   window.location.href="/";
			        		   }else{
			        			   self.message=response.data.message;
			        			   self.show=true;
			        			   self.btnLoading=false;
			        		   }
			        	   }).catch(function (error) { // 请求失败处理
			        		   self.message=error;
			        		   self.show=true;
			        		   self.btnLoading=false;
			        	   });;
			        	   
			        }
			      })
			    }
			  }
}
var LoginComponent = Vue.extend(Login)
new LoginComponent().$mount('#app')