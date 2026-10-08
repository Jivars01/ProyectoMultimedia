package com.example.inicioactivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: "[Nombre]"
        val apellidos = intent.getStringExtra("EXTRA_APELLIDOS") ?: "[Apellidos]"
        val contrasena = intent.getStringExtra("EXTRA_CONTRASENA") ?: "[Contraseña]"
        val telefono = intent.getStringExtra("EXTRA_TELEFONO") ?: "[Teléfono]"
        val correo = intent.getStringExtra("EXTRA_CORREO") ?: "[Correo Electrónico]"
        val direccion = intent.getStringExtra("EXTRA_DIRECCION") ?: "[Dirección]"
        val fecha = intent.getStringExtra("EXTRA_FECHA") ?: "[Fecha de Nacimiento]"

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PerfilPantalla(
                        nombre = nombre,
                        apellidos = apellidos,
                        contraseña = contrasena,
                        telefono = telefono,
                        correo = correo,
                        direccion = direccion,
                        fecha = fecha
                    )
                }
            }
        }
    }
}
@Composable
fun PerfilPantalla(
    nombre: String = "[Nombre]",
    apellidos: String = "[Apellidos]",
    contraseña: String = "[Contraseña]",
    telefono: String = "[Teléfono]",
    correo: String = "[Correo Electrónico]",
    direccion: String = "[Dirección]",
    fecha: String = "[Fecha de Nacimiento]"
) {

    val context = LocalContext.current

    var nombreInicial by remember { mutableStateOf(nombre) }
    var apellidoInicial by remember { mutableStateOf(apellidos) }
    var contraseñaInicial by remember { mutableStateOf(contraseña) }
    var telefonoInicial by remember { mutableStateOf(telefono) }
    var correoInicial by remember { mutableStateOf(correo) }
    var direccionInicial by remember { mutableStateOf(direccion) }
    var fechaInicial by remember { mutableStateOf(fecha) }

    val launcherFormulario = rememberLauncherForActivityResult( //Saber que hace el rememeber
        contract = ActivityResultContracts.StartActivityForResult() //Que es el activityresults
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            data?.getStringExtra("EXTRA_NOMBRE")?.let { nombreInicial = it }
            data?.getStringExtra("EXTRA_APELLIDOS")?.let { apellidoInicial = it }
            data?.getStringExtra("EXTRA_CONTRASENA")?.let { contraseñaInicial = it }
            data?.getStringExtra("EXTRA_TELEFONO")?.let { telefonoInicial = it }
            data?.getStringExtra("EXTRA_CORREO")?.let { correoInicial = it }
            data?.getStringExtra("EXTRA_DIRECCION")?.let { direccionInicial = it }
            data?.getStringExtra("EXTRA_FECHA")?.let { fechaInicial = it }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Perfil del Usuario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(text = "Nombre: $nombreInicial", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Apellidos: $apellidoInicial", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Contraseña: $contraseñaInicial", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Fecha de Nacimiento: $fechaInicial", style = MaterialTheme.typography.bodyLarge)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable() {
                    if (telefonoInicial != "[Teléfono]" && telefonoInicial.isNotEmpty()) {
                        val intentTelefono = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$telefonoInicial")
                        }
                        context.startActivity(intentTelefono)
                    }
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)

        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Teléfono (Toca para marcar):",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(text = telefonoInicial, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (correoInicial != "[Correo Electrónico]" && correoInicial.isNotEmpty()) {
                        val intentCorreo = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$correoInicial")
                        }
                        context.startActivity(intentCorreo)
                    }
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Correo electrónico (Toca para redactar):",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(text = correoInicial, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (direccionInicial != "[Dirección]" && direccionInicial.isNotEmpty()) {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(direccionInicial)}")
                        val intentMapa = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        context.startActivity(intentMapa)
                    }
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Dirección (Toca para buscar):",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(text = direccionInicial, style = MaterialTheme.typography.bodyLarge)
            }

        }
        Button(
            onClick = {
                val intent = Intent(context, FormularioActivity::class.java).apply {
                    putExtra("EXTRA_NOMBRE", nombreInicial)
                    putExtra("EXTRA_APELLIDOS", apellidoInicial)
                    putExtra("EXTRA_CONTRASENA", contraseñaInicial)
                    putExtra("EXTRA_TELEFONO", telefonoInicial)
                    putExtra("EXTRA_CORREO", correoInicial)
                    putExtra("EXTRA_DIRECCION", direccionInicial)
                    putExtra("EXTRA_FECHA", fechaInicial)
                }
                launcherFormulario.launch(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Editar perfil")
        }

        OutlinedButton(
            onClick = {
                val intent = Intent(context, InicioActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}