package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.AgentPlane;
import uz.pdp.pdpbot.entity.User;

import java.util.Optional;

public interface AgentPlaneRepository extends JpaRepository<AgentPlane,Integer> {
    Optional<AgentPlane> findByUserAndMoonYearContainingIgnoreCase (User user, String moon);

}
