package ninja.highground.sneakyassassins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SACmd implements CommandExecutor {

    private final SneakyAssassins sa;


    public SACmd(SneakyAssassins sa) {
        this.sa = sa;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sa")) {
            if (args.length == 0) {
                sendInvalid(sender, "<map|setLobby|lobby|start>");
                return true;
            }
//            if (args[0].equalsIgnoreCase("setLobby")) {
//                sa.setLobby(((Player) sender).getLocation());
//                sender.sendMessage(ChatColor.GREEN + "Lobby location set!");
//            }
//            if (args[0].equalsIgnoreCase("lobby")) {
//                if (sa == null) {
//                    sender.sendMessage(ChatColor.RED + "Lobby location not set!");
//                    return true;
//                }
//                for (Player p : Bukkit.getOnlinePlayers()) {
//                    p.teleport(sa.getLobby());
//                }
//                sender.sendMessage(ChatColor.GREEN + "Teleported all players to lobby.");
//            }
            if (args[0].equalsIgnoreCase("map")) {
                if (args[1].equalsIgnoreCase("list")) {
                    sender.sendMessage(sa.getMaps().keySet().toString());
                }
                if (args[1].equalsIgnoreCase("set")) {
                    if (!sa.getMarking().get((Player) sender)) {
                        sa.getMarking().put((Player) sender, true);
                        sender.sendMessage(ChatColor.GREEN + "Set the map area.\n(Left click to set first position; right click to set second position)\nDo /sa map set again when done.");
                        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
                        ItemMeta im = wand.getItemMeta();
                        im.setDisplayName(ChatColor.LIGHT_PURPLE + "Map Tool");
                        wand.setItemMeta(im);
                        ((Player) sender).getInventory().addItem(wand);
                    } else {
                        sa.getMarking().put((Player) sender, false);
                        sender.sendMessage(ChatColor.GREEN + "Map area set!\nChoose a name for this map.");
                        Inventory ai = Bukkit.createInventory(null, InventoryType.ANVIL, "Map Name");
                        ai.setItem(0, new ItemStack(Material.MAP));
                        ((Player) sender).openInventory(ai);
                    }
                }
            }
            if (args[0].equalsIgnoreCase("start")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    countDown(player, 5);
                }
            }
            return true;
        }
        return false;
    }

    public void sendInvalid(CommandSender sender, String usage) {
        sender.sendMessage(ChatColor.RED + "Usage: /sa " + usage);
    }

    public void countDown(Player player, int length) {
        new BukkitRunnable() {

            int i = length;

            @Override
            public void run() {
                if (i != 0) {
                    player.sendTitle(ChatColor.GREEN + "Game starts in " + i, null);
                    i--;
                } else {
                    player.resetTitle();
                    cancel();
                }

            }

        }.runTaskTimer(sa, 0, 20L);
    }

}
