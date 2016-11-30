package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class BlockUtil {
    companion object{
        fun CreateBlock(name: String, textureName: String = name, modID: String, material:Material, hardness:Float=1.5f, resistance:Float=10f):Block{
            val block = Block(material)
            block.unlocalizedName = name
            block.registryName = ResourceLocation(modID,textureName)
            block.setHardness(hardness)
            block.setResistance(resistance)
            return block
        }
    }
}