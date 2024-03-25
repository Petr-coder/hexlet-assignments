package exercise.controller;

import exercise.dto.CommentDTO;
import exercise.dto.PostDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
Добавьте контроллер и реализуйте в нем два маршрута для сущности Post. Необходимо реализовать следующие маршруты:
- GET /posts — cписок всех постов
- GET /posts/{id} — просмотр конкретного поста
Каждый пост содержит данные о привязанных к нему комментариях. Пример возвращаемого поста:
 */

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;


    @GetMapping
    public List<PostDTO> getPosts() {
        List<Post> postList = postRepository.findAll();

        var result = postList.stream()
                .map(this::toPostDTO)
                .toList();
        return result;
    }

    @GetMapping("/{id}")
    public PostDTO getIndex(@PathVariable long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        List<CommentDTO> result = toListCommentDTO(post.getId());
        PostDTO postDTO = toPostDTO(post);
        postDTO.setComments(result);

        return postDTO;
    }

    private PostDTO toPostDTO(Post post) {
        var postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());

        List<CommentDTO> result = toListCommentDTO(post.getId());
        postDTO.setComments(result);

        return postDTO;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody(comment.getBody());
        commentDTO.setId(comment.getId());

        return commentDTO;
    }

    private List<CommentDTO> toListCommentDTO(long id) {
        List<Comment> allRelatedComments = commentRepository.findByPostId(id);
        return allRelatedComments.stream()
                .map(this::toCommentDTO)
                .toList();
    }
}
// END