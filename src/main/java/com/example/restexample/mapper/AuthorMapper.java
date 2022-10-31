package com.example.restexample.mapper;

import com.example.restexample.dto.AuthorResponseDto;
import com.example.restexample.dto.CreateAuthorDto;
import com.example.restexample.model.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author map(CreateAuthorDto createAuthorDto);
    AuthorResponseDto map(Author author);

    List<AuthorResponseDto> map(List<Author> authorList);
}
