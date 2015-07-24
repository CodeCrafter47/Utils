package codecrafter47.data.vault;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.function.Function;

public class VaultSuffixProvider implements Function<Player, String>{
    public String apply(Player player) {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        Chat chat = rsp.getProvider();
        if(chat != null){
            return chat.getPlayerSuffix(player);
        }
        return null;
    }
}
