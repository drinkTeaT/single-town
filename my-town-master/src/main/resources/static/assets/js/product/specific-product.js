// 页面加载route
$(function() {
	if(!specificProductVailidation()) return
	// 获取类别以及商品数据
	specificProductLoadData()
})

function specificProductLoadData() {
	if(!specificProductVailidation()) return
	// 获取子类目
	var parentId = $.cookie('parentId')
	viewPrdByCatIdAndUserId(parentId)
}

function viewPrdByCatIdAndUserId(parentId) {
	if(!specificProductVailidation()) return
	// 根据子类目查询该用户的所有商品 
	$.get(SERVER_HOST + "/product/viewPrdByParentCatIdAndUserId", {
		catId: parentId
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			$("#script-component").tmpl(data.data).appendTo('#generate-prd')
		} else {
			xcsoft.error(data.message, 5000)
			console.log(data.message)
		}
	}, "json")
}

// 页面交互
// 为渲染的组件加事件
$("#generate-prd").on("click", "a", function() {
	if(!specificProductVailidation()) return
	if(this.classList.contains('delId')) { // 删除商品
		$.cookie('delId', this.getAttribute('code'))
		console.log("删除商品" + this.getAttribute('code'))
	} else if(this.classList.contains('editId')) { // 编辑商品
		$.cookie('editId', this.getAttribute('code'))
		$.cookie('addCatId', null)
		// 给模态框赋值
		setProductById(this.getAttribute('code'))
		console.log("编辑商品" + this.getAttribute('code'))
	} else { // 新增操作
		$.cookie('editId', null)
		$.cookie('addCatId', this.getAttribute('code'))
		// 赋值为null
		$("#image-file")[0].value = ""
		$("#enable")[0].checked = true
		$("#description")[0].value = null
		$("#price")[0].value = null
	}
})

// 编辑时塞值
function setProductById(id) {
	$.post(SERVER_HOST + "/product/view", {
		productId: id
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			var prd = data.data
			$("#description")[0].value = prd.description
			$("#price")[0].value = prd.price
			if(prd.enable == '1') {
				$("#enable")[0].checked = true
			} else {
				$("#enable")[0].checked = false
			}
		}
		console.log(data.message)
	}, "json")
}

$("#doDelete").click(function() {
	if(!specificProductVailidation()) return
	var productId = $.cookie('delId')
	$.post(SERVER_HOST + "/product/del", {
		productId: productId
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			window.location.reload()
		} else {
			xcsoft.error(data.message, 5000)
			console.log(data.message)
		}
	}, "json")
})

//--- 模态框 ----
// 对商品新增或编辑
$("#edit-modal-form").submit(function(e) {
	if(!specificProductVailidation()) return
	if($("#enable")[0].checked) {
		$("#editEnable")[0].value = '1'
	} else {
		$("#editEnable")[0].value = '0'
	}
	if('null' == $.cookie('editId')) { // 新增
		$('#modal-catid')[0].value = $.cookie('addCatId')
	} else { // 编辑
		//获取商品id
		$("#editProductId")[0].value = $.cookie("editId")
	}
})
$(document).ready(function() {
	if(!specificProductVailidation()) return
	$("#edit-modal-form").ajaxForm(function(data) {
		if(data.status == "SUCCESS") {
			window.location.reload()
			xcsoft.success(data.message, 1500)
		} else {
			$("#editModal").modal('hide');
			xcsoft.error(data.message, 5000)
			console.log(data.message)
		}
	});
})

$("#image-file").change(function() {
	if($("#image-file")[0].files[0].size > 1024 * 1024 * 4) {
		alert("图片大于4MB请重新上传")
		$("#image-file")[0].value = ""
	}
})
// tool
function specificProductVailidation() {
	if($('body').is('.page-specific-product')) {
		return true
	} else {
		return false
	}
}