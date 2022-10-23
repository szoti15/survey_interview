package surveys;


import surveys.controller.SurveyController;
import surveys.dao.*;


public class Main {
    private static final String COMPLETED = "Completed";

    public static void main(String[] args) {
        SurveyController surveyController = new SurveyController();

        System.out.println(surveyController.getMembersWithStatus(15, COMPLETED));
        System.out.println(surveyController.getSurveysByMemberAndStatus(15, COMPLETED));
        System.out.println(surveyController.getPointsOfMember(15));
        System.out.println(surveyController.getNonParticipiants(15));
        System.out.println(surveyController.getSurveyStatistics());


    }
}
