package exercise.controller;

import java.util.List;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    /*
    Реализуйте полный CRUD сущности книги. Создайте обработчики для просмотра списка всех книг и конкретной книги,
    создания, обновления и удаления книги:

    GET /books – просмотр списка всех книг
    GET /books/{id} – просмотр конкретной книги
    POST /books – добавление новой книги. При указании id несуществующего автора должен вернуться ответ с кодом 400 Bad request
    PUT /books/{id} – редактирование книги. При редактировании мы должны иметь возможность поменять название и автора.
    При указании id несуществующего автора также должен вернуться ответ с кодом 400 Bad request
    DELETE /books/{id} – удаление книги
     */
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> index() {
        var books = bookService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(books.size()))
                .body(books);
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {

        return bookService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        return bookService.create(bookCreateDTO);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO bookUpdateDTO) {
        return bookService.update(bookUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
    // END
}