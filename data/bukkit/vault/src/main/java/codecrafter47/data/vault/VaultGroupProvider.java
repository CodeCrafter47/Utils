package codecrafter47.data.vault;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.function.Function;

public class VaultGroupProvider implements Function<Player, String> {
    public String apply(Player player) {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
        if (rsp != null) {
            Permission permission = rsp.getProvider();
            if (permission != null) {
                return permission.getPrimaryGroup(player);
            }
        }
        return null;
    }
}
