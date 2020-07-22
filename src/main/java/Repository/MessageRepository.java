package Repository;

import org.backend.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message,Long> {

}
