package club.someoneice.togocup.tags;

import club.someoneice.togocup.recipebook.JarUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

class JsonHelper {
    static void readFromJson() {
        PineappleTags.LOGGER.info("PineappleRecipeBook is install! Now click tags form json...");

        Gson gson = new Gson();
        for (JarUtil.UrlBuffered buffered : JarUtil.getInstance().getDataSet()) {
            String name = buffered.getFileUrl();
            if (name.contains(".json") && name.contains("tag/")) {
                String fileName = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("."));
                if (name.contains("item/")) {
                    String text = JarUtil.getInstance().readFileFromUrl(buffered);
                    PineappleTags.LOGGER.info(text);
                    List<String> list = gson.fromJson(text, new TypeToken<List<String>>() {
                    }.getType());
                    if (list != null) {
                        PineappleTags.LOGGER.info("create tag by json: " + fileName);
                        TagsManager.manager().registerTag(fileName, getItems(list));
                    }
                } else if (name.contains("block/")) {
                    List<String> list = gson.fromJson(JarUtil.getInstance().readFileFromUrl(buffered), new TypeToken<List<String>>() {
                    }.getType());
                    if (list != null) {
                        PineappleTags.LOGGER.info("create tag by json: " + fileName);
                        TagsManager.manager().registerTag(fileName, getBlocks(list));
                    }
                }
            }
        }
    }

    private static Item[] getItems(List<String> s) {
        List<Item> items = Lists.newArrayList();
        for (String itmName : s) {
            Item it = (Item) Item.itemRegistry.getObject(itmName);
            if (it == null) {
                it = Item.getItemFromBlock(Block.getBlockFromName(itmName));
            }

            if (it != null) {
                items.add(it);
            }
        }

        Item[] array = new Item[items.size()];
        for (int i = 0; i < items.size(); i ++)
            array[i] = items.get(i);

        return array;
    }

    private static Block[] getBlocks(List<String> s) {
        List<Block> blocks = Lists.newArrayList();
        for (String itmName : s) {
            Block it = Block.getBlockFromName(itmName);

            if (it != null) {
                blocks.add(it);
            }
        }

        Block[] array = new Block[blocks.size()];
        for (int i = 0; i < blocks.size(); i ++)
            array[i] = blocks.get(i);

        return array;
    }
}
