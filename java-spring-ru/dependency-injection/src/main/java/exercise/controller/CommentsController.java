package exercise.controller;

import exercise.exception.ResourceAlreadyExistsException;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentRepository commentRepository;

    //GET /comments — список всех комментариев
    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        var commentsList = commentRepository.findAll();

        return ResponseEntity.ok().body(commentsList);
    }

    //GET /comments/{id} – просмотр конкретного комментария
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

        return ResponseEntity.ok().body(comment);
    }

    //POST /comments – создание нового комментария. При успешном создании возвращается статус 201
    // При успешном создании возвращается статус 201
    @PostMapping
    public ResponseEntity<Comment> createPost(@RequestBody Comment comment) {
        var existingComments = commentRepository.findAll();
        IntStream.range(0, existingComments.size()).filter(i -> comment.equals(existingComments.get(i))).forEach(i -> {
            throw new ResourceAlreadyExistsException("Продукт уже сущестует");
        });

        commentRepository.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //PUT /comments/{id} – обновление комментария
    @PutMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Comment update(@PathVariable long id, @RequestBody Comment productData) {
        var product = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        product.setPostId(productData.getPostId());
        product.setBody(productData.getBody());

        commentRepository.save(product);

        return product;
    }
    //DELETE /comments/{id} – удаление комментария
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
// END