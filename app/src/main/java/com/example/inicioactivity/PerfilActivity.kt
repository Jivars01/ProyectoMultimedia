package com.example.inicioactivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inicioactivity.ui.theme.InicioActivityTheme
import java.nio.file.WatchEvent

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
                        contrasena = contrasena,
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
    nombre: String,
    apellidos: String,
    contrasena: String,
    telefono: String,
    correo: String,
    direccion: String,
    fecha: String
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Perfil del Usuario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(text = "Nombre: $nombre", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Apellidos: $apellidos", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Contraseña: $contrasena", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Fecha de Nacimiento: $fecha", style = MaterialTheme.typography.bodyLarge)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable() {
                    if (telefono != "[Teléfono]" && telefono.isNotEmpty()) {
                        val intentTelefono = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$telefono")
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
                Text(text = telefono, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (correo != "[Correo Electrónico]" && correo.isNotEmpty()) {
                        val intentCorreo = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$correo")
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
                Text(text = correo, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (direccion != "[Dirección]" && direccion.isNotEmpty()) {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(direccion)}")
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
                Text(text = direccion, style = MaterialTheme.typography.bodyLarge)
            }

        }
    }
}