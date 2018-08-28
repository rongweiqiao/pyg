app.controller("uploadController",function ($scope,uploadService) {
    $scope.uploadFile=function () {
        uploadService.uploadFile().success(function (data) {
            if(data.success){
                $scope.image_entity={};
               $scope.image_entity.url=data.message;
            }else {
                alert(data.message);
            }
        })
    }
});