package com.anmol.rosei

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.anmol.rosei.Helpers.AuthUser

import com.canopydevelopers.canopyauth.CanopyAuthCallback
import com.canopydevelopers.canopyauth.CanopyLogin
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.messaging.FirebaseMessaging



class LoginActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var progressBar: ProgressBar? = null
    private var btnLogin: Button? = null
    private val btnReset: Button? = null
    var sid:String?=null
    var password:String?=null
    var fname:String?=null

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
        val authUser = AuthUser(this)
        btnLogin!!.setOnClickListener {
            progressBar!!.visibility = View.VISIBLE
            sid = inputEmail!!.text.toString().trim()
            password = inputPassword!!.text.toString().trim()
            fname = firstname.text.toString().trim()
            when {
                TextUtils.isEmpty(sid) -> Toast.makeText(applicationContext, "Enter Student ID!", Toast.LENGTH_SHORT).show()
                TextUtils.isEmpty(password) -> Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                TextUtils.isEmpty(fname) -> Toast.makeText(applicationContext, "Enter name!", Toast.LENGTH_SHORT).show()
                else -> {
                    val canopyAuthCallback = object : CanopyAuthCallback {
                        override fun onLoginSuccess(loginresponse: Boolean?) {
                            if (loginresponse!!){
                                if(authUser.writegender(gender) && authUser.writeuser(sid!!) && authUser.writeusername(fname!!)){
                                    val yr = sid!!.substring(2,4)
                                    System.out.println("topic year:$yr")
                                    FirebaseMessaging.getInstance().subscribeToTopic(yr)
                                    val intent = Intent(applicationContext, SplashActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                    finish()
                                    overridePendingTransition(R.anim.still,R.anim.slide_in_up)
                                }
                            }
                            else{
                                progressBar!!.visibility = View.GONE
                                Toast.makeText(applicationContext,"Incorrect credentials",Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onLoginFailure(loginerror: String?) {
                            progressBar!!.visibility = View.GONE
                            Toast.makeText(applicationContext,loginerror,Toast.LENGTH_SHORT).show()
                        }
                    }
                    val canopyLogin = CanopyLogin(canopyAuthCallback, this)
                    canopyLogin.generate_token(sid,password,"http://14.139.198.171:8080/token/generate-token")
                }
            }



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

