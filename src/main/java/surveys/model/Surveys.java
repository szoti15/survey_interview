package surveys.model;

public class Surveys {
    //Completion points,Filtered points
    public int surveyId;
    public String Name;
    public int expectedCompletes;
    public int completionPoints;
    public int filteredPoints;

    public Surveys(int surveyId, String name, int expectedCompletes, int completionPoints, int filteredPoints) {
        this.surveyId = surveyId;
        Name = name;
        this.expectedCompletes = expectedCompletes;
        this.completionPoints = completionPoints;
        this.filteredPoints = filteredPoints;
    }

    @Override
    public String toString() {
        return "Surveys{" +
                "surveyId=" + surveyId +
                ", Name='" + Name + '\'' +
                ", expectedCompletes=" + expectedCompletes +
                ", completionPoints=" + completionPoints +
                ", filteredPoints=" + filteredPoints +
                '}';
    }
}
