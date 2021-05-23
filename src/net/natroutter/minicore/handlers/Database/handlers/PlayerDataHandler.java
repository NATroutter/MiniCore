package net.natroutter.minicore.handlers.Database.handlers;

import com.j256.ormlite.dao.Dao;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.Database;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerDataHandler {

    private static final Database database = MiniCore.getDatabase();
    private final static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public static PlayerData queryForID(UUID uuid) {
        if (uuid == null) {return null;}
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
        return new PlayerData(uuid, "Unknown", "Unknown", false, "Unknown", "Unknown", "Unknown", 0.0, 0.0, 0, 0, "Unknown", 0F, 0F, "Unknown", 0L, 0L, "Unknown", false, false, false, false);
    }


}
