package ninja.highground.sneakyassassins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SomeListener implements Listener {

    private final SneakyAssassins sa;

    public SomeListener(SneakyAssassins sa) {
        this.sa = sa;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        sa.getMarking().put(e.getPlayer(), false);
    }

}
