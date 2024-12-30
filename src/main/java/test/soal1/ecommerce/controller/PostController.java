package test.soal1.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import test.soal1.ecommerce.model.Posts;
import test.soal1.ecommerce.model.Users;
import test.soal1.ecommerce.service.PostService;
import test.soal1.ecommerce.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    // Get all posts
    @GetMapping
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    // Get a specific post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Posts> getPostById(@PathVariable Long id) {
        Posts post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    // Create a new post (must be authenticated)
    @PostMapping
    public ResponseEntity<Posts> createPost(@RequestBody Posts post) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("Users not found"));
        post.setAuthorId(user);
        return ResponseEntity.ok(postService.save(post));
    }

    // Update an existing post (must be authenticated and author must match)
    @PutMapping("/{id}")
    public ResponseEntity<Posts> updatePost(@PathVariable Long id, @RequestBody Posts updatedPost) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("Users not found"));

        Posts existingPost = postService.findById(id);
        if (!existingPost.getAuthorId().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        existingPost.setContent(updatedPost.getContent());
        return ResponseEntity.ok(postService.update(existingPost));
    }

    // Delete a post (must be authenticated and author must match)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("Users not found"));

        Posts post = postService.findById(id);
        if (!post.getAuthorId().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        postService.delete(post);
        return ResponseEntity.noContent().build();
    }
}