package fr.isen.taraufau.tinihaudroidburger

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val sharedPref = this.getSharedPreferences(getString(R.string.sharedpref), Context.MODE_PRIVATE)

        val getname = sharedPref.getString("saved_name", "default_name")
        val getadresse = intent.getStringExtra("referAddress")
        val getnumero = intent.getStringExtra("referPhone")
        val getburger = intent.getStringExtra("referSelectedBurger")
        val getheure = intent.getStringExtra("referTimeDelivery")


        val commande = findViewById<TextView>(R.id.recap)
        commande.text = "Bonjour " + getname + ", vous avez commandé un " + getburger + ". Il sera livré à l'adresse : " + getadresse + " pour " + getheure + ". Nous vous appelerons au " + getnumero +". Merci pour votre commande !"

        val email = Intent(Intent.ACTION_SEND)

        findViewById<Button>(R.id.envoyer).setOnClickListener {
            email.data = Uri.parse("mailto:");
            email.type = "text/plain";
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf("marc.mollinari@gmail.com"));
            email.putExtra(Intent.EXTRA_SUBJECT, "Confirmation commande");
            email.putExtra(Intent.EXTRA_TEXT, "Merci d'avoir passé commande chez TinihauDroidBurger !");
            try {
                startActivity(Intent.createChooser(email, "Choose Email Client..."))
            } catch (e: Exception) {
            }
        }
    }
}