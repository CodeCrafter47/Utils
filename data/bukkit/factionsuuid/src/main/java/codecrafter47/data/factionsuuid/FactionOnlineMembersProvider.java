package codecrafter47.data.factionsuuid;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionOnlineMembersProvider implements Function<Player, Integer> {
    @Override
    public Integer apply(Player player) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if(fPlayer != null){
            Faction faction = fPlayer.getFaction();
            if(faction != null){
                return faction.getOnlinePlayers().size();
            }
        }
        return null;
    }
}
