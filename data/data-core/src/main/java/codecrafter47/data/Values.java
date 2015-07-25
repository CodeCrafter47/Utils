package codecrafter47.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Values {
    private static Map<String, Value<?>> idToValueMap = new HashMap<>();

    protected static <T> Value<T> registerValue(String id) {
        Value<T> value = Value.create(id);
        idToValueMap.put(value.getId(), value);
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <T> Value<T> getValue(String id) {
        return (Value<T>) idToValueMap.get(id);
    }

    static {
        // cause all classes to load
        c(Player.Minecraft.class);
        c(Player.Bukkit.class);
        c(Player.Vault.class);
        c(Player.VanishNoPacket.class);
        c(Player.PlayerPoints.class);
        c(Player.Factions.class);
        c(Player.SuperVanish.class);
        c(Player.SimpleClans.class);
        c(Player.Essentials.class);
        c(Server.Vault.class);
    }

    private static void c(Class<?> c) {
        try {
            // force load class
            Class.forName(c.getName());
        } catch (Throwable ignored) {

        }
    }

    public static class Player {
        public static class Minecraft {
            public final static Value<String> UserName = registerValue("minecraft:username");
            public final static Value<UUID> UUID = registerValue("minecraft:uuid");
            public final static Value<Double> Health = registerValue("minecraft:health");
            public final static Value<Double> MaxHealth = registerValue("minecraft:maxhealth");
            public final static Value<Integer> Level = registerValue("minecraft:xplevel");
            public final static Value<Float> XP = registerValue("minecraft:xp");
            public final static Value<Integer> TotalXP = registerValue("minecraft:totalxp");
            public final static Value<Double> PosX = registerValue("minecraft:posx");
            public final static Value<Double> PosY = registerValue("minecraft:posy");
            public final static Value<Double> PosZ = registerValue("minecraft:posz");
        }

        public static class Bukkit {
            public final static Value<String> PlayerListName = registerValue("bukkit:playerlistname");
            public final static Value<String> DisplayName = registerValue("bukkit:displayname");
            public final static Value<String> World = registerValue("bukkit:world");
        }

        public static class Vault {
            public final static Value<String> Prefix = registerValue("vault:prefix");
            public final static Value<String> Suffix = registerValue("vault:suffix");
            public final static Value<String> PermissionGroup = registerValue("vault:permgroup");
            public final static Value<Double> Balance = registerValue("vault:balance");
        }

        public static class VanishNoPacket {
            public final static Value<Boolean> IsVanished = registerValue("vanishnopacket:isvanished");
        }

        public static class PlayerPoints {
            public final static Value<Integer> Points = registerValue("playerpoints:points");
        }

        public static class Factions {
            public final static Value<String> FactionName = registerValue("factions:factionname");
            public final static Value<Integer> FactionMembers = registerValue("factions:members");
            public final static Value<Integer> OnlineFactionMembers = registerValue("factions:onlinemembers");
            public final static Value<String> FactionsWhere = registerValue("factions:where");
            public final static Value<String> FactionsRank = registerValue("factions:rank");
            public final static Value<Integer> FactionPower = registerValue("factions:factionpower");
            public final static Value<Integer> PlayerPower = registerValue("factions:factionpower");
        }

        public static class SuperVanish {
            public final static Value<Boolean> IsVanished = registerValue("supervanish:isvanished");
        }

        public static class SimpleClans {
            public final static Value<String> ClanName = registerValue("simpleclans:clanname");
            public final static Value<Integer> ClanMembers = registerValue("simpleclans:clanmembers");
            public final static Value<Integer> OnlineClanMembers = registerValue("simpleclans:onlineclanmembers");
            public final static Value<String> ClanTag = registerValue("simpleclans:clantag");
            public final static Value<String> ClanTagLabel = registerValue("simpleclans:clantaglabel");
            public final static Value<String> ClanColorTag = registerValue("simpleclans:clancolortag");
        }

        public static class Essentials {
            public static final Value<Boolean> IsVanished = registerValue("essentials:vanished");
        }
    }

    public static class Server {
        public static Value<Double> TPS = registerValue("minecraft:tps");
        public static Value<String> MinecraftVersion = registerValue("minecraft:version");
        public static Value<String> ServerModName = registerValue("bukkit:name");
        public static Value<String> ServerModVersion = registerValue("bukkit:version");

        public static class Vault {
            public final static Value<String> CurrencyNameSingular = registerValue("vault:currencynamesingular");
            public final static Value<String> CurrencyNamePlural = registerValue("vault:currencynameplural");
        }
    }
}
