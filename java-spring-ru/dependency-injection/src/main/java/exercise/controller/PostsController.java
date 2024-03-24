package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    //GET /posts — список всех постов
    @GetMapping
    public ResponseEntity<List<Post>> getProductList() {
        var postList = postRepository.findAll();

        return ResponseEntity.ok().body(postList);
    }

    //GET /posts/{id} – просмотр конкретного поста
    @GetMapping(path = "/{id}")
    public ResponseEntity<Post> getProductList(@PathVariable Long id) {
        var product = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return ResponseEntity.ok().body(product);
    }

    //POST /posts – создание нового поста.
    // При успешном создании возвращается статус 201
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        var existingPosts = postRepository.findAll();
        IntStream.range(0, existingPosts.size()).filter(i -> post.equals(existingPosts.get(i))).forEach(i -> {
            throw new ResourceNotFoundException("Продукт уже сущестует");
        });

        postRepository.save(post);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //PUT /posts/{id} – обновление поста
    @PutMapping(path = "/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post post) {
        var postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setBody(post.getBody());

        postRepository.save(postToUpdate);

        return postToUpdate;
    }

    //DELETE /posts/{id} – удаление поста.
    // При удалении поста удаляются все комментарии этого поста
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}
// END