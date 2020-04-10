package jolyjdia.api.skin.exeptions;

public class TooManyRequestsSkinException extends SkinRequestException {

    public TooManyRequestsSkinException() {
        super("Превышен лимит запросов");
    }
}
