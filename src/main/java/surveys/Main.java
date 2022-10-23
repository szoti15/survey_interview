package surveys;


import surveys.dao.*;


public class Main {

    public static void main(String[] args) {
        GetMembers members = new GetMembers();
        System.out.println(members.getMembers());

        GetParticipation participation = new GetParticipation();
        System.out.println(participation.getParticipation());

        GetStatus status = new GetStatus();
        System.out.println(status.getStatus());

        GetSurveys surveys = new GetSurveys();
        System.out.println(surveys.getSurveys());
    }
}
