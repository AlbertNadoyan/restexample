package com.example.restexample.endpoint;

import com.example.restexample.model.Book;
import com.example.restexample.model.BookLanguage;
import com.example.restexample.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookEndPoint {

    private final BookRepository bookRepository;



    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id){
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody Book book){
        bookRepository.save(book);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        if(book.getId() == 0){
            return ResponseEntity.badRequest().build();
        }
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable("id") int id){
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
