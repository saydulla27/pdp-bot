package uz.pdp.pdpbot.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.pdpbot.entity.Group;
import uz.pdp.pdpbot.entity.Role;
import uz.pdp.pdpbot.entity.User;
import uz.pdp.pdpbot.repository.GroupRepository;
import uz.pdp.pdpbot.repository.UserRepository;

@Component
public class Dataloder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setPhoneNumber("+998330571996");
        user.setRole(Role.ROLE_MANAGER);
        userRepository.save(user);


        Group guruh = new Group();
        guruh.setName("G11");
        groupRepository.save(guruh);

        User student = new User();
        student.setPhoneNumber("+998917706311");
        student.setRole(Role.ROLE_STUDENT);
        student.setGroup(guruh);
        userRepository.save(student);



    }
}
