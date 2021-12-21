package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.AgentHistory;
import uz.pdp.pdpbot.entity.User;

import java.util.List;
import java.util.Optional;

public interface AgentHistoryRepository extends JpaRepository<AgentHistory,Integer> {
    Optional<AgentHistory> findByBuffer(Long chatid);
    Optional<AgentHistory> findByUserAndDate (User user,String data);
    List<AgentHistory> findByUserAndDateContainingIgnoreCase (User user, String moon);
}
