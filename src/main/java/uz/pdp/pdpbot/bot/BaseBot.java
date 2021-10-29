package uz.pdp.pdpbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.pdpbot.entity.*;
import uz.pdp.pdpbot.repository.GroupRepository;
import uz.pdp.pdpbot.repository.SurveyRepository;
import uz.pdp.pdpbot.repository.UserRepository;
import uz.pdp.pdpbot.repository.UserResoultRepository;


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
    private Long studentChatId;
    private String userMessage;
    private String studentmassage;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserServiceBot userServiceBot;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    UserResoultRepository userResoultRepository;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        User client = null;
        User user = null;
        String delete = null;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                userChatId = update.getMessage().getChatId();
                String text = update.getMessage().getText();
                String ism = update.getMessage().getFrom().getFirstName();
                String familya = update.getMessage().getFrom().getLastName();
                if (text.equals("/start")) {
                    userMessage = Constant.WELCOME_TEXT;
                    Optional<User> byChatId = userRepository.findByChatId(userChatId);
                    if (!byChatId.isPresent()) {
                        User u1 = new User();
                        if (userChatId == 637495326) {
                            u1.setRole(Role.ROLE_SUPER_ADMIN);
                            u1.setChatId(userChatId);
                            u1.setState(State.SUPER_START);
                            u1.setFullName("Muminov Saydulla");
                            u1.setPhoneNumber("+998338476311");
                            userMessage = "super admin";
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
                    Optional<User> byBuffer = userRepository.findByBuffer(userChatId);
                    user = byChatId.get();
                    String state = user.getState();

                    switch (state) {
                        case State.START:
                            switch (text) {
                                case Constant.PDP_SEND_CONTACT:
                                    userMessage = "Raqam yuborish tugmasini bosing";
                                    user.setState(State.SEND_CONTACT);
                                    user.setBuffer(userChatId);
                                    userRepository.save(user);
                                    execute(userServiceBot.sendnumber(), null);
                                    break;

                                case Constant.PDP_INFO:
                                    userMessage = "77777777777";
                                    menu();
                                    break;

                                default:
                                    menu();
                                    break;

                            }
                            break;
                        case State.SEND_CONTACT:
                            if (!text.isEmpty()) {
                                userMessage = "PDP ACADEMY";
                                user.setState(State.START);
                                userRepository.save(user);
                                menu();
                                break;
                            }
                            break;


                        case State.SUPER_START:
                            switch (text) {
                                case Constant.ADD_ADMIN:
                                    User admin = new User();
                                    admin.setState(State.REG_ADMIN_PHONE);
                                    admin.setRole(Role.ROlE_ADMIN);
                                    userRepository.save(admin);
                                    user.setState(State.S_ADD_ADMIN);
                                    userRepository.save(user);
                                    userMessage = "raqam yozing";
                                    execute(null, null);
                                    break;

                                case Constant.DEL_ADMIN:
                                    List<User> byRole = userRepository.findByRole(Role.ROlE_ADMIN);
                                    if (!byRole.isEmpty()) {
                                        userMessage = "o`chadigan adminnni tanlang";
                                        user.setState(State.DELETE_ADMIN_1);
                                        userRepository.save(user);
                                        execute(userServiceBot.delateadmins(), null);
                                        break;
                                    } else
                                        userMessage = "admin mavjud emas";
                                    user.setState(State.SUPER_START);
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
                                    user.setState(State.SUPER_START);
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
                                    userMessage = "super menyu";
                                    user.setState(State.SUPER_START);
                                    userRepository.save(user);
                                    superMenu();
                                    break;
                                } else
                                    user.setState(State.SUPER_START);
                                userRepository.save(user);
                                Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(text);
                                userRepository.delete(byPhoneNumber.get());
                                userMessage = "bu admin tugadi";
                                superMenu();
                                break;
                            }
                            break;

                        case State.DELETE_MANAGER_1:
                            if (!text.isEmpty()) {
                                if (text.equals(Constant.BACK_M)) {
                                    userMessage = "super menyu";
                                    user.setState(State.SUPER_START);
                                    userRepository.save(user);
                                    superMenu();
                                    break;
                                } else
                                    user.setState(State.SUPER_START);
                                userRepository.save(user);
                                Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(text);
                                userRepository.delete(byPhoneNumber.get());
                                userMessage = "bu manager tugadi";
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
                                user.setState(State.SUPER_START);
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
                                user.setState(State.SUPER_START);
                                userRepository.save(user);
                                userMessage = "ok";
                            }
                            superMenu();
                            break;
                        case State.START_ADMIN:
                            switch (text) {
                                case Constant.ADD_GROUP:
                                    userMessage = "Guruh nomini yozing";
                                    user.setState(State.A_ADD_GROUP);
                                    userRepository.save(user);
                                    execute(null, null);
                                    break;
                                case Constant.ADD_STUDENT:
                                    userMessage = "o`quvchini guruhini belgilang";
                                    user.setState(State.A_ADD_STUDENT);
                                    userRepository.save(user);
                                    execute(userServiceBot.getgroup(), null);
                                    break;
                                case Constant.GET_GROUP:
                                    userMessage = "Guruhlar royxati";
                                    user.setState(State.START_ADMIN);
                                    userRepository.save(user);
                                    execute(userServiceBot.getgroup(), null);
                                    break;
                                default:
                                    userMessage = "Menu";
                                    execute(userServiceBot.addStudent(), null);
                                    break;
                            }
                            break;
                        case State.A_ADD_GROUP:
                            if (!text.isEmpty()) {
                                Group group = new Group();
                                group.setName(text);
                                groupRepository.save(group);
                                user.setState(State.START_ADMIN);
                                userRepository.save(user);
                                userMessage = "Guruh qo`shildi";
                                execute(userServiceBot.addStudent(), null);
                            }
                            break;


                        case State.A_ADD_STUDENT:
                            if (text.equals(Constant.BACK_M)) {
                                user.setState(State.START_ADMIN);
                                userRepository.save(user);
                                userMessage = "Kerakli ishni qiling";
                                execute(userServiceBot.addStudent(), null);
                                break;
                            } else if (!text.isEmpty()) {
                                Optional<Group> optionalGroup = groupRepository.findByName(text);
                                optionalGroup.get().setBuffer(userChatId);
                                groupRepository.save(optionalGroup.get());
                                userMessage = text + " " + "O`quvchini telefon raqamini kiriting :";
                                user.setState(State.A_ADD_STUDENT_1);
                                userRepository.save(user);
                                execute(null, null);
                            }
                            break;


                        case State.A_ADD_STUDENT_1:
                            String a = text.substring(0, 3);

                            switch (a) {
                                case "998":
                                    Optional<Group> optionalGroup = groupRepository.findByBuffer(userChatId);
                                    User student = new User();
                                    student.setPhoneNumber(text);
                                    student.setRole(Role.ROLE_STUDENT);
                                    student.setGroup(optionalGroup.get());
                                    userRepository.save(student);
                                    userMessage = optionalGroup.get().getName() + " + " + "keyingi telefon raqamini kiriting :";
                                    execute(userServiceBot.backAdmin(), null);
                                    break;
                                case Constant.BACK_ADMIN:
                                    userMessage = "Xamma raqamlar qoshildi";
                                    user.setState(State.START_ADMIN);
                                    userRepository.save(user);
                                    Optional<Group> byBufferGroup = groupRepository.findByBuffer(userChatId);
                                    byBufferGroup.get().setBuffer(null);
                                    groupRepository.save(byBufferGroup.get());
                                    execute(userServiceBot.addStudent(), null);
                                    break;
                                default:
                                    userMessage = "Raqamni togri tering (998......) ";
                                    execute(null, null);
                                    break;
                            }
                            break;

                        case State.START_MANAGER:
                            switch (text) {
                                case Constant.GET_QUESTION:
                                    for (User user1 : userRepository.findByRole(Role.ROLE_STUDENT)) {
                                        studentmassage = "Iltimos sorovnomamizda qatnashing";
                                        execute1(userServiceBot.start_survey(), user1.getChatId(), studentmassage);
                                    }
                                    break;


                                case Constant.ADD_QUESTION:
                                    userMessage = "";
                                    break;
                                case Constant.LIST_QUESTION:
                                    break;
                                case Constant.DEL_QUESTION:
                                    break;
                                case Constant.GET_RESULT:
                                    break;


                            }

                            break;
                        case State.ST_Q_1:
                            if (!text.isEmpty()) {
                                Optional<UserResoult> resoult = userResoultRepository.findByBuffer(userChatId);
                                resoult.get().setDescription(text);
                                resoult.get().setBuffer(0);
                                userResoultRepository.save(resoult.get());
                                user.setState(State.ST_Q_2);
                                userRepository.save(user);
                                Optional<Survey> survey = surveyRepository.findById(2);
                                userMessage = survey.get().getName() + "  \uD83D\uDC47";
                                execute(null, null);
                                break;
                            }
                            break;
                        case State.ST_Q_2:
                            if (!text.isEmpty()) {
                                Optional<Survey> survey = surveyRepository.findById(2);

                                UserResoult userResoult = new UserResoult();
                                userResoult.setUser(user);
                                userResoult.setSavol(survey.get());
                                userResoult.setDescription(text);
                                userResoultRepository.save(userResoult);
                                user.setState(State.ST_Q_3);
                                userRepository.save(user);
                                Optional<Survey> survey3 = surveyRepository.findById(3); // massage
                                userMessage = survey3.get().getName();
                                execute(null, null);
                                break;
                            }
                            break;
                        case State.ST_Q_3:
                            if (!text.isEmpty()) {
                                Optional<Survey> survey = surveyRepository.findById(3);
                                Optional<Survey> massage = surveyRepository.findById(4); // massage
                                UserResoult userResoult = new UserResoult();
                                userResoult.setUser(user);
                                userResoult.setSavol(survey.get());
                                userResoult.setDescription(text);
                                userResoultRepository.save(userResoult);
                                user.setState(State.ST_Q_4);
                                userRepository.save(user);
                                userMessage = "Quyidagi bo'limlarni baholab bera olasizmi? \n " + massage.get().getName();
                                execute(null, userServiceBot.fifeBall());
                                break;
                            }
                            break;
                        case State.ST_Q_10:
                            if (!text.isEmpty()) {
                                Optional<Survey> survey1 = surveyRepository.findById(10);
                                Optional<Survey> massage = surveyRepository.findById(11); // massage
                                UserResoult userResoult = new UserResoult();
                                userResoult.setUser(user);
                                userResoult.setSavol(survey1.get());
                                userResoult.setDescription(text);
                                userResoultRepository.save(userResoult);
                                user.setState(State.ST_Q_11);
                                userRepository.save(user);
                                userMessage = massage.get().getName() + " (Iltimos izox yozing)";
                                execute(null, null);
                                break;
                            }
                            break;
                        case State.ST_Q_11:
                            if (!text.isEmpty()) {
                                Optional<Survey> survey1 = surveyRepository.findById(11);
                                UserResoult userResoult = new UserResoult();
                                userResoult.setUser(user);
                                userResoult.setSavol(survey1.get());
                                userResoult.setDescription(text);
                                userResoultRepository.save(userResoult);
                                user.setState(State.START_STUDENT);
                                userRepository.save(user);
                                userMessage = "\uD83D\uDC4D";
                                execute(userServiceBot.Start_Student(), null);
                                break;
                            }
                            break;
                        case State.START_STUDENT:
                            if (!text.isEmpty()) {
                                userMessage = " Sizning guruhingiz   " + user.getGroup().getName() + "\uD83C\uDF93" + " \nSizning raqamingiz   " + user.getPhoneNumber() + "☎";
                                execute(userServiceBot.Start_Student(), null);
                                break;
                            }
                            break;
                    }
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
                    if (optionalUser.get().getRole().equals(Role.ROlE_ADMIN)) {
                        userRepository.delete(userBuffer.get());
                        optionalUser.get().setChatId(userChatId);
                        optionalUser.get().setRole(Role.ROlE_ADMIN);
                        optionalUser.get().setState(State.START_ADMIN);
                        optionalUser.get().setActive(true);
                        optionalUser.get().setFullName(ism + " " + familya);
                        userRepository.save(optionalUser.get());
                        userMessage = "Assalom aleykum  " + ism + " " + familya;
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
                        userMessage = "Assalom aleykum  " + ism + " " + familya;
                        execute(userServiceBot.startManager(), null);

                    }
                    if (optionalUser.get().getRole().equals(Role.ROLE_STUDENT)) {
                        userRepository.delete(userBuffer.get());
                        optionalUser.get().setChatId(userChatId);
                        optionalUser.get().setRole(Role.ROLE_STUDENT);
                        optionalUser.get().setState(State.START_STUDENT);
                        optionalUser.get().setActive(true);
                        optionalUser.get().setFullName(ism + " " + familya);
                        userRepository.save(optionalUser.get());
                        userMessage = "Assalom aleykum  " + ism + " " + familya;
                        execute(userServiceBot.Start_Student(), null);
                    }

                } else {
                    Optional<User> byState = userRepository.findByState(State.SEND_CONTACT);
                    byState.get().setState(State.START);
                    userMessage = "PDP da mexmonsiz";
                    byState.get().setChatId(userChatId);
                    userRepository.save(byState.get());
                    menu();
                }
            }

        } else if (update.hasCallbackQuery()) {
            userChatId = update.getCallbackQuery().getMessage().getChatId();
            String call_data = update.getCallbackQuery().getData();
            Optional<User> optionalUser = userRepository.findByChatId(userChatId);
            user = optionalUser.get();
            String state = user.getState();
            switch (state) {
                case State.START_STUDENT:
                    switch (call_data) {
                        case "sur":
                            Optional<Survey> survey = surveyRepository.findById(1);
                            userMessage = survey.get().getName() + "  \uD83D\uDC47";
                            user.setState(State.ST_Q_1);
                            userRepository.save(user);
                            UserResoult userResoult = new UserResoult();
                            userResoult.setUser(user);
                            userResoult.setBuffer(userChatId);
                            userResoult.setSavol(survey.get());
                            userResoultRepository.save(userResoult);
                            execute(null, null);
                            break;
                        case "a":
                            break;
                        case "b":
                            break;
                        case "c":
                            break;
                        default:
                            userMessage = "\uD83E\uDD0C";
                            execute(null, null);

                    }
                    break;
                case State.ST_Q_4:
                    if (!call_data.isEmpty()) {
                        Optional<Survey> survey1 = surveyRepository.findById(4);
                        Optional<Survey> massage = surveyRepository.findById(5); // massage
                        UserResoult userResoult = new UserResoult();
                        userResoult.setUser(user);
                        userResoult.setSavol(survey1.get());
                        userResoult.setBall(call_data);
                        userResoultRepository.save(userResoult);
                        user.setState(State.ST_Q_5);
                        userRepository.save(user);
                        userMessage = massage.get().getName();
                        execute(null, userServiceBot.fifeBall());
                        break;
                    }
                    break;
                case State.ST_Q_5:
                    if (!call_data.isEmpty()) {
                        Optional<Survey> survey1 = surveyRepository.findById(5);
                        Optional<Survey> massage = surveyRepository.findById(6); // massage
                        UserResoult userResoult = new UserResoult();
                        userResoult.setUser(user);
                        userResoult.setSavol(survey1.get());
                        userResoult.setBall(call_data);
                        userResoultRepository.save(userResoult);
                        user.setState(State.ST_Q_6);
                        userRepository.save(user);
                        userMessage = massage.get().getName();
                        execute(null, userServiceBot.fifeBall());
                        break;
                    }
                    break;
                case State.ST_Q_6:
                    if (!call_data.isEmpty()) {
                        Optional<Survey> survey1 = surveyRepository.findById(6);
                        Optional<Survey> massage = surveyRepository.findById(7); // massage
                        UserResoult userResoult = new UserResoult();
                        userResoult.setUser(user);
                        userResoult.setSavol(survey1.get());
                        userResoult.setBall(call_data);
                        userResoultRepository.save(userResoult);
                        user.setState(State.ST_Q_7);
                        userRepository.save(user);
                        userMessage = massage.get().getName();
                        execute(null, userServiceBot.fifeBall());
                        break;
                    }
                    break;
                case State.ST_Q_7:
                    if (!call_data.isEmpty()) {
                        Optional<Survey> survey1 = surveyRepository.findById(7);
                        Optional<Survey> massage = surveyRepository.findById(8); // massage
                        UserResoult userResoult = new UserResoult();
                        userResoult.setUser(user);
                        userResoult.setSavol(survey1.get());
                        userResoult.setBall(call_data);
                        userResoultRepository.save(userResoult);
                        user.setState(State.ST_Q_8);
                        userRepository.save(user);
                        userMessage = massage.get().getName();
                        execute(null, userServiceBot.fifeBall());
                        break;
                    }
                    break;
                case State.ST_Q_8:
                    if (!call_data.isEmpty()) {
                        Optional<Survey> survey1 = surveyRepository.findById(8);
                        Optional<Survey> massage = surveyRepository.findById(9); // massage
                        UserResoult userResoult = new UserResoult();
                        userResoult.setUser(user);
                        userResoult.setSavol(survey1.get());
                        userResoult.setBall(call_data);
                        userResoultRepository.save(userResoult);
                        user.setState(State.ST_Q_9);
                        userRepository.save(user);
                        userMessage = massage.get().getName();
                        execute(null, userServiceBot.teenBall());
                        break;
                    }
                    break;
                case State.ST_Q_9:
                    if (!call_data.isEmpty()) {
                        Optional<Survey> survey1 = surveyRepository.findById(9);
                        Optional<Survey> massage = surveyRepository.findById(10); // massage
                        UserResoult userResoult = new UserResoult();
                        userResoult.setUser(user);
                        userResoult.setSavol(survey1.get());
                        userResoult.setBall(call_data);
                        userResoultRepository.save(userResoult);
                        user.setState(State.ST_Q_10);
                        userRepository.save(user);
                        userMessage = massage.get().getName() + " (Iltimos izox yozing)";
                        execute(null, null);
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

    public ReplyKeyboardMarkup deleteUser() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow.add("O`chirish");
        keyboardRow1.add("orqaga qaytish");
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup abs() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("a");
        inlineKeyboardButton1.setCallbackData("Button \"a\" saydulla");
        inlineKeyboardButton2.setText("b");
        inlineKeyboardButton2.setCallbackData("Button \"Тык2\" uabydulla");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("s").setCallbackData("sss"));
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        execute(null, inlineKeyboardMarkup);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup line() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык");
        inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
        inlineKeyboardButton2.setText("Тык2");
        inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"));
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        execute(null, inlineKeyboardMarkup);
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup test(String name) {
        userMessage = name;
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("ok");
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        execute(replyKeyboardMarkup, null);
        return replyKeyboardMarkup;
    }


    public InlineKeyboardMarkup test_2() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRows = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton.setText("1").setCallbackData("a");
        inlineKeyboardButton1.setText("2").setCallbackData("b");
        inlineKeyboardButton2.setText("3").setCallbackData("s");
        inlineKeyboardButton3.setText("4").setCallbackData("d");
        inlineKeyboardButton4.setText("5").setCallbackData("e");

        keyboardRows.add(inlineKeyboardButton);
        keyboardRows.add(inlineKeyboardButton1);
        keyboardRows.add(inlineKeyboardButton2);
        keyboardRows.add(inlineKeyboardButton3);
        keyboardRows.add(inlineKeyboardButton4);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRows));
        return inlineKeyboardMarkup;
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

}


