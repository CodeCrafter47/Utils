package codecrafter47.data.factions275;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionRankProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        MPlayer mPlayer = MPlayer.get(player);
        if(mPlayer != null){
            Rel role = mPlayer.getRole();
            if(role != null){
                return role.name().toLowerCase();
            }
        }
        return null;
    }
}
