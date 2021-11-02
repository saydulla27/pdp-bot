package uz.pdp.pdpbot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.Survey;

import java.util.Optional;


public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    Optional<Survey> findByType(int type);

    Optional<Survey> findByBuffer(Long chatId);

    Optional<Survey> findByTitle(String title);





}
