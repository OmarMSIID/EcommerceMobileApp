package ca.qc.cgodin.ecommnerceapp.AppUtil

import android.content.Context
import android.widget.Toast

object AppUtil {
    fun showToaster(context:Context,message:String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
}