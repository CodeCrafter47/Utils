package codecrafter47.data.bukkit;

import codecrafter47.data.DataAggregator;
import codecrafter47.data.Values;
import codecrafter47.data.essentials.EssentialsIsVanishedProvider;
import codecrafter47.data.factions275.*;
import codecrafter47.data.playerpoints.PlayerPointsProvider;
import codecrafter47.data.simpleclans.SimpleClansClanNameProvider;
import codecrafter47.data.simpleclans.SimpleClansMembersProvider;
import codecrafter47.data.simpleclans.SimpleClansOnlineClanMembersProvider;
import codecrafter47.data.supervanish.SuperVanishIsVanishedProvider;
import codecrafter47.data.vanishnopacket.VanishNoPacketIsVanishedProvider;
import codecrafter47.data.vault.VaultBalanceProvider;
import codecrafter47.data.vault.VaultGroupProvider;
import codecrafter47.data.vault.VaultPrefixProvider;
import codecrafter47.data.vault.VaultSuffixProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.logging.Logger;

public class PlayerDataAggregator extends DataAggregator<Player> {

    @Inject
    public PlayerDataAggregator(Logger logger) {
        super(logger);
        init();
    }

    protected void init() {
        bind(Values.Player.Minecraft.UserName, Player::getName);
        bind(Values.Player.Minecraft.UUID, Player::getUniqueId);
        bind(Values.Player.Minecraft.Health, Damageable::getHealth);
        bind(Values.Player.Minecraft.Level, Player::getLevel);
        bind(Values.Player.Minecraft.MaxHealth, Player::getMaxHealth);
        bind(Values.Player.Minecraft.XP, Player::getExp);
        bind(Values.Player.Minecraft.TotalXP, Player::getTotalExperience);
        bind(Values.Player.Minecraft.PosX, player -> player.getLocation().getX());
        bind(Values.Player.Minecraft.PosY, player -> player.getLocation().getY());
        bind(Values.Player.Minecraft.PosZ, player -> player.getLocation().getZ());

        bind(Values.Player.Bukkit.DisplayName, Player::getDisplayName);
        bind(Values.Player.Bukkit.PlayerListName, Player::getPlayerListName);
        bind(Values.Player.Bukkit.World, player -> player.getWorld().getName());

        if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
            bind(Values.Player.Vault.Balance, new VaultBalanceProvider());
            bind(Values.Player.Vault.PermissionGroup, new VaultGroupProvider());
            bind(Values.Player.Vault.Prefix, new VaultPrefixProvider());
            bind(Values.Player.Vault.Suffix, new VaultSuffixProvider());
        }

        if(Bukkit.getPluginManager().getPlugin("VanishNoPacket") != null) {
            bind(Values.Player.VanishNoPacket.IsVanished, new VanishNoPacketIsVanishedProvider());
        }

        if(Bukkit.getPluginManager().getPlugin("PlayerPoints") != null) {
            bind(Values.Player.PlayerPoints.Points, new PlayerPointsProvider(logger));
        }

        if(Bukkit.getPluginManager().getPlugin("Factions") != null) {
            if(classExists("com.massivecraft.factions.FPlayer")){
                bind(Values.Player.Factions.FactionName, new codecrafter47.data.factionsuuid.FactionNameProvider());
                bind(Values.Player.Factions.FactionMembers, new codecrafter47.data.factionsuuid.FactionMembersProvider());
                bind(Values.Player.Factions.FactionPower, new codecrafter47.data.factionsuuid.FactionPowerProvider());
                bind(Values.Player.Factions.FactionsRank, new codecrafter47.data.factionsuuid.FactionRankProvider());
                bind(Values.Player.Factions.FactionsWhere, new codecrafter47.data.factionsuuid.FactionWhereProvider());
                bind(Values.Player.Factions.OnlineFactionMembers, new codecrafter47.data.factionsuuid.FactionOnlineMembersProvider());
                bind(Values.Player.Factions.PlayerPower, new codecrafter47.data.factionsuuid.FactionPlayerPowerProvider());
            } else if (classExists("com.massivecraft.factions.entity.MPlayer")) {
                bind(Values.Player.Factions.FactionName, new FactionNameProvider());
                bind(Values.Player.Factions.FactionMembers, new FactionMembersProvider());
                bind(Values.Player.Factions.FactionPower, new FactionPowerProvider());
                bind(Values.Player.Factions.FactionsRank, new FactionRankProvider());
                bind(Values.Player.Factions.FactionsWhere, new FactionWhereProvider());
                bind(Values.Player.Factions.OnlineFactionMembers, new FactionOnlineMembersProvider());
                bind(Values.Player.Factions.PlayerPower, new FactionsPlayerPowerProvider());
            } else {
                logger.warning("Unable to recognize your Factions version. Factions support is disabled. Please contact " +
                        "the plugin developer to request support for your Factions version (" +
                        Bukkit.getPluginManager().getPlugin("Factions").getDescription().getVersion() + ").");
            }
        }

        if(Bukkit.getPluginManager().getPlugin("SuperVanish") != null) {
            bind(Values.Player.SuperVanish.IsVanished, new SuperVanishIsVanishedProvider());
        }

        if(Bukkit.getPluginManager().getPlugin("SimpleClans") != null) {
            bind(Values.Player.SimpleClans.ClanName, new SimpleClansClanNameProvider());
            bind(Values.Player.SimpleClans.ClanMembers, new SimpleClansMembersProvider());
            bind(Values.Player.SimpleClans.OnlineClanMembers, new SimpleClansOnlineClanMembersProvider());
        }

        if(Bukkit.getPluginManager().getPlugin("Essentials") != null) {
            bind(Values.Player.Essentials.IsVanished, new EssentialsIsVanishedProvider());
        }
    }

    private static boolean classExists(String name){
        try {
            Class.forName(name);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
