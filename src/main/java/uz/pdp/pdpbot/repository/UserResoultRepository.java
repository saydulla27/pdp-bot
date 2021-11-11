package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pdpbot.entity.User;
import uz.pdp.pdpbot.entity.UserResoult;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserResoultRepository extends JpaRepository<UserResoult, Integer> {
    Optional<UserResoult> findByBuffer(Long chat_id);

    List<UserResoult> findAllByUser_Group_Name(String name);
    List<UserResoult> findAllBySavol_Name(String name);




}
