package com.weibo;

import com.google.flatbuffers.FlatBufferBuilder;
import com.weibo.model.*;

import java.nio.ByteBuffer;

/**
 * @author: zhangxin26
 * @create: 2019-06-02 12:10
 **/
public class MainEntrance {
    public static void main(String[] args) {
        FlatBufferBuilder builder = new FlatBufferBuilder(0);
        int weaponOneName = builder.createString("Sword");
        short weaponOneDamage = 3;
        int weaponTwoName = builder.createString("Axe");
        short weaponTwoDamage = 5;

        // Use the `createWeapon()` helper function to create the weapons, since we set every field.
        int[] weaps = new int[2];
        weaps[0] = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
        weaps[1] = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);

        // Serialize the FlatBuffer data.
        int name = builder.createString("Orc");
        byte[] treasure = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int inv = Monster.createInventoryVector(builder, treasure);//?
        int weapons = Monster.createWeaponsVector(builder, weaps);
        int pos = Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f);

        Monster.startMonster(builder);
        Monster.addPos(builder, pos);
        Monster.addName(builder, name);
        Monster.addColor(builder, Color.Red);
        Monster.addHp(builder, (short)300);
        Monster.addInventory(builder, inv);
        Monster.addWeapons(builder, weapons);
        Monster.addEquippedType(builder, Equipment.Weapon);
        Monster.addEquipped(builder, weaps[1]);
        int orc = Monster.endMonster(builder);

        builder.finish(orc); // You could also call `Monster.finishMonsterBuffer(builder, orc);`.

        // read
        ByteBuffer buf = builder.dataBuffer();

        // Get access to the root:
        Monster monster = Monster.getRootAsMonster(buf);
        System.out.println("The FlatBuffer was successfully created and verified!");
    }
}
