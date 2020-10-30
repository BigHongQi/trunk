$(function(){
	$('#log-out').click(function () {
		$.ajax({
			url : "/o2o/local/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					var usertype = $("#log-out").attr("usertype");
					window.location.href = '/o2o/local/login?usertype='+usertype;
					return false;
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});
});
