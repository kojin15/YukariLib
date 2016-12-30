package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

/**
 * @author C6H2Cl2
 */
data class BlockPos(var x: Int, var y: Int, var z: Int) {

    val up: BlockPos
        get() = BlockPos(x, y + 1, z)

    val down: BlockPos
        get() = BlockPos(x, y - 1, z)

    val north: BlockPos
        get() = BlockPos(x, y, z - 1)

    val south: BlockPos
        get() = BlockPos(x, y, z + 1)

    val east: BlockPos
        get() = BlockPos(x + 1, y, z)

    val west: BlockPos
        get() = BlockPos(x - 1, y, z)

    fun getBlockFromPos(world: World): Block {
        return world.getBlock(x, y, z)
    }

    fun getTileEntityFromPos(world: IBlockAccess): TileEntity? {
        return world.getTileEntity(x, y, z)
    }

    fun getDistance(posFrom: BlockPos, posTo: BlockPos): Double {
        return Math.sqrt(Math.pow((posFrom.x - posTo.x).toDouble(), 2.0) + Math.pow((posFrom.y - posTo.y).toDouble(), 2.0) + Math.pow((posFrom.z - posTo.z).toDouble(), 2.0))
    }

    fun getDistance(pos: BlockPos): Double = getDistance(this, pos)

    fun getBlockDirection(tilePos: BlockPos): ForgeDirection {
        if (up == tilePos) {
            return ForgeDirection.UP
        } else if (down == tilePos) {
            return ForgeDirection.DOWN
        } else if (north == tilePos) {
            return ForgeDirection.NORTH
        } else if (south == tilePos) {
            return ForgeDirection.SOUTH
        } else if (east == tilePos) {
            return ForgeDirection.EAST
        } else if (west == tilePos) {
            return ForgeDirection.WEST
        } else {
            return ForgeDirection.UNKNOWN
        }
    }

    @JvmOverloads fun writeToNBT(tagCompound: NBTTagCompound, tagName: String = "blockPos") {
        val tag = NBTTagCompound()
        tag.setInteger("x", x)
        tag.setInteger("y", y)
        tag.setInteger("z", z)
        tagCompound.setTag(tagName, tag)
    }

    @JvmOverloads fun readFromNBT(tagCompound: NBTTagCompound, tagName: String = "blockPos") {
        val tag = tagCompound.getCompoundTag(tagName)
        x = tag.getInteger("x")
        y = tag.getInteger("y")
        z = tag.getInteger("z")
    }
}