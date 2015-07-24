package codecrafter47.data.factionsuuid;

import com.massivecraft.factions.*;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionWhereProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        Faction factionAt = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
        if(factionAt != null){
            return factionAt.getId();
        }
        return null;
    }
}
