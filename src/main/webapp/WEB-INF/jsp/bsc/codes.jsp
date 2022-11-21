<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
$(document).ready(function () {
	var params = new URL(location.href).searchParams;
	var url = "/api/codes/" + params.get("cdCol");
	
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
				$("#data tbody").append(
						"<tr>"
						+ "	<td>" + value.cdCol + "</td>"
						+"	<td>" + value.cd + "</td>"
						+"	<td>" + value.cdNm + "</td>"
						+"	<td style=\"text-align: right;\">" + value.ord + "</td>"
						+"	<td style=\"text-align: center;\">" + value.useYn + "</td>"
						+"	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:modify();\"><i class=\"fa fa-gears\"></i> 수정</a></td>"
						+"</tr>");
			});
		}
	});
});

/**
 * 등록 버튼
 */
function add(){
	var params = new URL(location.href).searchParams;
	
	$("#data tbody").append(
			"<tr>"
			+ "	<td><input type=\"text\" id=\"CD_COL_NEW\" class=\"form-control\" value=\"" + params.get("cdCol") + "\" readonly=\"readonly\" /></td>"
			+"	<td><input type=\"text\" id=\"CD_NEW\" class=\"form-control\" value=\"\" /></td>"
			+"	<td><input type=\"text\" id=\"CD_NM_NEW\" class=\"form-control\" value=\"\" /></td>"
			+"	<td style=\"text-align: right;\"><input type=\"text\" id=\"ORD_NEW\" class=\"form-control\" value=\"\" /></td>"
			+"	<td style=\"text-align: center;\">"
			+"		<select id=\"USE_YN_NEW\" class=\"form-control\">"
			+"			<option value=\"Y\">Y</option>"
			+"			<option value=\"N\">N</option>"
			+"		</select>"
			+"	</td>"
			+"	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:save('NEW', 'POST');\"><i class=\"fa fa-gears\"></i> 저장</a></td>"
			+"</tr>");
}

/**
 * 저장버튼
 */
function save(id, method){
	if(confirm("저장하시겠습니까?")){
		var param = new Object();
		
		param.cdCol = $("#CD_COL_" + id).val();
		param.cd = $("#CD_" + id).val();
		param.cdNm = $("#CD_NM_" + id).val();
		param.ord = $("#ORD_" + id).val();
		param.useYn = $("#USE_YN_" + id).val();
		
		$.ajax({
			url: "/api/codes"
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

/**
 * 목록 버튼
 */
function list() {
	location.href="/bsc/columns.do";
}
</script>
</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
		
		<!-- 왼쪽 메뉴 시작 -->
			<%@ include file="/WEB-INF/jsp/common/left.jsp" %>
		<!-- 왼쪽 메뉴 종료 -->
		
		<!-- 상단 메뉴 시작 -->
			<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
		<!-- 상단 메뉴 종료 -->
			
		<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="clearfix"></div>
					
					<div class="row" style="display: block; min-height: 880px;">
						<div class="col-md-12 col-sm-12  ">
							<div class="x_panel">
								<div class="x_title">
									<h2>코드 목록</h2>
									<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
										<li><a class="close-link"><i class="fa fa-close"></i></a>
										</li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div class="table-responsive">
										<table id="data" class="table jambo_table bulk_action">
											<colgroup>
												<col style="width: 15%;" />
												<col style="width: 15%;" />
												<col style="width: 40%;" />
												<col style="width: 10%;" />
												<col style="width: 10%;" />
												<col style="width: 10%;" />
											</colgroup>
											<thead>
												<tr class="headings">
													<th class="column-title" style="text-align: center;">컬럼</th>
													<th class="column-title" style="text-align: center;">코드</th>
													<th class="column-title" style="text-align: center;">코드명</th>
													<th class="column-title" style="text-align: center;">순번</th>
													<th class="column-title" style="text-align: center;">사용여부</th>
													<th class="column-title" style="text-align: center;">-</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								
							<!-- 버튼 영역 시작 -->
								<div style="float: right;">
									<button type="button" class="btn btn-secondary" onclick="list();">목록</button>
									<button type="button" class="btn btn-success" onclick="add();">등록</button>
								</div>
							<!-- 버튼 영역 종료 -->
								
							</div>
						</div>
					</div>
				</div>
			</div>
		<!-- /page content -->
			
		</div>
	</div>
</body>
</html>