package br.com.alura.forum.domain.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByOrderByCreatedAtDesc();

    Boolean existsByTitle(String title);
    Boolean existsByMessage(String message);

}
