// 提交表單后的事件
$(document).ready(function() {
	if(!userSettingailidation()) return
	$("#setting_reg_form").ajaxForm(function(data) {
		if(data.status == "SUCCESS") {
			xcsoft.success(data.message, 1500)
		} else {
			xcsoft.error(data.message, 5000)
		}
	})
})