package codecrafter47.data.factions275;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionWhereProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(player.getLocation()));
        if(factionAt != null){
            return factionAt.getName();
        }
        return null;
    }
}
