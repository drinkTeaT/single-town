$(function() {
	if(!pageCustomerVailidation()) return
	loadProductsDataFromServerForCustomer(getUrlParam(window.location, "id"))
})

function loadProductsDataFromServerForCustomer(userId) {
	if(!pageCustomerVailidation()) return
	if(null == userId) {
		xcsoft.error("请先更新基本信息,再刷新网页!", 5000)
		return
	}
	$.get(SERVER_HOST + "/customer/visitCategories", {
		userId: userId
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			$("#left-cat-component").tmpl(data.data).appendTo('#product-cat-list')
		} else {
			xcsoft.error(data.message, 5000)
		}
	}, "json")
}

// 点击类目数据
$('#product-cat-list').on('shown.bs.tab', function(e) {
	if(!pageCustomerVailidation()) return
	var parentId = e.target.id // 一级节点的ID
	var html = ''
	if(parentId == 'cat-index-item') {
		html = buildSpecificProduct('customer/customer-index.html')
		$('#product-second-panel').html(html)
	} else {
		$.cookie('parentId', parentId)
		html = buildSpecificProduct('customer/customer-right.html')
		$('#product-second-panel').html(html)
	}
})

// tool
function pageCustomerVailidation() {
	if($('body').is('.page-customer')) {
		return true
	} else {
		return false
	}
}

//获取url中的参数
function getUrlParam(location, name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = location.search.substr(1).match(reg); //匹配目标参数
	if(r != null) return unescape(r[2]);
	return null; //返回参数值
}