//控制层
app.controller('orderController', function ($scope, orderService, addressService) {


    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        orderService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };
    $scope.findAddressList = function () {
        addressService.findAll().success(function (data) {
            $scope.addressList = data;
            for (var i = 0; i < $scope.addressList.length; i++) {
                if ($scope.addressList[i].isDefault == '1') {
                    $scope.address = $scope.addressList[i];
                    return;
                }
            }
        })
    };

    $scope.entity = {paymentType: '1'};
    $scope.isSelect = function (address) {
        if (address == $scope.address) {
            return true;
        }
        return false;
    };
    $scope.changeSelect = function (address) {
        for (var i = 0; i < $scope.addressList.length; i++) {
            if ($scope.addressList[i] == address) {
                $scope.addressList[i].isDefault = '1';
                $scope.address = $scope.addressList[i];
            } else {
                $scope.addressList[i].isDefault = '0';
            }
        }
    };
    $scope.findCartList = function () {
        orderService.findCartList().success(function (data) {
            $scope.cartList = data;
            $scope.totalValue = orderService.sum($scope.cartList);
        })
    };
    $scope.submitOrder = function () {
        $scope.entity.payment = $scope.totalValue.totalPrice;
        $scope.entity.receiverAreaName = $scope.address.address;
        $scope.entity.receiveMobile = $scope.address.mobile;
        $scope.entity.receiver = $scope.address.contact;
        orderService.submitOrder($scope.entity).success(function (data) {
            if (data.success) {
                window.location.href = "http://localhost:8087/pay.html";
            } else {
                alert(data.message);
            }
        })
    };
    //分页
    $scope.findPage = function (page, rows) {
        orderService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        orderService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = orderService.update($scope.entity); //修改
        } else {
            serviceObject = orderService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        orderService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        orderService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

});	
