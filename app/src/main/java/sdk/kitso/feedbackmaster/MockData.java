package sdk.kitso.feedbackmaster;

import com.github.javafaker.Faker;

import java.util.Random;

import sdk.kitso.feedbackmaster.db.Branch;
import sdk.kitso.feedbackmaster.db.Department;
import sdk.kitso.feedbackmaster.db.MultipleChoiceOption;
import sdk.kitso.feedbackmaster.db.Profile;
import sdk.kitso.feedbackmaster.db.Question;
import sdk.kitso.feedbackmaster.db.Survey;


public class MockData {
    private Faker faker;
    private Survey survey;
    private Branch branch;
    private Department dept;
    private Profile profile;
    private Question question;
    private MultipleChoiceOption option;
    private Random qtype;

    public MockData() {
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
        for(int i = 0; i <= max; i++) {
            survey.setId(i);
            survey.setCompany(faker.company().name());
            survey.setSurvey(faker.commerce().productName());

            for(int j = 0; j < 3; j++) {
                branch.setBrach(faker.address().cityName(), i);
                dept.setDepartment(faker.commerce().department(), i);
                MainActivity.surveyDB.surveyDao().addBranch(branch);
                MainActivity.surveyDB.surveyDao().addDepartment(dept);
            }
            MainActivity.surveyDB.surveyDao().addSurvey(survey);
        }
    }

    public void generateQuestions(int max) {
        int type = Globals.RATING_STARS;
        for(int i = 0; i <= max; i++) {
            question.setId(i);
            question.setQuestion(faker.lorem().sentence(15));
            question.setType(type);
            type = qtype.nextInt(6);
            if(type != Globals.MULTIPLE_CHOICE || type != Globals.MULTIPLE_CHOICES) {
                MainActivity.surveyDB.surveyDao().addQuestion(question);
                continue;
            }
            for(int j = 1; j < 11; j++) {
                option.setId(j);
                option.setQuestionId(i);
                option.setOption(faker.lorem().sentence());
                MainActivity.surveyDB.surveyDao().addOption(option);
            }
            MainActivity.surveyDB.surveyDao().addQuestion(question);
        }
    }

    /**
     public void getSurveys() {
        JsonReader parser = new JsonReader();
        try{
            Object obj = parser.parse(new FileReader("suerveys.json"));
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(ParseException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}
    }
     */
}
