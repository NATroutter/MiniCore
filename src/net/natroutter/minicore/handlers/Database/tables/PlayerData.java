package net.natroutter.minicore.handlers.Database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "PlayerData")
public class PlayerData {

    @DatabaseField(canBeNull = false, id = true)
    UUID uuid;

    @DatabaseField(canBeNull = false)
    String name;

    @DatabaseField(canBeNull = false)
    String displayname;

    @DatabaseField(canBeNull = false)
    Boolean online;

    @DatabaseField(canBeNull = false)
    String ip;

    @DatabaseField(canBeNull = false)
    String lastloc;

    @DatabaseField(canBeNull = false)
    String locale;

    @DatabaseField(canBeNull = false)
    Double health;

    @DatabaseField(canBeNull = false)
    Double maxhealth;

    @DatabaseField(canBeNull = false)
    Integer food;

    @DatabaseField(canBeNull = false)
    Integer level;

    @DatabaseField(canBeNull = false)
    String gamemode;

    @DatabaseField(canBeNull = false)
    Float flyspeed;

    @DatabaseField(canBeNull = false)
    Float walkspeed;

    @DatabaseField(canBeNull = false)
    String world;

    @DatabaseField(canBeNull = false)
    Long firstplayed;

    @DatabaseField(canBeNull = false)
    Long lastplayed;

    @DatabaseField(canBeNull = false)
    String bedlocation;

    @DatabaseField(canBeNull = false)
    Boolean flying;

    @DatabaseField(canBeNull = false)
    Boolean allowedfly;

    @DatabaseField(canBeNull = false)
    Boolean invehicle;

    @DatabaseField(canBeNull = false)
    Boolean god;

    public PlayerData() {}
    public PlayerData(UUID uuid, String name, String displayname, Boolean online, String ip, String lastloc,
                      String locale, Double health, Double maxhealth, Integer food, Integer level, String gamemode, Float flyspeed, Float walkspeed,
                      String world, Long firstplayed, Long lastplayed, String bedlocation, Boolean flying, Boolean allowedfly,
                      Boolean invehicle, Boolean god) {

        this.uuid = uuid;
        this.name = name;
        this.displayname = displayname;
        this.online = online;
        this.ip = ip;
        this.lastloc = lastloc;
        this.locale = locale;
        this.health = health;
        this.maxhealth = maxhealth;
        this.food = food;
        this.level = level;
        this.gamemode = gamemode;
        this.flyspeed = flyspeed;
        this.walkspeed = walkspeed;
        this.world = world;
        this.firstplayed = firstplayed;
        this.lastplayed = lastplayed;
        this.bedlocation = bedlocation;
        this.flying = flying;
        this.allowedfly = allowedfly;
        this.invehicle = invehicle;
        this.god = god;
    }
    public Double getMaxhealth() {
        return maxhealth;
    }

    public Boolean getOnline() {
        return online;
    }
    public Integer getFood() {
        return food;
    }
    public Double getHealth() {
        return health;
    }
    public Float getFlyspeed() {return flyspeed;}
    public Integer getLevel() {
        return level;
    }
    public Boolean getAllowedfly() {
        return allowedfly;
    }
    public Boolean getFlying() {
        return flying;
    }
    public Boolean getGod() {
        return god;
    }
    public Float getWalkspeed() {
        return walkspeed;
    }
    public String getLastloc() {
        return lastloc;
    }
    public String getDisplayname() {return displayname;}

    public Long getFirstplayed() {
        return firstplayed;
    }

    public String getGamemode() {
        return gamemode;
    }

    public String getIp() {
        return ip;
    }

    public Boolean getInvehicle() {
        return invehicle;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getLocale() {
        return locale;
    }

    public String getWorld() {
        return world;
    }

    public Long getLastplayed() {
        return lastplayed;
    }

    public String getBedlocation() {
        return bedlocation;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setMaxhealth(Double maxhealth) {
        this.maxhealth = maxhealth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLastloc(String lastloc) {
        this.lastloc = lastloc;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    public void setFlyspeed(Float flyspeed) {
        this.flyspeed = flyspeed;
    }

    public void setWalkspeed(Float walkspeed) {
        this.walkspeed = walkspeed;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setFirstplayed(Long firstplayed) {
        this.firstplayed = firstplayed;
    }

    public void setLastplayed(Long lastplayed) {
        this.lastplayed = lastplayed;
    }

    public void setBedlocation(String bedlocation) {
        this.bedlocation = bedlocation;
    }

    public void setFlying(Boolean flying) {
        this.flying = flying;
    }

    public void setAllowedfly(Boolean allowedfly) {
        this.allowedfly = allowedfly;
    }

    public void setInvehicle(Boolean invehicle) {
        this.invehicle = invehicle;
    }

    public void setGod(Boolean god) {
        this.god = god;
    }
}
