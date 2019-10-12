    var Main = {
  		  data () {
			    return {
			    	currentUser:{}
			    }
			  },
				mounted : function() {
					var self=this;
					axios.get('/base/user/getCurrentUser').then(response => {
						self.currentUser=response.data;
		        	});
				}
    }

var Component = Vue.extend(Main)
new Component().$mount('#app')