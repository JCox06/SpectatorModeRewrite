package me.ohowe12.spectatormode.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import me.ohowe12.spectatormode.SpectatorMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnWorldInteractListener implements Listener {

    private final SpectatorMode plugin;

    public OnWorldInteractListener(SpectatorMode plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        checkToCancel(event, event.getPlayer());
    }

    private void checkToCancel(Cancellable event, Player player) {
        if(plugin.getSpectatorManager().getStateHolder().hasPlayer(player)) {
            event.setCancelled(true);
        }
    }
}
