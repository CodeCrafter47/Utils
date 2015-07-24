package codecrafter47.data.vanishnopacket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishPlugin;

import java.util.function.Function;

public class VanishNoPacketIsVanishedProvider implements Function<Player, Boolean> {
    @Override
    public Boolean apply(Player player) {
        return ((VanishPlugin) Bukkit.getPluginManager().getPlugin("VanishNoPacket")).getManager().isVanished(player);
    }
}
