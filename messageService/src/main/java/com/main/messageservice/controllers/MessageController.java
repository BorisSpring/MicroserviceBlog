package com.main.messageservice.controllers;

import com.main.messageservice.domain.Message;
import com.main.messageservice.exceptions.MessageException;
import com.main.messageservice.service.MessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    public final MessageService msgService;

    @PutMapping("/readed/{msgId}")
    @ResponseStatus(HttpStatus.OK)
    public void setAsReadedHandler(@Positive(message = "Message id must be positive!")@PathVariable(name = "msgId") Integer msgId) throws MessageException{
        msgService.setReaded(msgId);
    }

    @PutMapping("/unread/{msgId}")
    @ResponseStatus(HttpStatus.OK)
    public void setAsUnReadedHandler(@Positive(message = "Message id must be positive!") @PathVariable(name = "msgId") Integer msgId) throws MessageException{
        msgService.setUnRead(msgId);
    }

    @DeleteMapping("/{msgId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessageHandler(@Positive(message = "Message id must be positive!") @PathVariable(name = "msgId") Integer msgId) throws MessageException{
        msgService.deleteMessage(msgId);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessageHandler(@Valid @RequestBody Message msg) throws MessageException {
        return ResponseEntity.status(HttpStatus.CREATED).body(msgService.sendMessage(msg));
    }

    @GetMapping
    public ResponseEntity<Page<Message>> findMessagesHandler(@Positive(message = "Page must be positive number!") @RequestParam(name="page" ,defaultValue = "1", required = false) int page,
                                                             @RequestParam(required = false) String filterBy){
        return ResponseEntity.ok(msgService.findMessages(page, filterBy));
    }
}