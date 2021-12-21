package uz.pdp.pdpbot.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.pdpbot.controller.TimeControl;
import uz.pdp.pdpbot.entity.*;
import uz.pdp.pdpbot.model.Response;
import uz.pdp.pdpbot.model.WeatherItem;
import uz.pdp.pdpbot.repository.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class BaseBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    String botToken;

    @Value("${bot.username}")
    String username;

    private Long userChatId;
    private String userMessage;


    @Autowired
    AgentPlaneRepository agentPlaneRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserServiceBot userServiceBot;
    @Autowired
    RegionsRepository regionsRepository;
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    UserResoultRepository userResoultRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AgentWorkShopRepository agentWorkShopRepository;
    @Autowired
    AgentHistoryRepository agentHistoryRepository;


    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
        String strDate = dateFormat.format(date);
        String strTime = dateFormat1.format(date);
        int day = Integer.parseInt(strDate.substring(0, 2));
        String moonYear = (strDate.substring(3, 10));
        int hour = Integer.parseInt(strTime.substring(0, 2));
        int min = Integer.parseInt(strTime.substring(3, 4));

        User client = null;
        User user = null;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                userChatId = update.getMessage().getChatId();
                String text = update.getMessage().getText();
                String ism = update.getMessage().getFrom().getFirstName();
                if (text.equals("/start")) {
                    userMessage = Constant.WELCOME_TEXT;
                    Optional<User> byChatId = userRepository.findByChatId(userChatId);
                    if (!byChatId.isPresent()) {
                        User u1 = new User();
                        if (userChatId == 1637495326) {
                            u1.setRole(Role.ROlE_DIRECTOR);
                            u1.setChatId(userChatId);
                            u1.setState(State.DIRECTOR_START);
                            u1.setFullName("Sadi");
                            u1.setPhoneNumber("+998917706311");
                            userMessage = "Director";
                            superMenu();

                        } else {
                            u1.setChatId(userChatId);
                            u1.setState(State.START);
                            u1.setRole(Role.ROLE_USER);
                            menu();
                        }
                        userRepository.save(u1);
                    }

                } else {
                    Optional<User> byChatId = userRepository.findByChatId(userChatId);
                    if (byChatId.isPresent()) {
                        if (text.equals("/restart")) {
                            byChatId.get().setState(State.START);
                            byChatId.get().setBuffer(0);
                            byChatId.get().setChatId(userChatId);
                            byChatId.get().setHistory(0);
                            userRepository.save(byChatId.get());
                            userMessage = "restart";
                            menu();
                        } else
                            user = byChatId.get();
                        String state = user.getState();
                        switch (state) {
                            case State.START:
                                switch (text) {
                                    case Constant.PDP_SEND_CONTACT:
                                        userMessage = "Raqam yuborish tugmasini bosing  ⤵️";
                                        user.setState(State.SEND_CONTACT);
                                        user.setBuffer(userChatId);
                                        userRepository.save(user);
                                        execute(userServiceBot.sendnumber(), null);
                                        break;

                                    case Constant.PDP_INFO:
                                        userMessage = "Assalom aleykum";
                                        menu();
                                        break;

                                    default:
                                        menu();
                                        break;

                                }
                                break;
                            case State.SEND_CONTACT:
                                if (!text.isEmpty()) {
                                    userMessage = "Iltimos raqam yuborish  tugmasidan foydalaning";
                                    user.setState(State.START);
                                    userRepository.save(user);
                                    menu();
                                    break;
                                }
                                break;


                            case State.DIRECTOR_START:
                                switch (text) {
                                    case Constant.ADD_ADMIN:
                                        User admin = new User();
                                        admin.setState(State.REG_ADMIN_PHONE);
                                        admin.setRole(Role.ROLE_SUPERVISOR);
                                        userRepository.save(admin);
                                        user.setState(State.S_ADD_ADMIN);
                                        userRepository.save(user);
                                        userMessage = "raqam yozing";
                                        execute(null, null);
                                        break;

                                    case Constant.DEL_ADMIN:
                                        List<User> byRole = userRepository.findByRole(Role.ROLE_SUPERVISOR);
                                        if (!byRole.isEmpty()) {
                                            userMessage = "o`chadigan adminnni tanlang";
                                            user.setState(State.DELETE_ADMIN_1);
                                            userRepository.save(user);
                                            execute(userServiceBot.delateadmins(), null);
                                            break;
                                        } else
                                            userMessage = "admin mavjud emas";
                                        user.setState(State.DIRECTOR_START);
                                        userRepository.save(user);
                                        superMenu();
                                        break;

                                    case Constant.ADD_MANAGER:
                                        User manager = new User();
                                        manager.setState(State.REG_MANAGER_PHONE);
                                        manager.setRole(Role.ROLE_MANAGER);
                                        userRepository.save(manager);
                                        user.setState(State.S_ADD_MANAGER);
                                        userRepository.save(user);
                                        userMessage = "raqam yozing";
                                        execute(null, null);
                                        break;

                                    case Constant.DEL_MANAGER:
                                        List<User> byRole1 = userRepository.findByRole(Role.ROLE_MANAGER);
                                        if (!byRole1.isEmpty()) {
                                            userMessage = "o`chadigan managerni tanlang";
                                            user.setState(State.DELETE_MANAGER_1);
                                            userRepository.save(user);
                                            execute(userServiceBot.delatemanager(), null);
                                            break;
                                        } else
                                            userMessage = "manager mavjud emas";
                                        user.setState(State.DIRECTOR_START);
                                        userRepository.save(user);
                                        superMenu();

                                        break;

                                    case State.BACK_MENU:
                                        superMenu();
                                        break;
                                }
                                break;

                            case State.DELETE_ADMIN_1:
                                if (!text.isEmpty()) {
                                    if (text.equals(Constant.BACK_MENU)) {
                                        userMessage = "super menu";
                                        user.setState(State.DIRECTOR_START);
                                        userRepository.save(user);
                                        superMenu();
                                        break;
                                    } else
                                        user.setState(State.DIRECTOR_START);
                                    userRepository.save(user);
                                    Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(text);
                                    userRepository.delete(byPhoneNumber.get());
                                    userMessage = "admin delete";
                                    superMenu();
                                    break;
                                }
                                break;

                            case State.DELETE_MANAGER_1:
                                if (!text.isEmpty()) {
                                    if (text.equals(Constant.BACK_M)) {
                                        userMessage = "super menu";
                                        user.setState(State.DIRECTOR_START);
                                        userRepository.save(user);
                                        superMenu();
                                        break;
                                    } else
                                        user.setState(State.DIRECTOR_START);
                                    userRepository.save(user);
                                    Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(text);
                                    userRepository.delete(byPhoneNumber.get());
                                    userMessage = "Manager delete";
                                    superMenu();
                                    break;
                                }
                                break;


                            case State.S_ADD_ADMIN:
                                if (!text.isEmpty()) {
                                    Optional<User> byState = userRepository.findByState(State.REG_ADMIN_PHONE);
                                    byState.get().setPhoneNumber(text);
                                    byState.get().setState(State.REG_ADMIN_PHONE_1);
                                    userRepository.save(byState.get());
                                    user.setState(State.S_ADD_ADMIN_NAME);
                                    userRepository.save(user);
                                    userMessage = "Ismini kiriting";
                                    execute(null, null);
                                }
                                break;

                            case State.S_ADD_ADMIN_NAME:
                                if (!text.isEmpty()) {
                                    Optional<User> byState = userRepository.findByState(State.REG_ADMIN_PHONE_1);
                                    byState.get().setFullName(text);
                                    byState.get().setState(State.REG_ADMIN_OK);
                                    userRepository.save(byState.get());
                                    user.setState(State.DIRECTOR_START);
                                    userRepository.save(user);
                                    userMessage = "ok";
                                }
                                superMenu();
                                break;

                            case State.S_ADD_MANAGER:
                                if (!text.isEmpty()) {
                                    Optional<User> byState = userRepository.findByState(State.REG_MANAGER_PHONE);
                                    byState.get().setPhoneNumber(text);
                                    byState.get().setState(State.REG_MANAGER_PHONE_1);
                                    userRepository.save(byState.get());
                                    user.setState(State.S_ADD_MANAGER_NAME);
                                    userRepository.save(user);
                                    userMessage = "Ismini kiriting";
                                    execute(null, null);
                                }
                                break;

                            case State.S_ADD_MANAGER_NAME:
                                if (!text.isEmpty()) {
                                    Optional<User> byState = userRepository.findByState(State.REG_MANAGER_PHONE_1);
                                    byState.get().setFullName(text);
                                    byState.get().setState(State.REG_MANAGER_OK);
                                    userRepository.save(byState.get());
                                    user.setState(State.DIRECTOR_START);
                                    userRepository.save(user);
                                    userMessage = "ok";
                                }
                                superMenu();
                                break;


                            case State.START_AGENT:
                                switch (text) {
                                    case Constant.WORK_LIST:
                                        send_plane_list(user, moonYear);
                                        userMessage = "Yaxshi dam oling ";
                                        execute(userServiceBot.Start_AgentON(), null);
                                        break;
                                }
                                break;


                            case State.AGENT_ON:
                                switch (text) {
                                    case Constant.ADD_SHOP:
                                        userMessage = "Telefon raqamini kiriting ";
                                        user.setState(State.A_ADD_SHOP);
                                        userRepository.save(user);
                                        execute(userServiceBot.back(), null);
                                        break;
                                    case Constant.LIST_SHOP:
                                        userMessage = "Kunini tanlang";
                                        user.setState(State.A_FIND_SHOP_1);
                                        userRepository.save(user);
                                        execute(userServiceBot.Kuni(), null);
                                        break;
                                    case Constant.FIND_SHOP:
                                        userMessage = "xarf kiriting";
                                        user.setState(State.A_FIND_SHOP);
                                        userRepository.save(user);
                                        execute(userServiceBot.back(), null);
                                        break;
                                    case Constant.ADD_ORDER:
                                        userMessage = "Do`konni toping : \uD83D\uDD0D";
                                        user.setState(State.A_FIND_ADD);
                                        userRepository.save(user);
                                        execute(userServiceBot.back(), null);
                                        break;
                                    case Constant.LIST_ORDER:
                                        send_work_list(user, strDate);
                                        userMessage = "Kuningizdan unumli foydalaning ";
                                        execute(userServiceBot.Start_AgentOFF(), null);
                                        break;

                                    case Constant.WORK_LIST:
                                        send_plane_list(user, moonYear);
                                        userMessage = "Kuningizdan unumli foydalaning ";
                                        execute(userServiceBot.Start_AgentOFF(), null);
                                        break;
                                }
                                break;

                            case State.A_FIND_ADD:
                                if (text.equals(Constant.BACK)) {
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    userMessage = "ishlash kere";
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;
                                } else
                                    userMessage = "<b>" + text + "</b>" + "  qidirilmoqda";
                                execute(userServiceBot.back(), userServiceBot.Find_Shop(user.getRegions(), text));
                                break;


                            case State.A_FIND_SHOP_1:
                                if (text.equals(Constant.BACK)) {
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    userMessage = "Ishla";
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;
                                } else
                                    userMessage = text + " kunidagi do`konlar";
                                user.setState(State.A_FIND_SHOP_3);
                                userRepository.save(user);
                                execute(userServiceBot.Kuni(), userServiceBot.Find_Shop_Day(user.getRegions(), text));
                                break;

                            case State.A_FIND_SHOP_3:
                                if (text.equals(Constant.BACK)) {
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    userMessage = "Ishla";
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;
                                } else ;
                                userMessage = text + " kunidagi do`konlar";
                                user.setState(State.A_FIND_SHOP_3);
                                userRepository.save(user);
                                execute(userServiceBot.Kuni(), userServiceBot.Find_Shop_Day(user.getRegions(), text));
                                break;

                            case State.A_FIND_SHOP_2:
                                if (text.equals(Constant.BACK)) {
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    userMessage = "Ishla";
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;
                                } else
                                    userMessage = text + " kunidagi do`konlar";
                                user.setState(State.A_FIND_SHOP_3);
                                userRepository.save(user);
                                execute(userServiceBot.Kuni(), userServiceBot.Find_Shop_Day(user.getRegions(), text));
                                break;

                            case State.A_FIND_SHOP:
                                if (text.equals(Constant.BACK)) {
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    userMessage = "ishlash kere";
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;
                                } else userMessage = "<b>" + text + "</b>" + "  qidirilmoqda";
                                execute(userServiceBot.back(), userServiceBot.Find_Shop(user.getRegions(), text));
                                break;

                            case State.A_ADD_SHOP:
                                if (text.equals(Constant.BACK)) {
                                    userMessage = "\uD83D\uDC4B";
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;
                                } else if (text.length() == 13 && text.substring(0, 1).equals("+")) {
                                    Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(text);
                                    if (!byPhoneNumber.isPresent()) {
                                        User shop = new User();
                                        shop.setRegions(user.getRegions());
                                        shop.setRole(Role.ROLE_SHOP);
                                        shop.setPhoneNumber(text.substring(1, 13));
                                        shop.setBuffer(userChatId);
                                        userRepository.save(shop);
                                        userMessage = "Do`konni nomini kiriting :";
                                        user.setState(State.A_ADD_SHOP_1);
                                        userRepository.save(user);
                                        execute(userServiceBot.back(), null);
                                        break;

                                    } else
                                        userMessage = "<b>Bu raqam egasi</b> " + byPhoneNumber.get().getNameShop();
                                    user.setState(State.AGENT_ON);
                                    userRepository.save(user);
                                    execute(userServiceBot.Start_AgentOFF(), null);
                                    break;

                                } else
                                    userMessage = "Raqamni to`gri tering (+998......)";
                                execute(null, null);
                                break;

                            case State.A_ADD_SHOP_3:
                                Optional<User> buffer2 = userRepository.findByBuffer(userChatId);
                                if (text.equals(Constant.BACK)) {
                                    user.setState(State.A_ADD_SHOP_1);
                                    userRepository.save(user);
                                    userMessage = "Dokon nomini kiriting :";
                                    execute(userServiceBot.back(), null);
                                    break;
                                }
                                userMessage = "Kunini tanlang";
                                buffer2.get().setShopOrienter(text);
                                userRepository.save(buffer2.get());
                                user.setState(State.A_ADD_SHOP_2);
                                userRepository.save(user);
                                execute(userServiceBot.Kuni(), null);
                                break;

                            case State.A_ADD_SHOP_1:
                                Optional<User> buffer = userRepository.findByBuffer(userChatId);
                                if (!text.isEmpty()) {
                                    if (text.equals(Constant.BACK)) {
                                        user.setState(State.A_ADD_SHOP);
                                        userRepository.save(user);
                                        userMessage = "Raqamni boshqattan kiriting:";
                                        userRepository.delete(buffer.get());
                                        execute(userServiceBot.back(), null);
                                        break;
                                    }
                                    userMessage = "Manzilini kiriting (ориентир)";
                                    buffer.get().setNameShop(text);
                                    userRepository.save(buffer.get());
                                    user.setState(State.A_ADD_SHOP_3);
                                    userRepository.save(user);
                                    execute(userServiceBot.back(), null);
                                    break;
                                }

                                break;

                            case State.A_ADD_SHOP_2:
                                Optional<User> buffer1 = userRepository.findByBuffer(userChatId);
                                if (!text.isEmpty()) {
                                    if (text.equals(Constant.BACK)) {
                                        user.setState(State.A_ADD_SHOP_3);
                                        userRepository.save(user);
                                        userMessage = "Manzilini kiriting (Ориентер) ";
                                        execute(userServiceBot.back(), null);
                                        break;
                                    }
                                    buffer1.get().setDayRegion(text);
                                    userRepository.save(buffer1.get());
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.
                                            append("<b>Do`kon nomi:</b>  " + buffer1.get().getNameShop()).
                                            append("\n<b>Do`kon manzili:</b>  " + buffer1.get().getShopOrienter()).
                                            append("\n<b>Do`kon kuni:</b>  " + buffer1.get().getDayRegion()).
                                            append("\n<b>Do`kon raqami:</b>  " + "+" + buffer1.get().getPhoneNumber());

                                    userMessage = String.valueOf(stringBuilder);
                                    user.setState(State.A_ADD_SHOP_4);
                                    userRepository.save(user);
                                    execute(userServiceBot.Add_Shop_Finish(), null);
                                    break;
                                }
                                break;

                            case State.A_ADD_SHOP_4:
                                Optional<User> buffer3 = userRepository.findByBuffer(userChatId);
                                switch (text) {
                                    case Constant.BACK:
                                        user.setState(State.A_ADD_SHOP_2);
                                        userRepository.save(user);
                                        userMessage = "Kunini tanlang";
                                        execute(userServiceBot.Kuni(), null);
                                        break;
                                    case Constant.ADD_SHOP_OK:
                                        userMessage = "Do`kon qo`shildi";
                                        user.setState(State.AGENT_ON);
                                        userRepository.save(user);
                                        buffer3.get().setBuffer(0);
                                        userRepository.save(buffer3.get());
                                        execute(userServiceBot.Start_AgentOFF(), null);
                                        break;
                                    case Constant.DEL_SHOP_OK:
                                        userMessage = "Bekor qilindi";
                                        userRepository.delete(buffer3.get());
                                        user.setState(State.AGENT_ON);
                                        userRepository.save(user);
                                        execute(userServiceBot.Start_AgentOFF(), null);
                                        break;
                                    case Constant.ADD_SHOP_SMART:
                                        userMessage = "Do`kon qo`shildi";
                                        user.setState(State.AGENT_ON);
                                        userRepository.save(user);
                                        buffer3.get().setBuffer(0);
                                        userRepository.save(buffer3.get());
                                        execute(userServiceBot.Start_AgentOFF(), null);
                                        break;
                                }
                                break;

                            case State.MENU_SHOP:

                                switch (text) {
                                    case Constant.KURS:
                                        userMessage = "";
                                        execute(userServiceBot.menu_shop(), null);
                                        break;
                                    case Constant.POGODA:
                                        getLocationWeather(user.getLat(), user.getLon(), strDate);
                                        userMessage = "Kuningiz hayrli bosin";
                                        execute(userServiceBot.menu_shop(), null);
                                        break;
                                    case Constant.INFO:
                                        userMessage = " Malumotlar ";
                                        execute(userServiceBot.menu_shop(), null);
                                        break;
                                    case Constant.SHIKOYAT:
                                        userMessage = "Biz tomonga shikoyatingiz bosa yozib yuboring :";
                                        user.setState(State.SHOP_SHIKOYAT);
                                        userRepository.save(user);
                                        execute(userServiceBot.back(), null);
                                        break;
                                    case Constant.TAKLIF:
                                        userMessage = "Bizga takliflaringiz bolsa yuboring :";
                                        user.setState(State.SHOP_TAKLIF);
                                        userRepository.save(user);
                                        execute(userServiceBot.back(), null);
                                        break;

                                }
                                break;

                            case State.SHOP_TAKLIF:
                                long taklif = -669509065;
                                if (text.equals(Constant.BACK)) {
                                    userMessage = "nima qilamiz";
                                    user.setState(State.MENU_SHOP);
                                    userRepository.save(user);
                                    execute(userServiceBot.menu_shop(), null);
                                    break;
                                }
                                user.setState(State.MENU_SHOP);
                                userRepository.save(user);
                                send_taklif(user, taklif, text);
                                userMessage = "Izohingiz uchun raxmat ";
                                execute(userServiceBot.menu_shop(), null);
                                break;

                            case State.SHOP_SHIKOYAT:
                                long shikoyat = -669509065;
                                if (text.equals(Constant.BACK)) {
                                    userMessage = "nima qilamiz";
                                    user.setState(State.MENU_SHOP);
                                    userRepository.save(user);
                                    execute(userServiceBot.menu_shop(), null);
                                    break;
                                }
                                user.setState(State.MENU_SHOP);
                                userRepository.save(user);
                                send_shikoyat(user, shikoyat, text);
                                userMessage = "O`rganib chiqamiz";
                                execute(userServiceBot.menu_shop(), null);
                                break;

                            case State.A_FIND_ADD_1:
                                if (text.equals(Constant.BACK)) {
                                    userMessage = "Do`konni toping : \uD83D\uDD0D";
                                    user.setState(State.A_FIND_ADD);
                                    userRepository.save(user);
                                    execute(userServiceBot.back(), null);
                                    break;
                                }
                                userMessage = "Do`konni toping : \uD83D\uDD0D";
                                user.setState(State.A_FIND_ADD);
                                userRepository.save(user);
                                execute(userServiceBot.back(), null);
                                break;

                            default:
                                send_massage(userChatId, "xato");
                        }
                    } else send_massage(userChatId, "/start");
                }
            }
            if (update.getMessage().hasContact()) {
                String text = update.getMessage().getText();
                userChatId = update.getMessage().getChatId();
                String ism = update.getMessage().getFrom().getFirstName();
                String familya = update.getMessage().getFrom().getLastName();
                String phone = update.getMessage().getContact().getPhoneNumber();
                if (phone.length() > 12) {
                    phone = phone.substring(1, 13);
                } else phone = update.getMessage().getContact().getPhoneNumber();
                Optional<User> optionalUser = userRepository.findByPhoneNumber(phone);
                Optional<User> userBuffer = userRepository.findByBuffer(userChatId);
                if (optionalUser.isPresent()) {
                    if (optionalUser.get().getRole().equals(Role.ROLE_SUPERVISOR)) {
                        userRepository.delete(userBuffer.get());
                        optionalUser.get().setChatId(userChatId);
                        optionalUser.get().setRole(Role.ROLE_SUPERVISOR);
                        optionalUser.get().setState(State.START_ADMIN);
                        optionalUser.get().setActive(true);
                        optionalUser.get().setFullName(ism + " " + familya);
                        userRepository.save(optionalUser.get());
                        userMessage = "Assalom aleykum  " + ism;
                        execute(userServiceBot.addStudent(), null);
                    }
                    if (optionalUser.get().getRole().equals(Role.ROLE_MANAGER)) {
                        userRepository.delete(userBuffer.get());
                        optionalUser.get().setChatId(userChatId);
                        optionalUser.get().setRole(Role.ROLE_MANAGER);
                        optionalUser.get().setState(State.START_MANAGER);
                        optionalUser.get().setActive(true);
                        optionalUser.get().setFullName(ism + " " + familya);
                        userRepository.save(optionalUser.get());
                        userMessage = "Assalom aleykum  " + ism;
                        execute(userServiceBot.startManager(), null);

                    }
                    if (optionalUser.get().getRole().equals(Role.ROLE_SHOP)) {
                        userRepository.delete(userBuffer.get());
                        optionalUser.get().setChatId(userChatId);
                        optionalUser.get().setRole(Role.ROLE_SHOP);
                        optionalUser.get().setState(State.START_SHOP);
                        optionalUser.get().setActive(true);
                        optionalUser.get().setFullName(ism + " " + familya);
                        userRepository.save(optionalUser.get());
                        userMessage = "\uD83D\uDC4B  " + ism + "\n" + "shartnoma";
                        execute(userServiceBot.Start_Shop(), null);
                    }
                    if (optionalUser.get().getRole().equals(Role.ROLE_AGENT)) {
                        userRepository.delete(userBuffer.get());
                        optionalUser.get().setChatId(userChatId);
                        optionalUser.get().setRole(Role.ROLE_AGENT);
                        optionalUser.get().setState(State.START_AGENT);
                        optionalUser.get().setActive(true);
                        optionalUser.get().setFullName(optionalUser.get().getFullName());
                        userRepository.save(optionalUser.get());
                        userMessage = "Salom agent";
                        execute(userServiceBot.Start_AgentON(), null);
                    }

                } else {
                    Optional<User> byState = userRepository.findByState(State.SEND_CONTACT);
                    byState.get().setState(State.START);
                    userMessage = "Sizni tanimadik !!!";
                    byState.get().setChatId(userChatId);
                    userRepository.save(byState.get());
                    menu();
                }
            }
            if (update.getMessage().hasLocation()) {
                Float lat = update.getMessage().getLocation().getLatitude();
                Float lot = update.getMessage().getLocation().getLongitude();
                Optional<User> byChatId = userRepository.findByChatId(userChatId);
                user = byChatId.get();
                String state = user.getState();
                Response response = userServiceBot.getWeatherFromLocation(lat.doubleValue(), lot.doubleValue());
                double gradus = Math.ceil(response.getMain().getTemp() - 273);
                String locationName = response.getName();
                switch (state) {
                    case State.START_AGENT:
                        TimeControl timeControl = userServiceBot.DifferentiateTime(user.getOperatingModeON());
                        if (locationName.equals(user.getRegions().getLocationName())) {
                            if (user.getHistory() == day) {
                                userMessage = "ertani kuting";
                                execute(userServiceBot.Start_AgentON(), null);
                                break;
                            }
                            AgentHistory agentHistory = new AgentHistory();
                            agentHistory.setUser(user);
                            agentHistory.setBuffer(userChatId);
                            agentHistory.setLocationName(locationName);
                            agentHistory.setOnLat(lat);
                            agentHistory.setOnLon(lot);
                            agentHistory.setTimeON(strTime);
                            agentHistory.setDate(strDate);
                            agentHistory.setLateness(timeControl.getTimeOf());
                            agentHistoryRepository.save(agentHistory);
                            userMessage = "Xayrli kun " + "xavo xarorati " + gradus + "° " + "\nIsh vaqtingiz " + user.getOperatingModeON() + " - " + user.getOperatingModeOFF() + " gacha" + "\n" + timeControl.getTimeOf();
                            user.setState(State.AGENT_ON);
                            user.setHistory(day);
                            userRepository.save(user);
                            execute(userServiceBot.Start_AgentOFF(), null);
                            break;
                        } else
                            userMessage = "ishga chiq";
                        System.out.println(locationName);
                        execute(userServiceBot.Start_AgentON(), null);
                        break;
                    case State.AGENT_ON:
                        Optional<AgentHistory> history = agentHistoryRepository.findByBuffer(userChatId);
                        List<AgentWorkShop> yes = agentWorkShopRepository.findByUserAndDateAndSell(user, strDate, true);
                        List<AgentWorkShop> No = agentWorkShopRepository.findByUserAndDateAndSell(user, strDate, false);
                        int shopSize = (yes.size() + No.size());
                        TimeControl timeControl1 = userServiceBot.WorkTime(history.get().getTimeON());
                        history.get().setTimeOFF(strTime);
                        history.get().setOffLat(lat);
                        history.get().setOffLon(lot);
                        history.get().setWorkTime(timeControl1.getTimeOf());
                        history.get().setBuffer(null);
                        history.get().setWorkShopSize(String.valueOf(shopSize));
                        agentHistoryRepository.save(history.get());
                        userMessage = "Bugun siz " + history.get().getWorkTime() + " ishladingiz \n" + shopSize + " ta \uD83D\uDED2  kirdingiz";
                        user.setState(State.START_AGENT);
                        userRepository.save(user);
                        execute(userServiceBot.Start_AgentON(), null);
                        break;
                    case State.START_SHOP:
                        userMessage = "salom";
                        user.setLat(Double.valueOf(lat));
                        user.setLon(Double.valueOf(lot));
                        user.setState(State.MENU_SHOP);
                        userRepository.save(user);
                        execute(userServiceBot.menu_shop(), null);
                        break;
                }


            }

        } else if (update.hasCallbackQuery()) {
            userChatId = update.getCallbackQuery().getMessage().getChatId();
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            Optional<User> optionalUser = userRepository.findByChatId(userChatId);
            user = optionalUser.get();
            String state = user.getState();

            switch (state) {
                case State.A_FIND_SHOP:
                    if (call_data.equals(Constant.BACK)) {
                        backRemove("ok", message_id);
                        userMessage = "Foydali ish qiling";
                        user.setState(State.AGENT_ON);
                        userRepository.save(user);
                        execute(userServiceBot.Start_AgentOFF(), null);
                        break;
                    }
                    user.setState(State.A_FIND_SHOP_2);
                    userRepository.save(user);
                    Remove(Integer.valueOf(call_data), message_id);
                    break;
                case State.A_FIND_SHOP_2:
                    if (call_data.equals(Constant.BACK)) {
                        backRemove("ok", message_id);
                        userMessage = "Foydali ish qiling";
                        user.setState(State.AGENT_ON);
                        userRepository.save(user);
                        execute(userServiceBot.Start_AgentOFF(), null);
                        break;
                    }
                    sendLocation(Integer.valueOf(call_data), message_id);
                    userMessage = "ok";
                    user.setState(State.AGENT_ON);
                    userRepository.save(user);
                    execute(userServiceBot.Start_AgentOFF(), null);
                    break;
                case State.A_FIND_SHOP_3:
                    if (call_data.equals(Constant.BACK)) {
                        backRemove("ok", message_id);
                        userMessage = "Foydali ish qiling";
                        user.setState(State.AGENT_ON);
                        userRepository.save(user);
                        execute(userServiceBot.Start_AgentOFF(), null);
                        break;
                    }
                    user.setState(State.A_FIND_SHOP_2);
                    userRepository.save(user);
                    Remove(Integer.valueOf(call_data), message_id);
                    break;

                case State.A_FIND_ADD:
                    if (call_data.equals(Constant.BACK)) {
                        user.setState(State.AGENT_ON);
                        userRepository.save(user);
                        backRemove("ok", message_id);
                        userMessage = "Foydali ish qil";
                        execute(userServiceBot.Start_AgentOFF(), null);
                        break;
                    }
                    user.setState(State.A_FIND_ADD_1);
                    userRepository.save(user);
                    send_find(Integer.valueOf(call_data), message_id);
                    break;

                case State.A_FIND_ADD_1:
                    String id = (call_data.substring(0, call_data.length() - 1));
                    String data = call_data.substring(call_data.length() - 1);
                    call_data = data;
                    switch (call_data) {
                        case "b":
                            user.setState(State.AGENT_ON);
                            userRepository.save(user);
                            backRemove("ok", message_id);
                            userMessage = "xayrlu kun";
                            execute(userServiceBot.Start_AgentOFF(), null);
                            break;
                        case "+":
                            Optional<User> byId = userRepository.findById(Integer.valueOf(id));
                            AgentWorkShop agentWorkShop = new AgentWorkShop();
                            agentWorkShop.setShop(byId.get().getNameShop());
                            agentWorkShop.setDate(strDate);
                            agentWorkShop.setTime(strTime);
                            agentWorkShop.setUser(user);
                            agentWorkShop.setSell(true);
                            agentWorkShopRepository.save(agentWorkShop);
                            user.setState(State.A_FIND_ADD);
                            userRepository.save(user);
                            backRemove("Zakaz olindi", message_id);
                            userMessage = "Do`konni toping : \uD83D\uDD0D";
                            execute(userServiceBot.back(), null);
                            break;
                        case "-":
                            Optional<User> byId1 = userRepository.findById(Integer.valueOf(id));
                            AgentWorkShop agentWorkShop1 = new AgentWorkShop();
                            agentWorkShop1.setShop(byId1.get().getNameShop());
                            agentWorkShop1.setDate(strDate);
                            agentWorkShop1.setTime(strTime);
                            agentWorkShop1.setUser(user);
                            agentWorkShop1.setSell(false);
                            agentWorkShopRepository.save(agentWorkShop1);
                            user.setState(State.A_FIND_ADD);
                            userRepository.save(user);
                            backRemove("Zakaz yo`q", message_id);
                            userMessage = "Do`konni toping : \uD83D\uDD0D";
                            execute(userServiceBot.back(), null);
                            break;

                    }

                    break;
            }

        }
    }

    private void execute(ReplyKeyboardMarkup replyKeyboardMarkup,
                         InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChatId);
        sendMessage.setText(userMessage);
        sendMessage.setParseMode("HTML");
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
        }
        if (inlineKeyboardMarkup != null)
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute1(InlineKeyboardMarkup inlineKeyboardMarkup, Long studentChatId, String studentmassage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(studentChatId);
        sendMessage.setText(studentmassage);
        sendMessage.setParseMode("HTML");
        if (inlineKeyboardMarkup != null)
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ReplyKeyboardMarkup menu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow.add(Constant.PDP_INFO);
        keyboardRow1.add(Constant.PDP_SEND_CONTACT);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        execute(replyKeyboardMarkup, null);
        return replyKeyboardMarkup;
    }


    public ReplyKeyboardMarkup superMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow.add(Constant.ADD_ADMIN);
        keyboardRow1.add(Constant.ADD_MANAGER);
        keyboardRow2.add(Constant.DEL_ADMIN);
        keyboardRow3.add(Constant.DEL_MANAGER);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        execute(replyKeyboardMarkup, null);
        return replyKeyboardMarkup;

    }


    private void sender(InlineKeyboardMarkup inlineKeyboardMarkup, Long userChatId, String name) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChatId);
        sendMessage.setText(name);
        if (inlineKeyboardMarkup != null)
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send_massage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send_shikoyat(User user, Long chatId, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\uD83D\uDED2 <b>Do`kon</b> " + user.getNameShop())
                .append("\n\uD83C\uDFD9 <b>Rayon</b> " + user.getRegions().getRegionName())
                .append("\n\uD83D\uDCDE <b>Telefon</b>  " + "+" + user.getPhoneNumber())
                .append("\n")
                .append("\n")
                .append("\uD83D\uDDE3<b>Shikoyat</b> : ⤵️")
                .append("\n" + text);


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode("HTML");
        sendMessage.setText(String.valueOf(stringBuilder));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void send_taklif(User user, Long chatId, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\uD83D\uDED2 <b>Do`kon</b> " + user.getNameShop())
                .append("\n\uD83C\uDFD9 <b>Rayon</b> " + user.getRegions().getRegionName())
                .append("\n\uD83D\uDCDE <b>Telefon</b>  " + "+" + user.getPhoneNumber())
                .append("\n")
                .append("\n")
                .append("\uD83D\uDC64 <b>Taklif</b> : ⤵️")
                .append("\n" + text);


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode("HTML");
        sendMessage.setText(String.valueOf(stringBuilder));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send_work_list(User user, String date) {
        List<AgentWorkShop> sellYes = agentWorkShopRepository.findByUserAndDateAndSell(user, date, true);
        List<AgentWorkShop> sellNo = agentWorkShopRepository.findByUserAndDateAndSell(user, date, false);
        Optional<AgentHistory> history = agentHistoryRepository.findByUserAndDate(user, date);
        TimeControl timeControl1 = userServiceBot.WorkTime(history.get().getTimeON());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("✳️<b>Bugungi ish haqida</b>  " + date)
                .append("\n")
                .append("\nZakaz berganlar <b>" + sellYes.size() + "</b> ta do`kon");

        for (AgentWorkShop sellYe : sellYes) {
            stringBuilder.append("\n\uD83D\uDED2 <i>" + sellYe.getShop() + "</i>   vaqti = " + sellYe.getTime() + " ✅");
        }
        stringBuilder
                .append("\n")
                .append("\n")
                .append("Zakaz bermagan dokonlar " + sellNo.size() + " ta dokon");

        for (AgentWorkShop sellNoo : sellNo) {
            stringBuilder.append("\n\uD83D\uDED2 <i>" + sellNoo.getShop() + "</i>   vaqti = " + sellNoo.getTime() + " ❎");
        }

        stringBuilder
                .append("\n")
                .append("\n♻️Xozirgi vaqtda " + "\n\uD83D\uDD50 " + timeControl1.getTimeOf() + " ichida  <b>" + (sellNo.size() + sellYes.size()) + "</b> ta dokonga kirdingiz")
                .append("\n");


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChatId);
        sendMessage.setParseMode("HTML");
        sendMessage.setText(String.valueOf(stringBuilder));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send_plane_list(User user, String moonYear) {
        Optional<AgentPlane> plane = agentPlaneRepository.findByUserAndMoonYearContainingIgnoreCase(user, moonYear);
        List<AgentHistory> agentHistories = agentHistoryRepository.findByUserAndDateContainingIgnoreCase(user, moonYear);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("<b>Bu oyning planlari</b> ")
                .append("\n" + plane.get().getTitle())
                .append("\n")
                .append("\n <b>Bu oyda iwlagan kunlaringiz</b> ");

        for (AgentHistory agentHistory : agentHistories) {
            stringBuilder
                    .append("\n\uD83D\uDCCC" + agentHistory.getDate() + " => \uD83D\uDD64 " + agentHistory.getWorkTime() + " => " + agentHistory.getLateness() + " \uD83D\uDED2 = " + agentHistory.getWorkShopSize());

        }


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChatId);
        sendMessage.setParseMode("HTML");
        sendMessage.setText(String.valueOf(stringBuilder));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void Remove(Integer call_data, Long message_id) {
        Optional<User> name_shop = userRepository.findById(call_data);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("\uD83D\uDED2 " + "<b>" + name_shop.get().getNameShop() + "</b>").
                append("\n<b>Manzil:</b> " + name_shop.get().getShopOrienter()).
                append("\n<b>Kuni:</b> " + name_shop.get().getDayRegion()).
                append("\n<b>Telefon:</b> " + "+" + name_shop.get().getPhoneNumber());


        EditMessageText new_message = new EditMessageText()
                .setChatId(userChatId)
                .setMessageId(Math.toIntExact(message_id))
                .setParseMode("HTML")
                .setText(String.valueOf(stringBuilder));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Constant.BACK).setCallbackData(Constant.BACK);
        inlineKeyboardButton.setText("location").setCallbackData(String.valueOf(call_data));
        keyboardRows.add(inlineKeyboardButton);
        keyboardRows.add(inlineKeyboardButton1);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRows));
        new_message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void backRemove(String message, Long message_id) {
        EditMessageText new_message = new EditMessageText()
                .setChatId(userChatId)
                .setMessageId(Math.toIntExact(message_id))
                .setText(message);


        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send_find(Integer call_data, Long message_id) {
        Optional<User> name_shop = userRepository.findById(call_data);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("\uD83D\uDED2 " + "<b>" + name_shop.get().getNameShop() + "</b>").
                append("\n<b>Manzil:</b> " + name_shop.get().getShopOrienter()).
                append("\n<b>Kuni:</b> " + name_shop.get().getDayRegion()).
                append("\n<b>Telefon:</b> " + "+" + name_shop.get().getPhoneNumber());


        EditMessageText new_message = new EditMessageText()
                .setChatId(userChatId)
                .setMessageId(Math.toIntExact(message_id))
                .setParseMode("HTML")
                .setText(String.valueOf(stringBuilder));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRows1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton.setText("➕").setCallbackData(call_data + "+");
        inlineKeyboardButton1.setText("➖").setCallbackData(call_data + "-");
        inlineKeyboardButton2.setText("\uD83D\uDD19 \uD83D\uDD19 \uD83D\uDD19").setCallbackData(Constant.BACK + "b");
        keyboardRows.add(inlineKeyboardButton);
        keyboardRows.add(inlineKeyboardButton1);
        keyboardRows1.add(inlineKeyboardButton2);
        rowList.add(keyboardRows);
        rowList.add(keyboardRows1);


        inlineKeyboardMarkup.setKeyboard(rowList);
        new_message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendLocation(Integer call_data, Long message_id) {
        Optional<User> name_shop = userRepository.findById(call_data);
        EditMessageText new_message = new EditMessageText()
                .setChatId(userChatId)
                .setMessageId(Math.toIntExact(message_id))
                .setParseMode("HTML")
                .setText(name_shop.get().getNameShop());

        Double lat = name_shop.get().getLat();
        Double lon = name_shop.get().getLon();

        SendLocation sendLocation = new SendLocation();
        sendLocation.setLatitude(lat.floatValue());
        sendLocation.setLongitude(lon.floatValue());
        sendLocation.setChatId(userChatId);

        try {
            execute(new_message);
            execute(sendLocation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void getLocationWeather(Double lat, Double lot, String str) throws IOException {
        String icon = null;
        String malumot = null;

        SendMessage sendMessage = new SendMessage()
                .setChatId(userChatId);

        Response response = userServiceBot.getWeatherFromLocation(lat, lot);

        for (WeatherItem weatherItem : response.getWeather()) {
            icon = weatherItem.getDescription();
        }

        switch (icon) {
            case "clear sky":
                malumot = "Ochiq havo ☀️";
                break;
            case "few clouds":
                malumot = "Kam bulutli \uD83C\uDF24";
                break;
            case "scattered clouds":
                malumot = "Bulutli  ☁";
                break;
            case "broken clouds":
                malumot = "Tarqoq bulutli ⛅";
                break;
            case "shower rain":
                malumot = "Kam yomg`irli   \uD83C\uDF26 ";
                break;
            case "rain":
                malumot = "Yomg`ir    ☔️ ";
                break;
            case "thunderstorm":
                malumot = "Chaqmoqli havo   \uD83C\uDF29 ";
                break;
            case "snow":
                malumot = "Qor   \uD83C\uDF28 ";
                break;
            case "mist":
                malumot = "Tumanli havo   \uD83C\uDF2B ";
                break;
            case "smoke":
                malumot = "\uD83C\uDF2B,  Tumanli";
                break;
        }
        System.out.println(icon);
        StringBuilder stringBuilder = new StringBuilder();
        double gradus = response.getMain().getTemp() - 273;
        stringBuilder
                .append("      << Bugun  " + str + " >>")
                .append("\n" + response.getName() + " da ")
                .append("\nHavo harorati " + Math.ceil(gradus) + "° " + malumot)
                .append("\n")
                .append("Nimadir");

        sendMessage.setText(String.valueOf(stringBuilder));
        try {
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
