package com.remag.ucse.items.curios;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemCurioUC;
import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.InterModComms;

import java.util.List;

public class EmblemScarab extends ItemCurioUC {

    private static final List<String> BLACKLIST = Lists.newArrayList();

    public EmblemScarab() {

        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "minecraft.effect.awkward");
        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "effect.ucse.zombification" );
        MinecraftForge.EVENT_BUS.addListener(this::onApplyPotion);
    }

    private void onApplyPotion(PotionEvent.PotionApplicableEvent event) {

        if (event.getEntityLiving() instanceof Player player) {
            if (hasCurio(player)) {
                if (!BLACKLIST.contains(event.getPotionEffect().getDescriptionId())) {
                    event.setResult(Event.Result.DENY);
                    return;
                }
            }
            if (event.getPotionEffect().getEffect() == MobEffects.HUNGER) {
                if (hasCurio(player, UCItems.EMBLEM_IRONSTOMACH.get()))
                    event.setResult(Event.Result.DENY);
            }
        }
    }

    public static void blacklistPotionEffect(String effect) {

        BLACKLIST.add(effect);
    }
}
