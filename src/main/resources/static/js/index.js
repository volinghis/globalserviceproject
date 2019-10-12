
var indexFrame = new Vue(
		{
			el : '#indexFrame',
			data : function() {
				return {
					menuData : [],
					activeTab : null,
					frameHeight : 0,
					theme : "light",
					mainTabs : [],
					hiddenMenusText : false,
					menuCollapsed : false,
					iconSize : 14,
					currentUserName:'',
					modelMenus:[],
					defaultActiveModel:'',
					portalContentUrl:'',
					portalContent:'',
					portalContentStyle:{
						height:20+'px',
						width:'100%',
						overflow:'auto'
					}
				};
			},
			mounted : function() {
				var self=this;
				self.setFrameHeight();
				axios.get('/auth/menu/findUserMenus').then(response => {
					
					self.menuData=response.data;
	        	});
				axios.get('/base/user/getCurrentUser').then(response => {
					self.currentUserName=response.data.account;
	        	});
				axios.get('/auth/menu/findModelMenus').then(response => {
					self.modelMenus=response.data;
	        		if(response.data.length>0){
	        			self.defaultActiveModel=response.data[0].name;
	        		}
	        		self.$nextTick(() => {
	        			self.$refs.modelMenuRef.updateOpened()
					    self.$refs.modelMenuRef.updateActiveName()
					})
	        		var uurl=response.data[0].url;
	        		self.portalContentUrl=uurl;
	        	});
		
				
			},
			watch : {
				'menuCollapsed' : function(val) {
					if (val) {
						this.iconSize = 24
					} else {
						this.iconSize = 14
					}
				}
			},
			computed : {
				rotateIcon : function() {
					return [ 'menu-icon',
							this.menuCollapsed ? 'rotate-icon' : '' ];
				}
				
			},
			methods : {
				loadPage:function(pageUrl){
					axios.get(pageUrl).then(response => {
						this.portalContent=response.data;

		        	})
				},
				modelSelect:function(name){
					var inm="";
					for(var i=0;i<100;i++){
						inm=inm+"<div>12312312</div>";
					}
					for(var i=0;i<100;i++){
						inm=inm+"<div>3333333</div>";
					}
					this.portalContent=inm;
					
					//spinMenuFrame.show();
//					Vue.prototype.$Spin.show({
//					    render: (h) => {
//					        return h('div', [
//					            h('Icon', {
//					                'class': 'demo-spin-icon-load',
//					                props: {
//					                    type: 'ios-loading',
//					                    size: 18
//					                }
//					            }),
//					            h('div', 'Loading')
//					        ])
//					    }
//					});
//					if(this.defaultActiveModel!=name){
//						for(var i=0;i<this.modelMenus.length;i++){
//							if(this.modelMenus[i].name==name){
//								this.defaultActiveModel=name;
//							     this.$Spin.show({
//					                    render: (h) => {
//					                        return h('.flex-container', [
//					                            h('Icon', {
//					                                'class': 'demo-spin-icon-load',
//					                                props: {
//					                                    type: 'ios-loading',
//					                                    size: 18
//					                                }
//					                            }),
//					                            h('div', 'Loading')
//					                        ])
//					                    }
//					                });
//								//								axios.get('/menu/menudata/findMenudata').then(response => {
////					        		this.menuData=response.data;
////					        	}).catch(function (error) { // 请求失败处理
////					        	});
//								break;
//							}
//						}
//					}
				},
				doLogout :function(){
					axios.get('/auth/login/doLogout').then(response => {
						   if(response.data.resultType=='ok'){
		        			   this.$Message.success({
		        				   content:response.data.message,
		        				   onClose:function(){
		        					   window.location.href="/html/login/login.html";
		        				   }
		        				   
		        			   });
		        			   
		        		   }
		        	});
				},
				toggleClick : function() {
					if (this.$refs.layoutleft.style.width == '60px') {
						this.menuCollapsed = !this.menuCollapsed;
						this.$refs.layoutleft.style.width = '220px';
						this.hiddenMenusText = !this.hiddenMenusText;
					} else {
						this.$refs.layoutleft.style.width = '60px'
						this.hiddenMenusText = !this.hiddenMenusText;
						this.menuCollapsed = !this.menuCollapsed;
					}
				},
				clickTab : function(name) {
					var vm = this;
					vm.frameHeight -= 1;
					// 解决chrome浏览器中tab切换滚动条消失的问题。
					window.setTimeout(function() {
						vm.frameHeight = vm.frameHeight + 1;
					}, 100);
				},
				// 根据名称来查找对应的菜单条目
				getMenuItem : function(m,name) {
					
					for (var sm = 0; sm < m.length; sm++) {
						if(m[sm].children!=null&&m[sm].children.length>0){
							var cc= this.getMenuItem(m[sm].children,name);
							if(cc!=null){
								return cc;
							}
						}else{
							if(m[sm].name==name){
								return m[sm];
							}
						}

					}
					

				},
				// 根据名称查找对应的Tab页。
				getTab : function(name) {
					for (var i = 0; i < this.mainTabs.length; i++) {
						if (this.mainTabs[i].name == name){
							return this.mainTabs[i];
						}
					}
					return null;// 没有找到
				},
				// 设置Tab页不可见。
				removeTab : function(name) {
					var tab = this.getTab(name);
					if (tab != null)
						tab.show = false;
					console.log("mainTabRemove, name=", name, ", label=",
							tab.label, ", url=", tab.url);
				},
				setFrameHeight : function() {
					// 调整掉一些补白的值
					var mainHeight = (document.documentElement.scrollHeight || document.body.scrollHeight);
					this.frameHeight = mainHeight - 180;
					this.portalContentStyle.height=(mainHeight - 180)+'px';
				},
				getFrameHeight:function(){
					return this.frameHeight;
				},
				// 菜单点击
				menuSelect : function(name) {
					if(this.activeTab != name){
						//this.$Message.info(name);
						var tab = this.getTab(name);
						if (tab == null) {
							
							var mi = this.getMenuItem(this.menuData,name);
							mi.show=true;
							this.mainTabs.push(mi);
							this.activeTab = name;
						} else {
							this.activeTab = name;
						}
					}

					
				},
				handleTabRemove:function(name){
					for (var i = 0; i < this.mainTabs.length; i++) {
						if (this.mainTabs[i].name ==name) {
							this.mainTabs.splice(i,1);
							if(i>0){
								this.activeTab=this.mainTabs[i-1].name;
							}else{
								this.activeTab='portalContentTab';
							}
						}
					}
					
				},
				closeTab : function(name) {
					if (name == "0") { // 关闭当前
						this.mainTabs = [];
					} else {// 关闭其它
						for (var i = 0; i < this.mainTabs.length; i++) {
							if (this.mainTabs[i].name != this.activeTab) {
								this.mainTabs[i].show = false;
							}
						}
					}
				},
				hover : function(e) {
					var ul = e.currentTarget.firstElementChild;
					if (ul.style.display == "none") {
						ul.style.display = "block"
					} else {
						ul.style.display = "none"
					}
				},
				logout : function() {
					this.$Modal.confirm({
						title : '提示',
						content : '<p>确认退出登录？</p>',
						onOk : function() {
							console.log("退出登录");
						},
						onCancel : function() {
						}
					});
				}
			}
		});
window.onresize = function() {
	indexFrame.setFrameHeight();
}