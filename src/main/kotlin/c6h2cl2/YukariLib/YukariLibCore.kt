@file:Suppress("UNUSED")

package c6h2cl2.YukariLib

import c6h2cl2.YukariLib.Common.CommonProxy
import c6h2cl2.YukariLib.Event.YukariLibEventHandler
import c6h2cl2.YukariLib.Util.RegisterHandler
import com.mojang.util.UUIDTypeAdapter
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.ModMetadata
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.client.Minecraft
import net.minecraft.launchwrapper.Launch
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.*

/**
 * @author C6H2Cl2
 */
@Mod(modid = YukariLibCore.MOD_ID, version = YukariLibCore.Version,name = YukariLibCore.MOD_ID ,useMetadata = true)
class YukariLibCore {
    companion object {
        const val MOD_ID = "YukariLib"
        const val DOMAIN = "yukarilib"
        const val Version = "1.2.3"
        @JvmStatic
        @Mod.Metadata
        var metadata: ModMetadata? = null
        @JvmStatic
        @SidedProxy(clientSide = "c6h2cl2.YukariLib.Client.ClientProxy", serverSide = "c6h2cl2.YukariLib.Common.CommonProxy")
        var proxy: CommonProxy? = null
        var enableDeathLog = true
            private set
        var maxThreadPerObject = 8
            private set
        var allowOffline = false
            private set

        fun getExecutorService() = Executors.newFixedThreadPool(YukariLibCore.maxThreadPerObject)!!
    }

    @Mod.EventHandler
    fun preinit(event: FMLPreInitializationEvent) {
        check(event)
        loadMeta()
        getConfig()
    }

    @EventHandler
    fun init(event: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(YukariLibEventHandler())
    }

    private fun check(event: FMLPreInitializationEvent) {
        if (event.side.isClient && Launch.blackboard["fml.deobfuscatedEnvironment"]?.equals(true) == false) {
            var purchased = true
            val session = Minecraft.getMinecraft().session
            val userName = session.username
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$userName")
            try {
                BufferedReader(InputStreamReader(url.openStream())).use {
                    purchased = it.readLine() != null
                }
            } catch (e: IOException) {
                return
            }

            try {
                UUIDTypeAdapter.fromString(session.playerID)
            } catch (e: Throwable) {
                purchased = false
            }
            purchased = (purchased && !Minecraft.getMinecraft().isDemo)
            if (!purchased) {
                throw PlayerNotOfficialPurchasedException()
            }
        }
    }

    private fun getConfig() {
        val proxy = proxy as CommonProxy
        val cfg = Configuration(File(proxy.getDir(), "config/YukariLib.cfg"))
        cfg.load()
        enableDeathLog = cfg.getBoolean("Enable Death Log", "Common", true, "Set false to disable player position log on death.")
        allowOffline = cfg.getBoolean("Allow offline mode", "Common", true, "Set false to prohibit playing offline")
        //maxThreadPerObject = cfg.getInt("NBT-Thread", "Common", 8, 1, 255, "The number of thread to handle NBT-Serializing")
        cfg.save()
    }

    private fun loadMeta() {
        val meta = metadata as ModMetadata
        meta.modId = DOMAIN
        meta.name = MOD_ID
        meta.version = Version
        meta.authorList.add("C6H2Cl2")
        meta.credits = "This mod includes Kotlin STDLib and Jetbrains Annotations.\nThey are licensed under the Apache 2 OSS License."
        meta.description = "This mod is an wrapper for mods made from KotlinLanguage and provides some utils."
        meta.autogenerated = false
    }
}