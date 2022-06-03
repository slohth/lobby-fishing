package dev.slohth.lobbyfishing.utils.item

import com.google.gson.*
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

/**
 * A utility class designed to serialize and deserialize ItemStacks to/from readable and easily modifiable Strings
 * Designed for config files for full control of items (as bukkit's built-in serializer is a bit garbo)
 * Uses json format to store the data, so it's useful if you're familiar with it
 *
 * Supports ALL ItemStack modification through the spigot api (not nms, so no nbt tags yet sorry)
 *
 * @author Brandon @slohth
 */
object ItemSerializer {

    private val parser = JsonParser()

    /**
     * Deserializes an ItemStack from a valid serialized String - makes use of Google's gson library
     * @param input The String to deserialize from
     * @return The deserialized ItemStack
     * @throws JsonSyntaxException If the serialization was formatted incorrectly
     */
    @Throws(JsonSyntaxException::class)
    fun deserialize(input: String): ItemStack {

        val json = parser.parse(input).asJsonObject
        val item = ItemBuilder(Material.STONE)

        for ((key, value) in json.entrySet()) {
            when (key) {
                "type" -> {
                    val material = Material.getMaterial(value.asString)
                    if (material != null) item.type(material)
                }
                "name" -> {
                    item.name(value.asString)
                }
                "amount" -> {
                    item.amount(value.asInt)
                }
                "lore" -> {
                    val lore: MutableList<String> = ArrayList()
                    for (element in value.asJsonArray) item.appendLore(element.asString)
                }
                "owner" -> {
                    if (item.type() == Material.PLAYER_HEAD) item.skull(value.asString)
                }
                "durability" -> {
                    item.durability(value.asInt)
                }
                "unbreakable" -> {
                    item.unbreakable(value.asBoolean)
                }
                "local-name" -> {
                    item.localisedName(value.asString)
                }
                "flags" -> {
                    for (element in value.asJsonArray) {
                        try {
                            item.flag(ItemFlag.valueOf(element.asString))
                        } catch (ignored: IllegalArgumentException) {}
                    }
                }
                "enchants" -> {
                    for (element in value.asJsonArray) {
                        val enchant = element.asJsonObject
                        val type = Enchantment.getByName(enchant["type"].asString)
                        if (type != null) item.enchant(type, enchant["level"].asInt)
                    }
                }
            }
        }

        return item.build()
    }

    /**
     * Serializes a String from an ItemStack using json
     * @param item The ItemStack to serialize
     * @return The serialized String
     */
    fun serialize(item: ItemStack): String {
        val json = JsonObject()
        val meta = item.itemMeta!!

        json.add("type", JsonPrimitive(item.type.toString()))

        if (meta.hasDisplayName()) json.add("name", JsonPrimitive(meta.displayName))

        if (item.amount != 1) json.add("amount", JsonPrimitive(item.amount))

        if (meta.hasLore()) {
            val array = JsonArray()
            for (line in meta.lore!!) array.add(line)
            json.add("lore", array)
        }

        if (item.type == Material.PLAYER_HEAD && (meta as SkullMeta).owningPlayer != null) {
            json.add("owner", JsonPrimitive(meta.owningPlayer!!.name))
        }

        if (item.durability.toInt() != 0) json.add("durability", JsonPrimitive(item.durability.toInt()))

        if (meta.isUnbreakable) json.add("unbreakable", JsonPrimitive(meta.isUnbreakable))

        if (meta.hasLocalizedName()) json.add("local-name", JsonPrimitive(meta.localizedName))

        if (!meta.itemFlags.isEmpty()) {
            val array = JsonArray()
            for (flag in meta.itemFlags) array.add(flag.toString())
            json.add("flags", array)
        }

        if (!meta.enchants.isEmpty()) {
            val array = JsonArray()
            for ((key, value) in meta.enchants) {
                val obj = JsonObject()
                obj.add("type", JsonPrimitive(key.name))
                obj.add("level", JsonPrimitive(value))
                array.add(obj)
            }
            json.add("enchants", array)
        }

        return json.toString()
    }
}