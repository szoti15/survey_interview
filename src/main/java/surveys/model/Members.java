package surveys.model;

public class Members {
    //Member Id,Full name,E-mail address,Is Active
    public int memberId;
    public String fullName;
    public String emailAddress;
    public boolean isActive;

    public Members(int memberId, String fullName, String emailAddress, boolean isActive) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.isActive = isActive;
    }



    @Override
    public String toString() {
        return "{" +
                "id=" + memberId +
                ", name='" + fullName + '\'' +
                //", email='" + emailAddress + '\'' +
                ", isActive=" + isActive +
                "}\n";
    }
}
