<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">

//저장 함수에서 사용하는 경로
var SAVE_URL = "/api/batchs";

//테이블 구성 정보
var data = [];

data.push({"NM" : "SEQ_",		"IDX" : "seq",	"VAL" : "seq",		"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "BATCH_NM_",	"IDX" : "seq",	"VAL" : "batchNm",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "PARM_1ST_",	"IDX" : "seq",	"VAL" : "parm1st",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "PARM_2ND_",	"IDX" : "seq",	"VAL" : "parm2nd",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "PARM_3RD_",	"IDX" : "seq",	"VAL" : "parm3rd",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "PARM_4TH_",	"IDX" : "seq",	"VAL" : "parm4th",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "EXE_YN_",	"IDX" : "seq",	"VAL" : "exeYn",	"FUNC" : "",	"TYPE" : "select",	"OPT" : ""});
data.push({"NM" : "SUC_YN_",	"IDX" : "seq",	"VAL" : "sucYn",	"FUNC" : "",	"TYPE" : "select",	"OPT" : ""});
data.push({"NM" : "EXE_ST_DT_",	"IDX" : "seq",	"VAL" : "exeStDt",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "EXE_ED_DT_",	"IDX" : "seq",	"VAL" : "exeEdDt",	"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});

$(document).ready(function () {
	var url = "/api/batchs/";
	
	ls_table_init(url);
});
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
									<h2>배치 목록</h2>
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
												<col />
												<col />
												<col />
												<col />
												<col />
												<col />
												<col />
												<col />
												<col />
												<col />
											</colgroup>
											<thead>
												<tr class="headings">
													<th class="column-title" style="text-align: center;">번호</th>
													<th class="column-title" style="text-align: center;">배치명</th>
													<th class="column-title" style="text-align: center;">변수1</th>
													<th class="column-title" style="text-align: center;">변수2</th>
													<th class="column-title" style="text-align: center;">변수3</th>
													<th class="column-title" style="text-align: center;">변수4</th>
													<th class="column-title" style="text-align: center;">실행여부</th>
													<th class="column-title" style="text-align: center;">성공여부</th>
													<th class="column-title" style="text-align: center;">실행시작일시</th>
													<th class="column-title" style="text-align: center;">실행종료일시</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								
							<!-- 버튼 영역 시작 -->
								<div style="float: right;">
									<button type="button" id="add-btn" class="btn btn-success" onclick="ls_table_add();">등록</button>
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