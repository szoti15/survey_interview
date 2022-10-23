package surveys.model;

public class SurveyStatistics {
    public int surveyId;
    public String surveyName;
    public int numbOfCompleted;
    public int numbOfFiltered;
    public int numbOfRejected;
    public int avgLength;

    @Override
    public String toString() {
        return "{" +
                "id=" + surveyId +
                ", surveyName='" + surveyName + '\'' +
                ", numbOfCompleted=" + numbOfCompleted +
                ", numbOfFiltered=" + numbOfFiltered +
                ", numbOfRejected=" + numbOfRejected +
                ", avgLength=" + avgLength +
                "}\n";
    }
}
