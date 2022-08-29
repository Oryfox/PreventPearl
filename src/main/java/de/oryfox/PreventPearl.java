package de.oryfox;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PreventPearl extends JavaPlugin implements Listener {

    private final HashMap<UUID, Boolean> roastEnderpearls = new HashMap<>();

    @Override
    public void onEnable() {
        this.getCommand("togglePearl").setExecutor(((commandSender, command, s, strings) -> {
            this.roastEnderpearls.put(((Player) commandSender).getUniqueId(), !this.roastEnderpearls.getOrDefault(((Player) commandSender).getUniqueId(), false));
            if (this.roastEnderpearls.get(((Player) commandSender).getUniqueId())) {
                commandSender.sendMessage("Es werden keine Enderperlen mehr gedroppt.");
            } else {
                commandSender.sendMessage("Es werden wieder Enderperlen gedroppt.");
            }
            return true;
        }));
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && roastEnderpearls.getOrDefault(event.getEntity().getKiller().getUniqueId(), false) && event.getEntity().getType() == EntityType.ENDERMAN) {
            event.getDrops().removeIf(itemStack -> itemStack.getType() == Material.ENDER_PEARL);
        }
    }
}