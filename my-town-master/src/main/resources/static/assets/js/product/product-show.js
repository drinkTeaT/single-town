// 页面加载route
$(function() {
	if(!productShowVailidation()) return
	// 获取类别以及商品数据
	loadDataFromServer()
})

function loadDataFromServer() {
	if(!productShowVailidation()) return
	$.get(SERVER_HOST + "/category/queryFirst", {
		// no data
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			$("#left-cat-component").tmpl(data.data).appendTo('#product-cat-list')
		} else {
			xcsoft.error(data.message,5000)
		}
	}, "json")
}

// 点击类目数据
$('#product-cat-list').on('shown.bs.tab', function(e) {
	if(!productShowVailidation()) return
	var parentId = e.target.id // 一级节点的ID
	var html = ''
	if(parentId == 'cat-index-item') {
		html = buildSpecificProduct('cat-index.html')
		$('#product-second-panel').html(html)
	} else {
		$.cookie('parentId', parentId)
		html = buildSpecificProduct('specific-product.html')
		$('#product-second-panel').html(html)
	}
})

// tool
function productShowVailidation() {
	if($('body').is('.page-product-show')) {
		return true
	} else {
		return false
	}
}