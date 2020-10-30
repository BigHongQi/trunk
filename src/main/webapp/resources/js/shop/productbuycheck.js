$(function() {
    var shopId = 1;
    var productName = '';
    getList();
    function getList() {
        var listUrl = '/o2o/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=9999&productName=' + productName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userProductMapList = data.userProductMapList;
                var tempHtml = '';
                userProductMapList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-productbuycheck">'
                         +          '<div class="col-20">'+ item.productName +'</div>'
                         +          '<div class="col-40 productbuycheck-time">'+ new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss") +'</div>'
                         +          '<div class="col-25">'+ item.userName +'</div>'
                         +          '<div class="col-15">'+ item.point +'</div>'
                         +      '</div>';
                });
                $('.productbuycheck-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('change', function (e) {
    	//依据商品名称模糊查询该商品的购买记录
        productName = e.target.value;
        //清空商品购买记录列表
        $('.productbuycheck-wrap').empty();
        //再次加载
        getList();
    });

   






    var myChart = echarts.init(document.getElementById('chart'));

    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['茉香奶茶','绿茶拿铁','冰雪奇缘']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['周一','周二','周三','周四','周五','周六','周日']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'茉香奶茶',
                type:'bar',
                data:[120, 132, 101, 134, 290, 230, 220]
            },
            {
                name:'绿茶拿铁',
                type:'bar',
                data:[60, 72, 71, 74, 190, 130, 110]
            },
            {
                name:'冰雪奇缘',
                type:'bar',
                data:[62, 82, 91, 84, 109, 110, 120]
            }
        ]
    };

    myChart.setOption(option);






});