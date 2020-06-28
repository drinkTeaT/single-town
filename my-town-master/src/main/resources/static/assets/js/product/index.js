// 页面加载route
$(function() {
	if(!pageIndexVailidation()) {
		return
	}
})

// ----- 页面加载后交互 ------
$("#index-login-form").submit(function(e) {
	if(!pageIndexVailidation()) return
	console.log("before submit")
})

// 获取表单提交后的数据
$(document).ready(function() {
	if(!pageIndexVailidation()) return
	$("#index-login-form").ajaxForm(function(data) {
		if(data.status == 'SUCCESS') {
			self.location = "home.html";
		} else {
			$("#index-login-alert")[0].innerHTML = $("#index-name-input").val() + data.message
		}
	});
})

// tool
function pageIndexVailidation() {
	if($('body').is('.page-index')) {
		return true
	} else {
		return false
	}

}