package com.example.eduputinf151873


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class SynchroActivity : ComponentActivity() {
    private lateinit var profileDbHandler: ProfileDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.synch_screen_layout)

        profileDbHandler = ProfileDataBase(this, null, null, 1)

        val lastSynchro = profileDbHandler.getSynchroDate()

        val synchroDate = findViewById<TextView>(R.id.last_sync_date)
        val synchroButton = findViewById<TextView>(R.id.sync_button)

        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.configure_layout, null, false)

        lateinit var progressBar: ProgressBar
        progressBar = findViewById(R.id.progressBar)


        synchroDate.text = "Last synchro date: $lastSynchro"

        synchroButton.setOnClickListener {

            this.deleteDatabase(ProfileDataBase.DATABASE_NAME)
            this.deleteDatabase(GamesDataBase.DATABASE_NAME)
            this.deleteDatabase(ExtensionsDataBase.DATABASE_NAME)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.boardgamegeek.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()

            val usrApiService = retrofit.create(UsrApiService::class.java)
            synchroDate.text = "Last synchro date: ${profileDbHandler.getSynchroDate()}"


            CoroutineScope(Dispatchers.Main).launch {

                val username = intent.getStringExtra("username")
                println(username)

                try {
                    progressBar.visibility = View.VISIBLE
                    val user = usrApiService.getUserData(username.toString())
                    val games = usrApiService.getGamesData(username.toString())
                    val extensions = usrApiService.getExtensionsData(username.toString(), "boardgameexpansion")
                    progressBar.visibility = View.GONE
                    println(extensions)




                    val amountOfGames = user.totalitems





                    val gameDbHandler = GamesDataBase(this@SynchroActivity, null,null,1)

                    var gamesOwned = 0

                    for (game in games.games) {
                        gameDbHandler.addGame(game.name.toString(),game.yearpublished.toString(),game.objectid,game.image.toString())


                    }

                    val extensionDbHandler = ExtensionsDataBase(this@SynchroActivity, null,null,1)

                    var extensionsOwned = 0


                    for(extension in extensions.extensions){
                        extensionDbHandler.addExtension(extension.name.toString(),extension.objectid,extension.image.toString())
                        extensionsOwned ++
                    }


                    val profileDbHandler = ProfileDataBase(this@SynchroActivity, null,null,1)
                    profileDbHandler.addProfile(username.toString(),amountOfGames,extensionsOwned,username.toString())





                } catch (e: Exception) {
                    println("Exception $e")
                }



            }


        }

    }
}



