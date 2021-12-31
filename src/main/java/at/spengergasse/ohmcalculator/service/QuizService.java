package at.spengergasse.ohmcalculator.service;

import at.spengergasse.ohmcalculator.model.QuizAnswer;
import at.spengergasse.ohmcalculator.model.QuizQuestion;
import at.spengergasse.ohmcalculator.model.QuizResponse;
import at.spengergasse.ohmcalculator.repository.QuizAnswerRepository;
import at.spengergasse.ohmcalculator.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService
{

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;


    public List<QuizResponse> getAllQuestions(){
        List<QuizQuestion> allQuestions = quizQuestionRepository.getAllQuestions();
        List<QuizResponse> response = new ArrayList<>();

        for(QuizQuestion q : allQuestions){
            QuizResponse res = new QuizResponse();
            res.setQuestion(q);
            res.setAnswers(quizAnswerRepository.getAnswersByQuestionId(q.getId()));
            response.add(res);
        }
        return response;
    }



    public QuizQuestion getQuestionById(Long id)
    {
        return quizQuestionRepository.getById(id);
    }

    @Transactional
    public QuizQuestion saveQuestion(QuizQuestion question, List<QuizAnswer> answers){
        if(question != null){
            QuizQuestion questionSaved = quizQuestionRepository.save(question);
            for(QuizAnswer ans: answers){
                ans.setQuizQuestion(questionSaved);
                quizAnswerRepository.save(ans);
            }
            return questionSaved;
        } else {
            return null;
        }
    }


    @Transactional
    public void deleteQuestion(Long id)
    {
        quizAnswerRepository.deleteAnswersByQuestionId(id);
        quizQuestionRepository.deleteById(id);
    }

}
