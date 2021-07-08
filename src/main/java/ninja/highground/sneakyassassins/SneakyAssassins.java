package ninja.highground.sneakyassassins;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SneakyAssassins extends JavaPlugin {

    private final HashMap<Player, Boolean> marking = new HashMap<>();
    private FileConfiguration data;
    private HashMap<String, Object> maps;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        createYaml();
        maps = (HashMap<String, Object>) this.data.getConfigurationSection("maps").getValues(false);
        System.out.println(maps);
        getCommand("sa").setExecutor(new SACmd(this));
        getServer().getPluginManager().registerEvents(new SomeListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public FileConfiguration getData() {
        return data;
    }

    public void setData(FileConfiguration data) {
        this.data = data;
    }

    public HashMap<String, Object> getMaps() {
        return maps;
    }

    public HashMap<Player, Boolean> getMarking() {
        return marking;
    }

    private void createYaml() {
        File dataFile = new File(getDataFolder(), "data.yml");

        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }

        data = new YamlConfiguration();

        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }


}
