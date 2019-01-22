package com.anmol.rosei

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest

import com.anmol.rosei.Model.Students
import com.canopydevelopers.canopyauth.CanopyAuthCallback
import com.canopydevelopers.canopyauth.CanopyLogin
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*

import org.json.JSONException
import org.json.JSONObject

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var progressBar: ProgressBar? = null
    private var btnLogin: Button? = null
    private val btnReset: Button? = null
    private var email: String? = null
    var sid:String?=null
    var password:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Login"
        setContentView(R.layout.activity_login)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        inputEmail = findViewById<View>(R.id.email) as EditText
        inputPassword = findViewById<View>(R.id.password) as EditText
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        btnLogin = findViewById<View>(R.id.btn_login) as Button
        var gender = "male"
        male.isChecked = true
        male.setOnClickListener {
            male.isChecked = true
            female.isChecked = false
            gender = "male"
        }
        female.setOnClickListener {
            female.isChecked = true
            male.isChecked = false
            gender = "female"
        }
        val sharedPreferences:SharedPreferences = getSharedPreferences("com.canopydevelopers.canopyauth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        btnLogin!!.setOnClickListener {
            sid = inputEmail!!.text.toString().trim()
            password = inputPassword!!.text.toString().trim()
            when {
                TextUtils.isEmpty(email) -> Toast.makeText(applicationContext, "Enter Student ID!", Toast.LENGTH_SHORT).show()
                TextUtils.isEmpty(password) -> Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                else -> {
                    val canopyAuthCallback = object : CanopyAuthCallback {
                        override fun onLoginSuccess(loginresponse: Boolean?) {
                            if (loginresponse!!){
                                editor.putString("gender",gender)
                                if(editor.commit()){
                                    val intent = Intent(applicationContext, SplashActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                    finish()
                                    overridePendingTransition(R.anim.still,R.anim.slide_in_up)
                                }

                            }
                        }
                        override fun onLoginFailure(loginerror: String?) {
                            Toast.makeText(applicationContext,loginerror,Toast.LENGTH_SHORT).show()
                        }
                    }
                    val canopyLogin = CanopyLogin(canopyAuthCallback, this)
                    canopyLogin.generate_token(sid,password,"http://14.139.198.171:8080/token/generate-token")
                }
            }


            progressBar!!.visibility = View.VISIBLE
        }
    }


    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
            overridePendingTransition(R.anim.still, R.anim.slide_out_down)
        } else {
            Toast.makeText(baseContext, "Press once again to exit!", Toast.LENGTH_SHORT).show()
            back_pressed = System.currentTimeMillis()
        }
    }

    companion object {
        private val TAG = "Login"
        private var back_pressed: Long = 0
    }


}

