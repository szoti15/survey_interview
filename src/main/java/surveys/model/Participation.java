package surveys.model;

public class Participation {
    // Member Id,Survey Id,Status,Length
    private int memberId;
    private int surveyId;
    private int statusId;
    private int length;

    public Participation(int memberId, int surveyId, int status, int length) {
        this.memberId = memberId;
        this.surveyId = surveyId;
        this.statusId = status;
        this.length = length;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "SurveysController{" +
                "memberId=" + memberId +
                ", surveyId=" + surveyId +
                ", status=" + statusId +
                ", length=" + length +
                '}';
    }
}
