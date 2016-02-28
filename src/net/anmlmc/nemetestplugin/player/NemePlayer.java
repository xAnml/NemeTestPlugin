package net.anmlmc.nemetestplugin.player;

import net.anmlmc.nemetestplugin.NemeTestPlugin;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

/*******************
 * Created by Anml *
 *******************/

public class NemePlayer {

    private UUID uuid;
    private int kills;
    private int deaths;
    private NemeTestPlugin instance = NemeTestPlugin.getInstance();

    public NemePlayer(UUID uuid) {
        this.uuid = uuid;
        try {
            load();
        } catch (Exception e) {
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public void save() {
        HashMap<String, Object> data = new HashMap<>();

        data.put("Kills", kills);
        data.put("Deaths", deaths);

        instance.getDatabaseManager().set("PlayerInfo", data, "UUID", uuid.toString());
    }

    private void load() throws Exception {
        if (instance.getDatabaseManager().exists("PlayerInfo", "UUID", uuid.toString())) {
            ResultSet set = instance.getDatabaseManager().getResultSetByUUID("PlayerInfo", uuid.toString());
            this.kills = Integer.parseInt(set.getString("Kills"));
            this.deaths = Integer.parseInt(set.getString("Deaths"));
        } else {
            kills = 0;
            deaths = 0;
        }
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int value) {
        kills = value;
    }

    public void incrementKills() {
        kills += 1;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int value) {
        deaths = value;
    }

    public void incrementDeaths() {
        deaths += 1;
    }

}
