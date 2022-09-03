(window.webpackJsonp=window.webpackJsonp||[]).push([[1],{197:function(e,t,n){"use strict";n.r(t);var i=n(504),a=n(431);for(var r in a)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return a[e]}))}(r);var o=n(0),l=Object(o.a)(a.default,i.a,i.b,!1,null,null,null);t.default=l.exports},424:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i,a=n(30),r=(i=a)&&i.__esModule?i:{default:i};t.default={addOrganization:function(e){return(0,r.default)({url:"/organization/insertOrganizationInfo",method:"post",data:e})},modifyOrganization:function(e){return(0,r.default)({url:"/organization/updateExplainById",method:"post",data:e})},modifyOrganizationOwner:function(e){return(0,r.default)({url:"/organization/insertOrganizationInfo",method:"post",data:e})},queryOrganization:function(e){return(0,r.default)({url:"/organization/queryOrganizationInfoByOiId",method:"post",data:e})},getOrganizationList:function(e){return(0,r.default)({url:"/organization/queryOrganizationInfoByForm",method:"post",data:e})},querySubOrganization:function(e){return(0,r.default)({url:"/organization/queryOrganizationInfoByTypeAndSuperior",method:"post",data:e})},addSubOrganization:function(e){return(0,r.default)({url:"/organization/insertOrganizationInfo",method:"post",data:e})},userOrganization:function(e){return(0,r.default)({url:"/organization/queryOrganizationInfoByUserId",method:"post",data:e})},addOrganizationMember:function(e){return(0,r.default)({url:"/organization/insertOrganizationUserPower",method:"get"})},deleteOrganizationMember:function(e){return(0,r.default)({url:"/organization/updateUserPowerByOpId",method:"get",data:e})},modifyOrganizationAuthority:function(e){return(0,r.default)({url:"/organization/updateOrganizationPowerStatusByOpId",method:"get",data:e})},queryOrganizationMember:function(e){return(0,r.default)({url:"/organizationPower/queryOrganizationPowerByOiId",method:"post",data:e})}}},431:function(e,t,n){"use strict";n.r(t);var i=n(432),a=n.n(i);for(var r in i)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return i[e]}))}(r);t.default=a.a},432:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=d(n(6)),a=d(n(20)),r=d(n(487)),o=d(n(193)),l=d(n(491)),u=d(n(194)),s=d(n(424));function d(e){return e&&e.__esModule?e:{default:e}}t.default={name:"Profile",components:{UserCard:r.default,SimplePopup:o.default,DialogAuth:l.default},data:function(){var e=this;return{user:{},activeTab:"activity",tableInfo:{data:[],rowHandle:{fixed:"right",width:"180px",custom:[{text:"修改",type:"warning",size:"mini",emit:"modify"},{text:"查看详情",type:"primary",size:"mini",emit:"view"}]},columns:[{title:"组织id",key:"oiId",minWidth:"50"},{title:"组织名",key:"oiName",minWidth:"100"},{title:"组织英文名",key:"oiEnglishName",minWidth:"100"},{title:"组织类型",key:"oiType",minWidth:"100"},{title:"组织说明",key:"oiExplain",minWidth:"150"},{title:"创建人名",key:"oiCreaterName",minWidth:"100"},{title:"拥有人名",key:"oiOwnerName",minWidth:"100"},{title:"创建时间",key:"createTime",minWidth:"180"},{title:"修改时间",key:"updateTime",minWidth:"180"}],events:{modify:function(t){e.$refs.editSimplePopup.handleCreate(JSON.parse((0,a.default)(t.row)))},view:function(t){e.$router.push({path:"/team/detail",query:{oiId:t.row.oiId,oiName:t.row.oiName,oiType:t.row.oiType}})}},rowClick:function(){console.log("点击了行")},pagination:{currentPage:1,pageSize:5,total:21}},popupInfo:{drawer:{attrs:{title:"添加",direction:"rtl"},events:{beforeClose:function(e){console.log("关闭弹框")}}},form:{width:"100px",items:[{props:"oiId",type:"input",label:"组织id",defaultValue:"0",attrs:{placeholder:"如果创建空间，默认为0"},rules:[{required:!0,message:"请输入组织id",trigger:"blur"}]},{props:"oiName",type:"input",label:"组织名",defaultValue:"",attrs:{placeholder:"请填写组织名称"},rules:[{required:!0,message:"请输入组织名称",trigger:"blur"}]},{props:"oiEnglishName",type:"input",label:"组织英文名",defaultValue:"",attrs:{placeholder:"请填写组织英文名"},rules:[{required:!0,message:"请输入组织英文名",trigger:"blur"}]},{props:"oiType",type:"select",label:"组织类型",defaultValue:"TEAM",attrs:{placeholder:"请选择组织类型",disabled:!0},options:[{label:"测试",value:"CODE_EXAMPLE"},{label:"系统默认",value:"SYSTEM_DEFAULT"},{label:"默认",value:"DEFAULT"},{label:"空间",value:"SPACE"},{label:"部门",value:"DEPARTMENT"},{label:"团队",value:"TEAM"},{label:"应用",value:"APPLICATION"},{label:"实例",value:"INTERFACE"},{label:"接口",value:"EXAMPLE"},{label:"实例接口",value:"INTERFACE_EXAMPLE"}],rules:[{required:!0,message:"请输入组织类型",trigger:"blur"}]},{props:"oiExplain",type:"input",label:"组织说明",defaultValue:"",attrs:{placeholder:"请填写组织说明"},rules:[{required:!0,message:"请输入组织说明",trigger:"blur"}]},{props:"ioLabel",type:"input",label:"组织标签",defaultValue:"",attrs:{placeholder:"请输入组织标签"},rules:[{message:"请输入组织标签",trigger:"blur"}]}]},events:{confirm:function(t){console.log("点击确定按钮==>",t),e.addOrganization(t)},cancel:function(){e.$refs.simplePopup.resetForm(),e.$refs.simplePopup.handleClose(),console.log("点击取消按钮")}}},editPopupInfo:null}},created:function(){var e=this;this.editPopupInfo={drawer:(0,i.default)({},this.popupInfo.drawer,{attrs:{title:"修改",direction:"rtl"}}),form:{width:"100px",items:[{props:"oiId",type:"input",label:"组织id",defaultValue:"111",attrs:{placeholder:"如果创建空间，默认为0",disabled:!0},rules:[{required:!0,message:"请输入组织id",trigger:"blur"}]},{props:"oiName",type:"input",label:"组织名",defaultValue:"",attrs:{placeholder:"请填写组织名称",disabled:!0},rules:[{required:!0,message:"请输入组织名称",trigger:"blur"}]},{props:"oiEnglishName",type:"input",label:"组织英文名",defaultValue:"",attrs:{placeholder:"请填写组织英文名",disabled:!0},rules:[{required:!0,message:"请输入组织英文名",trigger:"blur"}]},{props:"oiType",type:"select",label:"组织类型",defaultValue:"",attrs:{placeholder:"请选择组织类型",disabled:!0},options:[{label:"测试",value:"CODE_EXAMPLE"},{label:"系统默认",value:"SYSTEM_DEFAULT"},{label:"默认",value:"DEFAULT"},{label:"空间",value:"SPACE"},{label:"部门",value:"DEPARTMENT"},{label:"团队",value:"TEAM"},{label:"应用",value:"APPLICATION"},{label:"实例",value:"INTERFACE"},{label:"接口",value:"EXAMPLE"},{label:"实例接口",value:"INTERFACE_EXAMPLE"}],rules:[{required:!0,message:"请输入组织类型",trigger:"blur"}]},{props:"oiExplain",type:"input",label:"组织说明",defaultValue:"",attrs:{placeholder:"请填写组织说明"},rules:[{required:!0,message:"请输入组织说明",trigger:"blur"}]},{props:"ioLabel",type:"input",label:"组织标签",defaultValue:"",attrs:{placeholder:"请输入组织标签",disabled:!0},rules:[{message:"请输入组织标签",trigger:"blur"}]}]},events:{confirm:function(t){console.log("修改点击确定按钮==>",t),e.modifyOrganization(t)},cancel:function(){e.$refs.editSimplePopup.handleClose()}}},this.getUserInfo(),this.getUserOrganization()},methods:{confirmAuth:function(e){console.log(),console.log("主界面接受回调==>",e)},closeAuth:function(){console.log("主界面接受cancel回调")},getUserInfo:function(){var e=this;u.default.userInfo({uiId:this.$store.getters.userId}).then((function(t){e.user=t.data||{}})).catch((function(e){console.log("获取用户信息失败==>",e)}))},getUserOrganization:function(){var e=this;s.default.userOrganization({uiId:this.$store.getters.userId}).then((function(t){console.log("用户所属的团队组织列表==>",t),e.tableInfo.data=t.data||[]})).catch((function(e){console.log("获取团队组织列表失败==>",e)}))},addOrganization:function(e){var t=this;s.default.addOrganization(e).then((function(e){console.log("添加组织成功==>",e),t.$refs.SimplePopup.handleClose(),t.getUserOrganization()})).catch((function(e){console.log("添加组织失败==>",e)}))},modifyOrganization:function(e){var t=this;s.default.modifyOrganization(e).then((function(e){console.log("修改组织信息成功==>",e),t.$refs.editSimplePopup.handleClose(),t.getUserOrganization()})).catch((function(e){console.log("修改组织信息失败==>",e)}))},paginationCurrentChange:function(e){this.tableInfo.pagination.currentPage=e,console.log(e)}}}},433:function(e,t,n){"use strict";n.r(t);var i=n(434),a=n.n(i);for(var r in i)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return i[e]}))}(r);t.default=a.a},434:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=l(n(6)),a=l(n(488)),r=l(n(193)),o=l(n(194));function l(e){return e&&e.__esModule?e:{default:e}}t.default={components:{PanThumb:a.default,SimplePopup:r.default},props:{user:{type:Object,default:function(){return{name:"我想喝娃哈哈",email:"226337822121",avatar:"https://img1.baidu.com/it/u=725867585,4256543316&fm=26&fmt=auto&gp=0.jpg",role:"Admin"}}}},data:function(){var e=this;return{popupInfo:{drawer:{attrs:{title:"修改用户信息",direction:"rtl"},events:{beforeClose:function(e){console.log("关闭弹框")}}},form:{width:"120px",items:[{props:"newPassword",type:"input",label:"新密码",defaultValue:"",attrs:{placeholder:"请输入新的密码",showPassword:!0},rules:[{required:!0,message:"请输入账号密码",trigger:"blur"}]},{props:"newTwoPassword",type:"input",label:"请确认密码",defaultValue:"",attrs:{placeholder:"请再次输入新密码",showPassword:!0},rules:[{required:!0,message:"请再次输入新密码",trigger:"blur"}]}]},events:{confirm:function(t){console.log("点击确定按钮==>",t),o.default.manageModifyUserPassword((0,i.default)({},t)).then((function(t){console.log("修改用户密码成功==>",t),e.$refs.simplePopup.handleClose(),e.$emit("refresh")})).catch((function(e){console.log("修改用户密码失败==>",e)}))},cancel:function(){e.$refs.simplePopup.handleClose(),console.log("点击取消按钮")}}}}}}},435:function(e,t,n){"use strict";n.r(t);var i=n(436),a=n.n(i);for(var r in i)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return i[e]}))}(r);t.default=a.a},436:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"PanThumb",props:{image:{type:String,default:"https://img1.baidu.com/it/u=725867585,4256543316&fm=26&fmt=auto&gp=0.jpg"},zIndex:{type:Number,default:1},width:{type:String,default:"150px"},height:{type:String,default:"150px"}}}},437:function(e,t,n){},438:function(e,t,n){},439:function(e,t,n){"use strict";n.r(t);var i=n(440),a=n.n(i);for(var r in i)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return i[e]}))}(r);t.default=a.a},440:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i,a=n(193),r=(i=a)&&i.__esModule?i:{default:i};t.default={name:"Auth",components:{SimplePopup:r.default},props:{list:{type:Array,default:function(){return[{id:1,desc:"权限1"},{id:2,desc:"权限2"},{id:3,desc:"权限3",disabled:!0},{id:4,desc:"权限4"},{id:5,desc:"权限5"}]}}},computed:{popupInfo:function(){var e=this;return{drawer:{attrs:{title:"设置权限",direction:"rtl",size:"500"},events:{beforeClose:function(e){console.log("关闭弹框")}}},form:{width:"0px",items:[{props:"has_auth_list",type:"transfer",label:"",defaultValue:[],attrs:{props:{key:"id",label:"desc"},titles:["未拥有权限","已有权限"],buttonTexts:["移除","添加"],data:this.list}}]},events:{confirm:function(t){console.log("点击确定按钮==>",t),e.$emit("confirm",t)},cancel:function(){console.log("点击取消按钮"),e.$refs.SimplePopup.handleClose(),e.$emit("cancel")}}}}},methods:{handleCreate:function(){arguments.length>0&&void 0!==arguments[0]&&arguments[0];this.$refs.SimplePopup.handleCreate()}}}},487:function(e,t,n){"use strict";n.r(t);var i=n(518),a=n(433);for(var r in a)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return a[e]}))}(r);n(490);var o=n(0),l=Object(o.a)(a.default,i.a,i.b,!1,null,"5466fe10",null);t.default=l.exports},488:function(e,t,n){"use strict";n.r(t);var i=n(526),a=n(435);for(var r in a)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return a[e]}))}(r);n(489);var o=n(0),l=Object(o.a)(a.default,i.a,i.b,!1,null,"67700bf8",null);t.default=l.exports},489:function(e,t,n){"use strict";var i=n(437);n.n(i).a},490:function(e,t,n){"use strict";var i=n(438);n.n(i).a},491:function(e,t,n){"use strict";n.r(t);var i=n(519),a=n(439);for(var r in a)["default"].indexOf(r)<0&&function(e){n.d(t,e,(function(){return a[e]}))}(r);var o=n(0),l=Object(o.a)(a.default,i.a,i.b,!1,null,null,null);t.default=l.exports},504:function(e,t,n){"use strict";n.d(t,"a",(function(){return i})),n.d(t,"b",(function(){return a}));var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[e.user?n("div",[n("user-card",{attrs:{user:e.user},on:{refresh:e.getUserInfo}}),e._v(" "),n("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-plus"},on:{click:function(){e.$actionBind({id:1,type:"SPACE"})}}},[e._v("新增")]),e._v(" "),n("div",{staticClass:"bg-white mod mt-20"},[n("div",{staticClass:"title"},[e._v("团队组织列表")]),e._v(" "),n("d2-crud",e._g(e._b({on:{"pagination-current-change":e.paginationCurrentChange}},"d2-crud",e.tableInfo,!1),e.tableInfo.events))],1)],1):e._e(),e._v(" "),n("simple-popup",e._g({ref:"simplePopup",attrs:{settings:e.popupInfo}},e.popupInfo.events)),e._v(" "),e.editPopupInfo?n("simple-popup",e._g({ref:"editSimplePopup",attrs:{settings:e.editPopupInfo}},e.editPopupInfo.events)):e._e(),e._v(" "),n("dialog-auth",{ref:"dialogAuth",on:{confirm:e.confirmAuth,cancel:e.closeAuth}})],1)},a=[]},518:function(e,t,n){"use strict";n.d(t,"a",(function(){return i})),n.d(t,"b",(function(){return a}));var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"mod bg-white",staticStyle:{"margin-bottom":"20px"}},[n("div",{staticClass:"aic",staticStyle:{"justify-content":"space-between","margin-top":"-10px"}},[n("span",{staticClass:"title"},[e._v("个人信息")]),e._v(" "),n("el-button",{attrs:{type:"text"},on:{click:function(){e.$refs.simplePopup.handleCreate()}}},[e._v("修改信息")])],1),e._v(" "),n("el-row",{staticStyle:{display:"flex"},attrs:{gutter:24}},[n("el-col",{staticClass:"user-profile",attrs:{span:5}},[n("div",{staticClass:"box-center"},[n("pan-thumb",{attrs:{height:"100px",width:"100px",hoverable:!1}},[n("div",[e._v("Hello")])])],1),e._v(" "),n("div",{staticClass:"box-center"},[n("div",{staticClass:"user-name text-center"},[e._v(e._s(e.user.uiName))])])]),e._v(" "),n("el-col",{attrs:{span:8}},[n("div",{staticClass:"user-bio-section-header mb-10 title"},[e._v("\n          个人介绍\n        ")]),e._v(" "),n("div",{staticClass:"text-muted aic"},[n("div",[e._v("性别")]),e._v("\n          "+e._s(e.user.uiSex)+"\n        ")]),e._v(" "),n("div",{staticClass:"text-muted aic"},[n("div",[e._v("邮箱")]),e._v("\n          "+e._s(e.user.uiEmail)+"\n        ")]),e._v(" "),n("div",{staticClass:"text-muted aic"},[n("div",[e._v("电话号码")]),e._v("\n          "+e._s(e.user.uiPhone)+"Ff\n        ")]),e._v(" "),n("div",{staticClass:"text-muted aic"},[n("div",[e._v(e._s(e.user.uiIdType))]),e._v("\n          "+e._s(e.user.uiIdCard)+"\n        ")])])],1),e._v(" "),n("simple-popup",e._g({ref:"simplePopup",attrs:{settings:e.popupInfo}},e.popupInfo.events))],1)},a=[]},519:function(e,t,n){"use strict";n.d(t,"a",(function(){return i})),n.d(t,"b",(function(){return a}));var i=function(){var e=this.$createElement;return(this._self._c||e)("simple-popup",this._g({ref:"SimplePopup",attrs:{settings:this.popupInfo}},this.popupInfo.events))},a=[]},526:function(e,t,n){"use strict";n.d(t,"a",(function(){return i})),n.d(t,"b",(function(){return a}));var i=function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"pan-item",style:{zIndex:this.zIndex,height:this.height,width:this.width}},[t("div",{staticClass:"pan-info"},[t("div",{staticClass:"pan-info-roles-container"},[this._t("default")],2)]),this._v(" "),t("div",{staticClass:"pan-thumb",style:{backgroundImage:"url("+this.image+")"}})])},a=[]}}]);