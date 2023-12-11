package com.example.prueba_t2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvIMC: TextView
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
        tvIMC = findViewById(R.id.tvIMC)
        butRecalcular = findViewById(R.id.butRecalcular)
        sexo = intent.extras?.getInt("EXTRA_SEXO") ?: 0
        altura = intent.extras?.getInt("EXTRA_ALTURA") ?: 0
        peso = intent.extras?.getInt("EXTRA_PESO") ?: 0
        edad = intent.extras?.getInt("EXTRA_EDAD") ?: 0
        complexion = intent.extras?.getInt("EXTRA_COMPLEXION") ?: 0
    }

    private fun setActions() {
        val resultado = calcularIMC()
        tvIMC.text = String.format("%.2f", resultado)

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