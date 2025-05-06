package ca.qc.cgodin.ecommnerceapp.data.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class ProductViewModel : ViewModel() {
    private val repository = ProductRepo()

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            repository.getProducts().fold(
                onSuccess = { products ->
                    _uiState.value = ProductUiState.Success(products)
                    Log.d("ProductViewModel", "Loaded ${products.size} products")
                },
                onFailure = { e ->
                    _uiState.value = ProductUiState.Error(e.message ?: "Erreur inconnue")
                    Log.e("ProductViewModel", "Error loading products", e)
                }
            )
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
        Log.d("ProductViewModel", "Category selected: ${_selectedCategory.value}")
    }

    fun setSearchQuery(query: String) {
        Log.d("ProductViewModel", "Search query changed to: $query")
        _searchQuery.value = query
        // After setting search query, log filtered products count for debugging
        val filteredCount = getFilteredProducts().size
        Log.d("ProductViewModel", "Filtered products count: $filteredCount")
    }

    fun getFilteredProducts(): List<Product> {
        val currentState = _uiState.value
        if (currentState !is ProductUiState.Success) {
            Log.d("ProductViewModel", "No products available to filter")
            return emptyList()
        }

        val selectedCat = _selectedCategory.value
        val query = _searchQuery.value.trim().lowercase()

        Log.d("ProductViewModel", "Filtering with category: $selectedCat, query: $query")

        val filteredByCategory = if (selectedCat == null) {
            currentState.products
        } else {
            currentState.products.filter { it.category == selectedCat }
        }

        val filteredBySearch = if (query.isEmpty()) {
            filteredByCategory
        } else {
            filteredByCategory.filter {
                it.title.lowercase().contains(query) ||
                        it.description.lowercase().contains(query) ||
                        it.brand.lowercase().contains(query) ||
                        it.category.lowercase().contains(query) ||
                        it.model.lowercase().contains(query)
            }
        }

        Log.d("ProductViewModel", "Original count: ${currentState.products.size}, " +
                "After category filter: ${filteredByCategory.size}, " +
                "After search filter: ${filteredBySearch.size}")

        return filteredBySearch
    }

    fun selectProductById(id: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is ProductUiState.Success) {
                // Products are already loaded, find the product
                val product = currentState.products.find { it.id == id }
                _selectedProduct.value = product
            } else {
                // Products are not loaded yet, load them first
                repository.getProducts().fold(
                    onSuccess = { products ->
                        // Update the UI state
                        _uiState.value = ProductUiState.Success(products)
                        // Now find and set the product
                        val product = products.find { it.id == id }
                        _selectedProduct.value = product
                    },
                    onFailure = { e ->
                        _uiState.value = ProductUiState.Error(e.message ?: "Erreur inconnue")
                        _selectedProduct.value = null
                    }
                )
            }
        }
    }
}

sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}