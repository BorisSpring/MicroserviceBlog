package com.main.blogservice.controllers;

import com.main.blogservice.domain.Tag;
import com.main.blogservice.exceptions.TagException;
import com.main.blogservice.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> addTag(@Valid @RequestBody Tag tag) throws TagException {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.addTag(tag));
    }

    @PutMapping("/{tagId}/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTag(@PathVariable(name = "tagName") String tagName,
                          @PathVariable(name = "tagId") Integer tagId) throws TagException{
        tagService.updateTag(tagId,tagName);
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteTag(@PathVariable(name = "tagId") Integer tagId) throws TagException{
        tagService.deleteTag(tagId);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> findAllTags(){
        return ResponseEntity.ok(tagService.findAll());
    }
}