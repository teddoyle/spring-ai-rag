package guru.springframework.springairag.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Slf4j
@Configuration
public class VectorStoreConfig {

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        if(vectorStoreFile.exists()) {
            simpleVectorStore.load(vectorStoreFile);
        } else {
            log.debug("Loading documents into vector store");
            vectorStoreProperties.getDocumentsToLoad().forEach( document -> {
                log.debug("Loading document: " + document.getFilename());
                 // add data to vector store
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> docs = documentReader.get();  // file data and metadata
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                simpleVectorStore.add(splitDocs);
            });
            simpleVectorStore.save(vectorStoreFile);
        }

        return simpleVectorStore;
    }
}
