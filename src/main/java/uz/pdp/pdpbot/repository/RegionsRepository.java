package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.Regions;
import uz.pdp.pdpbot.entity.User;

import java.util.List;
import java.util.Optional;

public interface RegionsRepository extends JpaRepository<Regions,Integer> {
    Optional<Regions> findByBuffer(Long chatid);
}
