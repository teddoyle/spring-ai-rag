package guru.springframework.springairag.controller;

import guru.springframework.springairag.model.Answer;
import guru.springframework.springairag.model.Question;
import guru.springframework.springairag.service.SpringAiRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpringAiRagControllerImpl implements SpringAiRagController{
    private final SpringAiRagService springAiRagService;
    @PostMapping("/askQuestion")
    public Answer askQuestion(@RequestBody Question question) {
        return springAiRagService.sendQuestion(question);
    }

}
