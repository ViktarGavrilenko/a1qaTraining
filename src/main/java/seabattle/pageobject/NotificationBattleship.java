package seabattle.pageobject;

public enum NotificationBattleship {
    LOSE("game-over-lose"),
    WIN("game-over-win"),
    RIVAL_LEAVE("rival-leave"),
    SERVER_ERROR("server-error"),
    GAME_ERROR("game-error"),
    MOVE_ON("move-on"),
    MOVE_OFF("move-off");

    private final String textNotification;

    NotificationBattleship(String textNotification) {
        this.textNotification = textNotification;
    }

    public String getTextNotification() {
        return textNotification;
    }
}