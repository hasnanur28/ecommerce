package test.soal1.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import test.soal1.ecommerce.model.Posts;

public interface PostRepo extends JpaRepository<Posts, Long> {
}
