app.controller("specificationController",function ($scope,$controller,specificationService) {
    $controller("baseController",{$scope:$scope});
    $scope.searchEntity={};
    $scope.findByPage=function (pageNum,pageSize) {
        specificationService.search(pageNum,pageSize,$scope.searchEntity).success(function (data) {
            $scope.list=data.rows;
            $scope.paginationConf.totalItems=data.total;
        })
    };
    $scope.save=function () {
        var objMethod=null;
        if($scope.entity.tbSpecification.id!=null){
            objMethod=specificationService.update($scope.entity);
        }else {
            objMethod=specificationService.save($scope.entity);
        }
        objMethod.success(function (pygResult) {
            if(pygResult.success){
                $scope.searchEntity={};
                $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
            }else {
                alert(pygResult.message);
            }
        })
    };
    $scope.findById=function (id) {
        specificationService.findById(id).success(function (data) {
            $scope.entity=data;

        })
    };
    $scope.dele=function () {
        specificationService.dele($scope.ids).success(function (pygResult) {
            if(pygResult.success){
                $scope.ids=[];
                $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
            }else {
                alert(pygResult.message);
            }
        })
    };
    $scope.addTableRow=function () {
        $scope.entity.tbSpecificationOptionList.push({})
    };
    $scope.deleTableRow=function (index) {
        $scope.entity.tbSpecificationOptionList.splice(index,1);
    }

});