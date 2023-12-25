package com.main.messageservice.service;

import com.main.messageservice.domain.Message;
import com.main.messageservice.exceptions.MessageException;
import com.main.messageservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message sendMessage(Message msg) throws MessageException {
        return messageRepository.save(msg);
    }

    @Override
    public void deleteMessage(Integer msgId) throws MessageException {
        if(!messageRepository.existsById(msgId))
            throw new MessageException("Message with id " + msgId  + " doesnt exists!");
        messageRepository.deleteById(msgId);
    }

    @Override
    public void setReaded(Integer msgId) throws MessageException {
        Message msg = findById(msgId);
        if(!msg.isReaded()){
            msg.setReaded(true);
            messageRepository.save(msg);
        }
    }

    @Override
    public void setUnRead(Integer msgId) throws MessageException {
        Message msg = findById(msgId);

        if(msg.isReaded()){
            msg.setReaded(false);
            messageRepository.save(msg);
        }
    }

    @Override
    public Message findById(Integer msgId) throws MessageException {
        return  messageRepository.findById(msgId)
                    .orElseThrow(() ->  new MessageException("Message with id" + msgId + " doesnt exists"));
    }

    @Override
    public Page<Message> findMessages(int page, String filterBy) {
        System.out.println(page);
        PageRequest pageRequest = PageRequest.of((page - 1), 15);
        if(filterBy != null){
            if(filterBy.equals("readed")) {
                return  messageRepository.findAllByReadedIsTrue(pageRequest);
            }else if (filterBy.equals("unread")) {
                return  messageRepository.findAllByReadedIsFalse(pageRequest);
            }
        }
        return  messageRepository.findAll(pageRequest);
    }
}