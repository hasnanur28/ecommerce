package test.soal1.ecommerce.service;

import test.soal1.ecommerce.model.Posts;
import test.soal1.ecommerce.repo.PostRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public List<Posts> findAll() {
        return postRepo.findAll();
    }

    public Posts findById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Posts save(Posts post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepo.save(post);
    }

    public Posts update(Posts post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepo.save(post);
    }

    public void delete(Posts post) {
        postRepo.delete(post);
    }
}
