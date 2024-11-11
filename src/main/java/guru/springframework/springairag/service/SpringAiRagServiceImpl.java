package guru.springframework.springairag.service;

import guru.springframework.springairag.model.Answer;
import guru.springframework.springairag.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SpringAiRagServiceImpl implements SpringAiRagService {

    final SimpleVectorStore vectorStore;
    final ChatModel chatModel;

    // private final OpenAiService openAiService;
    @Value("classpath:/templates/rag-prompt-template-meta.st")
    private Resource ragPromptTemplate;
    public Answer sendQuestion(Question question) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(question.question()).withTopK(4) );
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt chatPrompt = promptTemplate.create(Map.of("input", question.question(),
                "documents", String.join("\n", contentList)));

        contentList.forEach(System.out::println);

        ChatResponse response = chatModel.call(chatPrompt);
        return new Answer(response.getResult().getOutput().getContent());
    }
}
