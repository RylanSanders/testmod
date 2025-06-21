package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
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
        int BLOCKS_PER_FOOD = 5;
        FoodData foodData = player.getFoodData();

        int maxBlocksToBreak = (int)((foodData.getFoodLevel()+foodData.getSaturationLevel()) * BLOCKS_PER_FOOD);
        SearchForSameBlock(toRemoveBlocks, state, pos, level,maxBlocksToBreak);
        toRemoveBlocks.forEach(p->level.destroyBlock(p, true));

        int removedFoodData = toRemoveBlocks.size()/BLOCKS_PER_FOOD ;
        if(removedFoodData<foodData.getSaturationLevel()){
            //Saturation covers the cost
            foodData.setSaturation(foodData.getSaturationLevel()-removedFoodData);
        }else{
            //Need to consume food as well
            foodData.setFoodLevel((int)(foodData.getFoodLevel()-(removedFoodData-foodData.getSaturationLevel())));
            foodData.setSaturation(0);

        }
    }
    private void SearchForSameBlock(List<BlockPos> toRemoveBlocks, BlockState blockType, BlockPos previousBlockPos, Level level, int maxBlocksToDestroy) {
        if (toRemoveBlocks.size() > maxBlocksToDestroy) {
            return;
        }
        for(BlockPos pos : GetSideBlocks(previousBlockPos)){
            if (level.getBlockState(pos).getBlock() == blockType.getBlock() && !toRemoveBlocks.contains(pos)) {
                toRemoveBlocks.add(pos);
                SearchForSameBlock(toRemoveBlocks, blockType, pos, level,maxBlocksToDestroy);
            }
        }
    }

    private List<BlockPos> GetSideBlocks(BlockPos pos){
        return Arrays.asList(pos.above(),pos.below(), pos.east(), pos.west(), pos.north(), pos.south());
    }
}
