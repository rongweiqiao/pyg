app.service("specificationService",function ($http) {
    this.findByPage=function (pageNum,pageSize) {
        return $http.get("../specification/findAllByPage?pageNum="+pageNum+"&pageSize="+pageSize);
    };
    this.findById=function (id) {
        return $http.get("../specification/findById?id="+id);
    };
    this.dele=function (ids) {
        return $http.get("../specification/delete?ids="+ids);
    };
    this.save=function (brand) {
        return $http.post("../specification/save",brand);
    };
    this.update=function (brand) {
        return $http.post("../specification/update",brand);
    }
});