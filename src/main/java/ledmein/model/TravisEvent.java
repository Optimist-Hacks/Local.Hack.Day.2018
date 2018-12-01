package ledmein.model;

public class TravisEvent {

    private String buildState;
    private int buildNumber;

    public TravisEvent(String buildState, int buildNumber) {
        this.buildState = buildState;
        this.buildNumber = buildNumber;
    }

    public String getBuildState() {
        return buildState;
    }

    public void setBuildState(String buildState) {
        this.buildState = buildState;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

}
