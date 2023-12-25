package com.main.blogservice.service;

import com.main.blogservice.domain.Tag;
import com.main.blogservice.exceptions.TagException;
import com.main.blogservice.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag addTag(Tag tag) throws TagException {

        if(tagRepository.existsByName(tag.getName()))
            throw new TagException("There is alerdy tag with same name, please chose another one!");

        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Integer tagId) throws TagException {
        if(!tagRepository.existsById(tagId))
            throw new TagException("Tag with id " + tagId + " doesnt exists");

        tagRepository.deleteById(tagId);
    }

    @Override
    public void updateTag(Integer tagId, String newTagName) throws TagException {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new TagException("Tag with id " + tagId + " doesnt exists"));

        if(tagRepository.existsByName(newTagName))
            throw new TagException("Tag with name " + newTagName + " alerd exists, Please chose another one!");

        tag.setName(newTagName);
        tagRepository.saveAndFlush(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Integer tagId) throws TagException {
        return  tagRepository.findById(tagId).orElseThrow(() -> new TagException("Tag with id " + tagId + " doesnt exists"));
    }
}
