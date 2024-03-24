package club.someoneice.togocup.tags;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(modid = "pineapple_tags")
public class PineappleTags {
    public static final Logger LOGGER = LogManager.getLogger("pineapple_tags");

    @Mod.EventHandler
    public void commonInit(FMLInitializationEvent event) {
        registryEvent(this);
        if (Loader.isModLoaded("pineapple_recipe_book"))
            JsonHelper.readFromJson();
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }

    @SubscribeEvent
    public void itemTooltipEvent(ItemTooltipEvent event) {
        if (!event.showAdvancedItemTooltips || event.itemStack == null) return;
        ItemStack item = event.itemStack;
        List<String> tags = TagsManager.manager().getTagsFromObjects(item.getItem());
        if (!tags.isEmpty()) event.toolTip.add("Tags: " + tags);
    }
}
