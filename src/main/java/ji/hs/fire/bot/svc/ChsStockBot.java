package ji.hs.fire.bot.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ChsStockBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		
		if(update.hasMessage()) {
			System.out.println(update.getMessage().getFrom().getFirstName()); //get ID 는 suer id
			System.out.println(update.getMessage().getFrom().getLastName()); //get ID 는 suer id
			System.out.println(update.getMessage().getMessageId());  // 메시지의 ID
			System.out.println(update.getMessage().getText());  // 받은 TEXT
			
			List<List<InlineKeyboardButton>> list = new ArrayList<>();
			
			List<InlineKeyboardButton> keyboardList1 = new ArrayList<>();
			
			InlineKeyboardButton button1 = new InlineKeyboardButton();
			button1.setText("버튼1");
			button1.setCallbackData(Integer.toString(update.getMessage().getMessageId()));
			keyboardList1.add(button1);
			
			List<InlineKeyboardButton> keyboardList2 = new ArrayList<>();
			InlineKeyboardButton button2 = new InlineKeyboardButton();
			button2.setText("버튼2");
			button2.setCallbackData("2번 버튼 콜백 데이터");
			keyboardList2.add(button2);
			
			list.add(keyboardList1);
			list.add(keyboardList2);
			
			SendMessage msg = new SendMessage();
			msg.setChatId(update.getMessage().getChatId());
			msg.setText("메뉴를 선택하세요");
			msg.setReplyToMessageId(update.getMessage().getMessageId());
			msg.setReplyMarkup(new InlineKeyboardMarkup(list));
			
			try {
				execute(msg);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(update.hasCallbackQuery()) {
			EditMessageText msg = new EditMessageText();
			msg.setChatId(update.getCallbackQuery().getMessage().getChatId());
			msg.setText("처리되었습니다.");
			msg.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
			
			try {
				execute(msg);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(update.getCallbackQuery().getData());
		}
		
	}

	@Override
	public String getBotUsername() {
		return "ChsStockBot";
	}

	@Override
	public String getBotToken() {
		return "5975226901:AAGVppK63tWgJEXSdy8TImJ4bu318FYvDR0";
	}
}
