package com.example.bondedbowbuffs;

import com.iamkaf.bonded.component.ItemLevelContainer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Bondedbowbuffs implements ModInitializer {

    // Tuning: each Bonded level adds this fraction on top of the normal value.
    private static final double SPEED_PER_LEVEL = 0.02;   // arrow speed (range)
    private static final double DAMAGE_PER_LEVEL = 0.03;  // arrow base damage
    private static final String BUFFED_TAG = "bonded_bow_buff";

    @Override
    public void onInitialize() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof AbstractArrow arrow)) return;
            if (arrow.getTags().contains(BUFFED_TAG)) return;

            ItemStack weapon = arrow.getWeaponItem();
            if (weapon == null || !weapon.is(Items.BOW)) return;

            ItemLevelContainer container =
                    weapon.get(com.iamkaf.bonded.registry.DataComponents.ITEM_LEVEL_CONTAINER.get());
            if (container == null) return;

            int level = container.getLevel();
            if (level <= 0) return;

            arrow.addTag(BUFFED_TAG);

			// Apply the speed bonus, Uncomment the following line if you want to apply speed bonus as well
            // arrow.setDeltaMovement(arrow.getDeltaMovement().scale(1.0 + SPEED_PER_LEVEL * level));

			// Apply the damage bonus
            arrow.setBaseDamage(2.0 * (1.0 + DAMAGE_PER_LEVEL * level));
        });
    }
}