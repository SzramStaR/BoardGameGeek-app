package com.example.eduputinf151873


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.net.URL

class ConfigureActivity : ComponentActivity() {
    private lateinit var UsrNameEditText : EditText
    private lateinit var ConfigButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_layout)


        UsrNameEditText = findViewById(R.id.confiugre_edit_text)
        ConfigButton = findViewById(R.id.configure_select_button)

        ConfigButton.setOnClickListener{
                val username = UsrNameEditText.text.toString()
               // val dbHandler = ProfileDataBase(this@Main, null,null,1)
//                dbHandler.addProfile(username,1,1,"nigdy")
                Toast.makeText(this,"Dodano do bazy",Toast.LENGTH_SHORT).show()
                val Intent = Intent(this,MainActivity::class .java)
                Intent.putExtra("username", username)
             //   dbHandler.close()

            //    this.deleteDatabase(ProfileDataBase.DATABASE_NAME)
                startActivity(Intent)
       }







    }


    }




