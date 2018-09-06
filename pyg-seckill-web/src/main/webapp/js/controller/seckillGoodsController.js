 //控制层 
app.controller('seckillGoodsController' ,function($scope,$location,$interval,seckillGoodsService){
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		seckillGoodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		seckillGoodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		seckillGoodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}

	//保存
	$scope.save=function(){
		var serviceObject;//服务层对象
		if($scope.entity.id!=null){//如果有ID
			serviceObject=seckillGoodsService.update( $scope.entity ); //修改
		}else{
			serviceObject=seckillGoodsService.add( $scope.entity  );//增加
		}
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}
		);
	}


	//批量删除
	$scope.dele=function(){
		//获取选中的复选框
		seckillGoodsService.dele( $scope.selectIds ).success(
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
		seckillGoodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	$scope.findSecKillGoodsList=function () {
		seckillGoodsService.findSecKillGoodsList().success(function (data) {
			$scope.seckillGoodList=data;
        })
    };
	$scope.findSeckillById=function () {
       var id = $location.search()['id'];
       seckillGoodsService.findSeckillById(id).success(function (data) {
           $scope.seckillGood=data;
           var endTime=new Date(data.endTime).getTime();
           var startTime=new Date().getTime();
           var seconds=Math.floor(endTime-startTime)/1000;
           var time=$interval(function () {
               if(seconds>0){
                    seconds=seconds-1;
                   getTimeString(seconds);
               }else {
                   $interval.cancel(time);
               }

           },1000)
       })
    }
    $scope.toItem=function (id) {
        location.href="seckill-item.html#?id="+id;
    };

    getTimeString=function (seconds) {
        var day= Math.floor(seconds/60/60/24);
        var hour=Math.floor((seconds-day*60*60*24)/60/60);
        var minute = Math.floor((seconds-day*60*60*24-hour*60*60)/60);
        var second = Math.floor(seconds-day*60*60*24-hour*60*60-minute*60);
        if(day>0){
            $scope.timeString=day+"天"+hour+":"+minute+":"+second;
        }else {
            $scope.timeString=hour+":"+minute+":"+second;
        }
    }


});	
