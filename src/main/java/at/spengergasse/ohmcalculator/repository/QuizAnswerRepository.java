package at.spengergasse.ohmcalculator.repository;

import at.spengergasse.ohmcalculator.model.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long>
{
    @Modifying
    @Query("delete from QuizAnswer qa where qa.quizQuestion.id = :questionId")
    void deleteAnswersByQuestionId(@Param("questionId") Long questionId);

    @Query("select qa from QuizAnswer qa where qa.quizQuestion.id = :questionId")
    List<QuizAnswer> getAnswersByQuestionId(@Param("questionId") Long questionId);
}
