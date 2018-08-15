app.service("brandService",function ($http) {
    this.findByPage=function (pageNum,pageSize) {
        return $http.get("../brand/findAllByPage?pageNum="+pageNum+"&pageSize="+pageSize);
    };
    this.findById=function (id) {
        return $http.get("../brand/findById?id="+id);
    };
    this.dele=function (ids) {
        return $http.get("../brand/delete?ids="+ids);
    };
    this.save=function (brand) {
        return $http.post("../brand/save",brand);
    };
    this.update=function (brand) {
        return $http.post("../brand/update",brand);
    }
    this.findBrandList=function () {
        return $http.get("../brand/findBrandList");
    }
    this.search=function (pageNum,pageSize,searchEntity) {
        return $http.post("../brand/search?pageNum="+pageNum+"&pageSize="+pageSize,searchEntity);
    }
});