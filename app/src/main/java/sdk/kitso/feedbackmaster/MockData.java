package sdk.kitso.feedbackmaster;

import android.content.Context;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;

import sdk.kitso.feedbackmaster.db.Branch;
import sdk.kitso.feedbackmaster.db.Department;
import sdk.kitso.feedbackmaster.db.MultipleChoiceOption;
import sdk.kitso.feedbackmaster.db.Profile;
import sdk.kitso.feedbackmaster.db.Question;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;


public class MockData {
    private Faker faker;
    private Survey survey;
    private List<Survey> surveys;
    private Branch branch;
    private Department dept;
    private Profile profile;
    private Question question;
    private MultipleChoiceOption option;
    private Random qtype;
    private Context context;

    public MockData(Context context) {
        this.context = context;
        this.faker = new Faker();
        this.profile = new Profile();
        this.survey = new Survey();
        this.dept = new Department();
        this.branch = new Branch();
        this.question = new Question();
        this.option = new MultipleChoiceOption();
        this.qtype = new Random();
    }

    public void generateProfile() {
        profile.setId(1);
        profile.setAge(faker.number().numberBetween(18, 100));
        profile.setGender("male");
        profile.setPhone(
                new Integer(faker.phoneNumber().cellPhone())
        );
    }

    public void generateSurveys(int max) {
        int type;
        for(int i = 0; i <= max; i++) {
            survey.setId(i);
            survey.setCompany(faker.company().name());
            survey.setSurvey(faker.commerce().productName());

            for(int j = 0; j < 3; j++) {
                branch.setBranch(faker.address().cityName(), i);
                dept.setDepartment(faker.commerce().department(), i);
                MainActivity.surveyDB.surveyDao().addBranch(branch);
                MainActivity.surveyDB.surveyDao().addDepartment(dept);
            }
            for (int k = 1; k < 11; k++) {
                question.setQuestion(faker.lorem().sentence(15));
                question.setSurveyId(i);
                type = qtype.nextInt(6);
                question.setType(type);
                for (int j = 0; j < 6; j++) {
                    option.setQuestionId(k);
                    option.setOption(faker.lorem().sentence());
                    SurveysFragment.questionDB.questionDao().addOption(option);
                }
                SurveysFragment.questionDB.questionDao().addQuestion(question);
            }
            MainActivity.surveyDB.surveyDao().addSurvey(survey);
        }
    }
}
