package dio.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")

public class OpenAiSpeechModelIT {
    @Autowired
    OpenAiAudioSpeechModel openAiSpeechModel;

   @Test
    public void should_produceAudio_when_textIsProvided () throws IOException {
       var response = openAiSpeechModel.call("O valor total do servço ficou em 80 reais, posso confirmar o pagamento?");
       assertThat(response).hasSizeGreaterThan(1025);

       var tempFile = Files.createTempFile("AUDIO", ".mp3");
       Files.write(tempFile, response);
       System.out.println(tempFile.toAbsolutePath());
    }
}

