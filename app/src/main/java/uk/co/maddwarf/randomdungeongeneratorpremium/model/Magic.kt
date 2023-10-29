package uk.co.maddwarf.randomdungeongeneratorpremium.model

data class Magic(
    var description: String = "Default Magic Item",
    val type: String = "",
    val id: Int = 0,
    val name: String = "Deafault Magic Name",
    val table: String = "A",
    val weight: Int = 1
)