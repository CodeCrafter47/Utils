package codecrafter47.data.factions275;

import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionNameProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        MPlayer mPlayer = MPlayer.get(player);
        if(mPlayer != null){
            return mPlayer.getFactionName();
        }
        return null;
    }
}
