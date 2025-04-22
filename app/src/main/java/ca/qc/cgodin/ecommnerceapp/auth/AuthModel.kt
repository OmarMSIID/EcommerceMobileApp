package ca.qc.cgodin.ecommnerceapp.auth

import androidx.lifecycle.ViewModel
import ca.qc.cgodin.ecommnerceapp.data.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthModel : ViewModel() {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    fun signUp(email:String,password:String,address:String,OnResultat:(Boolean,String?)->Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val userId=it.result?.user?.uid
                    val user = UserModel(
                        email,
                        address,
                        userId!!
                    )
                    firestore.collection("users")
                        .document(userId)
                        .set(user)
                        .addOnCompleteListener {dbTask->
                            if(dbTask.isSuccessful){
                                OnResultat(true,null)
                            }
                            else{
                                OnResultat(false,"something goes wrong!!")
                            }
                        }
                }
                else{
                    OnResultat(false,it.exception?.localizedMessage)
                }
            }
    }

    fun logIn(email:String,password:String,OnResultat: (Boolean, String?) -> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    OnResultat(true,null)
                }
                else{
                    OnResultat(false,it.exception?.localizedMessage)
                }
            }
    }
}