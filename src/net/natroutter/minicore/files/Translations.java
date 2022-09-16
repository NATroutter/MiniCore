package net.natroutter.minicore.files;


import net.natroutter.natlibs.handlers.langHandler.TranslationTemplate;
import net.natroutter.natlibs.handlers.langHandler.language.key.TranslationKey;

public enum Translations implements TranslationTemplate {
    Prefix,
    NoPerm,
    InvalidArgs,
    ToomanyArgs,
    OnlyIngame,
    ToggleFly,
    ToggleFlyOther,
    SpeedNotDefined,
    InvalidSpeed,
    InvalidSpeedType,
    SpeedOutOfRange,
    SpeedChanged,
    SpeedChangedOther,
    InvalidPlayer,
    InvalidGamemode,
    GamemodeChanged,
    GamemodeChangedOther,
    ItemRenamed,
    InvalidItem,
    LoreChanged,
    EnderChestOpened,
    EnderChestOpenedOther,
    PlayerInvOpened,
    SpawnSet,
    SpawnNotset,
    TeleportedToSpawn,
    TeleportedToSpawnOther,
    ItemCleaned,
    ItemAlreadyClean,
    LoreAdded,
    InvalidBroadcastMessage,
    ChatCleaned,
    InventoryCleaned,
    InventoryCleanedOther,
    TimeSetToDay,
    TimeSetToNight,
    Fed,
    FedOther,
    Healed,
    HealedOther,
    TeleportedToTop,
    TeleportedToYou,
    AllPlayersTeleported,
    CantTargetYourSelf,
    GodToggled,
    GodToggledOther,
    Unknown,
    Joined,
    Quit,
    FirstTimeWelcome,
    ConsoleName,
    WorldNeeded,
    InvalidWorld,
    LocationFormat,
    ListFormat_Format("ListFormat.Format"),
    ListFormat_Entry("ListFormat.Entry"),
    ListFormat_Separator("ListFormat.Separator"),
    GameModes_Survival("GameModes.Survival"),
    GameModes_Creative("GameModes.Creative"),
    GameModes_Adventure("GameModes.Adventure"),
    GameModes_Separator("GameModes.Spectator"),
    ToggleStates_Enabled("ToggleStates.Enabled"),
    ToggleStates_Disabled("ToggleStates.Disabled"),
    Statues_yes("Statues.yes"),
    Statues_no("Statues.no"),
    SpeedTypes_Walking("SpeedTypes.Walking"),
    SpeedTypes_Flying("SpeedTypes.Flying");

    private String path = null;
    Translations() {}
    Translations(String path) {
        this.path = path;
    }

    @Override
    public TranslationKey getKey() {
        if (path == null) {
            return TranslationKey.of(this.name());
        }
        return TranslationKey.of(this.path);
    }

}