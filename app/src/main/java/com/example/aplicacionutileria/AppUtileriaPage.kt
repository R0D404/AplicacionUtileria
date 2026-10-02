package com.example.aplicacionutileria

import android.R
import android.widget.Button
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUtileriaPage(viewModel: AppUtileriaViewModel = viewModel()) {
    // Aqui conectaremos los estados y la interfaz
    val totalCuenta by viewModel.totalCuenta.collectAsStateWithLifecycle()
    val porcentajePropina by viewModel.porcentajePropina.collectAsStateWithLifecycle()
    val numeroPersonas by viewModel.numeroPersonas.collectAsStateWithLifecycle()
    val montoPropina by viewModel.montoPropina.collectAsStateWithLifecycle()
    val totalPagar by viewModel.totalPagar.collectAsStateWithLifecycle()
    val totalPorPersona by viewModel.totalPorPersona.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Propina") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // Aqui va todo el contenido que vera el usuario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            OutlinedTextField(
                value = totalCuenta,
                onValueChange ={ nuevoTexto-> viewModel.cambiarTotalCuenta(nuevoTexto)},
                label = {Text ("Total de la cuenta $")},
                placeholder = {Text("0")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text="Propina: $porcentajePropina%",
                fontSize = 20.sp,
            )
            Slider(
                value=porcentajePropina.toFloat(),
                onValueChange ={nuevoValor-> viewModel.cambiarPorcentajePropina(nuevoValor)},
                valueRange = 0f..30f,
                steps = 29,
                modifier=Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text="Dividir Cuenta",
                fontSize = 25.sp,
            )
            Spacer(modifier= Modifier.height(10.dp))
            Row(
                modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Button(onClick = {viewModel.decrementarPersonas()},
                    enabled= numeroPersonas > 1) {
                    Text(
                        text="-",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = "$numeroPersonas ${if(numeroPersonas==1) "persona" else "personas"}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier=Modifier.width(24.dp))

                Button(onClick = {viewModel.incrementarPersonas()},
                    enabled = numeroPersonas < 10
                ) {
                    Text( text="+",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(modifier = Modifier.height(24.dp))



            Card(
                modifier= Modifier.fillMaxWidth(),

            ) {
                Column(
                    modifier=Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Resumen de la cuenta",
                        fontSize = 20.sp
                    )
                    Text(
                        text="Propina total:$ $montoPropina",
                        fontSize = 16.sp
                    )
                    Text(
                        text="Total a pagar =$ $totalPagar",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Cada persona paga: $$totalPorPersona",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { viewModel.limpiar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpiar campos")
            }



            // Secciones de la pantalla


        }
    }
}