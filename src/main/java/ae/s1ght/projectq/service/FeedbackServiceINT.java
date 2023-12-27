package ae.s1ght.projectq.service;

import ae.s1ght.projectq.enums.FeedbackType;
import ae.s1ght.projectq.model.Feedback;

import java.util.List;

public interface FeedbackServiceINT {
    Feedback getFeedbackById(Long id);
    Feedback saveFeedback(Feedback feedback);
    void deleteFeedback(Long id);
    List<Feedback> getFeedbackByType(FeedbackType feedbackType);
}
