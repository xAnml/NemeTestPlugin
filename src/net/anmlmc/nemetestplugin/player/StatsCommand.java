package net.anmlmc.nemetestplugin.player;

import net.anmlmc.nemetestplugin.NemeTestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/*******************
 * Created by Anml *
 *******************/

public class StatsCommand implements CommandExecutor {

    NemeTestPlugin instance;

    public StatsCommand(NemeTestPlugin instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(instance.colorize("&4Usage: &c/" + cmd + " <player>"));
            return false;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

        if (offlinePlayer.isOnline()) {
            execute(sender, offlinePlayer.getUniqueId());
            return true;
        }

        boolean exists = instance.getDatabaseManager().exists("PlayerInfo", "UUID", offlinePlayer.getUniqueId().toString());

        if (exists) {
            execute(sender, offlinePlayer.getUniqueId());
            return true;
        } else {
            sender.sendMessage(instance.colorize("&c" + offlinePlayer.getName() + " currently does not have any existing stats."));
            return false;
        }
    }

    public void execute(CommandSender sender, UUID uuid) {
        NemePlayer nemePlayer = instance.getNemePlayer(uuid);
        String objectiveColor = instance.getConfig().contains("ObjectiveColor") ? instance.getConfig().getString("ObjectiveColor") : "&f";
        String valueColor = instance.getConfig().contains("ValueColor") ? instance.getConfig().getString("ValueColor") : "&f";
        sender.sendMessage(instance.colorize("\n&b" + Bukkit.getOfflinePlayer(uuid).getName() + "&f's Stats:"));
        sender.sendMessage(instance.colorize(objectiveColor + "Kills: " + valueColor + nemePlayer.getKills()));
        sender.sendMessage(instance.colorize(objectiveColor + "Deaths: " + valueColor + nemePlayer.getDeaths() + "\n"));
    }
}
