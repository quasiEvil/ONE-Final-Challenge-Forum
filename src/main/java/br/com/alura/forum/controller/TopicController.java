package br.com.alura.forum.controller;

import br.com.alura.forum.domain.topic.dto.CreateTopicDTO;
import br.com.alura.forum.domain.topic.dto.TopicDetailsDTO;
import br.com.alura.forum.domain.topic.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/new")
    public ResponseEntity<TopicDetailsDTO> createTopic(@Valid @RequestBody CreateTopicDTO createTopicDTO,
                                                       @RequestHeader("Authorization") String token) {
        TopicDetailsDTO topicDetailsDTO = topicService.save(createTopicDTO, token);
        return ResponseEntity.ok(topicDetailsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> updateTopic(@PathVariable Long id,
                                                       @RequestBody @Valid CreateTopicDTO data,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        TopicDetailsDTO updatedTopic = topicService.update(id, data, token);
        return ResponseEntity.ok(updatedTopic);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TopicDetailsDTO> updateTopicStatus(@PathVariable Long id,
                                                             @RequestParam("status") String newStatus,
                                                             @RequestHeader("Authorization") String token) throws Exception {
        TopicDetailsDTO updatedTopic = topicService.updateTopicStatus(id, newStatus, token);
        return ResponseEntity.ok(updatedTopic);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> findById(@PathVariable Long id) {
        TopicDetailsDTO topicDetails = topicService.findById(id);
        return ResponseEntity.ok(topicDetails);
    }

    @GetMapping
    public ResponseEntity<List<TopicDetailsDTO>> findAll() {
        List<TopicDetailsDTO> topicDetailsList = topicService.findAll();
        return ResponseEntity.ok(topicDetailsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id,
                                            @RequestHeader("Authorization") String token) throws Exception {
        topicService.delete(id, token);
        return ResponseEntity.noContent().build();
    }

}
