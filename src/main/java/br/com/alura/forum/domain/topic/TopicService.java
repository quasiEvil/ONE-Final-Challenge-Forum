package br.com.alura.forum.domain.topic;

import br.com.alura.forum.domain.course.Categories;
import br.com.alura.forum.domain.course.Course;
import br.com.alura.forum.domain.course.CourseRepository;
import br.com.alura.forum.domain.topic.dto.CreateTopicDTO;
import br.com.alura.forum.domain.topic.dto.TopicDetailsDTO;
import br.com.alura.forum.domain.user.User;
import br.com.alura.forum.domain.user.UserRepository;
import br.com.alura.forum.infra.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public TopicDetailsDTO save(CreateTopicDTO data, String token) {
        // Verifica se os dados recebidos são nulos ou vazios
        if (data == null || data.title() == null || data.title().isEmpty() ||
                data.message() == null || data.message().isEmpty() ||
                data.authorID() == null || data.category() == null) {
            throw new IllegalArgumentException("Dados do tópico inválidos");
        }

        // Valida dados adicionais
        validateTopicData(data);

        // Obtém autor pelo token
        User author = getUserByToken(token);

        // Encontra curso pelo enum Category do DTO
        Categories category = Categories.valueOf(String.valueOf(data.category()));

        // Encontra o autor pelo id do autor
        User topicAuthor = userRepository.findById(data.authorID())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));

        // Encontra curso pelo enum Category do DTO
        Course course = (Course) courseRepository.findByCategory(category)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        // Cria e salva o tópico
        Topic topic = new Topic(data.title(), data.message(), topicAuthor, course);
        topic.setTopicStatus(TopicStatus.OPEN);
        topic = topicRepository.save(topic);

        return new TopicDetailsDTO(topic);
    }

    public TopicDetailsDTO findById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado"));
        return new TopicDetailsDTO(topic);
    }

    public List<TopicDetailsDTO> findAll() {
        List<Topic> topics = topicRepository.findAllByOrderByCreatedAtDesc();
        return topics.stream()
                .map(TopicDetailsDTO::new)
                .toList();
    }

    @Transactional
    public TopicDetailsDTO update(Long id, CreateTopicDTO data, String token) throws Exception {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado"));
        User author = getUserByToken(token);

        if (!author.equals(topic.getAuthor())) {
            throw new Exception("Usuário não autorizado a atualizar este tópico");
        }

        validateTopicData(data);

        topic.setTitle(data.title());
        topic.setMessage(data.message());
        Course course = (Course) courseRepository.findByCategory(data.category())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));
        topic.setCourse(course);

        return new TopicDetailsDTO(topic);
    }

    @Transactional
    public TopicDetailsDTO updateTopicStatus(Long id, String newStatus, String token) throws Exception {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado"));

        User author = getUserByToken(token);

        if (!author.equals(topic.getAuthor())) {
            throw new Exception("Usuário não autorizado a atualizar o status deste tópico");
        }

        // Validar se o novo status é válido
        if (!newStatus.equalsIgnoreCase("open") && !newStatus.equalsIgnoreCase("solved")) {
            throw new IllegalArgumentException("Status inválido");
        }

        // Define o novo status do tópico
        if (newStatus.equalsIgnoreCase("open")) {
            topic.setTopicStatus(TopicStatus.OPEN);
        } else if (newStatus.equalsIgnoreCase("solved")) {
            topic.setTopicStatus(TopicStatus.SOLVED);
        }

        // Salva as alterações no banco de dados
        topic = topicRepository.save(topic);

        return new TopicDetailsDTO(topic);
    }

    @Transactional
    public void delete(Long id, String token) throws Exception {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado"));
        User author = getUserByToken(token);

        if (!author.equals(topic.getAuthor())) {
            throw new Exception("Usuário não autorizado");
        }

        topicRepository.deleteById(id);
    }

    private void validateTopicData(CreateTopicDTO data) {

        Categories category = Categories.fromDisplayName(data.category().getDisplayName());

        boolean titleExists = topicRepository.existsByTitle(data.title());
        if (titleExists) {
            throw new IllegalArgumentException("Já existe um tópico com esse título");
        }

        boolean messageExists = topicRepository.existsByMessage(data.message());
        if (messageExists) {
            throw new IllegalArgumentException("Já existe um tópico com essa mensagem");
        }
    }

    private User getUserByToken(String token) {
        String email = tokenService.getSubject(token.replace("Bearer ", "").trim());
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail " + email));
    }
}
