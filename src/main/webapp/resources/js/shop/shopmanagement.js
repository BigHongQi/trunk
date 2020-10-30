$(function () {
    var shopId = getQueryString('shopId');
    var shopInfoUrl = "/o2o/shopadmin/getshopmanagementinfo?shopId=" + shopId;
    // 记得回调函数里有data
    $.getJSON(shopInfoUrl, function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
        }
         $("#shopInfo").attr("href", "/o2o/shopadmin/shopedit?shopId=" + shopId);
        
    });

});