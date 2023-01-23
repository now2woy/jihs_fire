package ji.hs.fire.bot.svc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.svc.ActTrdService;
import ji.hs.fire.act.vo.ActTrdVO;
import ji.hs.fire.act.vo.ActVO;
import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 지니 봇
 * @author now2w
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JihsGenieBot extends TelegramLongPollingBot {
	/**
	 * 계좌 정보 Mapper
	 */
	private final ActMapper actMapper;
	/**
	 * 코드 정보 Mapper
	 */
	private final BscCdMapper bscCdMapper;
	/**
	 * 계좌 거래 Service
	 */
	private final ActTrdService actTrdService;
	/**
	 * 
	 */
	@Override
	public String getBotUsername() {
		return "지니";
	}
	/**
	 * 
	 */
	@Override
	public String getBotToken() {
		return "5954674938:AAFxvOPbsfgoKNFBbvBeUJUN9ZmPbHo5OfY";
	}
	
	/**
	 * 봇 메시지를 읽어서 처리 한다.
	 */
	@Override
	public void onUpdateReceived(Update update) {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_BOT);
		try {
			// 메시지일 경우
			if(update.hasMessage()) {
				// 메시지를 분석하여 처리할 로직을 결정할 코드를 가져온다.
				String messageTypeCd = getMessageTypeCd(update.getMessage().getText());
				
				SendMessage message = new SendMessage();
				message.setChatId(update.getMessage().getChatId());
				message.setReplyToMessageId(update.getMessage().getMessageId());
				
				// 모든 메뉴 출력
				if("00001".equals(messageTypeCd)) {
					message.setText("메뉴를 선택하세요");
					message.setReplyMarkup(new InlineKeyboardMarkup(getAllMenuList()));
					
				// NH투자증권 분배금 처리
				} else if("00002".equals(messageTypeCd)) {
					String msg = update.getMessage().getText();
					String year = BscUtils.thisDateTime("yyyy");
					
					int result = actTrdService.botInsert(msg.substring(25, 36).replaceAll("\\*", "_")
													   , "00005"
													   , msg.substring(52, msg.indexOf(" ", 52)).replaceAll(",", "")
													   , null
													   , null
													   , msg.substring(msg.indexOf(" ", 52) + 1, msg.lastIndexOf(" 분배금"))
													   , year + "-" + msg.substring(40, 51).replaceAll("/", "-").replace(" ", "T")
													   , null
													   , Integer.toString(update.getMessage().getMessageId()));
					
					if(result == 1) {
						message.setText("입력에 성공하였습니다.");
					} else {
						message.setText("입력에 실패하였습니다.");
					}
					
				// NH투자증권 주문체결 처리
				} else if("00003".equals(messageTypeCd)) {
					String msg = update.getMessage().getText();
					
					log.info("{}", msg);
				}
				
				execute(message);
				
			// 콜백쿼리일 경우
			} else if(update.hasCallbackQuery()) {
				String cd = StringUtils.defaultString(update.getCallbackQuery().getData(), "");
				
				// "TLGRM_MSG_CD"로 시작할 경우
				if(cd.startsWith("TLGRM_MSG_CD")) {
					// 코드컬럼 값 삭제
					cd = cd.replace("TLGRM_MSG_CD", "");
					
					// 계좌목록일 경우
					if(cd.equals("00001")) {
						SendMessage message = new SendMessage();
						message.setChatId(update.getCallbackQuery().getMessage().getChatId());
						message.setText("계좌를 선택하세요");
						message.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());
						message.setReplyMarkup(new InlineKeyboardMarkup(getQ1stMenuList(Long.toString(update.getCallbackQuery().getMessage().getChatId()))));
						
						execute(message);
					}
					
					BscCdVO paramBscCdVO = new BscCdVO();
					paramBscCdVO.setCdCol("TLGRM_MSG_CD");
					paramBscCdVO.setCd(cd);
					
					BscCdVO bscCdVO = bscCdMapper.selectOne(paramBscCdVO);
					
					// 버튼 메시지를 변환한다.
					EditMessageText message = new EditMessageText();
					message.setChatId(update.getCallbackQuery().getMessage().getChatId());
					message.setText(bscCdVO.getCdNm() + "을(를) 선택하였습니다.");
					message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
					
					execute(message);
					
					// 계좌 선택
				} else if(cd.startsWith("AC_MT")) {
					// 코드컬럼 값 삭제
					cd = cd.replace("AC_MT", "");
					
					ActTrdVO actTrdVO = new ActTrdVO();
					actTrdVO.setActSeq(cd);
					actTrdVO.setLimit(1);
					actTrdVO.setOffset(0);
					Map<String, Object> result = actTrdService.list(actTrdVO);
					
					NumberFormat numberFormat = NumberFormat.getInstance();
					
					String text = "총입금액 : " + numberFormat.format(Double.parseDouble(StringUtils.defaultString((String)result.get("inSumAmt"), "0"))) + "원\n"
							+ "총출금액 : " + numberFormat.format(Double.parseDouble(StringUtils.defaultString((String)result.get("outSumAmt"), "0"))) + "원\n"
							+ "입출금합계 : " + numberFormat.format(Double.parseDouble(StringUtils.defaultString((String)result.get("inOutSumAmt"), "0"))) + "원\n"
							+ "총이자 : " + numberFormat.format(Double.parseDouble(StringUtils.defaultString((String)result.get("itrstSumAmt"), "0"))) + "원\n"
							+ "총배당금 : " + numberFormat.format(Double.parseDouble(StringUtils.defaultString((String)result.get("dvdnSumAmt"), "0"))) + "원\n";
					
					// 버튼 메시지를 변환한다.
					EditMessageText message = new EditMessageText();
					message.setChatId(update.getCallbackQuery().getMessage().getChatId());
					message.setText(text);
					message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
					
					execute(message);
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		MDC.clear();
	}
	
	/**
	 * 전체 메뉴
	 * @return
	 */
	private List<List<InlineKeyboardButton>> getAllMenuList() throws Exception {
		List<List<InlineKeyboardButton>> menuList = new ArrayList<>();
		List<InlineKeyboardButton> line = new ArrayList<>();
		
		BscCdVO paramBscCdVO = new BscCdVO();
		paramBscCdVO.setCdCol("TLGRM_MSG_CD");
		
		for(BscCdVO bscCdVO : bscCdMapper.selectAll(paramBscCdVO)) {
			line.add(InlineKeyboardButton.builder().text(bscCdVO.getCdNm()).callbackData(bscCdVO.getCdCol() + bscCdVO.getCd()).build());
		}
		
		menuList.add(line);
		
		return menuList;
	}
	
	/**
	 * "TLGRM_MSG_CD00001" 계좌 목록
	 * @return
	 */
	private List<List<InlineKeyboardButton>> getQ1stMenuList(String tlgrmId) throws Exception {
		List<List<InlineKeyboardButton>> menuList = new ArrayList<>();
		
		ActVO paramActVO = new ActVO();
		paramActVO.setTlgrmId(tlgrmId);
		
		for(ActVO actVO : actMapper.selectAll(paramActVO)) {
			List<InlineKeyboardButton> line = new ArrayList<>();
			line.add(InlineKeyboardButton.builder().text(actVO.getBkNm() + " " + actVO.getActCdNm()).callbackData("AC_MT" + actVO.getActSeq()).build());
			
			menuList.add(line);
		}
		
		return menuList;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	private String getMessageTypeCd(String message) {
		String messageTypeCd = "99999";
		
		// 모든 메뉴 출력
		if("?".equals(message)) {
			messageTypeCd = "00001";
			
		// NH투자증권 분배금 처리
		} else if(message.indexOf("[NH투자증권]") != -1 && message.indexOf("분배금") != -1) {
			messageTypeCd = "00002";
			
		// NH투자증권 주문체결 처리
		} else if(message.indexOf("[NH투자증권]") != -1 && message.indexOf("주문체결 알림") != -1) {
			messageTypeCd = "00003";
		}
		
		return messageTypeCd;
	}
}
