package exercise.controller;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.repository.AuthorRepository;
import exercise.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    /*
    Реализуйте полный CRUD сущности автора. Создайте обработчики для просмотра списка всех авторов и конкретного автора, создания, обновления и удаления автора:

    GET /authors – просмотр списка всех авторов
    GET /authors/{id} – просмотр конкретного автора
    POST /authors – добавление нового автора
    PUT /authors/{id} – редактирование автора. При редактировании мы должны иметь возможность поменять имя и фамилию
    DELETE /authors – удаление автора
     */
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    // BEGIN
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AuthorDTO>> index() {
        var posts = authorService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping("/{id}")
    public AuthorDTO getProductById(@PathVariable Long id) {

        return authorService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO createProduct(@RequestBody AuthorCreateDTO productCreateDTO) {
        return authorService.create(productCreateDTO);
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        return authorService.update(authorUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        authorService.delete(id);
    }
    // END
}