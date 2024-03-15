package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// BEGIN

@RestController
@RequestMapping("/api")
public class PostsController {

    private List<Post> postList = Data.getPosts();

    //GET /api/users/{id}/posts — список всех постов, написанных пользователем с таким же userId, как id в маршруте
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable int id) {

        List<Post> filteredPosts = postList.stream()
                .filter(post -> post.getUserId() == id)
                .collect(Collectors.toList());

        if (filteredPosts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filteredPosts);
    }

    //POST /api/users/{id}/posts – создание нового поста, привязанного к пользователю по id. Код должен возвращать
    // статус 201, тело запроса должно содержать slug, title и body. Обратите внимание, что userId берется из маршрута
    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post data) {
        Post createdPost = data;

        createdPost.setUserId(id);
        postList.add(createdPost);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}
// END