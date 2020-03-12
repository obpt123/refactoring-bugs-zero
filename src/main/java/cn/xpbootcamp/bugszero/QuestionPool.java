package cn.xpbootcamp.bugszero;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionPool {
    private String category;
    private int currentIndex = 0;
    private List<String> questions;

    public QuestionPool(String category, int count) {
        super();
        this.category = category;
        this.questions = IntStream.range(0, count)
                .mapToObj(index -> String.format("%s Question %d", category, index))
                .collect(Collectors.toList());
    }
    
    public String getCategory() {
        return category;
    }

    public String getNewQuestion() {
        String current = questions.get(currentIndex);
        currentIndex = (currentIndex + 1) % questions.size();
        return current;
    }
}