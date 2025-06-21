package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.examplemod.ExampleMod.LOGGER;

public class KeyEventHandler {

    public KeyEventHandler() {
    }


    @SubscribeEvent
    public void KeyEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        //if (StickUtil.holdingStick(player) == null) return;

        //LOGGER.info("Input!" + event.getKey());

    }

    //@SubscribeEvent
    //public void onClickInput(InputEvent.InteractionKeyMappingTriggered  event){

    //}

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(event.getHand());
        if (!(stack.getItem() instanceof MagicWandItem)) {
            return;
        }
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);

        List<BlockPos> toRemoveBlocks = new ArrayList<BlockPos>();
        toRemoveBlocks.add(pos);
        SearchForSameBlock(toRemoveBlocks, state, pos, level);
        toRemoveBlocks.forEach(p->level.destroyBlock(p, true));
    }

    private void SearchForSameBlock(List<BlockPos> toRemoveBlocks, BlockState blockType, BlockPos previousBlockPos, Level level) {
        if (toRemoveBlocks.size() > 50) {
            return;
        }
        if (level.getBlockState(previousBlockPos.above()) == blockType && !toRemoveBlocks.contains(previousBlockPos.above())) {
            toRemoveBlocks.add(previousBlockPos.above());
            SearchForSameBlock(toRemoveBlocks, blockType, previousBlockPos.above(), level);
        }
        if (level.getBlockState(previousBlockPos.below()) == blockType && !toRemoveBlocks.contains(previousBlockPos.below())) {
            toRemoveBlocks.add(previousBlockPos.below());
            SearchForSameBlock(toRemoveBlocks, blockType, previousBlockPos.below(), level);
        }
        if (level.getBlockState(previousBlockPos.east()) == blockType && !toRemoveBlocks.contains(previousBlockPos.east())) {
            toRemoveBlocks.add(previousBlockPos.east());
            SearchForSameBlock(toRemoveBlocks, blockType, previousBlockPos.east(), level);
        }
        if (level.getBlockState(previousBlockPos.west()) == blockType && !toRemoveBlocks.contains(previousBlockPos.west())) {
            toRemoveBlocks.add(previousBlockPos.west());
            SearchForSameBlock(toRemoveBlocks, blockType, previousBlockPos.west(), level);
        }
        if (level.getBlockState(previousBlockPos.north()) == blockType && !toRemoveBlocks.contains(previousBlockPos.north())) {
            toRemoveBlocks.add(previousBlockPos.north());
            SearchForSameBlock(toRemoveBlocks, blockType, previousBlockPos.north(), level);
        }
        if (level.getBlockState(previousBlockPos.south()) == blockType && !toRemoveBlocks.contains(previousBlockPos.south())) {
            toRemoveBlocks.add(previousBlockPos.south());
            SearchForSameBlock(toRemoveBlocks, blockType, previousBlockPos.south(), level);
        }
    }

    private List<BlockPos> GetSideBlocks(BlockPos pos){
        return Arrays.asList(pos.above(),pos.below(), pos.east(), pos.west(), pos.north(), pos.south());
    }
}
