package codecrafter47.data.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.function.Function;

public class VaultBalanceProvider implements Function<Player, Double>{
    public Double apply(Player player) {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        Economy economy = rsp.getProvider();
        if(economy != null){
            return economy.getBalance(player);
        }
        return null;
    }
}
