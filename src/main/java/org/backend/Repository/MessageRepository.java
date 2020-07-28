package org.backend.Repository;

import org.backend.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MessageRepository")
public interface MessageRepository extends JpaRepository<Message,Long> {

}
