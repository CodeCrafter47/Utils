package codecrafter47.data.factions275;

import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class FactionsPlayerPowerProvider implements Function<Player, Integer> {
    @Override
    public Integer apply(Player player) {
        MPlayer mPlayer = MPlayer.get(player);
        if(mPlayer != null){
            return mPlayer.getPowerRounded();
        }
        return null;
    }
}
