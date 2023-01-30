package ji.hs.fire.bot.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.svc.ActPrdtService;
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
	 * 계좌 상품 거래 정보 Service
	 */
	private final ActPrdtService actPrdtService;
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
	@Transactional
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
				
				int result = 0;
				boolean isSetMeaage = false;
				
				/**
				 * 코드  : TLGRM_CMD_CD_00001
				 * 명칭  : 전체 메뉴
				 * 명령어 : ?
				 */
				if("TLGRM_CMD_CD_00001".equals(messageTypeCd)) {
					message.setText("메뉴를 선택하세요");
					message.setReplyMarkup(new InlineKeyboardMarkup(getTlgrmCmdCd("00001", null, null, null)));
					
				/**
				 * 코드  : TLGRM_CMD_CD_00004
				 * 명칭  : NH투자증권 분배금 입력
				 * 명령어 : 문자에 "NH투자증권", "분배금" 문자열 포함
				 */
				} else if("TLGRM_CMD_CD_00004".equals(messageTypeCd)) {
					isSetMeaage = true;
					String msg = update.getMessage().getText();
					
					ActTrdVO actTrdVO = new ActTrdVO();
					actTrdVO.setActNo(msg.substring(25, 36).replaceAll("\\*", "_"));
					actTrdVO.setTrdCd("00005");
					actTrdVO.setAmt(msg.substring(52, msg.indexOf(" ", 52)).replaceAll(",", ""));
					actTrdVO.setNote(msg.substring(msg.indexOf(" ", 52) + 1, msg.lastIndexOf(" 분배금")));
					actTrdVO.setTrdDt(BscUtils.thisDateTime("yyyy") + "-" + msg.substring(40, 51).replaceAll("/", "-").replace(" ", "T"));
					actTrdVO.setTlgrmId(Long.toString(update.getMessage().getChatId()));
					actTrdVO.setTlgrmMsgId(Integer.toString(update.getMessage().getMessageId()));
					
					result = actTrdService.botInsert(actTrdVO);
					
				/**
				 * 코드  : TLGRM_CMD_CD_00005
				 * 명칭  : NH투자증권 주문체결 입력
				 * 명령어 : 문자에 "NH투자증권", "주문체결 알림" 문자열 포함
				 */
				} else if("TLGRM_CMD_CD_00005".equals(messageTypeCd)) {
					isSetMeaage = true;
					String msg = update.getMessage().getText();
					
					ActTrdVO actTrdVO = new ActTrdVO();
					actTrdVO.setItmCd( msg.substring(msg.indexOf("종목코드") + 7, msg.indexOf("종목코드") + 13));
					actTrdVO.setQty(msg.substring(msg.indexOf("체결수량") + 7, msg.indexOf("주", msg.indexOf("체결수량"))));
					actTrdVO.setPrc(new BigDecimal(msg.substring(msg.indexOf("체결단가") + 7, msg.indexOf("원", msg.indexOf("체결단가"))).replaceAll(",", "")));
					actTrdVO.setAmt(BscUtils.multiply(new BigDecimal(actTrdVO.getQty()), actTrdVO.getPrc(), 0).toString());
					actTrdVO.setTrdDt(BscUtils.thisDateTime("yyyy-MM-dd HH:mm").replace(" ", "T"));
					actTrdVO.setTlgrmId(Long.toString(update.getMessage().getChatId()));
					actTrdVO.setTlgrmMsgId(Integer.toString(update.getMessage().getMessageId()));
					
					if(msg.indexOf("체결종류 : 매수") != -1) {
						actTrdVO.setTrdCd("00006");
						actTrdVO.setAmt(BscUtils.multiply(BigDecimal.valueOf(-1), new BigDecimal(actTrdVO.getAmt()), 0).toString());
					} else if(msg.indexOf("체결종류 : 매도") != -1) {
						actTrdVO.setTrdCd("00007");
					}
					
					// 계좌번호와 계좌일련번호가 없을 경우 계좌를 선택 할 수 있도록 한다.
					if(StringUtils.isEmpty(actTrdVO.getActNo()) && StringUtils.isEmpty(actTrdVO.getActSeq())) {
						message.setReplyMarkup(new InlineKeyboardMarkup(getTlgrmCmdCd("00005", "00006", Long.toString(update.getMessage().getChatId()), Integer.toString(update.getMessage().getMessageId()))));
					}
					
					result = actTrdService.botInsert(actTrdVO);
					
					// AC_PRDT_DT 테이블 데이터 입력 추가
					actPrdtService.botInsert(actTrdVO);
				}
				
				if(isSetMeaage) {
					if(result == 1) {
						message.setText("입력에 성공하였습니다.");
					} else {
						message.setText("입력에 실패하였습니다.");
					}
				}
				
				execute(message);
				
			// 콜백쿼리일 경우
			} else if(update.hasCallbackQuery()) {
				String cd = StringUtils.defaultString(update.getCallbackQuery().getData(), "");
				
				/**
				 * 코드 : TLGRM_CMD_CD_00002
				 * 명칭 : 계좌 목록
				 */
				if(cd.startsWith("TLGRM_CMD_CD_00002")) {
					SendMessage sendMessage = new SendMessage();
					sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
					sendMessage.setText("계좌를 선택하세요");
					sendMessage.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());
					sendMessage.setReplyMarkup(new InlineKeyboardMarkup(getTlgrmCmdCd("00002", "00003", Long.toString(update.getCallbackQuery().getMessage().getChatId()), null)));
					
					execute(sendMessage);
					
					// 버튼 메시지를 변환한다.
					EditMessageText editMessageText = new EditMessageText();
					editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
					editMessageText.setText("계좌목록을 선택하였습니다.");
					editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
					
					execute(editMessageText);
					
				/**
				 * 코드 : TLGRM_CMD_CD_00003
				 * 명칭 : 계좌 선택
				 */
				} else if(cd.startsWith("TLGRM_CMD_CD_00003")) {
					cd = cd.replace("TLGRM_CMD_CD_00003_", "");
					
					ActTrdVO actTrdVO = new ActTrdVO();
					actTrdVO.setActSeq(cd);
					actTrdVO.setLimit(1);
					actTrdVO.setOffset(0);
					Map<String, Object> result = actTrdService.list(actTrdVO);
					
					StringBuilder text = new StringBuilder();
					text.append("선택한 계좌는 \"");
					text.append(((ActVO)result.get("actData")).getBkNm());
					text.append(" ");
					text.append(((ActVO)result.get("actData")).getActCdNm());
					text.append("\" 입니다.\n");
					text.append("총 입금액  : ");
					text.append(BscUtils.numberCommaFormat(StringUtils.defaultString((String)result.get("inSumAmt"), "0")));
					text.append("원\n");
					text.append("총 출금액  : ");
					text.append(BscUtils.numberCommaFormat(StringUtils.defaultString((String)result.get("outSumAmt"), "0")));
					text.append("원\n");
					text.append("입출금합계 : ");
					text.append(BscUtils.numberCommaFormat(StringUtils.defaultString((String)result.get("inOutSumAmt"), "0")));
					text.append("원\n");
					text.append("총 이자   : ");
					text.append(BscUtils.numberCommaFormat(StringUtils.defaultString((String)result.get("itrstSumAmt"), "0")));
					text.append("원\n");
					text.append("총 배당금  : ");
					text.append(BscUtils.numberCommaFormat(StringUtils.defaultString((String)result.get("dvdnSumAmt"), "0")));
					text.append("원\n");
					
					// 버튼 메시지를 변환한다.
					EditMessageText editMessageText = new EditMessageText();
					editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
					editMessageText.setText(text.toString());
					editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
					
					execute(editMessageText);
					
				/**
				 * 코드 : TLGRM_CMD_CD_00006
				 * 명칭 : 계좌 선택
				 */
				} else if(cd.startsWith("TLGRM_CMD_CD_00006")) {
					String tlgrmMsgCd = cd.replace("TLGRM_CMD_CD_00006_", "").split("_")[0];
					String actSeq = cd.replace("TLGRM_CMD_CD_00006_", "").split("_")[1];
					
					// 계좌 거래내역 수정
					actTrdService.updateActSeqByTlgrmMsgId(tlgrmMsgCd, actSeq);
					
					// 계좌상품거래내역 수정
					actPrdtService.updateActSeqByByTlgrmMsgId(tlgrmMsgCd, actSeq);
					
					// 버튼 메시지를 변환한다.
					EditMessageText editMessageText = new EditMessageText();
					editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
					editMessageText.setText("입력에 성공하였습니다.");
					editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
					
					execute(editMessageText);
				}
				
			}
		} catch (Exception e) {
			log.error("", e);
		}
		MDC.clear();
	}
	
	/**
	 * 계좌 목록 명령어 처리
	 * @throws Exception
	 */
	private List<List<InlineKeyboardButton>> getTlgrmCmdCd(String tlgrmCmdCd, String targetTlgrmCmdCd, String tlgrmId, String tlgrmMsgId) throws Exception {
		List<List<InlineKeyboardButton>> menuList = new ArrayList<>();
		
		// 전체 메뉴 목록
		if("00001".equals(tlgrmCmdCd)) {
			List<InlineKeyboardButton> line = new ArrayList<>();
			
			BscCdVO paramBscCdVO = new BscCdVO();
			paramBscCdVO.setCdCol("TLGRM_MSG_CD");
			
			for(BscCdVO bscCdVO : bscCdMapper.selectAll(paramBscCdVO)) {
				line.add(InlineKeyboardButton.builder().text(bscCdVO.getCdNm()).callbackData(bscCdVO.getCdVal()).build());
			}
			
			menuList.add(line);
			
		// 계좌 선택
		}else if("00002".equals(tlgrmCmdCd) || "00005".equals(tlgrmCmdCd)) {
			ActVO paramActVO = new ActVO();
			paramActVO.setTlgrmId(tlgrmId);
			
			for(ActVO actVO : actMapper.selectAll(paramActVO)) {
				List<InlineKeyboardButton> line = new ArrayList<>();
				
				// 00005의 경우 기존 입력된 tlgrmMsgId를 이용하여 업데이트 해야 되서 달고 가야 됨
				if(StringUtils.isNotEmpty(tlgrmMsgId)) {
					line.add(InlineKeyboardButton.builder().text(actVO.getBkNm() + " " + actVO.getActCdNm()).callbackData("TLGRM_CMD_CD_" + targetTlgrmCmdCd + "_" + tlgrmMsgId + "_" + actVO.getActSeq()).build());
					
				// 00002는 없음
				}else {
					line.add(InlineKeyboardButton.builder().text(actVO.getBkNm() + " " + actVO.getActCdNm()).callbackData("TLGRM_CMD_CD_" + targetTlgrmCmdCd + "_" + actVO.getActSeq()).build());
				}
				
				menuList.add(line);
			}
		}
		
		return menuList;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	private String getMessageTypeCd(String message) {
		String messageTypeCd = "TLGRM_CMD_CD_99999";
		
		// 모든 메뉴 출력
		if("?".equals(message)) {
			messageTypeCd = "TLGRM_CMD_CD_00001";
			
		// NH투자증권 분배금 처리
		} else if(message.indexOf("[NH투자증권]") != -1 && message.indexOf("분배금") != -1) {
			messageTypeCd = "TLGRM_CMD_CD_00004";
			
		// NH투자증권 주문체결 처리
		} else if(message.indexOf("[NH투자증권]") != -1 && message.indexOf("주문체결 알림") != -1) {
			messageTypeCd = "TLGRM_CMD_CD_00005";
		}
		
		return messageTypeCd;
	}
}
