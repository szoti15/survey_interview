package surveys.model;

public class Points {
    public int completionPoints;
    public int filteredPoints;

    @Override
    public String toString() {
        return "Points{" +
                "completionPoints=" + completionPoints +
                ", filteredPoints=" + filteredPoints +
                '}';
    }
}
