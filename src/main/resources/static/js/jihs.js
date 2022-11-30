/**
 * 목록 테이블 초기화
 */
function ls_table_init(url){
	$.ajax({
		url: url
		, data : null
		, method: "GET"
		, dataType: "json"
		, async : true
	})
	.done(function(json) {
		if(json.length != 0){
			$.each(json, function(key, value){
				var idx;
				var html = "<tr>";
				
				data.forEach(function(item) {
					html = html + "	<td id=\"TD_" + item["NM"] + value[item["IDX"]] + "\">";
					
					if(item["FUNC"] != ""){
						html = html + "<a href=\"#\" onclick=\"" + item["FUNC"] + "\">" + nullToBlank(value[item["VAL"]]) + "</a></td>";
					} else {
						html = html + nullToBlank(value[item["VAL"]]) + "</td>";
					}
					idx = value[item["IDX"]];
				});
				
				html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:ls_table_mod('" + idx + "');\"><i class=\"fa fa-pencil\"></i> 수정</a></td>";
				html = html + "</tr>";
				
				$("#data tbody").append(html);
			});
		}
	});
}

/**
 * 등록 버튼
 */
function ls_table_add(){
	var html = "<tr>";
	
	data.forEach(function(item) {
		if(item["TYPE"] == "text"){
			html = html + "	<td><input type=\"text\" id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\" value=\"\"" + item["OPT"] + " /></td>";
		} else if(item["TYPE"] == "select"){
			html = html + "	<td style=\"text-align: center;\"><select id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\"" + item["OPT"] + "><option value=\"Y\">Y</option><option value=\"N\">N</option></select></td>"
		}
	});
	
	html = html + "	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:ls_table_save('NEW', 'POST');\"><i class=\"fa fa-floppy-o\"></i> 저장</a></td>";
	html = html + "</tr>";
	
	$("#data tbody").append(html);
	$("#add-btn").attr("style", "display : none;");
}

/**
 * 목록 테이블 수정 버튼
 */
function ls_table_mod(id){
	data.forEach(function(item) {
		// 데이터 추출
		item["DATA"] = $("#TD_" + item["NM"] + id).text();
		
		// 데이터 삭제
		$("#TD_" + item["NM"] + id).empty();
		
		// 인풋박스로 변경
		// text 타입
		if(item["TYPE"] == "text"){
			$("#TD_" + item["NM"] + id).append("<input type=\"text\" id=\"" + item["NM"] + id + "\" class=\"form-control\" value=\"" + item["DATA"] + "\"" + item["OPT"] + " />");
			
		// select 타입
		} else if(item["TYPE"] == "select"){
			if(item["DATA"] == "Y"){
				$("#TD_" + item["NM"] + id).append("<select id=\"" + item["NM"] + id + "\" class=\"form-control\"" + item["OPT"] + "><option selected=\"selected\" value=\"Y\">Y</option><option value=\"N\">N</option></select>");
				
			} else if(item["data"] == "N"){
				$("#TD_" + item["NM"] + id).append("<select id=\"" + item["NM"] + id + "\" class=\"form-control\"" + item["OPT"] + "><option value=\"Y\">Y</option><option selected=\"selected\" value=\"N\">N</option></select>");
			}
		}
	});
	$("#TD_BTN_" + id).empty();
	$("#TD_BTN_" + id).append("<a href=\"javascript:ls_table_save('" + id + "', 'PUT');\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
}

/**
 * 목록 테이블 저장 버튼
 */
function ls_table_save(id, method){
	if(confirm("저장하시겠습니까?")){
		var param = new Object();
		
		data.forEach(function(item) {
			param[item["VAL"]] = $("#" + item["NM"] + id).val();
		});
		
		$.ajax({
			url: SAVE_URL
			, data : JSON.stringify(param)
			, method: method
			, dataType: "json"
			, contentType: 'application/json'
			, async : false
		})
		.done(function(json) {
			location.reload();
		});
	}
}

function nullToBlank(str){
	if(str == null){
		return "";
	} else {
		return str;
	}
}