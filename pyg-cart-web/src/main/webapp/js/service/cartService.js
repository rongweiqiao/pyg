//服务层
app.service('cartService', function ($http) {

    //查询购物车列表
    this.findCartList = function () {
        return $http.get('../cart/findCartList');
    }
    //计算商品总价格
    this.sum = function (cartList) {
        var totalValue = {totalNum: 0, totalPrice: 0};
        for (var i = 0; i < cartList.length; i++) {
            var orderItemList = cartList[i].orderItemList;
            for (var j = 0; j < orderItemList.length; j++) {
                totalValue.totalNum+=orderItemList[j].num;
                totalValue.totalPrice+=orderItemList[j].totalFee;
            }
        }

        return totalValue;
    };

    //添加购物车
    this.addGoodsToCartList = function (itemId,num) {
        return $http.get('../cart/addGoodsToCartList?itemId='+itemId+'&num='+num);
    }
});
