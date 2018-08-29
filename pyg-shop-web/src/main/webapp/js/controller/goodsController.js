 //控制层 
app.controller('goodsController' ,function($scope,$controller,itemCatService,typeTemplateService,uploadService,goodsService){

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
					$scope.reloadList();//刷新列表
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

	$scope.selectItemCat1List=function () {
		itemCatService.findByParentId('0').success(function (data) {
			$scope.itemCatList=data;
        })
    };
	$scope.$watch('entity.tbGoods.category1Id',function (newValue,oldValue) {
		itemCatService.findByParentId(newValue).success(function (data) {
			$scope.itemCat2List=data;
			$scope.itemCat3List=[];
        })
		
    });
    $scope.$watch('entity.tbGoods.category2Id',function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (data) {
            $scope.itemCat3List=data;
        })

    });

    $scope.$watch('entity.tbGoods.category3Id',function (newValue,oldValue) {
		itemCatService.findOne(newValue).success(function (data) {
			$scope.entity.tbGoods.typeTemplateId=data.typeId;
        })
    });
    $scope.entity={tbGoodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]}};
    $scope.$watch('entity.tbGoods.typeTemplateId',function (newValue,oldValue) {
		typeTemplateService.findOne(newValue).success(function (data) {
			$scope.brandIds=JSON.parse(data.brandIds);
			$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse(data.customAttributeItems);
        });
		typeTemplateService.findSpecList(newValue).success(function (data) {
			$scope.specList=data;
        })
    });
    $scope.uploadFile=function () {
        uploadService.uploadFile().success(function (data) {
            if(data.success){
                $scope.image_entity.url=data.message;
            }else {
                alert(data.message);
            }
        })
    };

    $scope.add_image_entity=function () {
		$scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity);
		$scope.image_entity={};
    };
    // $scope.updateImageSelection=function ($event,index) {
		// $scope.indexs=[];
		// if($event.target.checked){
    //         $scope.indexs.push(index);
		// }else {
    //         $scope.indexs.splice($scope.indexs.indexOf(index),1);
		// }
    // };
    // $scope.remove_image_entity=function () {
    // 	var indexList=$scope.indexs;
    // 	for(var i=0;i<indexList.length;i++) {
    //         $scope.entity.tbGoodsDesc.itemImages.splice(indexList[i], 1);
    //     }
    // }
    $scope.remove_image_entity=function (index) {
        $scope.entity.tbGoodsDesc.itemImages.splice(index, 1);
    };
    $scope.searchObjectByKey=function(list,key,keyValue){
        for(var i=0;i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    };
    //$scope.entity={ goodsDesc:{itemImages:[],specificationItems:[]}  };

    $scope.updateSpecAttribute=function($event,name,value){
        var object= $scope.searchObjectByKey($scope.entity.tbGoodsDesc.specificationItems ,'attributeName', name);
        if(object!=null){
            if($event.target.checked ){//选中事件
                object.attributeValue.push(value);
            }else{//取消勾选
                object.attributeValue.splice( object.attributeValue.indexOf(value ) ,1);//移除选项
                //如果选项都取消了，将此条记录移除
                if(object.attributeValue.length==0){
                    $scope.entity.tbGoodsDesc.specificationItems.splice(
                        $scope.entity.tbGoodsDesc.specificationItems.indexOf(object),1);
                }
            }
        }else{
            $scope.entity.tbGoodsDesc.specificationItems.push(
                {"attributeName":name,"attributeValue":[value]});
        }
    };

    $scope.createItemList=function(){
        $scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0' } ];//初始
        var items=  $scope.entity.tbGoodsDesc.specificationItems;
        for(var i=0;i< items.length;i++){
            $scope.entity.itemList = addColumn( $scope.entity.itemList,items[i].attributeName,items[i].attributeValue );
        }
    };
   //添加列值
    addColumn=function(list,columnName,conlumnValues){
        var newList=[];//新的集合
        for(var i=0;i<list.length;i++){
            var oldRow= list[i];
            for(var j=0;j<conlumnValues.length;j++){
                var newRow= JSON.parse( JSON.stringify( oldRow )  );//深克隆
                newRow.spec[columnName]=conlumnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
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
    $scope.markeList=["下架","上架"];

    $scope.updateMarke=function (marke) {
       goodsService.updateMarke(marke,$scope.ids).success(function (data) {
           if(data.success){
               $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
               $scope.ids=[];
           }else {
               alert(data.message);
           }
       })
    }



    
});	
