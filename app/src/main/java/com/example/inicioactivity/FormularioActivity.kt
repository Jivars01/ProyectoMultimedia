package com.example.inicioactivity

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.activity.compose.LocalActivity

class FormularioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombreInicial = intent.getStringExtra("EXTRA_NOMBRE")?.takeIf { it != "[Nombre]" } ?: ""
        val apellidosInicial = intent.getStringExtra("EXTRA_APELLIDOS")?.takeIf { it != "[Apellidos]" } ?: ""
        val contrasenaInicial = intent.getStringExtra("EXTRA_CONTRASENA")?.takeIf { it != "[Contraseña]" } ?: ""
        val telefonoInicial = intent.getStringExtra("EXTRA_TELEFONO")?.takeIf { it != "[Teléfono]" } ?: ""
        val correoInicial = intent.getStringExtra("EXTRA_CORREO")?.takeIf { it != "[Correo Electrónico]" } ?: ""
        val direccionInicial = intent.getStringExtra("EXTRA_DIRECCION")?.takeIf { it != "[Dirección]" } ?: ""
        val fechaInicial = intent.getStringExtra("EXTRA_FECHA")?.takeIf { it != "[Fecha de Nacimiento]" } ?: ""

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Formulario(
                        nombreInicial = nombreInicial,
                        apellidosInicial = apellidosInicial,
                        contraseñaInicial = contrasenaInicial,
                        telefonoInicial = telefonoInicial,
                        correoInicial = correoInicial,
                        direccionInicial = direccionInicial,
                        fechaInicial = fechaInicial)
                }
            }
        }
    }
}
@Composable
fun Formulario(
    nombreInicial: String = "",
    apellidosInicial: String = "",
    contraseñaInicial: String = "",
    telefonoInicial: String = "",
    correoInicial: String = "",
    direccionInicial: String = "",
    fechaInicial: String = ""
) {

    val activity = LocalActivity.current

//El by se usa para acceder al valor sin tener que usar el .value ttodo el tiempo
    // El remember lo usamos para evitar que se reinicie la caja cada vez que escribimos en el
    var name by remember { mutableStateOf(nombreInicial) } //El mutable lo usamos para que la variable puedo cambiar sin problema
    var apellidos by remember { mutableStateOf(apellidosInicial) }
    var contraseña by remember { mutableStateOf(contraseñaInicial) }
    var telefono by remember { mutableStateOf(telefonoInicial) }
    var correoElectronico by remember { mutableStateOf(correoInicial) }
    var direccion by remember { mutableStateOf(direccionInicial) }
    var fechaNacimiento by remember { mutableStateOf(fechaInicial) }

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Campos de texto
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            placeholder = { Text("Ej. Ivan") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            placeholder = { Text("Ej. Morata ") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(), //Esta parte hace que el coidgo que escribamos se vea encriptado (en ****)
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.all { it.isDigit() }) {
                    telefono = nuevoTexto
                }
            },
            label = { Text("Teléfono") },
            placeholder = { Text("Ej. 612345678") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correoElectronico,
            onValueChange = { nuevoTexto ->
                correoElectronico = nuevoTexto.trim().lowercase()
            },
            label = { Text("Correo electrónico") },
            placeholder = { Text("ejemplo@correo.com") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            placeholder = { Text("Ej. Av. Principal 123, 4ºB") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.length <= 10 && nuevoTexto.all { it.isDigit() || it == '/' || it == '-' }) {
                    fechaNacimiento = nuevoTexto
                }
            },
            label = { Text("Fecha de nacimiento") },
            placeholder = { Text("DD/MM/AAAA") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val resultIntent = Intent().apply {  //Cambio lo que teniamos hecho porque se crea la actividad nueva sino lo cambiamos
                    putExtra("EXTRA_NOMBRE", name.ifEmpty { "[Nombre]" })
                    putExtra("EXTRA_APELLIDOS", apellidos.ifEmpty { "[Apellidos]" })
                    putExtra("EXTRA_CONTRASENA", contraseña.ifEmpty { "[Contraseña]" })
                    putExtra("EXTRA_TELEFONO", telefono.ifEmpty { "[Teléfono]" })
                    putExtra("EXTRA_CORREO", correoElectronico.ifEmpty { "[Correo Electrónico]" })
                    putExtra("EXTRA_DIRECCION", direccion.ifEmpty { "[Dirección]" })
                    putExtra("EXTRA_FECHA", fechaNacimiento.ifEmpty { "[Fecha de Nacimiento]" })
                }

                if (nombreInicial.isEmpty()) {
                    resultIntent.setClass(activity!!, PerfilActivity::class.java)
                    activity.startActivity(resultIntent)
                    activity.finish()
                } else {
                    activity?.setResult(RESULT_OK, resultIntent)
                    activity?.finish()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Guardar y Ver Perfil")
        }
    }
}
