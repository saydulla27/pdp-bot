package uz.pdp.pdpbot.bot;

public interface State {
    String START = "start";
    String SUPER_START = "super_start";
    String SEND_CONTACT = "send_contact";
    String BACK_MENU = "back_menu";

    String REG_ADMIN_PHONE = "reg_admin_phone";
    String S_ADD_ADMIN = "add_admin";
    String REG_ADMIN = "reg_admin";
    String REG_ADMIN_PHONE_1 = "reg_admin_phone_1";
    String S_ADD_MANAGER = "s_add_manager";
    String DELETE_ADMIN_1 = "delete_admin_1";
    String DELETE_MANAGER_1 = "delete_manager_1";
    String START_ADMIN = "start_admin";
    String S_ADD_ADMIN_NAME = "s_add_admin_name";
    String REG_ADMIN_OK = "reg_admin_ok";
    String S_ADD_MANAGER_NAME = "s_add_manager_name";
    String REG_MANAGER_PHONE = "reg_manager_phone";
    String REG_MANAGER_PHONE_1 = "reg_manager_phone_1";
    String REG_MANAGER_OK = "reg_manager_ok";
    String START_MANAGER = "start_manager";

    String A_ADD_GROUP = "a_add_group";
    String A_ADD_STUDENT = "a_add_student";
    String A_ADD_STUDENT_1 = "a_add_student_1";

    String START_STUDENT = "start_student";

}
