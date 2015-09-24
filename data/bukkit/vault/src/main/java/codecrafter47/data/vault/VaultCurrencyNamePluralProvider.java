package codecrafter47.data.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.function.Function;

public class VaultCurrencyNamePluralProvider implements Function<Server, String> {
    public String apply(Server server) {
        RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            Economy economy = rsp.getProvider();
            if (economy != null && economy.isEnabled()) {
                return economy.currencyNamePlural();
            }
        }
        return null;
    }
}
