package surveys.model;

public class Statuses {
    public int statusId;
    public String name;

    public Statuses(int statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Statuses{" +
                "statusId=" + statusId +
                ", name='" + name + '\'' +
                '}';
    }
}
