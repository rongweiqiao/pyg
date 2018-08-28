app.service("uploadService",function ($http) {
   this.uploadFile=function () {
       var formDate=new FormData();
       formDate.append("file",file.files[0]);
       return $http({
           method:'POST',
           url:"../file/upload",
           data:formDate,
           headers:{'Content-Type':undefined},
           transformRequest:angular.identity
       })
   }
});