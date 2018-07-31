package net.runelite.client.plugins.inventorynotifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("inventorynotifier")
public interface InventoryNotifierConfig extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "itemName",
            name = "Item name",
            description = "Configures specifically what item to notify for."
    )
    default String itemName()
    {
        return "";
    }

    @ConfigItem(
            keyName = "count",
            name = "Notify On Count",
            description = "Configure when to notify",
            position = 2
    )
    default int count()
    {
        return 2;
    }
}
