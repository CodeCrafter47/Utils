package codecrafter47.data.essentials;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.function.Function;

public class EssentialsIsVanishedProvider implements Function<Player, Boolean> {
    @Override
    public Boolean apply(Player player) {
        Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if(essentials == null)return null;
        return essentials.getUser(player).isVanished();
    }
}
