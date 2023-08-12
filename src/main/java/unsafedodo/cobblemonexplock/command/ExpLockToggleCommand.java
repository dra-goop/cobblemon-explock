package unsafedodo.cobblemonexplock.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.NoPokemonStoreException;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import unsafedodo.cobblemonexplock.util.ExpData;
import unsafedodo.cobblemonexplock.util.IPokemonDataSaver;

public class ExpLockToggleCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("explock")
                .then(CommandManager.argument("slotNumber", IntegerArgumentType.integer(1,6))
                        .requires(Permissions.require("explock.toggle", 0))
                        .executes(ExpLockToggleCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try{
            PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(context.getSource().getPlayer().getUuid());
            int slot = (IntegerArgumentType.getInteger(context, "slotNumber")-1);
            Pokemon pokemon = partyStore.get(slot);
            if(pokemon != null){
                boolean state = ExpData.setExpState((IPokemonDataSaver) pokemon);
                context.getSource().sendFeedback(Text.literal("Exp gain state for "+pokemon.getDisplayName().getString()+" changed to "+state).formatted(Formatting.GREEN), false);
            } else {
                context.getSource().sendFeedback(Text.literal("Invalid slot").formatted(Formatting.RED), false);
                return -1;
            }
        } catch (NoPokemonStoreException e){
            e.printStackTrace();
        }

        return 0;
    }
}
