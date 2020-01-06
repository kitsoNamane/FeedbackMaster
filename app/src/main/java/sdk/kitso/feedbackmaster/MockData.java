package sdk.kitso.feedbackmaster;

import com.github.javafaker.Faker;

import sdk.kitso.feedbackmaster.db.Branch;
import sdk.kitso.feedbackmaster.db.Department;
import sdk.kitso.feedbackmaster.db.Profile;
import sdk.kitso.feedbackmaster.db.Survey;


public class MockData {
    Faker faker;
    Survey survey;
    Branch branch;
    Department dept;
    Profile profile;

    public MockData(Profile profile, Survey survey, Branch branch, Department dept) {
        this.faker = new Faker();
        this.profile = profile;
        this.survey = survey;
        this.dept = dept;
        this.branch = branch;
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
