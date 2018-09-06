//服务层
app.service('orderService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../order/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../order/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../order/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../order/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../order/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../order/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../order/search.do?page='+page+"&rows="+rows, searchEntity);
	}
	this.findCartList=function () {
		return $http.post("../order/findCartList");
    }
    this.sum=function (cartList) {
		var totalValue={totalSum:0,totalPrice:0};
		for(var i=0;i<cartList.length;i++){
			var orderItemList= cartList[i].orderItemList
			for(var j=0;j<orderItemList.length;j++){
				totalValue.totalSum+=orderItemList[j].num;
				totalValue.totalPrice+=orderItemList[j].totalFee;
			}
		}
        return totalValue;
    }
    this.submitOrder=function (entity) {
		return $http.post("../order/submitOrder",entity);
    }
});
