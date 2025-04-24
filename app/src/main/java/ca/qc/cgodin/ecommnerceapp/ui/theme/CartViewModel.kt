package ca.qc.cgodin.ecommnerceapp.ui.theme

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import ca.qc.cgodin.ecommnerceapp.data.remote.Product
import ca.qc.cgodin.ecommnerceapp.database.MIGRATION_1_2
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanier
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanierModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(application: Application) : AndroidViewModel(application) {

    // Database instance
    private val database = Room.databaseBuilder(
        application.applicationContext,
        ProduitPanierModel::class.java,
        "produit_panier_database"
    ).addMigrations(MIGRATION_1_2) // Ajout de la migration
        .build()

    private val produitDao = database.produitDao

    // MutableStateFlow to hold cart items
    private val _cartItems = MutableStateFlow<List<ProduitPanier>>(emptyList())
    val cartItems: StateFlow<List<ProduitPanier>> = _cartItems.asStateFlow()

    // MutableStateFlow for total price
    private val _totalPrice = MutableStateFlow(0f)
    val totalPrice: StateFlow<Float> = _totalPrice.asStateFlow()

    // Map to track selected items
    private val _selectedItems = mutableStateMapOf<Int, Boolean>()

    // State for "Select All" checkbox
    val areAllItemsSelected = mutableStateOf(false)

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val items = produitDao.getAllProduit()
                _cartItems.value = items

                // Initialize selection state for all items
                items.forEach { item ->
                    _selectedItems[item.id] = true
                }

                updateAreAllItemsSelected()
                calculateTotalPrice()
            }
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val produitPanier = ProduitPanier(
                    nom = product.title,
                    description = product.description,
                    prix = product.price.toFloat(),
                    quantity = quantity,
                    image = product.image // Ajout de l'URL de l'image
                )
                produitDao.addProduit(produitPanier)
                loadCartItems()
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                produitDao.deleteProduit(productId)
                _selectedItems.remove(productId)
                loadCartItems()
            }
        }
    }

    fun updateItemQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                produitDao.updateProduitQuantity(productId, newQuantity)
                loadCartItems()
            }
        }
    }

    fun selectItem(productId: Int, isSelected: Boolean) {
        _selectedItems[productId] = isSelected
        updateAreAllItemsSelected()
        calculateTotalPrice()
    }

    fun selectAllItems(isSelected: Boolean) {
        _cartItems.value.forEach { item ->
            _selectedItems[item.id] = isSelected
        }
        areAllItemsSelected.value = isSelected
        calculateTotalPrice()
    }

    fun isItemSelected(productId: Int): Boolean {
        return _selectedItems[productId] ?: false
    }

    private fun updateAreAllItemsSelected() {
        areAllItemsSelected.value = _cartItems.value.isNotEmpty() &&
                _cartItems.value.all { isItemSelected(it.id) }
    }

    private fun calculateTotalPrice() {
        var total = 0f
        _cartItems.value.forEach { item ->
            if (isItemSelected(item.id)) {
                total += item.prix * item.quantity
            }
        }
        _totalPrice.value = total
    }
}