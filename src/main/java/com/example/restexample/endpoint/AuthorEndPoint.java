package com.example.restexample.endpoint;

 import com.example.restexample.dto.AuthorResponseDto;
import com.example.restexample.dto.CreateAuthorDto;
import com.example.restexample.mapper.AuthorMapper;
import com.example.restexample.model.Author;
import com.example.restexample.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorEndPoint {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @GetMapping("/authors")
    public List<AuthorResponseDto> getAllAuthors(){
        return authorMapper.map(authorRepository.findAll());
    }

    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(@RequestBody CreateAuthorDto createAuthorDto){
        Author saveAuthor = authorRepository.save(authorMapper.map(createAuthorDto));
        return ResponseEntity.ok(saveAuthor);
    }
}
