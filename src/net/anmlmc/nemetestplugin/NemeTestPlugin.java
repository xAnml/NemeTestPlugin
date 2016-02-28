package net.anmlmc.nemetestplugin;

import com.google.common.collect.Maps;
import net.anmlmc.nemetestplugin.database.DatabaseManager;
import net.anmlmc.nemetestplugin.player.DeathListener;
import net.anmlmc.nemetestplugin.player.NemePlayer;
import net.anmlmc.nemetestplugin.player.StatsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

/*******************
 * Created by Anml *
 *******************/

public class NemeTestPlugin extends JavaPlugin {

    private static NemeTestPlugin instance;
    private DatabaseManager databaseManager;
    private Map<UUID, NemePlayer> players = Maps.newConcurrentMap();

    public static NemeTestPlugin getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        String[] data = getConfig().getString("SQL").split(" ");
        databaseManager = new DatabaseManager(data[0], data[1], data[2], data[4], data[4]);
        registerEvents();
        registerCommands();
    }

    public void onDisable() {

        for (NemePlayer nemePlayer : players.values()) {
            nemePlayer.save();
        }

        saveConfig();
        instance = null;
    }

    public void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new DeathListener(this), this);
    }

    public void registerCommands() {
        getCommand("stats").setExecutor(new StatsCommand(this));
    }

    public NemePlayer getNemePlayer(UUID uuid) {
        if (players.containsKey(uuid))
            return players.get(uuid);

        if (Bukkit.getPlayer(uuid) != null) {
            NemePlayer nemePlayer = new NemePlayer(uuid);
            players.put(uuid, nemePlayer);
            return nemePlayer;
        } else {
            return new NemePlayer(uuid);
        }
    }

    public String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

}
