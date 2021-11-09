package uz.pdp.pdpbot.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.pdpbot.entity.*;
import uz.pdp.pdpbot.repository.GroupRepository;
import uz.pdp.pdpbot.repository.SurveyRepository;
import uz.pdp.pdpbot.repository.UserRepository;
import uz.pdp.pdpbot.repository.UserResoultRepository;

@Component
public class Dataloder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SurveyRepository surveyRepository;


    @Autowired
    UserResoultRepository userResoultRepository;

    @Override
    public void run(String... args) throws Exception {


        User student12 = new User();
        student12.setPhoneNumber("998917706311");
        student12.setRole(Role.ROLE_MANAGER);
        userRepository.save(student12);

        Group group = new Group();
        group.setName("g11");
        groupRepository.save(group);


        User student1 = new User();
        student1.setPhoneNumber("998338476311");
        student1.setRole(Role.ROLE_STUDENT);
        student1.setGroup(group);
        userRepository.save(student1);



        User student2 = new User();
        student2.setPhoneNumber("998330571996");
        student2.setRole(Role.ROLE_STUDENT);
        student2.setGroup(group);
        userRepository.save(student2);


        Survey survey = new Survey();
        survey.setName("Agar PDP Academy o’quv markazi bo’lmaganida, o’qishingizni aynan qaysi o’quv markazida boshlagan bo’lar edingiz? ");
        survey.setType(Type.STANDARD);
        survey.setTitle("standart");
        surveyRepository.save(survey);

        Survey survey1 = new Survey();
        survey1.setName("PDP Academy ni tanlashingizning asosiy sabablarini yozib bera olasizmi? ");
        survey1.setType(Type.STANDARD);
        surveyRepository.save(survey1);

        Survey survey2 = new Survey();
        survey2.setName("Bo’sh vaqtingizda dasturlashdan tashqari nimalar bilan shug’ullanishni yaxshi ko’rasiz? (xobbi va qiziqishlaringiz)");
        survey2.setType(Type.STANDARD);
        surveyRepository.save(survey2);


        Survey survey4 = new Survey();
        survey4.setName(" 1. Ro'yxatdan o'tish  ( Reception ) ");
        survey4.setType(Type.STANDARD);
        surveyRepository.save(survey4);

        Survey survey5 = new Survey();
        survey5.setName(" 2. Aloqa bo'limi (Call-center)");
        survey5.setType(Type.STANDARD);
        surveyRepository.save(survey5);

        Survey survey6 = new Survey();
        survey6.setName(" 3. Kassa");
        survey6.setType(Type.STANDARD);
        surveyRepository.save(survey6);

        Survey survey7 = new Survey();
        survey7.setName(" 4. Tadbirlar");
        survey7.setType(Type.STANDARD);
        surveyRepository.save(survey7);

        Survey survey8 = new Survey();
        survey8.setName(" 5. Texnik ta’minot (Wifi va h.k)");
        survey8.setType(Type.STANDARD);
        surveyRepository.save(survey8);

        Survey survey9 = new Survey();
        survey9.setName("O'quv markazini tanishlaringizga tavsiya qilishingiz ehtimoli qanchalik  yuqori ?");
        survey9.setType(Type.STANDARD);
        surveyRepository.save(survey9);

        Survey survey10 = new Survey();
        survey10.setName("PDP Academy nimani o’zgartirishi kerak deb o’ylaysiz?");
        survey10.setType(Type.STANDARD);
        surveyRepository.save(survey10);

        Survey survey11 = new Survey();
        survey11.setName("PDP Academy’da hech qachon o’zgarmasligi kerak bo’lgan bitta xususiyatni yozib qoldiring");
        survey11.setType(Type.STANDARD);
        surveyRepository.save(survey11);

        Survey survey12 = new Survey();
        survey12.setName("Mentoringizning dars bo'yicha bilim darajasini baholang.");
        survey12.setType(Type.FIVE_BALL);
        survey12.setTitle("Mentor_darsini_baxolash");
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
        survey17.setName("PDP Academy nimani o’zgartirishi kerak ?");
        survey17.setType(Type.COMMIT);
        survey17.setTitle("Pdp_nimani_ozgartirishi");
        surveyRepository.save(survey17);

//
//        UserResoult userResoult =  new UserResoult();
//        userResoult.setUser(student2);
//        userResoult.setBall("5");
//        userResoult.setDescription("Yomon");
//        userResoult.setSavol(survey17);
//        userResoultRepository.save(userResoult);
//
//        UserResoult userResoult1 =  new UserResoult();
//        userResoult1.setUser(student2);
//        userResoult1.setBall("3");
//        userResoult1.setDescription("yaxshi");
//        userResoult1.setSavol(survey15);
//        userResoultRepository.save(userResoult1);
//
//        UserResoult userResoult2 =  new UserResoult();
//        userResoult2.setUser(student2);
//        userResoult2.setBall("2");
//        userResoult2.setDescription("yaxshi");
//        userResoult2.setSavol(survey14);
//        userResoultRepository.save(userResoult2);


    }


}
