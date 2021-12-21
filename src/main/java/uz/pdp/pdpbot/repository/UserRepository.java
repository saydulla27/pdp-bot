package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pdpbot.entity.Regions;
import uz.pdp.pdpbot.entity.Role;
import uz.pdp.pdpbot.entity.User;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByChatId(long id);
    Optional<User> findByPhoneNumber(String phone);
    Optional<User> findByBuffer(Long chatid);
    Optional<User> findByState(String state);
    List<User> findByRoleAndActiveTrue  (Role role);
    List<User> findByRole  (Role role);
    List<User> findByActiveFalseAndRole(Role role);
    List<User> findAllByActiveTrue();
    List<User> findByRoleAndRegions (Role role, Regions regions);
    List<User> findByRoleAndRegionsAndDayRegion (Role role, Regions regions,String day);
    Optional<User> findById(Integer call_data);
    List<User> findByRoleAndRegionsAndNameShopContainingIgnoreCase (Role role, Regions regions,String name);

}
