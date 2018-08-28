 //控制层 
app.controller('goodsController' ,function($scope,$controller,itemCatService,goodsService){

    $controller("baseController",{$scope:$scope});
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	};
	
	//分页
	$scope.findPage=function(page,rows){
        goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	
	//查询实体 
	$scope.findOne=function(id){
        goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	};
	
	//保存 
	$scope.save=function(){
		$scope.entity.tbGoodsDesc.introduction=editor.html();
        goodsService.add( $scope.entity).success(
			function(response){
				if(response.success){
					$scope.entity={};
					editor.html("");
		        	alert(response.message)
				}else{
					alert(response.message);
				}
			}		
		);				
	};
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.ids ).success(
			function(response){
				if(response.success){
                    $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
					$scope.ids=[];
				}						
			}		
		);				
	};
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.findByPage=function(page,rows){
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};

    $scope.status=['未审核','已审核','审核未通过','关闭'];

    $scope.itemCatList=[];
    $scope.findItemList=function () {
        itemCatService.findAll().success(function (data) {
            for(var i=0;i<data.length;i++){
                $scope.itemCatList[data[i].id]=data[i].name;
            }
        });
        $scope.itemCatList2=$scope.itemCatList;
    };
    $scope.updateStatus=function (status) {
		goodsService.updateStatus(status,$scope.ids).success(function (data) {
			if(data.success){
                $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
                $scope.ids=[];
			}else {
				alert(data.message)
			}
        })
    };


    
});	
