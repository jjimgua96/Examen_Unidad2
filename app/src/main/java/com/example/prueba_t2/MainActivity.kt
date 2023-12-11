package com.example.prueba_t2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {

    private lateinit var cvHombre: CardView
    private lateinit var cvMujer: CardView
    private lateinit var tvAltura: TextView
    private lateinit var rsAltura: RangeSlider
    private lateinit var tvPeso: TextView
    private lateinit var butPesoMinus: FloatingActionButton
    private lateinit var butPesoPlus: FloatingActionButton
    private lateinit var tvEdad: TextView
    private lateinit var butEdadMinus: FloatingActionButton
    private lateinit var butEdadPlus: FloatingActionButton
    private lateinit var butCalcular: Button
    private lateinit var tvComplexion: TextView
    private lateinit var rsComplexion: RangeSlider

    private var altura: Int = Constantes.defAltura
    private var sexo: Int = Constantes.valHombre
    private var peso: Int = Constantes.defPeso
    private var edad: Int = Constantes.defEdad
    private var complexion: Int = Constantes.compNormal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setVariables()
        setActions()
    }

    private fun setVariables() {
        cvHombre = findViewById(R.id.cvHombre)
        cvMujer = findViewById(R.id.cvMujer)
        tvAltura = findViewById(R.id.tvAltura)
        rsAltura = findViewById(R.id.rsAltura)
        tvPeso = findViewById(R.id.tvPeso)
        butPesoMinus = findViewById(R.id.butPesoMinus)
        butPesoPlus = findViewById(R.id.butPesoPlus)
        tvEdad = findViewById(R.id.tvEdad)
        butEdadMinus = findViewById(R.id.butEdadMinus)
        butEdadPlus = findViewById(R.id.butEdadPlus)
        tvComplexion = findViewById(R.id.tvComplexion)
        rsComplexion = findViewById(R.id.rsComplexion)
        butCalcular = findViewById(R.id.butCalcular)
    }

    private fun setActions() {
        cvHombre.setCardBackgroundColor(getColor(R.color.dark_blue_3))
        tvAltura.text = String.format("$altura cm")
        tvPeso.text = peso.toString()
        tvEdad.text = edad.toString()
        tvComplexion.text = buscarComplexion(complexion)


        cvHombre.setOnClickListener {
            cvHombre.setCardBackgroundColor(getColor(R.color.dark_blue_3))
            cvMujer.setCardBackgroundColor(getColor(R.color.dark_blue_1))
            sexo = Constantes.valHombre
        }
        cvMujer.setOnClickListener {
            cvMujer.setCardBackgroundColor(getColor(R.color.dark_blue_3))
            cvHombre.setCardBackgroundColor(getColor(R.color.dark_blue_1))
            sexo = Constantes.valMujer
        }

        rsAltura.addOnChangeListener { _, value, _ ->
            altura = value.toInt()
            tvAltura.text = String.format("$altura cm")
        }

        butPesoMinus.setOnClickListener {
            if (peso > 1) {
                peso--
            }
            tvPeso.text = peso.toString()
        }

        butPesoPlus.setOnClickListener {
            peso++
            tvPeso.text = peso.toString()
        }

        butEdadMinus.setOnClickListener {
            if (edad > 1) {
                edad--
            }
            tvEdad.text = edad.toString()
        }

        butEdadPlus.setOnClickListener {
            edad++
            tvEdad.text = edad.toString()
        }

        rsComplexion.addOnChangeListener { _, value, _ ->
            complexion = value.toInt()
            tvComplexion.text = buscarComplexion(complexion)
        }

        butCalcular.setOnClickListener {
            val intentarCalcular = Intent(this, ResultIMCActivity::class.java)
            intentarCalcular.putExtra("EXTRA_SEXO", sexo)
            intentarCalcular.putExtra("EXTRA_ALTURA", altura)
            intentarCalcular.putExtra("EXTRA_PESO", peso)
            intentarCalcular.putExtra("EXTRA_EDAD", edad)
            intentarCalcular.putExtra("EXTRA_COMPLEXION", complexion)
            startActivity(intentarCalcular)
        }
    }

    private fun buscarComplexion(complexion: Int): String {
        lateinit var res: String
        when (complexion) {
            Constantes.compDelgado -> res = "Delgado"
            Constantes.compNormal -> res = "Normal"
            Constantes.compAtletico -> res = "Atletico"
            else -> {
                res = "Corpulento"
            }
        }
        return res
    }
}