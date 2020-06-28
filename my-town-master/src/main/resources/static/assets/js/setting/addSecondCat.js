$('#setting_secondCat').on('shown.bs.collapse', function() {
	if(!userSettingailidation()) return
	loadSecondCategorySelect()
})

function loadSecondCategorySelect() {
	$.get(SERVER_HOST + "/category/queryFirst", {
		// no data
	}, function(data, textStatus) {
		if(data.status == 'SUCCESS') {
			$("#firstCatSelect").tmpl(data.data).replaceAll('#fisrt_cat_select')
		} else {
			xcsoft.error(data.message, 5000)
		}
	}, "json")
}


$(document).ready(function() {
	if(!userSettingailidation()) return
	$("#add_second_cat_form").ajaxForm(function(data) {
		if(data.status == "SUCCESS") {
			xcsoft.success(data.message, 1500)
		} else {
			xcsoft.error(data.message, 5000)
		}
	})
})