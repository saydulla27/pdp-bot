package uz.pdp.pdpbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.pdpbot.entity.Group;
import uz.pdp.pdpbot.entity.Role;
import uz.pdp.pdpbot.entity.User;
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
        keyboardRow1.add(new KeyboardButton("Bosh menu"));
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

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        KeyboardRow keyboardRow5 = new KeyboardRow();
        KeyboardRow keyboardRow6 = new KeyboardRow();
        KeyboardRow keyboardRow7 = new KeyboardRow();
        keyboardRow.add(Constant.STANDART_SAVOLLAR);
        keyboardRow1.add(Constant.Mentor_tushuntirishini_baxolash);
        keyboardRow2.add(Constant.Pdp_programmasi_baxolash);
        keyboardRow3.add(Constant.Pdp_sharoiti_baxolash);
        keyboardRow4.add(Constant.Pdp_tavsiya_ehtimoli);
        keyboardRow5.add(Constant.Pdp_nimani_ozgartirishi);
        keyboardRow6.add(Constant.Mentor_darsini_baxolash);
        keyboardRow7.add(Constant.BACK_M);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        keyboardRows.add(keyboardRow5);
        keyboardRows.add(keyboardRow6);
        keyboardRows.add(keyboardRow7);
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

    public InlineKeyboardMarkup start_survey() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Sorovnomadan o`tish").setCallbackData("sur");
        keyboardRows.add(inlineKeyboardButton);

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
        keyboardRow2.add(Constant.DEL_QUESTION);
        keyboardRow2.add(Constant.GET_RESULT);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
//        keyboardRows.add(keyboardRow3);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup Mentor_darsini_baxolash() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows4 = new ArrayList<>();
        inlineKeyboardButton.setText(" 1 – bilimlari yetarli emas").setCallbackData("1");
        inlineKeyboardButton1.setText(" 2 – bilimlari pastroq").setCallbackData("2");
        inlineKeyboardButton2.setText(" 3 – bilimlari o’rta darajada").setCallbackData("3");
        inlineKeyboardButton3.setText(" 4 – bilimlari yetarli").setCallbackData("4");
        inlineKeyboardButton4.setText(" 5 – bilimlari juda ham kuchli").setCallbackData("5");
        keyboardRows.add(inlineKeyboardButton);
        keyboardRows1.add(inlineKeyboardButton1);
        keyboardRows2.add(inlineKeyboardButton2);
        keyboardRows3.add(inlineKeyboardButton3);
        keyboardRows4.add(inlineKeyboardButton4);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardRows);
        rowList.add(keyboardRows1);
        rowList.add(keyboardRows2);
        rowList.add(keyboardRows3);
        rowList.add(keyboardRows4);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup survey_comment(String call) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Sorovnomadan o`tish").setCallbackData(call);
        keyboardRows.add(inlineKeyboardButton);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRows));
        return inlineKeyboardMarkup;
    }
}
