package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pdpbot.entity.UserResoult;

@Repository
public interface UserResoultRepository extends JpaRepository<UserResoult, Integer> {





}
