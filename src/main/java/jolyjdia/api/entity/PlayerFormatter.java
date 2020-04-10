package jolyjdia.api.entity;

public interface PlayerFormatter {
    void setPrefix(String prefix);

    void setSuffix(String suffix);

    String getPrefix();

    String getSuffix();
}
