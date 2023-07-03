package com.example.eduputinf151873


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Retrofit
import kotlinx.coroutines.*
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eduputinf151873.databinding.MultipleGameScreenBinding
import kotlin.system.exitProcess




class MainActivity : ComponentActivity() {
    private lateinit var binding: MultipleGameScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_layout)

        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.configure_layout, null, false)

        val configButton : Button = findViewById(R.id.BackToConfigButton)
        val buttonSynch : Button = findViewById(R.id.SynchButton)
        val gamesButton : Button = findViewById(R.id.GamesButton)
        val extButton : Button = findViewById(R.id.ExtensionsButton)
        val usrNameEditText :EditText = layout.findViewById(R.id.confiugre_edit_text)
        val main_nickname : TextView = findViewById(R.id.main_nickname)
        val main_synchro_date : TextView = findViewById(R.id.synchro_date)
        val eraseButton: Button = findViewById(R.id.EraseButton)
        val main_amount_of_games : TextView = findViewById(R.id.amount_games)
        val main_amount_of_extensions : TextView = findViewById(R.id.amount_extensions)
        lateinit var builder: AlertDialog.Builder
        lateinit var progressBar: ProgressBar
        progressBar = findViewById(R.id.progressBar)

        buttonSynch.setOnClickListener{
            val Intent = Intent(this,SynchroActivity::class .java)
            startActivity(Intent)
        }

        gamesButton.setOnClickListener{
            val intent = Intent(this, MultipleGameActivity::class.java)

            startActivity(intent)
        }

        extButton.setOnClickListener{
            val Intent = Intent(this,MultipleExtensionsActivity::class .java)
            startActivity(Intent)
        }
        configButton.setOnClickListener{
            val Intent = Intent(this,ConfigureActivity::class .java)
            this.deleteDatabase(ProfileDataBase.DATABASE_NAME)
            this.deleteDatabase(GamesDataBase.DATABASE_NAME)
            this.deleteDatabase(ExtensionsDataBase.DATABASE_NAME)
            startActivity(Intent)
        }

        builder = AlertDialog.Builder(this)

        eraseButton.setOnClickListener{
            builder.setTitle("Erase all data")
            builder.setMessage("Are you sure you want to erase all data?")
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                this.deleteDatabase(ProfileDataBase.DATABASE_NAME)
                this.deleteDatabase(GamesDataBase.DATABASE_NAME)
                this.deleteDatabase(ExtensionsDataBase.DATABASE_NAME)
                finish();
                exitProcess(0)
            }
            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()



        }







        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.boardgamegeek.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val usrApiService = retrofit.create(UsrApiService::class.java)


        CoroutineScope(Dispatchers.Main).launch {

            val username = intent.getStringExtra("username")
            main_nickname.text = username


            try {
                progressBar.visibility = View.VISIBLE
                val user = usrApiService.getUserData(username.toString())
                val games = usrApiService.getGamesData(username.toString())
                val extensions = usrApiService.getExtensionsData(username.toString(), "boardgameexpansion")
                progressBar.visibility = View.GONE
                println(extensions)



                val lastLogin = user.pubdate
                val amountOfGames = user.totalitems
                val lastLoginString = lastLogin.split("+")
                println(lastLoginString[0])
                println(lastLoginString[1])

                main_synchro_date.text = "Last login: ${lastLoginString[0]}"
                main_amount_of_games.text = "You have $amountOfGames games in your collection"

//                val profileDbHandler = ProfileDataBase(this@MainActivity, null,null,1)
//                profileDbHandler.addProfile(main_nickname.text.toString(),1,1,main_synchro_date.text.toString())

                val gameDbHandler = GamesDataBase(this@MainActivity, null,null,1)

                var gamesOwned = 0

                for (game in games.games) {
                    gameDbHandler.addGame(game.name.toString(),game.yearpublished.toString(),game.objectid,game.image.toString())


                }

                val extensionDbHandler = ExtensionsDataBase(this@MainActivity, null,null,1)

                var extensionsOwned = 0


                for(extension in extensions.extensions){
                    extensionDbHandler.addExtension(extension.name.toString(),extension.objectid,extension.image.toString())
                    extensionsOwned ++
                }
                if (extensionsOwned == 0){
                    main_amount_of_extensions.text = "You have no extensions in your collection"
                } else if (extensionsOwned == 1){
                    main_amount_of_extensions.text = "You have $extensionsOwned extension in your collection"
                } else {
                    main_amount_of_extensions.text = "You have $extensionsOwned extensions in your collection"
                }

                val profileDbHandler = ProfileDataBase(this@MainActivity, null,null,1)
                profileDbHandler.addProfile(main_nickname.text.toString(),amountOfGames,extensionsOwned,main_synchro_date.text.toString())





            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "User does not exist or an error occurred", Toast.LENGTH_SHORT).show()
                println("Exception $e")
            }



                }

    }


}
