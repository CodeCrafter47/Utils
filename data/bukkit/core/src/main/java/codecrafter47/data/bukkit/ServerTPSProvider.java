package codecrafter47.data.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import java.util.function.Function;

class ServerTPSProvider implements Function<Server, Double>, Runnable {
    private static ServerTPSProvider instance = null;

    public static synchronized ServerTPSProvider getInstance(Plugin plugin){
        if(instance == null){
            instance = new ServerTPSProvider(plugin);
        }
        return instance;
    }

    private ServerTPSProvider(Plugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, monitorInterval, monitorInterval);
    }

    @Override
    public Double apply(Server server) {
        return tps;
    }

    private final int monitorInterval = 40;
    private long prevtime;
    private double tps = 20;

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        long elapsedtime = time - prevtime;
        double elapsedtimesec = (double) elapsedtime / 1000;
        tps = monitorInterval / elapsedtimesec;
        prevtime = time;
    }
}
