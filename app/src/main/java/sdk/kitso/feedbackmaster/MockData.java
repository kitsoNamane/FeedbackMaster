package sdk.kitso.feedbackmaster;

import com.github.javafaker.Faker;

import sdk.kitso.feedbackmaster.db.Profile;
import sdk.kitso.feedbackmaster.db.Survey;


public class MockData {
    Faker faker;
    Survey survey;
    Profile profile;

    public MockData(Profile profile, Survey survey) {
        this.faker = new Faker();
        this.profile = profile;
        this.survey = survey;
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
            MainActivity.surveyDB.surveyDao().addSurvey(survey);
        }
    }

}
