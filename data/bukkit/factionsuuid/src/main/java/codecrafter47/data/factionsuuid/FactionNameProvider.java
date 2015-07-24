package codecrafter47.data.factionsuuid;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionNameProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if(fPlayer != null){
            return fPlayer.getFactionId();
        }
        return null;
    }
}
