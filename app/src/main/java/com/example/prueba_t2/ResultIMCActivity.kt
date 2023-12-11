package com.example.prueba_t2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvSexo: TextView
    private lateinit var tvEdad: TextView
    private lateinit var tvAltura: TextView
    private lateinit var tvPeso: TextView
    private lateinit var tvTipoPeso: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvMensaje: TextView
    private lateinit var butRecalcular: Button
    private var sexo: Int = 0
    private var altura: Int = 0
    private var peso: Int = 0
    private var edad: Int = 0
    private var complexion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)
        setVariables()
        setActions()
    }

    private fun setVariables() {
        tvSexo = findViewById(R.id.tvSexo)
        tvEdad = findViewById(R.id.tvEdad)
        tvAltura = findViewById(R.id.tvAltura)
        tvPeso = findViewById(R.id.tvPeso)
        tvTipoPeso = findViewById(R.id.tvTipoPeso)
        tvIMC = findViewById(R.id.tvIMC)
        tvMensaje = findViewById(R.id.tvMensaje)
        butRecalcular = findViewById(R.id.butRecalcular)
        sexo = intent.extras?.getInt("EXTRA_SEXO") ?: 0
        altura = intent.extras?.getInt("EXTRA_ALTURA") ?: 0
        peso = intent.extras?.getInt("EXTRA_PESO") ?: 0
        edad = intent.extras?.getInt("EXTRA_EDAD") ?: 0
        complexion = intent.extras?.getInt("EXTRA_COMPLEXION") ?: 0
    }

    private fun setActions() {
        val IMC = calcularIMC()
        tvIMC.text = String.format("%.2f", IMC)

        when (sexo) {
            Constantes.valMujer -> tvSexo.text = String.format("Mujer")
            else -> tvSexo.text = String.format("Hombre")
        }

        tvEdad.text = String.format("$edad años")
        tvAltura.text = String.format("$altura cm")
        tvPeso.text = String.format("$peso kg")

        when (IMC) {
            in 0.0..18.5 -> {
                tvTipoPeso.text = String.format("Peso Bajo")
                if (sexo == Constantes.valMujer) {
                    tvMensaje.text = String.format("Estás muy delgada \n Tienes que ganar peso")
                } else {
                    tvMensaje.text = String.format("Estás muy delgado \n Tienes que ganar peso")
                }
            }
            in 18.6..25.0 -> {
                tvTipoPeso.text = String.format("Normal")
                if (sexo == Constantes.valMujer) {
                    tvMensaje.text = String.format("Estás muy bien, tía!!!! \n Sigue así!!!!")
                } else {
                    tvMensaje.text = String.format("Estás muy bien, tío!!!! \n Sigue así!!!!")
                }
            }
            in 26.0..30.0 -> {
                tvTipoPeso.text = String.format("Sobrepeso")
                if (sexo == Constantes.valMujer) {
                    tvMensaje.text = String.format("Estás rellenita \n Cuídate!!!")
                } else {
                    tvMensaje.text = String.format("Estás rellenito \n Cuídate!!!")
                }
            }
            else -> {
                tvTipoPeso.text = String.format("Obesidad")
                if (sexo == Constantes.valMujer) {
                    tvMensaje.text = String.format("Estás gordita \n Tienes que adelgazar!!!")
                } else {
                    tvMensaje.text = String.format("Estás gordito \n Tienes que adelgazar!!!")
                }
            }
        }

        butRecalcular.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun calcularIMC(): Double {
        val res: Double
        val altMetros = altura.toDouble()/100
        res = peso/(altMetros*altMetros)*factorGenero()*factorEdad()*factorCorpulencia()
        return res
    }

    private fun factorGenero(): Double {
        val res: Double = if (sexo == Constantes.valMujer) {
            1.0
        } else {
            0.9
        }
        return res
    }

    private fun factorEdad(): Double {
        val res: Double = when (edad) {
            in 1..15 -> 1.1
            in 16..30 -> 1.0
            in 31..60 -> 0.95
            else -> 1.05
        }
        return res
    }

    private fun factorCorpulencia(): Double {
        val res: Double = when (complexion) {
            Constantes.compDelgado -> 1.03
            Constantes.compNormal -> 1.0
            Constantes.compAtletico -> 0.97
            else -> 0.94
        }
        return res
    }
}