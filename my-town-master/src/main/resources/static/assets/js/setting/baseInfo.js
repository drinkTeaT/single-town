$(function() {
	if(!userSettingailidation()) return
	// 根据权限开放
	openCardByPermission()
	// 设置url链接地址
	setUrlSrc()
})

$('#setting_baseInfo').on('shown.bs.collapse', function() {
	if(!userSettingailidation()) return
	queryAndSetBaseInfo()
})

function openCardByPermission() {
	$.post(SERVER_HOST + "/permission/showCard", {
		// no data
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS' && data.data != null) {
			if(!data.data) {
				$("#setting_fisrt_collapse").hide()
				$("#setting_secondCat").hide()
				$("#setting_account_collapse").hide()
			}
		} else {
			xcsoft.error(data.message, 5000)
		}
	}, "json")
}

function queryAndSetBaseInfo() {
	$.post(SERVER_HOST + "/user/ext/queryExeInfo", {
		// no data
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS' && data.data != null) {
			var ent = data.data
			$("#baseInfo_name")[0].value = ent.name
			$("#baseInfo_shopName")[0].value = ent.shopName
			$("#baseInfo_wx")[0].value = ent.wxChat
			$("#baseInfo_phone")[0].value = ent.phone
		} else if(data.status == 'FAIL') {
			xcsoft.error(data.message, 5000)
		} else {

		}
	}, "json")
}

function setUrlSrc() {
	$.post(SERVER_HOST + "/userInfo/nameAndId", {
		// no data
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS' && data.data != null) {
			$("#customer-link")[0].href = '../customer.html?id=' + data.data.id
		} else if(data.status == 'FAIL') {
			xcsoft.error(data.message, 5000)
		} else {

		}
	}, "json")
}

// 提交表單后的事件
$(document).ready(function() {
	if(!userSettingailidation()) return
	$("#baseInfo-form").ajaxForm(function(data) {
		xcsoft.success(data.message, 1500)
	})
})

// tool
function userSettingailidation() {
	if($('body').is('.page_setting')) {
		return true
	} else {
		return false
	}
}