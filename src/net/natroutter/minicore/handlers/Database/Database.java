package net.natroutter.minicore.handlers.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sqlite.SQLiteConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.util.UUID;

public class Database {

    private Dao<PlayerData, UUID> PlayerDao;

    private boolean valid = false;

    public boolean getValid() {
        return valid;
    }

    public Database(JavaPlugin pl) {
        ConsoleCommandSender console = pl.getServer().getConsoleSender();


        try {
            String ConString = "jdbc:sqlite:" + pl.getDataFolder() + "/database.db";
            JdbcPooledConnectionSource source = new JdbcPooledConnectionSource(ConString);
            TableUtils.createTableIfNotExists(source, PlayerData.class);
            PlayerDao = DaoManager.createDao(source, PlayerData.class);

            valid = true;
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"][Database] §cDatabase failure");
            e.printStackTrace();
        }
    }

    public Dao<PlayerData, UUID> getPlayerData() {
        if (!valid) {return null;}
        return PlayerDao;
    }

}
