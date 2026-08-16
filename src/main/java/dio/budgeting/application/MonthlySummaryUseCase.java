package dio.budgeting.application;

import dio.budgeting.application.output.CategorySummaryOutput;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlySummaryUseCase {
    private final TransactionRepository transactionRepository;

    public MonthlySummaryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "monthly-summary-by-category", description = "Gera um resumo dos gastos do mês anterior agrupados por categoria")
    public List<CategorySummaryOutput> execute() {
        var previousMonth = YearMonth.now().minusMonths(1);
        var start = previousMonth.atDay(1);
        var end = previousMonth.atEndOfMonth();

        var transactions = transactionRepository.findAllByDateBetween(start, end);

        var totalsByCategory = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCategory().name(),
                        Collectors.summingLong(transaction -> transaction.getAmount())));

        return totalsByCategory.entrySet().stream()
                .map(entry -> CategorySummaryOutput.from(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(CategorySummaryOutput::category))
                .toList();
    }
}