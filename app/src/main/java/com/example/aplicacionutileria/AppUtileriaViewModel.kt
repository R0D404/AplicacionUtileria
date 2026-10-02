package com.example.aplicacionutileria

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


// - Hereda de la clase ViewModel() de Android para sobrevivir a cambios de configuracion (como rotar la pantalla).
class AppUtileriaViewModel : ViewModel() {

    // Guarda el texto que el usuario escribe en el campo de la cuenta
    private val _totalCuenta = MutableStateFlow("")
    val totalCuenta: StateFlow<String> = _totalCuenta.asStateFlow()

    // Va de 0 a 30.
    private val _porcentajePropina = MutableStateFlow(10)
    val porcentajePropina: StateFlow<Int> = _porcentajePropina.asStateFlow()

    private val _numeroPersonas = MutableStateFlow(1)
    val numeroPersonas: StateFlow<Int> = _numeroPersonas.asStateFlow()

    private val _montoPropina = MutableStateFlow(0)
    val montoPropina: StateFlow<Int> = _montoPropina.asStateFlow()

    // guarda (cuenta + propina, redondeado a entero).
    private val _totalPagar = MutableStateFlow(0)
    val totalPagar: StateFlow<Int> = _totalPagar.asStateFlow()

    private val _totalPorPersona = MutableStateFlow(0)
    val totalPorPersona: StateFlow<Int> = _totalPorPersona.asStateFlow()


    fun cambiarTotalCuenta(nuevoTotal: String) {
        if (nuevoTotal.all { it.isDigit() }) {
            _totalCuenta.value = nuevoTotal
            calcularTotales()
        }
    }

    fun cambiarPorcentajePropina(nuevoPorcentaje: Float) {
        _porcentajePropina.value = nuevoPorcentaje.toInt()
        calcularTotales()
    }

    fun incrementarPersonas() {
        if (_numeroPersonas.value < 10) {
            _numeroPersonas.value++
            calcularTotales()
        }
    }

    fun decrementarPersonas() {

        if (_numeroPersonas.value > 1) {
            _numeroPersonas.value--

            calcularTotales()
        }
    }

    private fun calcularTotales() {

        val cuenta = _totalCuenta.value.toIntOrNull() ?: 0

        val propina = (cuenta * _porcentajePropina.value) / 100

        val total = cuenta + propina

        val porPersona = if (_numeroPersonas.value > 0) {
            total / _numeroPersonas.value
        } else {
            0
        }

        // Actualizamos los estados
        _montoPropina.value = propina
        _totalPagar.value = total
        _totalPorPersona.value = porPersona
    }

    // Funcion opcional para restablecer todos los valores al inicio.
    fun limpiar() {
        _totalCuenta.value = ""
        _porcentajePropina.value = 10
        _numeroPersonas.value = 1
        _montoPropina.value = 0
        _totalPagar.value = 0
        _totalPorPersona.value = 0
    }
}
