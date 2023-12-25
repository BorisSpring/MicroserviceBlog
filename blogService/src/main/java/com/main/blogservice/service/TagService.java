package com.main.blogservice.service;

import com.main.blogservice.domain.Tag;
import com.main.blogservice.exceptions.TagException;

import java.util.List;

public interface TagService {

    Tag addTag(Tag tag) throws TagException;

    void deleteTag(Integer tagId) throws TagException;

    void updateTag(Integer tagId, String newTagName) throws TagException;

    List<Tag> findAll();

    Tag findById(Integer tagId) throws TagException;
}
