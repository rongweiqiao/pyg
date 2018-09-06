app.service("payService",function ($http) {
    this.createNative=function () {
        return $http.post("../pay/createNative");
    }
    this.queryOrderStatus=function (orderNum) {
        return $http.post("../pay/queryOrderStatus?orderNum="+orderNum);
    }
})