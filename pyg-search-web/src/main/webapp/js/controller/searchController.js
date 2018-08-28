app.controller("searchController",function ($scope,$location,searchService) {

    $scope.searchMap={
        "keywords":"",
        "category":"",
        "brand":"",
        "price":"",
        "spec":{},
        "sort":"ASC",
        "sortField":"price",
        "page":1,
        "pageSize":30
    };

    $scope.searchList=function () {
        if($scope.searchMap.keywords==""){
            $scope.searchMap.keywords = $location.search()['keywords'];
        }
        searchService.searchList($scope.searchMap).success(function (data) {
            $scope.list=data;
            $scope.buildPageLable();
        })
    };
    $scope.reloadSearchList=function () {
        if($scope.searchMap.keywords!=null&&$scope.searchMap.keywords!="") {
            window.location.href = "http://localhost:8083/search.html#?keywords="+$scope.searchMap.keywords;
            window.location.reload(true);
        }else {
            window.location.href="http://localhost:8083/search.html";
        }
    };
    $scope.addFilterCondition=function (key,value) {
        if(key=="price"||key=="category"||key=="brand"){
            $scope.searchMap[key]=value;
        }else {
            $scope.searchMap.spec[key]=value;
        }
        $scope.searchList();
    };
    $scope.removeSearchItem=function (key) {
        if(key=="price"||key=="category"||key=="brand"){
            $scope.searchMap[key]="";
        }else {
            delete $scope.searchMap.spec[key];
        }
        $scope.searchList();
    };
    $scope.sortSearch=function (sortFieldValue,sortValue) {
        $scope.searchMap.sortField=sortFieldValue;
        $scope.searchMap.sort=sortValue;
        $scope.searchList();
    };

    $scope.isFirstShow=false;
    $scope.isLastShow=false;
    $scope.buildPageLable=function () {
        $scope.pageArr=[];
        var maxPage=$scope.list.totalPages;
        var startPage=0;
        if($scope.searchMap.page-2<=0){
            startPage = 1;
            $scope.isFirstShow=false;
            $scope.isLastShow=true;
        }else {
            startPage=$scope.searchMap.page-2;
            $scope.isFirstShow=true;
            $scope.isLastShow=true;
        }
        if(startPage==1){
            $scope.isFirstShow=false;
        }
        if(startPage+4>maxPage){
            startPage=$scope.list.totalPages-3;
            $scope.isLastShow=false;
        }
        for(var i=startPage;i<startPage+4;i++){
            $scope.pageArr.push(i);
        }
    };
    $scope.queryForPage=function (page) {
        if(page==0){
            return;
        }
        if(page>$scope.list.totalPages) {
            return;
        }
        $scope.searchMap.page=parseInt(page);
        $scope.searchList();
    };


    $scope.isFirstPage=function () {
        if($scope.searchMap.page==1){
            return true;
        }
        return false;
    };
    $scope.isLastPage=function () {
      if($scope.searchMap.page==$scope.list.totalPages){
          return true;
      }
      return false;
    }
});