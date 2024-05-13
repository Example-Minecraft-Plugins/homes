package me.davipccunha.homes.command;

import me.davipccunha.homes.HomesPlugin;
import me.davipccunha.homes.command.subcommand.*;
import me.davipccunha.utils.messages.ErrorMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HomeCommand implements CommandExecutor {
    private static String COMMAND_USAGE;
    private final Map<String, HomeSubCommand> subCommands = new HashMap<>();

    public HomeCommand(HomesPlugin plugin) {
        this.loadSubCommands(plugin);
        this.updateUsage();
    }

    private void updateUsage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/home <");
        for (String subCommand : this.subCommands.keySet()) {
            stringBuilder.append(subCommand).append(" | ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        stringBuilder.append(">");

        COMMAND_USAGE = stringBuilder.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessages.EXECUTOR_NOT_PLAYER.getMessage());
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§cUso: " + COMMAND_USAGE);
            return false;
        }

        final HomeSubCommand subCommand = this.subCommands.get(args[0]);

        if (subCommand == null) {
            sender.sendMessage(ErrorMessages.SUBCOMMAND_NOT_FOUND.getMessage());
            sender.sendMessage("§cUso: " + COMMAND_USAGE);
            return false;
        }

        if (!subCommand.execute(player, args)) {
            sender.sendMessage("§cUso: " + subCommand.getUsage());
            return false;
        }

        return true;
    }

    private void loadSubCommands(HomesPlugin plugin) {
        this.subCommands.put("definir", new HomeDefinirSubCommand(plugin));
        this.subCommands.put("set", new HomeDefinirSubCommand(plugin));
        this.subCommands.put("ir", new HomeIrSubCommand(plugin));
        this.subCommands.put("deletar", new HomeDeletarSubCommand(plugin));
        this.subCommands.put("del", new HomeDeletarSubCommand(plugin));
        this.subCommands.put("listar", new HomeListarSubCommand(plugin));
        this.subCommands.put("l", new HomeListarSubCommand(plugin));
    }
}
