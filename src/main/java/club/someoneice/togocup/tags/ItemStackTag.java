package club.someoneice.togocup.tags;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackTag extends Tag<ItemStack> {
    ItemStackTag(String name) {
        super(name);
    }

    @Override
    public boolean has(Object item) {
        if (item instanceof ItemStack) return hasItemStack((ItemStack) item);
        else if (item instanceof Item) return hasItemStack(new ItemStack((Item) item));
        return false;
    }

    @Override
    public boolean hasAssignableFrom(Object item) {
        if (item instanceof ItemStack) return hasItemAssignableFrom(((ItemStack) item).getItem());
        else if (item instanceof Item) return hasItemAssignableFrom((Item) item);
        return false;
    }

    @Override
    public boolean hasAssignableBy(Object item) {
        return super.hasAssignableBy(item);
    }

    public boolean hasItemStack(ItemStack item) {
        if (item == null) return false;
        for (ItemStack it : this.items)
            if (item.getItem() == it.getItem() && item.getItemDamage() == it.getItemDamage())
                return true;
        return false;
    }


    public boolean hasItemAssignableFrom(Item item) {
        for (ItemStack obj : items)
            if (obj.getItem().getClass().isAssignableFrom(item.getClass())) return true;
        return false;
    }

    public boolean hasItemAssignableBy(Item item) {
        for (ItemStack obj : items)
            if (item.getClass().isAssignableFrom(obj.getItem().getClass())) return true;
        return false;
    }
}
