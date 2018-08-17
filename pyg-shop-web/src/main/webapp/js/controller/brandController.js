app.controller("brandController",function ($scope,$controller,brandService) {
    $controller("baseController",{$scope:$scope});
    $scope.searchEntity={};
    $scope.findByPage=function (pageNum,pageSize) {
        brandService.search(pageNum,pageSize,$scope.searchEntity).success(function (data) {
            $scope.list=data.rows;
            $scope.paginationConf.totalItems=data.total;
        })
    };
    $scope.save=function () {
        var objMethod=null;
        if($scope.brand.id!=null){
            objMethod=brandService.update($scope.brand);
        }else {
            objMethod=brandService.save($scope.brand);
        }
        objMethod.success(function (pygResult) {
            if(pygResult.success){
                $scope.searchEntity={};//添加或者修改成功应该要把搜索条件设置为空
                $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
            }else {
                alert(pygResult.message);
            }
        })
    };
    $scope.findById=function (id) {
        brandService.findById(id).success(function (tbBrand) {
            $scope.brand=tbBrand;
        })
    };
    $scope.dele=function () {
        brandService.dele($scope.ids).success(function (pygResult) {
            if(pygResult.success){
                $scope.ids=[];
                $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
            }else {
                alert(pygResult.message);
            }
        })
    };


});