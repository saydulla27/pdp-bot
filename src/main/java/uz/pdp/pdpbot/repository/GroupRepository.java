package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.Group;
import uz.pdp.pdpbot.entity.User;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group,Integer> {
    Optional<Group> findByName(String name);
    Optional<Group> findByBuffer(Long chatid);
}
