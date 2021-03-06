package c6h2cl2.YukariLib.Util

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class ItemUtil {
    companion object {
        fun CreateItem(name: String, textureName: String = name, modID: String, creativeTabs: CreativeTabs? = null, stackSize: Int = 64, /*hasSubType : Boolean = false,
                       maxMeta : Int = 0,*/isFull3D: Boolean = false, containerItem: Item? = null): Item {
            val item = Item()
            item.unlocalizedName = name
            item.registryName = ResourceLocation(modID, textureName)
            item.creativeTab = creativeTabs
            item.setMaxStackSize(stackSize)
            if (isFull3D) item.setFull3D()
            if (containerItem != null) item.containerItem = containerItem
            return item
        }
    }
}