package matheusbristot.firebaseandroid.presentation.authentication.register

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegisterErrorType : Exception() {

    companion object {

        fun getErrorMessage(exception: Exception): String {
            return when (exception) {
                is FirebaseAuthWeakPasswordException -> "Senha fraca, tente uma com mais diferenciação de caracteres"
                is FirebaseAuthInvalidCredentialsException -> "Credenciais inválidas"
                is FirebaseAuthUserCollisionException -> "Oops. Tente com outro email seu cadastro."
                else -> "Oops. Ação indisponível no momento. Por favor tente mais tarde."
            }
        }
    }
}