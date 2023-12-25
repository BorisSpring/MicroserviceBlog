package com.main.messageservice.service;

import com.main.messageservice.domain.Message;
import com.main.messageservice.exceptions.MessageException;
import org.springframework.data.domain.Page;

public interface MessageService {


    Message sendMessage(Message msg) throws MessageException;

    void deleteMessage(Integer msgId) throws MessageException;

    void setReaded(Integer msgId) throws MessageException;

    void setUnRead(Integer msgId) throws MessageException;

    Message findById(Integer msgId) throws MessageException;

    Page<Message> findMessages(int page, String filterBy);
}