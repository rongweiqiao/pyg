app.controller("baseController",function ($scope) {
    $scope.paginationConf={
        currentPage:1,
        totalItems:10,
        itemsPerPage:10,
        perPageOptions:[10,20,30,40,50],
        onChange:function () {
            $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
        }
    };

    $scope.ids=[];
    $scope.updateSelection=function ($event,id) {
        if($event.target.checked){
            $scope.ids.push(id);
        }else {
            $scope.ids.splice($scope.ids.indexOf(id),1);
        }
    };
});