package net.runelite.client.plugins.inventorynotifier;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "Inventory Notifier",
        description = "Notify you when your inventory changes",
        tags = {"notify", "inventory", "notification", "notifier", "change"},
        enabledByDefault = false
)
public class InventoryNotifierPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private Notifier notifier;

    @Inject
    private ItemManager itemManager;

    @Inject
    private InventoryNotifierConfig config;

    @Provides
    InventoryNotifierConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(InventoryNotifierConfig.class);
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged itemContainerChanged)
    {
        ItemContainer itemContainer = itemContainerChanged.getItemContainer();
        if (itemContainer == client.getItemContainer(InventoryID.INVENTORY))
        {
            final Item[] inventory = itemContainer.getItems();
            final String notifyItemName = config.itemName();
            final int notifyItemCount = config.count();
            int count = 0;

            for(Item item: inventory)
            {
                ItemComposition itemComposition = itemManager.getItemComposition(item.getId());
                if(itemComposition.getName().equalsIgnoreCase(notifyItemName)) count++;
            }

            if(count == notifyItemCount) notifier.notify("You have " + count + " " + notifyItemName + ".");
        }
    }
}
