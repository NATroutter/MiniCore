package net.natroutter.minicore.handlers.Database.tables;

import com.j256.ormlite.dao.Dao;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.Database;
import net.natroutter.natlibs.objects.BasePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerDataHandler {

    private static final Database database = MiniCore.getDatabase();
    private final static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public static PlayerData queryForID(UUID uuid) {
        try {
            PlayerData data = database.getPlayerData().queryForId(uuid);
            if (data == null) {
                data = DefaultPlayerData(uuid);
                if (database.getPlayerData().create(data) == 0) {
                    console.sendMessage("§4[MiniCore][PlayerDataHandler](query) §cFailed to create new player data to database replying default!");
                }
            }
            return data;
        } catch (SQLException e) {
            console.sendMessage("§4[MiniCore][PlayerDataHandler](query) §cDatabase failure!");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateForID(PlayerData data) {
        try {
            database.getPlayerData().createOrUpdate(data);
            return true;
        } catch (SQLException e) {
            console.sendMessage("§4[MiniCore][PlayerDataHandler](update) §cDatabase failure!");
            e.printStackTrace();
        }
        return false;
    }

    private static PlayerData DefaultPlayerData(UUID uuid) {
        return null; //TODO Default return object here
    }


}
