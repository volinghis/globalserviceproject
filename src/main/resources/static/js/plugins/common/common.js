

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response;
  }, function (error) {
	if(error.response.status==901){
		 window.top.location.href="/html/login/login.html";
	}else if(error.response.status==500){
		window.top.Vue.prototype.$Loading.error();
		window.top.Vue.prototype.$Notice.error({
            title: '服务异常:500',
            desc: error.request.responseURL,
        });
	}else if(error.response.status==404){
		window.top.Vue.prototype.$Loading.error();
		window.top.Vue.prototype.$Notice.error({
            title: '无法访问到资源:404',
            desc: error.request.responseURL,
        });
	}else if(error.response.status==503){
		window.top.Vue.prototype.$Loading.error();
		window.top.Vue.prototype.$Notice.error({
            title: '服务器正在维护:503',
            desc: error.request.responseURL,
        });
	}else if(error.response.status==400){
		window.top.Vue.prototype.$Loading.error();
		window.top.Vue.prototype.$Notice.error({
            title: '服务器无法解析请求:400',
            desc: error.request.responseURL,
        });
	}else if(error.response.status==502){
		window.top.Vue.prototype.$Loading.error();
		window.top.Vue.prototype.$Notice.error({
            title: '服务器无响应:502',
            desc: error.request.responseURL,
        });
	}
    // 对响应错误做点什么
    return Promise.reject(error);
  });
function randomString() {
	 　　var len = 32;
	 　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
	 　var maxPos = $chars.length;
	 　　var pwd = '';
	 　for (i = 0; i < len; i++) {
	 　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	 　　}
	　　return pwd;
}
//添加请求拦截器
axios.interceptors.request.use(function (config) {
	if(config.url.indexOf("?")>0){
		config.url=config.url+"&tt="+Math.random();
	}else{
		config.url=config.url+"?tt="+Math.random();
	}
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });
var GPageModel={

		info:function(config){
			config.closable=true;
			config.title=config.title?config.title:'视窗';
			config.height= config.height?config.height:180;
			config.maskClosable=false;
			config.zIndex=config.zIndex?config.zIndex:99;
			config.mask=true;
			config.value=true;
			config.footerHide=true;
			var d=document.createElement('div');
			d.id=randomString();
			document.body.appendChild(d);
			var tt=new Vue({
				el : '#'+d.id,
				render: (h) => {
	                return h('modal', {
	                    props: config,
	                    on:{
	                    	'on-visible-change': function (result){
	                    		document.body.removeChild(document.querySelector(".v-transfer-dom"));	
	                    		return result;
	                    	}
	                    }
	                },[
	                	h('iframe', {
	                		attrs:{
		                     	frameborder: 0,
								width:'100%',
								height: config.height,
								scrolling:'auto',
								marginwidth:0,
								src:config.url
	                			}
	                	})
	                ])
	            }
			});

		},
		close:function(){
			parent.document.body.removeChild(parent.document.querySelector(".v-transfer-dom"));			
		}
}

function getQueryString(url,name){  
    var reg = new RegExp("(\\?|&)"+ name +"=([^&]*)(&|$)");  
    var r = url.substr(1).match(reg);  
    if(r!=null)return unescape(r[2]); return null;  
}  