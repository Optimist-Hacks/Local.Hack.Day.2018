package ledmein.model;

public enum EventType {
    COMMIT,
    PULL_REQUEST,
    FORK,
    PUSH,
    ISSUE,
    IGNORE,
    BUILD_STARTED,
    BUILD_SUCCESS,
    BUILD_FAILED
}
