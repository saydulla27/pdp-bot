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
        surveyRepository.save(survey);

        Survey survey1 = new Survey();
        survey1.setName("PDP Academy ni tanlashingizning asosiy 3 ta sababni yozib bera olasizmi? ");
        survey1.setType(Type.COMMIT);
        surveyRepository.save(survey1);

        Survey survey2 = new Survey();
        survey2.setName("Bo’sh vaqtlaringizda ITdan tashqari nima bilan shug’ullanishni xush ko’rasiz? (xobilaringiz, qiziqshlaringiz)");
        survey2.setType(Type.COMMIT);
        surveyRepository.save(survey2);


        Survey survey4 = new Survey();
        survey4.setName(" 1. Ro'yxatdan o'tish  ( Reception ) ");
        survey4.setType(Type.FIVE_BALL);
        surveyRepository.save(survey4);

        Survey survey5 = new Survey();
        survey5.setName(" 2. Aloqa bo'limi (Call-center)");
        survey5.setType(Type.FIVE_BALL);
        surveyRepository.save(survey5);

        Survey survey6 = new Survey();
        survey6.setName(" 3. Kassa");
        survey6.setType(Type.FIVE_BALL);
        surveyRepository.save(survey6);

        Survey survey7 = new Survey();
        survey7.setName(" 4. Tadbirlar");
        survey7.setType(Type.FIVE_BALL);
        surveyRepository.save(survey7);

        Survey survey8 = new Survey();
        survey8.setName(" 5. IT bo'lim");
        survey8.setType(Type.FIVE_BALL);
        surveyRepository.save(survey8);

        Survey survey9 = new Survey();
        survey9.setName("O'quv markazini tanishlaringizga tavsiya qilishingiz ehtimoli qanchalik  yuqori ?");
        survey9.setType(Type.TEEN_BAll);
        surveyRepository.save(survey9);

        Survey survey10 = new Survey();
        survey10.setName("PDP Academy  nimani o’zgartirish kerak ?");
        survey10.setType(Type.COMMIT);
        surveyRepository.save(survey10);

        Survey survey11 = new Survey();
        survey11.setName("PDP Academy da hech qachon o'zgarmsligi kerak  bo'lgan  bitta  xususiyatni  yozib  qoldirin");
        survey11.setType(Type.COMMIT);
        surveyRepository.save(survey11);


    }
}
