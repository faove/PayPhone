 package com.pay.payphone

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*


 enum class ProviderType{
     BASIC
 }

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val emailtxt = intent.getStringExtra("email")

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.emailView).apply {
            text = emailtxt
        }
        //var email: String = intent.getStringExtra("email")
        //var provider: String = intent.getStringExtra("provider")
        //Recargar phone
        var bundle :Bundle ?= intent.extras
        //val email:String = intent.getStringExtra("email")
        val email = bundle?.getString("email")
        //val email2:String ?= bundle?.getString("email")
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        val provider:String ?= bundle?.getString("provider")
        //val email:String ?= bundle?.getString("email")
        //val provider:String ?= bundle?.getString("provider")
        //var email: String = intent.getStringExtra("email")
        //var provider: String = intent.getStringExtra("provider")
        //showAlert(email ?: "No tiene email")
        payphone(email ?: "", provider ?: "")

    }

    private fun payphone(email: String, provider: String){
        title = "Pay Phone"
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("payphone-b299f")

        myRef.setValue("Hello, World!")

        emailView.text = email
        providerView.text = provider

        btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value =
                    dataSnapshot.getValue(String::class.java)
                Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

    }

    private fun showAlert(message:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Info")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
