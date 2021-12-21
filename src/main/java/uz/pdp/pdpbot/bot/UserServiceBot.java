package uz.pdp.pdpbot.bot;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.pdpbot.controller.TimeControl;
import uz.pdp.pdpbot.entity.*;
import uz.pdp.pdpbot.model.Response;
import uz.pdp.pdpbot.repository.RegionsRepository;
import uz.pdp.pdpbot.repository.SurveyRepository;
import uz.pdp.pdpbot.repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;


@Component
public class UserServiceBot {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BaseBot baseBot;
    @Autowired
    RegionsRepository regionsRepository;

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
        List<User> all = userRepository.findByRole(Role.ROLE_SUPERVISOR);
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

    public ReplyKeyboardMarkup getregion() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<Regions> all = regionsRepository.findAll();
        for (Regions reg : all) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(reg.getRegionName()));
            keyboardRows.add(keyboardRow);
        }
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(Constant.BACK_M));
        keyboardRows.add(keyboardRow1);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getShop(Regions regions) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<User> all = userRepository.findByRoleAndRegions(Role.ROLE_SHOP, regions);
        for (User shop : all) {
            List<InlineKeyboardButton> keyboardRows1 = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            keyboardRows1.add(inlineKeyboardButton.setText(shop.getNameShop()).setCallbackData(shop.getNameShop()));
            rowList.add(keyboardRows1);
        }
        List<InlineKeyboardButton> keyboardRows2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        keyboardRows2.add(inlineKeyboardButton.setText(Constant.BACK).setCallbackData(Constant.BACK));
        rowList.add(keyboardRows2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup Find_Shop(Regions regions, String name) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<User> all = userRepository.findByRoleAndRegionsAndNameShopContainingIgnoreCase(Role.ROLE_SHOP, regions, name);
        for (User shop : all) {
            List<InlineKeyboardButton> keyboardRows1 = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            keyboardRows1.add(inlineKeyboardButton.setText(shop.getNameShop()).setCallbackData(String.valueOf(shop.getId())));
            rowList.add(keyboardRows1);
        }
        List<InlineKeyboardButton> keyboardRows2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        keyboardRows2.add(inlineKeyboardButton.setText(Constant.BACK).setCallbackData(Constant.BACK));
        rowList.add(keyboardRows2);


        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup Find_Shop_Day(Regions regions, String day) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<User> all = userRepository.findByRoleAndRegionsAndDayRegion(Role.ROLE_SHOP, regions, day);
        for (User shop_day : all) {
            List<InlineKeyboardButton> keyboardRows1 = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            keyboardRows1.add(inlineKeyboardButton.setText(shop_day.getNameShop()).setCallbackData(String.valueOf(shop_day.getId())));
            rowList.add(keyboardRows1);
        }
        List<InlineKeyboardButton> keyboardRows2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        keyboardRows2.add(inlineKeyboardButton.setText(Constant.BACK).setCallbackData(Constant.BACK));
        rowList.add(keyboardRows2);


        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
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

    public ReplyKeyboardMarkup back() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Constant.BACK);
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

    public ReplyKeyboardMarkup Start_AgentON() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();


        keyboardRow.add(new KeyboardButton("Ish boshlash  \uD83D\uDCCD").setRequestLocation(true));
        keyboardRow1.add(Constant.WORK_LIST);

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);


        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup Start_AgentOFF() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();

        keyboardRow.add(new KeyboardButton("Ish tugatish  \uD83D\uDCCD").setRequestLocation(true));
        keyboardRow1.add(Constant.ADD_SHOP);
        keyboardRow1.add(Constant.ADD_ORDER);
        keyboardRow2.add(Constant.LIST_SHOP);
        keyboardRow2.add(Constant.LIST_ORDER);
        keyboardRow3.add(Constant.FIND_SHOP);
        keyboardRow3.add(Constant.WORK_LIST);

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup Start_Shop() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(Constant.START_SHOP).setRequestLocation(true));
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup Add_Shop_Finish() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow.add(Constant.BACK);
        keyboardRow1.add(Constant.DEL_SHOP_OK);
        keyboardRow2.add(Constant.ADD_SHOP_OK);
        keyboardRow3.add(Constant.ADD_SHOP_SMART);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
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


    public ReplyKeyboardMarkup menu_shop() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();


        keyboardRow.add(Constant.INFO);
        keyboardRow1.add(Constant.SHIKOYAT);
        keyboardRow1.add(Constant.TAKLIF);
        keyboardRow2.add(Constant.KURS);
        keyboardRow2.add(Constant.POGODA);

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup location(String call) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("location").setCallbackData(call);
        keyboardRows.add(inlineKeyboardButton);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRows));
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getType() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();


        keyboardRow1.add(Type.FIVE_BALL.name());
        keyboardRow2.add(Type.COMMIT.name());

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public static Response getWeatherFromLocation(Double lat, Double lon) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=f896915e4f2e4765f254632549b41209");

        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Gson gson = new Gson();
        Response response = gson.fromJson(bufferedReader, Response.class);
        return response;
    }

    public TimeControl DifferentiateTime(String times) {
        int soat = Integer.parseInt(times.substring(0, 2));
        int minut = Integer.parseInt(times.substring(3, 5));
        LocalTime localTime = LocalTime.of(soat, minut);
        LocalTime localTime1 = LocalTime.now();
        String time = String.valueOf(Duration.between(localTime1, localTime));
        String a = time.replace("PT-", "Kechikish \uD83D\uDE21 ").replace("H-", " soat ").replace("M-", " min.").replace("PT", "Erta \uD83D\uDE0A  ").replace("H", " soat ").replace("M", " min.");
        String b = a.substring(0, a.length() - 7);
        return new TimeControl(b);
    }

    public TimeControl WorkTime(String on) {
        int hhon = Integer.parseInt(on.substring(0, 2));
        int mmon = Integer.parseInt(on.substring(3, 5));
        LocalTime localTime = LocalTime.of(hhon, mmon);
        LocalTime localTime1 = LocalTime.now();
        String time = String.valueOf(Duration.between(localTime, localTime1));
        String a = time.replace("PT", " ").replace("H", " soat ").replace("M", " min.");
        String b = a.substring(0, a.length() - 7);
        return new TimeControl(b);
    }

    public ReplyKeyboardMarkup Kuni() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow.add("111");
        keyboardRow.add("222");
        keyboardRow.add("333");
        keyboardRow1.add("444");
        keyboardRow1.add("555");
        keyboardRow1.add("666");
        keyboardRow2.add(Constant.BACK);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public Currency[] getOneInfo(String valyuta) throws IOException, IOException {
        Gson gson = new Gson();
        LocalDate localDate = LocalDate.now();
        URL url = new URL("https://cbu.uz/oz/arkhiv-kursov-valyut/json/" + valyuta + "/" + localDate + "/");


        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        Currency[] currencies = gson.fromJson(reader, Currency[].class);

        //OneCurrencyItem
        return currencies;
    }


}
