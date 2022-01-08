package uz.pdp.pdpbot.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.pdpbot.entity.AgentPlane;
import uz.pdp.pdpbot.entity.Regions;
import uz.pdp.pdpbot.entity.Role;
import uz.pdp.pdpbot.entity.User;
import uz.pdp.pdpbot.repository.*;

@Component
public class Dataloder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RegionsRepository regionsRepository;

    @Autowired
    SurveyRepository surveyRepository;


    @Autowired
    UserResoultRepository userResoultRepository;

    @Autowired
    AgentHistoryRepository agentHistoryRepository;

    @Autowired
    AgentPlaneRepository agentPlaneRepository;

    @Override
    public void run(String... args) throws Exception {

        Regions regions = new Regions();
        regions.setRegionName("Zangata 1");
        regions.setLocationName("Toshkent Shahri");
        regions.setLat(41.333443);
        regions.setLon(69.237656);
        regionsRepository.save(regions);


        User user1 = new User();
        user1.setFullName("Iroda");
        user1.setPhoneNumber("998917887252");
        user1.setRole(Role.ROLE_AGENT);
        user1.setRegions(regions);
        user1.setOperatingModeON("09.00");
        user1.setOperatingModeOFF("18.00");
        user1.setBrand("Bella");
        userRepository.save(user1);


        User user2 = new User();
        user2.setFullName("Sadi");
        user2.setPhoneNumber("998917706311");
        user2.setRole(Role.ROLE_AGENT);
        user2.setRegions(regions);
        user2.setOperatingModeON("09.00");
        user2.setOperatingModeOFF("18.00");
        user1.setBrand("Bella");
        userRepository.save(user2);


        User user8 = new User();
        user8.setFullName("asdad");
        user8.setPhoneNumber("998881784777");
        user8.setRole(Role.ROLE_AGENT);
        user8.setRegions(regions);
        user8.setOperatingModeON("09.00");
        user8.setOperatingModeOFF("18.00");
        user8.setBrand("Bella");
        userRepository.save(user8);

//        AgentHistory agentHistory = new AgentHistory();
//        agentHistory.setDate("19-12-2021");
//        agentHistory.setTimeON("09:00");
//        agentHistory.setWorkTime(" 4 daqiqa ");
//        agentHistory.setLateness("1 soat 2 min");
//        agentHistory.setWorkShopSize("20");
//        agentHistory.setUser(user2);
//        agentHistoryRepository.save(agentHistory);
//
//
//        AgentHistory agentHistory1 = new AgentHistory();
//        agentHistory1.setDate("20-12-2021");
//        agentHistory1.setTimeON("09:00");
//        agentHistory1.setWorkTime(" 4 daqiqa ");
//        agentHistory1.setLateness("1 soat 2 min");
//        agentHistory1.setWorkShopSize("20");
//        agentHistory1.setUser(user2);
//        agentHistoryRepository.save(agentHistory1);
//
//        AgentHistory agentHistory2 = new AgentHistory();
//        agentHistory2.setDate("21-12-2021");
//        agentHistory2.setTimeON("09:00");
//        agentHistory2.setWorkTime(" 4 daqiqa ");
//        agentHistory2.setLateness("1 soat 2 min");
//        agentHistory2.setWorkShopSize("20");
//        agentHistory2.setUser(user2);
//        agentHistoryRepository.save(agentHistory2);




        User user3 = new User();
        user3.setFullName("???");
        user3.setPhoneNumber("998988887337");
        user3.setRole(Role.ROLE_AGENT);
        user3.setRegions(regions);
        user3.setOperatingModeON("09.00");
        user3.setOperatingModeOFF("18.00");
        user3.setBrand("Bella");
        userRepository.save(user3);



        User dokon5 = new User();
        dokon5.setFullName("Boxodir");
        dokon5.setPhoneNumber("998977052400");
        dokon5.setRole(Role.ROLE_AGENT);
        dokon5.setRegions(regions);
        dokon5.setOperatingModeON("09.00");
        dokon5.setOperatingModeOFF("18.00");
        userRepository.save(dokon5);

        User dokon1 = new User();
        dokon1.setShopOrienter("keles pasi");
        dokon1.setNameShop("Anvar ota");
        dokon1.setPhoneNumber("998917776677");
        dokon1.setRole(Role.ROLE_SHOP);
        dokon1.setRegions(regions);
        dokon1.setDayRegion("111");
        dokon1.setLat(41.48986);
        dokon1.setLon(69.57755);
        userRepository.save(dokon1);

        User dokon2 = new User();
        dokon2.setShopOrienter("minvada");
        dokon2.setNameShop("Toxirov Nodir");
        dokon2.setPhoneNumber("998936667788");
        dokon2.setRole(Role.ROLE_SHOP);
        dokon2.setRegions(regions);
        dokon2.setDayRegion("222");
        dokon2.setLat(41.48986);
        dokon2.setLon(69.57755);
        userRepository.save(dokon2);

        User dokon3 = new User();
        dokon3.setShopOrienter("chala qozo");
        dokon3.setNameShop("Abdullaeva A");
        dokon3.setPhoneNumber("998947896621");
        dokon3.setRole(Role.ROLE_SHOP);
        dokon3.setRegions(regions);
        dokon3.setDayRegion("333");
        dokon3.setLat(41.48986);
        dokon3.setLon(69.57755);
        userRepository.save(dokon3);


        AgentPlane agentPlane = new AgentPlane();
        agentPlane.setTitle("supervisor yozadi planlarni");
        agentPlane.setMoonYear("19-12-2021");
        agentPlane.setUser(user2);
        agentPlaneRepository.save(agentPlane);



    }


}




