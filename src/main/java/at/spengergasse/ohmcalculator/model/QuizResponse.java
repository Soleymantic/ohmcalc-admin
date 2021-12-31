package at.spengergasse.ohmcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse
{
    private QuizQuestion question;

    private List<QuizAnswer> answers;
}
