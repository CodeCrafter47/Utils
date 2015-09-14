package codecrafter47.data.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.logging.Level;

public class VaultBalanceProvider implements Function<Player, Double>{
    private final Plugin plugin;

    public VaultBalanceProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    public Double apply(Player player) {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            Economy economy = rsp.getProvider();
            if (economy != null && economy.isEnabled()) {
                if(economy.getName().equals("Gringotts")){
                    try {
                        return Bukkit.getScheduler().callSyncMethod(plugin, () -> economy.getBalance(player)).get();
                    } catch (InterruptedException | ExecutionException e) {
                        plugin.getLogger().log(Level.SEVERE, "Failed to query balance for player " + player.getName(), e);
                    }
                } else {
                    return economy.getBalance(player);
                }
            }
        }
        return null;
    }
}
