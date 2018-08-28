 //控制层 
app.controller('contentController' ,function($scope,contentService){

    $scope.contentList=[];
    $scope.findContentListByCategoryId=function (categoryId) {
        contentService.findContentListByCategoryId(categoryId).success(function (data) {
            $scope.contentList[categoryId]=data;
        })
    };
    $scope.solrSearch=function () {
        window.location.href="http://localhost:8083/search.html#?keywords="+$scope.keywords;
    }


});	
