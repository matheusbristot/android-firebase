package matheusbristot.firebaseandroid.presentation.login

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import matheusbristot.firebaseandroid.presentation.base.lifecycle.FlexibleLiveData
import matheusbristot.firebaseandroid.presentation.base.view.BaseViewModel

class LoginViewModel(
        private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    val userLogged: LiveData<FirebaseUser> get() = userLoggedLiveData
    val error: LiveData<String> get() = errorLiveData
    val shouldProgress: LiveData<Boolean> get() = shouldProgressLiveData

    private val userLoggedLiveData: FlexibleLiveData<FirebaseUser> = FlexibleLiveData()
    private val errorLiveData: FlexibleLiveData<String> = FlexibleLiveData()
    private val shouldProgressLiveData: FlexibleLiveData<Boolean> = FlexibleLiveData.default(false)

    private var email: String = ""
    private var password: String = ""

    fun logIn() {
        email.let {
            password.let {
                if (email.isNotBlank() && password.isNotBlank()) {
                    shouldProgressLiveData.value = true // mostra o loading
                    firebaseAuth.signInWithEmailAndPassword(email, password) // inicializa o login com email e senha
                            .addOnCompleteListener {
                                //Deu certo encontrou o usuário no nosso banco
                                if (it.isSuccessful) userLoggedLiveData.value = it.result.user
                                shouldProgressLiveData.value = false // remove o loading
                            }.addOnFailureListener { exception ->
                                // Deu erro, e vai processar a mensagem
                                errorLiveData.value = LoginErrorType.getError(exception)
                                shouldProgressLiveData.value = false // remove o loading
                            }
                } else {
                    errorLiveData.value = "Os campos não devem ficar em branco"
                }
            }
        }
    }

    fun getTextInput(text: String?, isPassword: Boolean) {
        text?.let { if (isPassword) password = it else email = it }
    }

    //EXTRA - FirebaseAuth tem o gerenciamento de Cache da sessão
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun verifyLoggedSessionInCache() {
        if (firebaseAuth.currentUser != null) {
            Log.e("LoggedSessionInCache", "LOGADO: ${firebaseAuth.currentUser?.email}")
        } else Log.e("LoggedSessionInCache", "DESLOGADO")
    }
}