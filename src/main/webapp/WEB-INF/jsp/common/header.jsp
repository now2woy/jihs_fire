<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="top_nav">
	<div class="nav_menu" style="padding-bottom: 10px;">
		<div class="nav toggle">
			<a id="menu_toggle"><i class="fa fa-bars"></i></a>
		</div>
		<nav class="nav navbar-nav">
			<ul class=" navbar-right">
				<li class="nav-item dropdown open" style="padding-left: 15px;">
					<a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">John Doe</a>
					<div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="javascript:;"> Profile</a>
						<a class="dropdown-item" href="javascript:;"> <span class="badge bg-red pull-right">50%</span> <span>Settings</span></a>
						<a class="dropdown-item" href="javascript:;">Help</a>
						<a class="dropdown-item" href="login.html"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
					</div>
				</li>
			</ul>
		</nav>
	</div>
	<div id="popup_layer" class="dim-layer">
		<div class="dimBg"></div>
		<div id="popup">
			<div class="x_panel">
				<div class="x_title">
					<h2 id="popup_title"></h2>
					<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
						<li><a href="#" onclick="pu_close();"><i class="fa fa-close"></i></a></li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div id="popup_body" class="x_content"></div>
			</div>
		</div>
	</div>
</div>
