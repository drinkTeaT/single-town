// 页面加载route
$(function() {
	if(!customerRightProductValidation()) return
	// 获取类别以及商品数据
	customerRightProductLoadData(getUrlParam(window.parent.location, "id"))
})

function customerRightProductLoadData(userId) {
	if(!customerRightProductValidation()) return
	// 获取子类目
	var parentId = $.cookie('parentId')
	customerRightProductByCatIdAndUserId(parentId, userId)
}

function customerRightProductByCatIdAndUserId(parentId, userId) {
	if(!customerRightProductValidation()) return
	// 根据子类目查询该用户的所有商品 
	$.get(SERVER_HOST + "/customer/visitProducts", {
		catId: parentId,
		userId: userId
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			$("#customer-component").tmpl(data.data).appendTo('#generate-prd')
			$(".zoomify").zoomify()
		} else {
			xcsoft.error(data.message, 5000)
			console.log(data.message)
		}
	}, "json")
}

// tool
function customerRightProductValidation() {
	if($('body').is('.customer-right-product')) {
		return true
	} else {
		return false
	}
}