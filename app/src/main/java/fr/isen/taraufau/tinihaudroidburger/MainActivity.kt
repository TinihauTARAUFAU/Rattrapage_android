package fr.isen.taraufau.tinihaudroidburger

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.FieldPosition
import java.util.*
import android.widget.TimePicker
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)
        val burgers = arrayOf("Burger du chef","Cheese burger","Burger Montagnard","Burger Italien","Burger Végétarien")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, burgers)
        spinner.adapter = adapter
        var selectedItem = ""

        spinner.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = burgers[position]
                Toast.makeText(this@MainActivity,"Burger choisi : $selectedItem",Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })

        val mPickTimeBtn = findViewById<Button>(R.id.time_select_button)
        val timeDel = findViewById<TextView>(R.id.display_time)

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR, hour)
                cal.set(Calendar.MINUTE, minute)
                timeDel.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
        }

        val nom = findViewById<EditText>(R.id.editnom)
        val prenom = findViewById<EditText>(R.id.editprenom)
        val adresse = findViewById<EditText>(R.id.editadresse)
        val numero = findViewById<EditText>(R.id.editnumero)
        val heure = findViewById<TextView>(R.id.display_time)

        val commander = findViewById<Button>(R.id.commander)
        commander.setOnClickListener { view: View ->

            val txtnom = nom.text.toString()
            val txtprenom = prenom.text.toString()
            val txtadresse = adresse.text.toString()
            val txtnumero = numero.text.toString()
            val txtheure = heure.text.toString()

            if (txtnom.trim().isEmpty() || txtprenom.trim().isEmpty() || txtadresse.trim()
                    .isEmpty() || txtnumero.trim().isEmpty() || txtheure.trim().isEmpty()
            ) {
                Toast.makeText(this, "les champs ne peuvent pas être vides !", Toast.LENGTH_LONG)
                    .show()
            } else {
                var Activity2 = Intent(this, MainActivity2::class.java).apply {
                    putExtra("referName", txtnom)
                    putExtra("referAddress", txtadresse)
                    putExtra("referPhone", txtnumero)
                    putExtra("referTimeDelivery", txtheure)
                    putExtra("referSelectedBurger", selectedItem)
                }

                val sharedPref = this?.getSharedPreferences(getString(R.string.sharedpref), Context.MODE_PRIVATE)
                //val sharedPref = getPreferences(Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("saved_name", txtnom)
                    putString("saved_address", txtadresse)
                    putString("saved_phone", txtnumero)
                    apply()
                }

                Toast.makeText(this, "Confirmation de commande", Toast.LENGTH_LONG).show()

                startActivity(Activity2)
            }

        }
    }
}
