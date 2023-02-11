package xyz.bluspring.kilt.loader

import com.llamalad7.mixinextras.MixinExtrasBootstrap
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraftforge.fml.ModLoadingPhase
import xyz.bluspring.kilt.Kilt

class KiltEarlyInitializer : PreLaunchEntrypoint {
    override fun onPreLaunch() {
        MixinExtrasBootstrap.init()
        Kilt.loader.preloadMods()

        Kilt.loader.loadMods()
    }
}