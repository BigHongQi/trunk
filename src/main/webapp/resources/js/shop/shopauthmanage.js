$(function() {
	var shopId = getQueryString('shopId');
    var listUrl = '/o2o/shopadmin/listshopauthmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId;
    var modifyUrl = '/o2o/shopadmin/modifyshopauthmap';
    getList();
    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var shopauthList = data.shopAuthMapList;
                var tempHtml = '';
                shopauthList.map(function (item, index) {
                	var textOp = "恢复";
                	var ContraryStatus = 0;
                	if(item.enableStatus == 1){
                		//若状态值为1.表面授权生效，操作变为删除；
                		textOp = "删除";
                		ContraryStatus = 0;
                	}else{
                		ContraryStatus = 1;
                	}
                	 tempHtml += '' + '<div class="row row-shopauth">'
                         + '<div class="col-40">'+ item.name +'</div>';
                	 if(item.titleFlag != 0){
                		 //若不是店家本人的授权信息，则加入编辑以及改变状态等操作
                		 tempHtml += '<div class="col-20">'+ item.title 
                		     +'</div>' + '<div class="col-40">'
                             + '<a href="#" class="edit" data-employee-id="'
                             + item.employeeId +'" data-auth-id="'
                             + item.shopAuthId +'">编辑</a>'
                             + '<a href="#" class="status" data-auth-id="'
                             + item.shopAuthId +'" data-status="'
                             + ContraryStatus +'">'+ textOp +'</a>'
                             + '</div>';
                	 }else {
                		 tempHtml += '<div class="col-20">'+ item.title 
            		     +'</div>' + '<div class="col-40">'
            		     +'<span>不可操作</span>'+'</div>'
					}
                	 tempHtml +='</div>';
                });
                $('.shopauth-wrap').html(tempHtml);
            }
        });
    }

   

    function deleteItem(id,status) {
    	var shopAuth = {};
    	shopAuth.shopAuthId = id;
    	shopAuth.enableStatus = status;
        $.confirm('确定么?', function () {
            $.ajax({
                url: modifyUrl,
                type: 'POST',
                data: {
                    shopAuthMapStr: JSON.stringify(shopAuth),
                    statusChange: true,
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }

    $('.shopauth-wrap').on('click', 'a', function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
        	var url = '/o2o/shopadmin/shopauthedit?shopauthId=' + e.currentTarget.dataset.authId;
            window.location.href = url;
        } else if (target.hasClass('status')) {
            deleteItem(e.currentTarget.dataset.authId,e.currentTarget.dataset.status);
        }
    });

    // $('#new').click(function () {
    //     window.location.href = '/myo2o/shop/shopauthedit';
    // });
});