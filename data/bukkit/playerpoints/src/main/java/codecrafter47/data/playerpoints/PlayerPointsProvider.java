package codecrafter47.data.playerpoints;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerPointsProvider implements Function<Player, Integer> {
    private final Logger logger;

    @Inject
    public PlayerPointsProvider(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Integer apply(Player player) {
        PlayerPointsAPI playerPoints = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
        for(int i = 0; i < 5; i++){
            try {
                return playerPoints.look(player.getUniqueId());
            } catch (Throwable th){
                if (i == 4){
                    logger.log(Level.SEVERE, "Failed to query PlayerPoints for " + player.getName() + ". Attempt 5", th);
                }
            }
        }
        return null;
    }
}
