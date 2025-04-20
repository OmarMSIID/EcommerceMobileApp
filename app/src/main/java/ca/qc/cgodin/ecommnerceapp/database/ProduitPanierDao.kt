package ca.qc.cgodin.ecommnerceapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProduitPanierDao {

    @Query("SELECT * FROM produit_basket")
    suspend fun getAllProduit();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduit(produitPanierModel: ProduitPanierModel)
    @Update
    suspend fun updateProduit(produitPanierModel: ProduitPanierModel)
    @Delete
    suspend fun deleteProduit(produitPanierModel: ProduitPanierModel)
}