package unsafedodo.cobblemonexplock.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import unsafedodo.cobblemonexplock.util.IPokemonDataSaver;

@Mixin(Pokemon.class)
public abstract class SaveNbtMixin implements IPokemonDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData(){
        if(this.persistentData == null){
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "loadFromNBT", at = @At("TAIL"))
    protected void loadFromNbt(DynamicRegistryManager drm, NbtCompound nbt, CallbackInfoReturnable<Pokemon> cir){
        if(nbt.contains("explock")){
            persistentData = nbt.getCompound("explock");
        }
    }


    @Inject(method = "saveToNBT", at = @At("TAIL"))
    protected void saveToNbt(DynamicRegistryManager drm, NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir){
        if(persistentData != null){
            nbt.put("explock", persistentData);
        }
    }
}
