package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.AuthorNotFoundException;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // Реализуйте сервис для работы с сущностью книги.
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAll() {
        var posts = bookRepository.findAll();
        var result = posts.stream()
                .map(bookMapper::map)
                .toList();
        return result;
    }

    public BookDTO create(BookCreateDTO postData) {
        if (!authorRepository.existsById(postData.getAuthorId())) {
            // Обработка отсутствия автора, например, выброс исключения
            throw new AuthorNotFoundException("Author with ID " + postData.getAuthorId() + " not found");
        }
        var post = bookMapper.map(postData);
        bookRepository.save(post);
        return bookMapper.map(post);
    }

    public BookDTO findById(Long id) {
        var post = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var postDTO = bookMapper.map(post);
        return postDTO;
    }

    public BookDTO update(BookUpdateDTO postData, Long id) {
        var post = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        bookMapper.update(postData, post);
        bookRepository.save(post);
        var postDTO = bookMapper.map(post);
        return postDTO;
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
    // END
}