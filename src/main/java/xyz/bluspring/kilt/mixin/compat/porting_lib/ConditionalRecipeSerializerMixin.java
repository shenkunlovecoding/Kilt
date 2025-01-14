package xyz.bluspring.kilt.mixin.compat.porting_lib;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.fabricators_of_create.porting_lib.data.ConditionalRecipe;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(value = ConditionalRecipe.Serializer.class, remap = false)
public class ConditionalRecipeSerializerMixin {
    @Unique
    private static final AtomicBoolean kilt$useForgeConditions = new AtomicBoolean(false);

    @WrapOperation(at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/util/GsonHelper;getAsJsonArray(Lcom/google/gson/JsonObject;Ljava/lang/String;)Lcom/google/gson/JsonArray;"), method = "fromJson", remap = true)
    public JsonArray kilt$processForgeAndFabricConditions(JsonObject jsonObject, String string, Operation<JsonArray> original) {
        // Process Forge conditions instead
        if (!jsonObject.has(string)) {
            kilt$useForgeConditions.set(true);
            return GsonHelper.getAsJsonArray(jsonObject, "conditions");
        }

        return original.call(jsonObject, string);
    }

    @Inject(at = @At("HEAD"), method = "processConditions", cancellable = true, remap = false)
    private static void kilt$processForgeConditions(JsonArray conditions, CallbackInfoReturnable<Boolean> cir) {
        if (kilt$useForgeConditions.get()) {
            kilt$useForgeConditions.set(false);
            cir.setReturnValue(CraftingHelper.processConditions(conditions, ICondition.IContext.EMPTY));
        }
    }
}
