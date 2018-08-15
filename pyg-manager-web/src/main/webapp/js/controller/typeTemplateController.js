 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService,brandService,specificationService){
	
	$controller('baseController',{$scope:$scope});//继承

    $scope.searchEntity={};//定义搜索对象
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	};
	
	//分页
	// $scope.findByPage=function(page,rows){
	// 	typeTemplateService.findPage(page,rows).success(
	// 		function(response){
	// 			$scope.list=response.rows;
	// 			$scope.paginationConf.totalItems=response.total;//更新总记录数
	// 		}
	// 	);
	// }
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(data){
				$scope.entity= data;
				$scope.entity.brandIds=JSON.parse(data.brandIds);
				$scope.entity.specIds=JSON.parse(data.specIds);
				$scope.entity.customAttributeItems=JSON.parse(data.customAttributeItems);
			}
		);				
	};
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询
                    $scope.searchEntity={};
		        	$scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		typeTemplateService.dele( $scope.ids ).success(
			function(response){
				if(response.success){
					$scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);//刷新列表
					$scope.ids=[];
				}						
			}		
		);				
	};

	
	//搜索
	$scope.findByPage=function(page,rows){
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};

	$scope.addTableRow=function () {
		$scope.entity.customAttributeItems.push({});
    };
    $scope.deleTableRow=function (index) {
		$scope.entity.customAttributeItems.splice(index,1);
    }

    $scope.findBrandList=function () {
		brandService.findBrandList().success(function (data) {
			$scope.brandList={data:data};
        })
    };
    $scope.findSpecificationList=function () {
		specificationService.findSpecificationList().success(function (data) {
			$scope.specificationList={data:data};
        })
    }
    
});	
