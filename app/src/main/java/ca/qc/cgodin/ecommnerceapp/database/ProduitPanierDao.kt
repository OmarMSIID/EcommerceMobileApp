package ca.qc.cgodin.ecommnerceapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProduitPanierDao {

    @Query("SELECT * FROM produit_basket ;")
    suspend fun getAllProduit(): List<ProduitPanier>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduit(produitPanier: ProduitPanier)
    @Query("UPDATE produit_basket set quantity= :quantity WHERE id = :id ")
    fun updateProduitQuantity(id: Int,quantity: Int)
    @Query("DELETE FROM produit_basket WHERE id= :id ;")
    fun deleteProduit(id: Int)

    @Query("SELECT * FROM produit_basket WHERE id=:id")
    fun getProduit(id:Int): ProduitPanier
}