package br.iesb.songs.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.MainActivity
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.fragments.UserNameFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.action_bar))

        id_button_entrar.setOnClickListener { login() }
        id_recuperar_senha.setOnClickListener { forgotPassword() }
        id_volttar_menu_inicial.setOnClickListener { backMenu() }

        backLogin.setOnTouchListener { _, _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun login() {
        val email = id_email.text.toString()
        val password = id_senha_cadastrar.text.toString()

        viewModel.login(email, password) { result ->
            Toast.makeText(this, result[1], Toast.LENGTH_SHORT).show()
            if (result[0] == "OK") {
                viewModel.verifyName { verified ->
                    if (verified == "EMPTY") {
                        val manager = supportFragmentManager

                        manager.beginTransaction()
                            .add(
                                R.id.backLogin,
                                UserNameFragment("doesntExists"),
                                "userName"
                            )
                            .commit()
                    } else {
                        val intentLogin = Intent(this, PrincipalActivity::class.java)
                        startActivity(intentLogin)
                    }
                }
            }
        }
    }

    private fun forgotPassword() {
        val intentForgotPassword = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intentForgotPassword)
    }

    private fun backMenu() {
        val intentBackMenu = Intent(this, MainActivity::class.java)
        startActivity(intentBackMenu)
    }
}
