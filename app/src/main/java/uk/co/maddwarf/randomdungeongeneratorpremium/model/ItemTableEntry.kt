package uk.co.maddwarf.randomdungeongeneratorpremium.model

data class ItemTableEntry (
    val numberOfMundaneItems: String = "0d0",
    val mundaneItemType:String = "ART",
    val mundaneValue:Int = 0,
    val cr:Int = 0,
    val numberOfMagicItemsFromTableOne:String = "0d0",
    val numberOfMagicItemsFromTableTwo:String = "0d0",
    val magicItemTableOne: String = "A",
    val magicItemTableTwo: String = "B",
    val weight: Int = 1
)