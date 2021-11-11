package uz.pdp.pdpbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpbot.entity.Attachment;


public interface AttachmentRepository  extends JpaRepository<Attachment,Integer> {
}
