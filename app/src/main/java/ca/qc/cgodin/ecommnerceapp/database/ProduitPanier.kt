package ca.qc.cgodin.ecommnerceapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "produit_basket")
data class ProduitPanier(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    @ColumnInfo(name = "nom")
    val nom : String,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "prix")
    val prix : Float,
    @ColumnInfo(name = "quantity")
    val quantity : Int
)
