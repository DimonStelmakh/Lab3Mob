package com.example.lab3

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import androidx.activity.ComponentActivity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab3.ui.theme.Lab1Theme
import kotlin.math.pow
import kotlin.math.exp
import kotlin.math.sqrt
import kotlin.math.PI


class Task1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)

        val inputAverageDayPower = findViewById<EditText>(R.id.averageDayPower)
        val inputForecastRootMeanSquareDeviation = findViewById<EditText>(R.id.forecastRootMeanSquareDeviation)
        val inputTargetForecastRootMeanSquareDeviation = findViewById<EditText>(R.id.targetForecastRootMeanSquareDeviation)

        val calculateButton = findViewById<Button>(R.id.calculateButton)

        val coalSolidParticlesEmission = findViewById<TextView>(R.id.coalSolidParticlesEmission)
        val coalGrossEmission = findViewById<TextView>(R.id.coalGrossEmission)

        val oilFuelSolidParticlesEmission = findViewById<TextView>(R.id.oilFuelSolidParticlesEmission)
        val oilFuelGrossEmission = findViewById<TextView>(R.id.oilFuelGrossEmission)

        val naturalGasSolidParticlesEmission = findViewById<TextView>(R.id.naturalGasSolidParticlesEmission)
        val naturalGasGrossEmission = findViewById<TextView>(R.id.naturalGasGrossEmission)

        calculateButton.setOnClickListener {
            try {
                val ashCollectorEfficiency = 0.985

                val coalWorkingLHV = 20.47
                val coalWorkingAshPercentage = 25.20
                val coalFlyAshPercentage = 0.80
                val coalCombustibleSubstancesInFlyAshPercentage = 1.5
//                val coalCombustibleSubstancesInSlagPercentage = 0.5

                val averageDayPower = inputAverageDayPower.text.toString().toDouble()
                val forecastRootMeanSquareDeviation = inputForecastRootMeanSquareDeviation.text.toString().toDouble()
                val targetForecastRootMeanSquareDeviation = inputTargetForecastRootMeanSquareDeviation.text.toString().toDouble()

                val coalSolidParticlesEmissionCalculated = (10.0.pow(6.0) / coalWorkingLHV) * coalFlyAshPercentage * (coalWorkingAshPercentage / (100 - coalCombustibleSubstancesInFlyAshPercentage)) * (1 - ashCollectorEfficiency)
                val coalGrossEmissionCalculated = 10.0.pow(-6.0) * coalSolidParticlesEmissionCalculated * coalWorkingLHV * coalVolume

                coalSolidParticlesEmission.text = "Емісія твердих частинок при спалюванні: %.2f".format(coalSolidParticlesEmissionCalculated)
                coalGrossEmission.text = "Валовий викид при спалюванні: %.2f".format(coalGrossEmissionCalculated)

                val oilFuelWorkingLHV = 39.48
                val oilFuelWorkingAshPercentage = 0.15
                val oilFuelFlyAshPercentage = 1
                val oilFuelCombustibleSubstancesInFlyAshPercentage = 0
//                val coalCombustibleSubstancesInSlagPercentage = 0.5

                val oilFuelSolidParticlesEmissionCalculated = (10.0.pow(6.0) / oilFuelWorkingLHV) * oilFuelFlyAshPercentage * (oilFuelWorkingAshPercentage / (100 - oilFuelCombustibleSubstancesInFlyAshPercentage)) * (1 - ashCollectorEfficiency)
                val oilFuelGrossEmissionCalculated = 10.0.pow(-6.0) * oilFuelSolidParticlesEmissionCalculated * oilFuelWorkingLHV * oilFuelVolume

                oilFuelSolidParticlesEmission.text = "Емісія твердих частинок при спалюванні: %.2f".format(oilFuelSolidParticlesEmissionCalculated)
                oilFuelGrossEmission.text = "Валовий викид при спалюванні: %.2f".format(oilFuelGrossEmissionCalculated)

                // оскільки при спалюванні природного газу тверді частинки відсутні, то емісія твердих частинок складає 0
                val naturalGasSolidParticlesEmissionCalculated = 0.0
                // як наслідок, валовий викид також складає 0, адже один з множників дорівнює 0
                val naturalGasGrossEmissionCalculated = 0.0

                naturalGasSolidParticlesEmission.text = "Емісія твердих частинок при спалюванні: %.2f".format(naturalGasSolidParticlesEmissionCalculated)
                naturalGasGrossEmission.text = "Валовий викид при спалюванні: %.2f".format(naturalGasGrossEmissionCalculated)

            } catch (e: Exception) {
                Toast.makeText(this, "Будь ласка, введіть правильні числові значення!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun calculatePd(p: Double, pC: Double, sigma1: Double): Double {
    val exponent = -((p - pC).pow(2)) / (2 * sigma1.pow(2))
    return (1 / (sigma1 * sqrt(2 * PI))) * exp(exponent)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab1Theme {
        Greeting("Android")
    }
}