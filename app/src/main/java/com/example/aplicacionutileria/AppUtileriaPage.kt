package com.example.aplicacionutileria

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUtileriaPage(viewModel: AppUtileriaViewModel = viewModel()) {
    // 1. Estados observados del ViewModel
    val totalCuenta by viewModel.totalCuenta.collectAsStateWithLifecycle()
    val porcentajePropina by viewModel.porcentajePropina.collectAsStateWithLifecycle()
    val numeroPersonas by viewModel.numeroPersonas.collectAsStateWithLifecycle()
    val montoPropina by viewModel.montoPropina.collectAsStateWithLifecycle()
    val totalPagar by viewModel.totalPagar.collectAsStateWithLifecycle()
    val totalPorPersona by viewModel.totalPorPersona.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Calculadora de Propina",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier=Modifier.height(10.dp))
            // 1. Campo de texto estilizado para ingresar la cuenta
            OutlinedTextField(
                value = totalCuenta,
                onValueChange = { nuevoTexto -> viewModel.cambiarTotalCuenta(nuevoTexto) },
                label = { Text("Total de la cuenta") },
                placeholder = { Text("0") },
                prefix = { Text("$ ", fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Encabezado y Slider para seleccionar la propina
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Propina sugerida",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$porcentajePropina%",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Slider(
                value = porcentajePropina.toFloat(),
                onValueChange = { nuevoValor -> viewModel.cambiarPorcentajePropina(nuevoValor) },
                valueRange = 0f..30f,
                steps = 29,
                modifier = Modifier.fillMaxWidth()
            )

            // Referencias visuales de los extremos del Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0%", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                Text("15%", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                Text("30%", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Control de personas usando el composable reutilizable BotonContador
            Text(
                text = "Dividir entre personas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reutilizacion 1: Boton para restar
                BotonContador(
                    simbolo = "-",
                    habilitado = numeroPersonas > 1,
                    alHacerClic = { viewModel.decrementarPersonas() }
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "$numeroPersonas ${if (numeroPersonas == 1) "persona" else "personas"}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(20.dp))

                // Reutilizacion 2: Boton para sumar
                BotonContador(
                    simbolo = "+",
                    habilitado = numeroPersonas < 10,
                    alHacerClic = { viewModel.incrementarPersonas() }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 4. Tarjeta elegante de resultados con sombra (ElevatedCard)
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Resumen de la cuenta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )


                    FilaResumen(
                        etiqueta = "Propina calculada:",
                        monto = montoPropina
                    )

                    FilaResumen(
                        etiqueta = "Total con propina:",
                        monto = totalPagar
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Tarjeta interior destacada para lo que paga cada persona
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total por persona",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "$$totalPorPersona",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 5. Boton para reiniciar valores
            OutlinedButton(
                onClick = { viewModel.limpiar() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restablecer valores", fontSize = 16.sp)
            }
        }
    }
}


// Composable reutilizable 1: Boton con estilo moderno para + y -
@Composable
fun BotonContador(
    simbolo: String,
    habilitado: Boolean,
    alHacerClic: () -> Unit
) {
    FilledTonalButton(
        onClick = alHacerClic,
        enabled = habilitado,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = simbolo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Composable reutilizable 2: Fila para alinear concepto y monto en el resumen
@Composable
fun FilaResumen(
    etiqueta: String,
    monto: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            fontSize = 15.sp
        )
        Text(
            text = "$$monto",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
