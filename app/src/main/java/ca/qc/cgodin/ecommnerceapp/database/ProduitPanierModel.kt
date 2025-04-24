package ca.qc.cgodin.ecommnerceapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Migration pour ajouter le champ image
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE produit_basket ADD COLUMN image TEXT NOT NULL DEFAULT ''")
    }
}

@Database(entities = [ProduitPanier::class], version = 2) // Mise à jour de la version
abstract class ProduitPanierModel: RoomDatabase() {
    abstract val produitDao: ProduitPanierDao
}