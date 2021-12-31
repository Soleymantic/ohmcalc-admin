package at.spengergasse.ohmcalculator.controller;

import at.spengergasse.ohmcalculator.model.QuizQuestion;
import at.spengergasse.ohmcalculator.model.QuizRequest;
import at.spengergasse.ohmcalculator.model.QuizResponse;
import at.spengergasse.ohmcalculator.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("rest/quiz")
//accept only request from localhost:8081 (client side)
@CrossOrigin(origins = "https://ohmcalc.herokuapp.com/")
public class QuizRestController
{
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuestions(){
        final List<QuizResponse> allQuestions = quizService.getAllQuestions();
        return ResponseEntity.ok(allQuestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizQuestion> getQuestion(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(quizService.getQuestionById(id));
    }

    @PostMapping
    public ResponseEntity<QuizQuestion> addQuestion(@RequestBody QuizRequest quiz)
    {
        final QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestion(quiz.getQuestion());
        return ResponseEntity.ok(quizService.saveQuestion(quizQuestion, quiz.getAnswers()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id)
    {
        quizService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }
}
