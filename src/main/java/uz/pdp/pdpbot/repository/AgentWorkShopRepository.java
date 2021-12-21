package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.AgentWorkShop;
import uz.pdp.pdpbot.entity.User;

import java.util.List;
import java.util.Optional;

public interface AgentWorkShopRepository extends JpaRepository<AgentWorkShop,Integer> {
    Optional<AgentWorkShop> findByBuffer(Long chatid);
    List<AgentWorkShop> findByUserAndDateAndSell(User user,String date,boolean sell);


}
