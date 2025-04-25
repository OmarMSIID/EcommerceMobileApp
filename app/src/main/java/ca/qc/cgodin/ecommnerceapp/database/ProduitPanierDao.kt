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
    suspend fun getAllProduit(): List<ProduitPanier>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduit(produitPanier: ProduitPanier)

    @Query("UPDATE produit_basket SET quantity = :newQuantity WHERE id = :productId")
    suspend fun updateProduitQuantity(productId: Int, newQuantity: Int)

    @Query("DELETE FROM produit_basket WHERE id = :productId")
    suspend fun deleteProduit(productId: Int)
}