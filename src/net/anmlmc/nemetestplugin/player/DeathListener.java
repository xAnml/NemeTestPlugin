package net.anmlmc.nemetestplugin.player;

import net.anmlmc.nemetestplugin.NemeTestPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/*******************
 * Created by Anml *
 *******************/

public class DeathListener implements Listener {

    private NemeTestPlugin instance;

    public DeathListener(NemeTestPlugin instance) {
        this.instance = instance;
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(final PlayerDeathEvent e) {

        if (!(e.getEntity().getKiller() instanceof Player))
            return;

        Player playerKilled = e.getEntity();
        NemePlayer killed = instance.getNemePlayer(playerKilled.getUniqueId());
        Player playerKiller = e.getEntity().getKiller();
        NemePlayer killer = instance.getNemePlayer(playerKiller.getUniqueId());

        killed.incrementDeaths();
        killer.incrementKills();

    }
}
