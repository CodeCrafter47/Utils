package codecrafter47.data.simpleclans;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class SimpleClansClanNameProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        ClanPlayer clanPlayer = SimpleClans.getInstance().getClanManager().getClanPlayer(player);
        if(clanPlayer != null){
            Clan clan = clanPlayer.getClan();
            if(clan != null){
                return clan.getName();
            }
        }
        return null;
    }
}
