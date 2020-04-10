package jolyjdia.api.skin.exeptions;

public class SkinRequestException extends RuntimeException {

    private static final long serialVersionUID = 4509807027209453678L;

    public SkinRequestException(String message) {
        super(message);
    }

    public SkinRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}