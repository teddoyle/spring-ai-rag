package guru.springframework.springairag.service;

import guru.springframework.springairag.model.Answer;
import guru.springframework.springairag.model.Question;

public interface SpringAiRagService {

    Answer sendQuestion(Question question);
}
