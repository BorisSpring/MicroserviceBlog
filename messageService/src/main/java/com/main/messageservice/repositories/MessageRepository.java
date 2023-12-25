package com.main.messageservice.repositories;

import com.main.messageservice.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {


    Page<Message>  findAllByReadedIsTrue(Pageable pageable);

    Page<Message>  findAllByReadedIsFalse(Pageable pageable);
}
