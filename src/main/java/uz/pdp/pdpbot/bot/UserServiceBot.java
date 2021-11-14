package uz.pdp.pdpbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.pdpbot.entity.*;
import uz.pdp.pdpbot.repository.GroupRepository;
import uz.pdp.pdpbot.repository.SurveyRepository;
import uz.pdp.pdpbot.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserServiceBot {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BaseBot baseBot;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SurveyRepository surveyRepository;


    public ReplyKeyboardMarkup addStudent() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow.add(Constant.ADD_GROUP);
        keyboardRow2.add(Constant.ADD_STUDENT);
        keyboardRow1.add(Constant.GET_GROUP);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup delateadmins() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<User> all = userRepository.findByRole(Role.ROlE_ADMIN);
        for (User admin : all) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(admin.getPhoneNumber()));
            keyboardRows.add(keyboardRow);
        }
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(Constant.BACK_MENU));
        keyboardRows.add(keyboardRow1);


        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup delatemanager() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<User> all = userRepository.findByRole(Role.ROLE_MANAGER);
        for (User manager : all) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(manager.getPhoneNumber()));
            keyboardRows.add(keyboardRow);
        }
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(Constant.BACK_M));
        keyboardRows.add(keyboardRow1);


        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup sendnumber() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(Constant.SEND_CONTACT).setRequestContact(true));
        keyboardRow1.add(new KeyboardButton("Bosh menu   \uD83C\uDFE0 "));
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getgroup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<Group> all = groupRepository.findAll();
        for (Group group : all) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(group.getName()));
            keyboardRows.add(keyboardRow);
        }
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(Constant.BACK_M));
        keyboardRows.add(keyboardRow1);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getSurvey() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<Survey> all = surveyRepository.findAll();
        for (Survey survey : all) {
            if (survey.getTitle() != null) {
                KeyboardRow keyboardRow = new KeyboardRow();
                keyboardRow.add(new KeyboardButton(survey.getTitle()));
                keyboardRows.add(keyboardRow);

            }
        }

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(Constant.BACK_M));
        keyboardRows.add(keyboardRow1);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;

    }

    public ReplyKeyboardMarkup backAdmin() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Constant.BACK_ADMIN);
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup fifeBall() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton.setText(" 1 ").setCallbackData("1");
        inlineKeyboardButton1.setText(" 2 ").setCallbackData("2");
        inlineKeyboardButton2.setText(" 3 ").setCallbackData("3");
        inlineKeyboardButton3.setText(" 4 ").setCallbackData("4");
        inlineKeyboardButton4.setText(" 5 ").setCallbackData("5");
        keyboardRows.add(inlineKeyboardButton);
        keyboardRows.add(inlineKeyboardButton1);
        keyboardRows.add(inlineKeyboardButton2);
        keyboardRows.add(inlineKeyboardButton3);
        keyboardRows.add(inlineKeyboardButton4);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRows));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup teenBall() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton8 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton9 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows1 = new ArrayList<>();
        inlineKeyboardButton.setText(" 1 ").setCallbackData("1");
        inlineKeyboardButton1.setText(" 2 ").setCallbackData("2");
        inlineKeyboardButton2.setText(" 3 ").setCallbackData("3");
        inlineKeyboardButton3.setText(" 4 ").setCallbackData("4");
        inlineKeyboardButton4.setText(" 5 ").setCallbackData("5");
        inlineKeyboardButton5.setText(" 6 ").setCallbackData("6");
        inlineKeyboardButton6.setText(" 7 ").setCallbackData("7");
        inlineKeyboardButton7.setText(" 8 ").setCallbackData("8");
        inlineKeyboardButton8.setText(" 9 ").setCallbackData("9");
        inlineKeyboardButton9.setText(" 10 ").setCallbackData("10");
        keyboardRows.add(inlineKeyboardButton);
        keyboardRows.add(inlineKeyboardButton1);
        keyboardRows.add(inlineKeyboardButton2);
        keyboardRows.add(inlineKeyboardButton3);
        keyboardRows.add(inlineKeyboardButton4);
        keyboardRows1.add(inlineKeyboardButton5);
        keyboardRows1.add(inlineKeyboardButton6);
        keyboardRows1.add(inlineKeyboardButton7);
        keyboardRows1.add(inlineKeyboardButton8);
        keyboardRows1.add(inlineKeyboardButton9);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRows);
        rowList.add(keyboardRows1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup Start_Student() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Constant.START_STUDENT);
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup startManager() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow.add(Constant.GET_QUESTION);
        keyboardRow1.add(Constant.ADD_QUESTION);
        keyboardRow1.add(Constant.LIST_QUESTION);
        keyboardRow2.add(Constant.GET_RESULT);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
//        keyboardRows.add(keyboardRow3);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup survey_comment(String call) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        inlineKeyboardButton.setText("So`rovnomadan o`tish").setCallbackData(call);

        keyboardRows.add(inlineKeyboardButton);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRows));
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getType() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();


        keyboardRow.add(Type.TEEN_BAll.name());
        keyboardRow1.add(Type.FIVE_BALL.name());
        keyboardRow2.add(Type.COMMIT.name());

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}
