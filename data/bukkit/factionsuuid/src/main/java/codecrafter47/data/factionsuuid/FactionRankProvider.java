package codecrafter47.data.factionsuuid;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Role;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionRankProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if(fPlayer != null){
            Role role = fPlayer.getRole();
            if(role != null){
                return role.getTranslation().toString();
            }
        }
        return null;
    }
}
