package com.main.commentservice.mappers;

import com.main.commentservice.domain.Comment;
import com.main.commentservice.model.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {

    @Mapping(source = "createdDate", target = "createdDate")
    CommentDto commentToDto(Comment comment);
}
