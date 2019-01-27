var vue = new Vue({
    el:"#app",
    data:{
        userList:[],
        user:{},
        selectList:[],
        keyword:"",
        currentPage:0,
        pageSize:5
    },

    methods:{
        findAll:function () {
            axios.get('../user/findAll').then(function (value) {
                vue.userList = value.data;
            }).catch(function (reason) {
                alert("查找失败")
            })
        },

        findById:function (id) {
            axios.get('/user/findById/'+id).then(function (value) {
                vue.user = value.data;
            }).catch(function (reason) {
                alert("初始化失败")
            })
        },
        
        save:function () {
            axios.post('../user/update',vue.user).then(function (value) {
                vue.findAll();
                vue.user={};
            }).catch(function (reason) {
                alert("修改失败")
            })
        },

        //写一个方法,增加勾选框
        addSelect:function ($event,id) {
            if ($event.target.checked) {
                this.selectList.push(id)
            }else {
                this.selectList.splice(this.selectList.indexOf(id),1)
            }
        },

        //写一个删除的方法
        deleteUser:function () {
            if (vue.selectList.length==0){
                alert("请选择需要删除项");
                return;
            }
            if (confirm("是否需要删除,数据删除之后将无法恢复!!!")){
                axios.post('../user/delete',vue.selectList).then(function (value) {
                    //删除成功
                    vue.findAll();
                    vue.selectList=[];
                }).catch(function (reason) {
                    alert("删除失败")
                })
            }
        },

        //写一个方法   点击新建之后清除之前的信息
        cleanUser:function () {
            this.user = {}
        },

        // 编写一个方法,查询
        searchAny:function (e) {
            var keyCode = e.keyCode;
            if (keyCode==13){
                if (vue.keyword==""){
                    //如果关键字是空,则查询所有
                    location.reload();
                }
                //根据名字的关键字查询
                axios.get('../user/searchAny/'+vue.keyword).then(function (value) {
                    //成功
                    vue.userList = value.data;
                }).catch(function (reason) {
                    //失败
                    alert("查询失败")
                })
            }
        },

        //写一个分页查询的方法
        findPage:function () {
            axios.get('../user/findPage/'+this.currentPage+"/"+this.pageSize).then(function (value) {
                //成功
                vue.userList = value.data.users;
                vue.totalPages = value.data.totalPages;
                vue.totalElements = value.data.totalElements;
            }).catch(function (reason) {
                //失败
                alert("查询失败")
            })
        },

        //写一个方法    跳转上一页下一页
        checkCurrentPage:function (num) {
            num = parseInt(num);
            if (num==-1&&this.currentPage==0){
                alert("已经是第一页");
                return;
            }
            if (num==1&&this.currentPage==vue.totalPages-1){
                alert("已经是最后一页");
                return;
            }
            this.currentPage = this.currentPage + num;
            this.findPage();
        },



    },

    created:function () {
      this.findPage();
    }
	
});