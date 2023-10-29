package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Art
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Cash
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Gem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.ItemTableEntry
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Loot
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Magic
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.DescriptionsRepository.getCantrip
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.DescriptionsRepository.getPotionDescription
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.DescriptionsRepository.getSpellByLevel
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.LootRepository.getArtList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.LootRepository.getCashTableList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.LootRepository.getGemList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.LootRepository.getItemTableList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.MagicItemRepository.getMagicItemsList

class GetLootUseCase {

    fun buildLoot(type: String, level: Int, context: Context): Loot {
        var loot = Loot(name = "$type Loot")

        var lootLevel = when (level) {
            in 1..4 -> "4"
            in 5..10 -> "10"
            in 11..16 -> "16"
            in 17..20 -> "20"
            else -> "0"
        }

        var cashTableList = listOf<Cash>()
        when (type) {
            "Individual" -> {
                cashTableList =
                    getCashTableList(context = context, table = "cash", lootLevel = lootLevel)
                lootLevel = "0"
            }

            "Hoard" -> {
                cashTableList =
                    getCashTableList(context = context, table = "cashhoard", lootLevel = lootLevel)
            }

            else -> {
                lootLevel = "0"
                // loot = Loot(name = "UNKNOWN LOOT")
            }
        }//end when

        val itemTable = getItemTableList(context = context, lootLevel = lootLevel)
        val itemTableEntry = chooseFromItemTable(itemTable = itemTable)
        loot = rollOnLootTable(itemTableEntry = itemTableEntry, context = context)

        val cashTable: Cash = chooseFromCashTablesList(cashTableList)
        val cash = buildCash(cashTable)
        loot = loot.copy(coins = cash)

        var magicItemsList = mutableListOf<Magic>()
        //magic table one
        val magicTableOne: List<Magic> =
            getMagicItemsList(context = context, table = itemTableEntry.magicItemTableOne)
        val numberOfItemsFromTableOne = Dice().roll(itemTableEntry.numberOfMagicItemsFromTableOne)
        for (i in 1..numberOfItemsFromTableOne) {
            magicItemsList.add(chooseFromMagicItemsList(magicList = magicTableOne, context = context))
        }

        //magic table two
        val magicTableTwo =
            getMagicItemsList(context = context, table = itemTableEntry.magicItemTableOne)
        val numberOfItemsFromTableTwo = Dice().roll(itemTableEntry.numberOfMagicItemsFromTableTwo)
        for (i in 1..numberOfItemsFromTableTwo) {
            magicItemsList.add(chooseFromMagicItemsList(magicList = magicTableTwo, context = context))
        }
        loot = loot.copy(magicItemsList = magicItemsList)


        return loot
    }//end Build Loot

    private fun chooseFromMagicItemsList(magicList: List<Magic>, context: Context): Magic {
        var totalWeight = 0
        for (aMagic in magicList) {
            totalWeight += aMagic.weight
        }

        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aMagic in magicList) {
            val thisWeight: Int = aMagic.weight
            testWeight += thisWeight
            if (roll <= testWeight) {
                aMagic.description = makeMagicDescription(item = aMagic, context = context)
                return aMagic
            }
        }
        return Magic()
    }

    private fun makeMagicDescription(item: Magic, context: Context): String {

        //todo write all these repos
        val description = when (item.description) {
            "POTION" -> getPotionDescription(context = context)
            "CANTRIP" -> getCantrip(context = context)
            "levelone" -> getSpellByLevel(context = context, level = "1")
            "leveltwo" -> getSpellByLevel(context = context, level = "2")
            "levelthree" -> getSpellByLevel(context = context, level = "3")
            "levelfour" ->getSpellByLevel(context = context, level = "4")
            "levelfive" -> getSpellByLevel(context = context, level = "5")
            "levelsix" -> getSpellByLevel(context = context, level = "6")
            "levelseven" -> getSpellByLevel(context = context, level = "7")
            "leveleight" ->getSpellByLevel(context = context, level = "8")
            "levelnine" -> getSpellByLevel(context = context, level = "9")
            "ammo" -> "Arrows and such"
            "armour" -> "For covering your vital bits"
            "weapon" -> "Pointy Stick"
            "Sword" -> "Sharp!"
            "figurine" -> "miniature"

            else -> {item.description}
        }
        
        return description
    }

    private fun chooseFromCashTablesList(cashTableList: List<Cash>): Cash {

        var totalWeight = 0
        for (aCash in cashTableList) {
            totalWeight += aCash.weight
        }

        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aCash in cashTableList) {
            val thisWeight: Int = aCash.weight
            testWeight += thisWeight
            if (roll <= testWeight) {
                return aCash
            }
        }
        return Cash()
    }//end choose from cash tables list

    private fun buildCash(cashTable: Cash): Cash {
        val pp = cashTable.pp * Dice().roll("1d6")
        val gp = cashTable.gp * (1..6).random()
        val ep = cashTable.ep * (1..6).random()
        val sp = cashTable.sp * (1..6).random()
        val cp = cashTable.cp * (1..6).random()
        return Cash(pp = pp, gp = gp, ep = ep, sp = sp, cp = cp)
    }//end build cash

    private fun chooseFromItemTable(itemTable: List<ItemTableEntry>): ItemTableEntry {

        var totalWeight = 0
        for (anEntry in itemTable) {
            totalWeight += anEntry.weight
        }

        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (anEntry in itemTable) {
            val thisWeight: Int = anEntry.weight
            testWeight += thisWeight
            if (roll <= testWeight) {
                return anEntry
            }
        }
        return ItemTableEntry()
    }

    private fun rollOnLootTable(itemTableEntry: ItemTableEntry, context: Context): Loot {

        //art/gem
        val artList = mutableListOf<Art>()
        val gemList = mutableListOf<Gem>()
        val numberOfMundaneItems = Dice().roll(itemTableEntry.numberOfMundaneItems)
        for (i in 1..numberOfMundaneItems) {
            when (itemTableEntry.mundaneItemType) {
                "GEM" -> gemList.add(
                    makeGem(
                        gemValue = itemTableEntry.mundaneValue,
                        context = context
                    )
                )

                "ART" -> artList.add(
                    makeArt(
                        artValue = itemTableEntry.mundaneValue,
                        context = context
                    )
                )
            }
        }

        val newLoot = Loot(name = "A Loot", artList = artList, gemList = gemList)
        return newLoot
    }//end roll on loot table

    private fun makeGem(context: Context, gemValue: Int): Gem {
        val gemList = getGemList(context = context, gemValue = gemValue)
        val newGem = gemList.random()
        return newGem
    }//end Make Gem

    private fun makeArt(context: Context, artValue: Int): Art {
        val artList = getArtList(context = context, artValue = artValue)
        val newArt = artList.random()
        return newArt
    }//end Make Art

}//end Gey Loot Use Case