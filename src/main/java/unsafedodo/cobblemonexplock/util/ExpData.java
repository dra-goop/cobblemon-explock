package unsafedodo.cobblemonexplock.util;

import net.minecraft.nbt.NbtCompound;

public class ExpData {

    public static boolean setExpState(IPokemonDataSaver pokemon){
        NbtCompound nbt = pokemon.getPersistentData();
        boolean state = nbt.getBoolean("explock");
        state = !state;

        nbt.putBoolean("explock", state);
        return state;
    }
}
