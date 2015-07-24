package codecrafter47.data.bukkit;

import codecrafter47.data.DataAggregator;
import codecrafter47.data.Values;
import codecrafter47.data.vault.*;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class ServerDataAggregator extends DataAggregator<Server> {

    private final Plugin plugin;

    @Inject
    public ServerDataAggregator(Plugin plugin) {
        super(plugin.getLogger());
        this.plugin = plugin;
        init();
    }

    protected void init() {
        bind(Values.Server.MinecraftVersion, Server::getVersion);
        bind(Values.Server.ServerModName, Server::getName);
        bind(Values.Server.ServerModVersion, Server::getBukkitVersion);
        bind(Values.Server.TPS, ServerTPSProvider.getInstance(plugin));

        if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
            bind(Values.Server.Vault.CurrencyNamePlural, new VaultCurrencyNamePluralProvider());
            bind(Values.Server.Vault.CurrencyNameSingular, new VaultCurrencyNameSingularProvider());
        }
    }

}
