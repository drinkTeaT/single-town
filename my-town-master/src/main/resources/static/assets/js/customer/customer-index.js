$(function(){
	if (!customerIndexValidation()) return
	queryAndSetInfoForCustomer(getUrlParam(window.parent.location, "id"))
})


function queryAndSetInfoForCustomer(userId) {
	$.post(SERVER_HOST + "/customer/queryExeInfo", {
		userId : userId
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS' && data.data != null) {
			var ent = data.data
			$("#name_for_customer")[0].innerText = ent.name
			$("#shop_for_customer")[0].innerText = ent.shopName
			$("#wx_for_customer")[0].innerText = ent.wxChat
			$("#phone_foe_customer")[0].innerText = ent.phone
		} else if(data.status == 'FAIL') {
			xcsoft.error(data.message, 5000)
		} else {

		}
	}, "json")
}

// tool
function customerIndexValidation() {
	if($('body').is('.customer-index')) {
		return true
	} else {
		return false
	}
}