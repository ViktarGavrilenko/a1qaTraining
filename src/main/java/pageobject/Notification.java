package pageobject;

public enum Notification {
    LOSE("game-over-lose"),
    WIN("game-over-win"),
    RIVAL_LEAVE("rival-leave"),
    SERVER_ERROR("server-error"),
    GAME_ERROR("game-error"),
    MOVE_ON("move-on"),
    MOVE_OFF("move-off");

    private String textNotification;

    Notification(String title) {
        this.textNotification = title;
    }

    public String getTextNotification() {
        return textNotification;
    }
}
