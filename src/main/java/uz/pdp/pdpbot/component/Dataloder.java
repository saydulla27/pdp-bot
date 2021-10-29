package uz.pdp.pdpbot.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.pdpbot.entity.*;
import uz.pdp.pdpbot.repository.GroupRepository;
import uz.pdp.pdpbot.repository.SurveyRepository;
import uz.pdp.pdpbot.repository.UserRepository;

@Component
public class Dataloder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Override
    public void run(String... args) throws Exception {

        User user3 = new User();
        user3.setPhoneNumber("998330571996");
        user3.setRole(Role.ROlE_ADMIN);
//        user.setGroup(guruh);
        userRepository.save(user3);


        Group guruh = new Group();
        guruh.setName("G11");
        groupRepository.save(guruh);

        User student = new User();
        student.setPhoneNumber("+998917706311");
        student.setRole(Role.ROLE_STUDENT);
        student.setGroup(guruh);
        userRepository.save(student);



        User user1 = new User();
        user1.setPhoneNumber("998973451445");
        user1.setRole(Role.ROLE_STUDENT);
        user1.setGroup(guruh);
        userRepository.save(user1);

        User student1 = new User();
        student1.setPhoneNumber("998338476311");
        student1.setRole(Role.ROLE_MANAGER);
        userRepository.save(student1);



        Survey survey = new Survey();
        survey.setName("Agar PDP Academy o’quvmarkazi bo’lmaganda o’qishingizni aynan qaysi o’quv markazida boshlagan bo’lardingiz? ");
        survey.setType(Type.COMMIT);
        survey.setTitle("standart");
        surveyRepository.save(survey);

        Survey survey1 = new Survey();
        survey1.setName("PDP Academy ni tanlashingizning asosiy 3 ta sababni yozib bera olasizmi? ");
        survey1.setType(Type.COMMIT);
        survey1.setTitle("standart");
        surveyRepository.save(survey1);

        Survey survey2 = new Survey();
        survey2.setName("Bo’sh vaqtlaringizda ITdan tashqari nima bilan shug’ullanishni xush ko’rasiz? (xobilaringiz, qiziqshlaringiz)");
        survey2.setType(Type.COMMIT);
        survey2.setTitle("standart");
        surveyRepository.save(survey2);


        Survey survey4 = new Survey();
        survey4.setName(" 1. Ro'yxatdan o'tish  ( Reception ) ");
        survey4.setType(Type.FIVE_BALL);
        survey4.setTitle("standart");
        surveyRepository.save(survey4);

        Survey survey5 = new Survey();
        survey5.setName(" 2. Aloqa bo'limi (Call-center)");
        survey5.setType(Type.FIVE_BALL);
        survey5.setTitle("standart");
        surveyRepository.save(survey5);

        Survey survey6 = new Survey();
        survey6.setName(" 3. Kassa");
        survey6.setType(Type.FIVE_BALL);
        survey6.setTitle("standart");
        surveyRepository.save(survey6);

        Survey survey7 = new Survey();
        survey7.setName(" 4. Tadbirlar");
        survey7.setType(Type.FIVE_BALL);
        survey7.setTitle("standart");
        surveyRepository.save(survey7);

        Survey survey8 = new Survey();
        survey8.setName(" 5. IT bo'lim");
        survey8.setType(Type.FIVE_BALL);
        survey8.setTitle("standart");
        surveyRepository.save(survey8);

        Survey survey9 = new Survey();
        survey9.setName("O'quv markazini tanishlaringizga tavsiya qilishingiz ehtimoli qanchalik  yuqori ?");
        survey9.setType(Type.TEEN_BAll);
        survey9.setTitle("standart");
        surveyRepository.save(survey9);

        Survey survey10 = new Survey();
        survey10.setName("PDP Academy  nimani o’zgartirish kerak ?");
        survey10.setType(Type.COMMIT);
        survey10.setTitle("standart");
        surveyRepository.save(survey10);

        Survey survey11 = new Survey();
        survey11.setName("PDP Academy da hech qachon o'zgarmsligi kerak  bo'lgan  bitta  xususiyatni  yozib  qoldirin");
        survey11.setType(Type.COMMIT);
        survey11.setTitle("standart");
        surveyRepository.save(survey11);

        Survey survey12 = new Survey();
        survey12.setName("Mentoringizning dars bo'yicha bilim darajasini baholang.");
        survey12.setType(Type.FIVE_BALL);
        survey12.setTitle("Mentor_darsini_baxolash ");
        surveyRepository.save(survey12);

        Survey survey13 = new Survey();
        survey13.setName("Mentoringizning tushuntirib bera olish darajasini baholang.");
        survey13.setType(Type.FIVE_BALL);
        survey13.setTitle("Mentor_tushuntirishini_baxolash");
        surveyRepository.save(survey13);

        Survey survey14 = new Survey();
        survey14.setName("O’quv programmasidagi berilgan ma’lumotlarnig foydalilik va tushunarlilik darajasini baholang.           ");
        survey14.setType(Type.FIVE_BALL);
        survey14.setTitle("Pdp_programmasi_baxolash ");
        surveyRepository.save(survey14);


        Survey survey15 = new Survey();
        survey15.setName("O’quv markazining sharoitlarini baholang.");
        survey15.setType(Type.FIVE_BALL);
        survey15.setTitle("Pdp_sharoiti_baxolash");
        surveyRepository.save(survey15);

        Survey survey16 = new Survey();
        survey16.setName("O'quv markazini tanishlaringizga tavsiya qilishingiz ehtimoli qanchalik yuqori ? ");
        survey16.setType(Type.TEEN_BAll);
        survey16.setTitle("Pdp_tavsiya_ehtimoli");
        surveyRepository.save(survey16);


        Survey survey17 = new Survey();
        survey17.setName("PDP Academy nimanio’zgartirishkerak ?");
        survey17.setType(Type.COMMIT);
        survey17.setTitle("Pdp_nimani_ozgartirishi");
        surveyRepository.save(survey17);
    }
}
