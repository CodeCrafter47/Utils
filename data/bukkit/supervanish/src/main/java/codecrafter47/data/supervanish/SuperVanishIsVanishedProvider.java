package codecrafter47.data.supervanish;

import me.MyzelYam.SuperVanish.api.SVAPI;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class SuperVanishIsVanishedProvider implements Function<Player, Boolean> {
    @Override
    public Boolean apply(Player player) {
        return SVAPI.isInvisible(player);
    }
}
