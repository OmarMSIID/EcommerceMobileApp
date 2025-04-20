package ca.qc.cgodin.ecommnerceapp.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ProduitPanier::class], version = 1)
abstract class ProduitPanierModel: RoomDatabase() {
    abstract val produitDao: ProduitPanierDao
}