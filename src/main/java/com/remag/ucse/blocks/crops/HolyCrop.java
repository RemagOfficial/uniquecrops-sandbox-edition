package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class HolyCrop extends BaseCropsBlock {

    public HolyCrop() {

        super(() -> Items.APPLE, UCItems.BLESSED_SEED, Properties.copy(Blocks.WHEAT).lightLevel(l -> l.getValue(AGE) == 7 ? 15 : 0));
        setBonemealable(false);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (entity instanceof LivingEntity) {
            boolean blessed = isMaxAge(state);
            if (!blessed) {
                if (!(entity instanceof Villager)) return;

                VillagerProfession prof = ((Villager)entity).getVillagerData().getProfession();
                if (prof == VillagerProfession.CLERIC) {
                    world.setBlock(pos, setValueAge(getMaxAge()), 3);
                }
            }
            if (((LivingEntity)entity).getMobType() == MobType.UNDEAD) {
                entity.setSecondsOnFire(2);
                entity.hurt(DamageSource.MAGIC, blessed ? 3.0F : 1.5F);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {

        // NO-OP
    }
}
