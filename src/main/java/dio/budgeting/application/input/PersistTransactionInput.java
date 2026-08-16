package dio.budgeting.application.input;

import dio.budgeting.domain.Category;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput (@ToolParam(description= "Descrição do gasto") String description,
                                       @Tool(description= "Valor do gasto (em fcentavos)") long amount,
                                       @Tool(description= "Categoria de uma transação") Category category){
}
