// 表单提交前校验
$("#setting_password_form").submit(function(e) {
	if(!userSettingailidation()) return
	if($("#new_password")[0].value != $("#repeat_password")[0].value) {
		xcsoft.error("修改失败，两次密码输入不一样", 5000)
		$("#new_password")[0].value = null
		$("#repeat_password")[0].value = null
		return false
	}
})

// 提交表單后的事件
$(document).ready(function() {
	if(!userSettingailidation()) return
	$("#setting_password_form").ajaxForm(function(data) {
		if(data.status == "SUCCESS") {
			xcsoft.success(data.message, 1500)
			window.parent.location.reload()
		} else {
			xcsoft.error(data.message, 5000)
		}
	})
})