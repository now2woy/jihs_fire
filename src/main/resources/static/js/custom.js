
$(document).ready(function () {
	$BODY = $('body');
	$MENU_TOGGLE = $('#menu_toggle');
	$SIDEBAR_MENU = $('#sidebar-menu');
	$SIDEBAR_FOOTER = $('.sidebar-footer');
	$LEFT_COL = $('.left_col');
	$RIGHT_COL = $('.right_col');
	$NAV_MENU = $('.nav_menu');
	$FOOTER = $('footer');
	
	var setContentHeight = function () {
		// reset height
		$RIGHT_COL.css('min-height', $(window).height());
		
		var bodyHeight = $BODY.outerHeight(),
			footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
			leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
			contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;
		
		// normalize content
		contentHeight -= $NAV_MENU.height() + footerHeight;
		
		$RIGHT_COL.css('min-height', contentHeight);
	};
	
	var openUpMenu = function () {
		$SIDEBAR_MENU.find('li').removeClass('active active-sm');
		$SIDEBAR_MENU.find('li ul').slideUp();
	}
	
	// 상단 메뉴접기 버튼
	$MENU_TOGGLE.on('click', function () {
		if ($BODY.hasClass('nav-md')) {
			$SIDEBAR_MENU.find('li.active ul').hide();
			$SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
		} else {
			$SIDEBAR_MENU.find('li.active-sm ul').show();
			$SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
		}
		
		$BODY.toggleClass('nav-md nav-sm');
		
		setContentHeight();
		
		$('.dataTable').each(function () { $(this).dataTable().fnDraw(); });
	});
	
	// 왼쪽 메뉴 접기
	$SIDEBAR_MENU.find('a').on('click', function (ev) {
		var $li = $(this).parent();
		
		if ($li.is('.active')) {
			$li.removeClass('active active-sm');
			$('ul:first', $li).slideUp(function () {
				setContentHeight();
			});
		} else {
			// prevent closing menu if we are on child menu
			if (!$li.parent().is('.child_menu')) {
				openUpMenu();
			} else {
				if ($BODY.is('nav-sm')) {
					if (!$li.parent().is('child_menu')) {
						openUpMenu();
					}
				}
			}
		
			$li.addClass('active');
			
			$('ul:first', $li).slideDown(function () {
				setContentHeight();
			});
		}
	});
});