package dio.budgeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Transaction {
    private TransactionId id;
    private String description;
    private long amount;
    private Category category;
    private LocalDate date;

    public Transaction(String description, long amount, Category category) {
        this(new TransactionId(), description, amount, category, LocalDate.now());
    }
}
