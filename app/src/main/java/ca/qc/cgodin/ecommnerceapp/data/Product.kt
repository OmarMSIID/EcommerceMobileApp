
package ca.qc.cgodin.ecommnerceapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val imageRes: Int
) : Parcelable