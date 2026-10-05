package com.example.aplicacionutileria

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


// - Hereda de la clase ViewModel() de Android para sobrevivir a cambios de configuracion (como rotar la pantalla).
class AppUtileriaViewModel : ViewModel() {

    // Guarda el texto que el usuario escribe en el campo de la cuenta
    private var _totalCuenta = MutableStateFlow("")
    val totalCuenta: StateFlow<String> = _totalCuenta.asStateFlow()

    // Va de 0 a 30.
    private var _porcentajePropina = MutableStateFlow(10)
    val porcentajePropina: StateFlow<Int> = _porcentajePropina.asStateFlow()

    private var _numeroPersonas = MutableStateFlow(1)
    val numeroPersonas: StateFlow<Int> = _numeroPersonas.asStateFlow()

    private var _montoPropina = MutableStateFlow(0)
    val montoPropina: StateFlow<Int> = _montoPropina.asStateFlow()

    // guarda (cuenta + propina, redondeado a entero).
    private var _totalPagar = MutableStateFlow(0)
    val totalPagar: StateFlow<Int> = _totalPagar.asStateFlow()

    private var _totalPorPersona = MutableStateFlow(0)
    val totalPorPersona: StateFlow<Int> = _totalPorPersona.asStateFlow()

    private var _name= MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()
    private var _matricula = MutableStateFlow("")
    val matricula: StateFlow<String> = _matricula.asStateFlow()

    fun mostrarInformacion(){
        _name.value = "Rodrigo Gonzalez Morales"
        _matricula.value = "253696"
    }


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


    fun limpiar() {
        _totalCuenta.value = ""
        _porcentajePropina.value = 10
        _numeroPersonas.value = 1
        _montoPropina.value = 0
        _totalPagar.value = 0
        _totalPorPersona.value = 0
        _name.value = ""
        _matricula.value =""
        }

}
