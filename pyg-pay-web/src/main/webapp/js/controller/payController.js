app.controller("payController", function ($scope, $location,payService) {
    $scope.createNative = function () {
        payService.createNative().success(function (data) {
            $scope.totalFee = (data.totalFee/100).toFixed(2);
            $scope.orderNum = data.orderNum;
            var qr = new QRious({
                element: document.getElementById("qrious"),
                size: 250,
                level: 'H',
                value: data.code_url
            });
            queryOrderStatus($scope.orderNum);
        })
    };
    queryOrderStatus=function (orderNum) {
        payService.queryOrderStatus(orderNum).success(function (data) {
            if(data.success){
                location.href="paysuccess.html#?totalFee="+$scope.totalFee;
            }else if(data.message=="timeOut"){
                if(window.confirm("二维码已经过期,请重新获取")){
                    $scope.createNative();
                }
            }else {
                alert(data.message);
            }
        })
    };
    $scope.getTotalFee=function () {
        return $location.search()['totalFee'];
    }

});