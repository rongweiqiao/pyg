app.controller("loginController",function ($scope,loginService) {
   $scope.login=function () {
       loginService.login().success(function (data) {
           $scope.user=data;
       })
   }
});