package at.spengergasse.ohmcalculator.repository;

import at.spengergasse.ohmcalculator.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long>
{
    @Query("select q from QuizQuestion q")
    List<QuizQuestion> getAllQuestions();
}
