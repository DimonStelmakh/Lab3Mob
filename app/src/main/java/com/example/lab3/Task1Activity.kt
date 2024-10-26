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
        val inputElectricityPrice = findViewById<EditText>(R.id.electricityPrice)

        val calculateButton = findViewById<Button>(R.id.calculateButton)

        val moneyBalance = findViewById<TextView>(R.id.moneyBalance)
//        val coalGrossEmission = findViewById<TextView>(R.id.coalGrossEmission)

        val oilFuelSolidParticlesEmission = findViewById<TextView>(R.id.oilFuelSolidParticlesEmission)
        val oilFuelGrossEmission = findViewById<TextView>(R.id.oilFuelGrossEmission)

        val naturalGasSolidParticlesEmission = findViewById<TextView>(R.id.naturalGasSolidParticlesEmission)
        val naturalGasGrossEmission = findViewById<TextView>(R.id.naturalGasGrossEmission)

        calculateButton.setOnClickListener {
            try {
                val allowedMistakePercentage = 5

                val averageDayPower = inputAverageDayPower.text.toString().toDouble()
                val forecastRootMeanSquareDeviation = inputForecastRootMeanSquareDeviation.text.toString().toDouble()
                val targetForecastRootMeanSquareDeviation = inputTargetForecastRootMeanSquareDeviation.text.toString().toDouble()
                val electricityPrice = inputElectricityPrice.text.toString().toDouble()

                val shareWithoutImbalancesCalculated = calculateShareWithoutImbalance(averageDayPower, forecastRootMeanSquareDeviation, allowedMistakePercentage.toDouble())

                val profit = calculateElectricityValue(calculateElectricityQuantity(averageDayPower, shareWithoutImbalancesCalculated/100), electricityPrice)
                val loss = calculateElectricityValue(calculateElectricityQuantity(averageDayPower, (1-shareWithoutImbalancesCalculated/100)), electricityPrice)

                val initiateMoneyBalanceCalculated = profit - loss

                moneyBalance.text = "Баланс доходу/втрати, грн: %.2f".format(initiateMoneyBalanceCalculated)
//                coalGrossEmission.text = "Валовий викид при спалюванні: %.2f".format(coalGrossEmissionCalculated)

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

fun calculateShareWithoutImbalance(pC: Double, sigma1: Double, delta: Double): Double {
    return trapezoidalIntegral(pC, sigma1, pC-delta, pC+delta, 1000)
}

fun trapezoidalIntegral(
    pC: Double,
    sigma1: Double,
    start: Double,
    end: Double,
    steps: Int
): Double {
    val stepSize = (end - start) / steps
    var integral = 0.0
    for (i in 0 until steps) {
        val p1 = start + i * stepSize
        val p2 = start + (i + 1) * stepSize
        val pd1 = calculatePd(p1, pC, sigma1)
        val pd2 = calculatePd(p2, pC, sigma1)
        integral += (pd1 + pd2) / 2 * stepSize
    }
    return integral
}

fun calculateElectricityQuantity(pC: Double, deltaW: Double): Double {
    return pC * 24 * deltaW
}

fun calculateElectricityValue(W: Double, cost: Double): Double {
    return W * cost
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