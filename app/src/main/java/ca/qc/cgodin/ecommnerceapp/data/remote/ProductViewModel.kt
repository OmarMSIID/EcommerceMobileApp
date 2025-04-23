package ca.qc.cgodin.ecommnerceapp.data.remote

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class ProductViewModel : ViewModel() {
    private val repository = ProductRepo()

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            repository.getProducts().fold(
                onSuccess = { products ->
                    _uiState.value = ProductUiState.Success(products)
                },
                onFailure = { e ->
                    _uiState.value = ProductUiState.Error(e.message ?: "Erreur inconnue")
                }
            )
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
    }

    fun getFilteredProducts(): List<Product> {
        val currentState = _uiState.value
        if (currentState !is ProductUiState.Success) return emptyList()

        val selectedCat = _selectedCategory.value
        return if (selectedCat == null) {
            currentState.products
        } else {
            currentState.products.filter { it.category == selectedCat }
        }
    }
}
sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}