package guru.springframework.springairag.controller;

import guru.springframework.springairag.model.Answer;
import guru.springframework.springairag.model.Question;
import org.springframework.web.bind.annotation.RequestBody;



public interface SpringAiRagController {

    Answer askQuestion(@RequestBody Question question);
}
