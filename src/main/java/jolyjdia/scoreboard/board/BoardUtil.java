package jolyjdia.scoreboard.board;

public final class BoardUtil {
    private BoardUtil() {}

    @Deprecated
    public static String[] parse(String text) { //todo переписать в соответствии с новой версией 1.13
        String center;
        int length = text.length();
        String suffix = "";
        String prefix = text.substring(0, 16);
        if (length > 32) {
            center = text.substring(16, 32);
            suffix = length > 48 ? text.substring(32, 48) : text.substring(32);
        } else {
            center = text.substring(16);
        }
        return new String[]{prefix, center, suffix};
    }
}