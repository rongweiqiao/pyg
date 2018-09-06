 //控制层 
app.controller('userController' ,function($scope,userService){

	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		userService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	};
	
	//分页
	$scope.findPage=function(page,rows){			
		userService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		userService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	};
	$scope.entity={};
	
	//发送消息，获取短信验证码
	$scope.sendSms = function () {
		//手机号不能为空
        if($scope.entity.phone==null || $scope.entity.phone==""){
        	alert("手机号不能为空");
            return;
        }
        //调用服务实现发送消息，获取短信验证码
		userService.sendSms($scope.entity.phone).success(function (data) {
			if(data.success){
				alert(data.message);
			}else{
                alert(data.message);
			}
        })



    }
	
	//保存 
	$scope.save=function(){
		//验证2次是否匹配
		if($scope.entity.password != $scope.password){
			alert("密码不匹配");
			return;
		}
		//验证输入用户名，密码，手机号不能为空
		if($scope.entity.username==null || $scope.entity.username==""){
			alert("用户名不能为空");
			return;
		}

        if($scope.entity.password==null || $scope.entity.password==""){
			alert("密码不能为空");
            return;
        }

        if($scope.entity.phone==null || $scope.entity.phone==""){
			alert("手机号码不能为空");
            return;
        }

        if($scope.smsCode==null || $scope.smsCode==""){
			alert("验证码不能为空");
            return;
        }
		//完成注册
        userService.add($scope.entity,$scope.smsCode).success(
			function(response){
				if(response.success){
		        	location.href="login.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	};
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		userService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		userService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	
