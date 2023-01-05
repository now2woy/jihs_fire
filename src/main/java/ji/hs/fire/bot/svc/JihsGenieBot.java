package ji.hs.fire.bot.svc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.vo.ActVO;
import ji.hs.fire.bsc.mpr.BscCdMapper;
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
	 * 
	 */
	@Override
	public void onUpdateReceived(Update update) {
		try {
			// 메시지일 경우
			if(update.hasMessage()) {
				// 메시지가 "?"일 경우
				if("?".equals(update.getMessage().getText())) {
					SendMessage message = new SendMessage();
					message.setChatId(update.getMessage().getChatId());
					message.setText("메뉴를 선택하세요");
					message.setReplyToMessageId(update.getMessage().getMessageId());
					message.setReplyMarkup(new InlineKeyboardMarkup(getAllMenuList()));
					
					execute(message);
				}
				// 콜백쿼리일 경우
			} else if(update.hasCallbackQuery()) {
				String cd = StringUtils.defaultString(update.getCallbackQuery().getData(), "");
				
				// "A"로 시작할 경우
				if(cd.startsWith("A")) {
					// 계좌목록일 경우
					if(cd.equals("A_00001")) {
						SendMessage message = new SendMessage();
						message.setChatId(update.getCallbackQuery().getMessage().getChatId());
						message.setText("계좌를 선택하세요");
						message.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());
						message.setReplyMarkup(new InlineKeyboardMarkup(getQ1stMenuList(Long.toString(update.getCallbackQuery().getMessage().getChatId()))));
						
						execute(message);
					}
					
					BscCdVO paramBscCdVO = new BscCdVO();
					paramBscCdVO.setCdCol("TLGRM_MSG_CD");
					paramBscCdVO.setCd(cd.substring(2));
					
					BscCdVO bscCdVO = bscCdMapper.selectOne(paramBscCdVO);
					
					// 버튼 메시지를 변환한다.
					EditMessageText message = new EditMessageText();
					message.setChatId(update.getCallbackQuery().getMessage().getChatId());
					message.setText(bscCdVO.getCdNm() + "을(를) 선택하였습니다.");
					message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
					
					execute(message);
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
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
			line.add(InlineKeyboardButton.builder().text(bscCdVO.getCdNm()).callbackData("A_" + bscCdVO.getCd()).build());
		}
		
		menuList.add(line);
		
		return menuList;
	}
	
	/**
	 * Q_00001 계좌 목록
	 * @return
	 */
	private List<List<InlineKeyboardButton>> getQ1stMenuList(String tlgrmId) throws Exception {
		List<List<InlineKeyboardButton>> menuList = new ArrayList<>();
		
		ActVO paramActVO = new ActVO();
		paramActVO.setTlgrmId(tlgrmId);
		
		for(ActVO actVO : actMapper.selectAll(paramActVO)) {
			List<InlineKeyboardButton> line = new ArrayList<>();
			line.add(InlineKeyboardButton.builder().text(actVO.getBkNm() + " " + actVO.getActCdNm()).callbackData("B_00001_" + actVO.getActSeq()).build());
			
			menuList.add(line);
		}
		
		return menuList;
	}
}
