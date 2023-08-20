package com.backend.librarymanagementsystem.Service;

import com.backend.librarymanagementsystem.DTO.AuthorRequestDto;
import com.backend.librarymanagementsystem.Entity.Author;
import com.backend.librarymanagementsystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void addAuthor(AuthorRequestDto authorRequestDto) {

        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setAge(authorRequestDto.getAge());
        author.setMobNo(authorRequestDto.getMoblNo());
        author.setEmail(authorRequestDto.getEmail());

        authorRepository.save(author);

    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }
}
