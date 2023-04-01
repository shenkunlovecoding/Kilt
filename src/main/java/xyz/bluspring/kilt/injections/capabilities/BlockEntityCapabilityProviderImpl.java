package xyz.bluspring.kilt.injections.capabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProviderImpl;
import xyz.bluspring.kilt.workarounds.CapabilityProviderWorkaround;

public interface BlockEntityCapabilityProviderImpl extends ICapabilityProviderImpl<BlockEntity> {
    default CapabilityProviderWorkaround<BlockEntity> getWorkaround() {
        throw new IllegalStateException("should be overridden by mixin");
    }
}
