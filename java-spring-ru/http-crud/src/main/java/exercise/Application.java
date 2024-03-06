package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> amount() {
        return posts;
    }

    @GetMapping("posts/{id}")
    public Optional<Post> show(@PathVariable String id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    //PUT /posts/{id} – обновление поста
    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post data) {
        var tempPost = posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
        if (tempPost.isPresent()) {
            var post = tempPost.get();
            post.setBody(data.getBody());
            post.setId(data.getId());
            post.setTitle(data.getTitle());
        }
        return data;
    }

    // DELETE /posts/{id} – удаление поста

    @DeleteMapping("/posts/{id}")
    public void humiliate(@PathVariable String id){
        posts.removeIf(post -> post.getId().equals(id));
    }

    // END
}
