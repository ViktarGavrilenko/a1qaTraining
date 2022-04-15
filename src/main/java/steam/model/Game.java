package steam.model;

import java.util.Objects;

public class Game {
    public int discountPct;
    public float discountOriginalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return discountPct == game.discountPct && Float.compare(game.discountOriginalPrice, discountOriginalPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountPct, discountOriginalPrice);
    }
}