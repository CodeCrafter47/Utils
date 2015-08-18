package codecrafter47.util.bungee;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

public class PingTask implements Runnable {

    private final ServerInfo server;
    private boolean online = true;
    private int maxPlayers = Integer.MAX_VALUE;
    private int onlinePlayers = 0;

    public PingTask(ServerInfo server) {
        this.server = server;
    }

    public boolean isOnline() {
        return online;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    @Override
    public void run() {
        if (!BungeeCord.getInstance().isRunning) return;
        server.ping(new Callback<ServerPing>() {

            @Override
            public void done(ServerPing v, Throwable thrwbl) {
                if (thrwbl != null) {
                    online = false;
                    return;
                }
                if (v == null) {
                    PingTask.this.online = false;
                    return;
                }
                online = true;
                maxPlayers = v.getPlayers().getMax();
                onlinePlayers = v.getPlayers().getOnline();
            }

        });
    }
}
