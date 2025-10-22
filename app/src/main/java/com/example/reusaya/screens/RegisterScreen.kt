package com.example.reusaya.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reusaya.components.MaterialDesign3TextField
import com.example.reusaya.data.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    // Estados para los campos del formulario
    var nombreUsuario by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var confirmarContrasena by rememberSaveable { mutableStateOf("") }
    var recordarContrasena by rememberSaveable { mutableStateOf(false) }

    // Estados para el manejo de errores
    var errores by remember { mutableStateOf(emptyMap<String, String>()) }
    var estaEnviando by remember { mutableStateOf(false) }

    // Función para validar el formulario
    fun validarFormulario(): Boolean {
        val nuevosErrores = mutableMapOf<String, String>()
        if (nombreUsuario.isBlank()) nuevosErrores["nombreUsuario"] = "El nombre de usuario es obligatorio"
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) nuevosErrores["correo"] = "El correo no es válido"
        if (contrasena.length < 6) nuevosErrores["contrasena"] = "La contraseña debe tener al menos 6 caracteres"
        if (contrasena != confirmarContrasena) nuevosErrores["confirmarContrasena"] = "Las contraseñas no coinciden"
        errores = nuevosErrores
        return nuevosErrores.isEmpty()
    }

    // Función para manejar el registro
    val manejarRegistro = {
        if (validarFormulario()) {
            estaEnviando = true
            // Simulación de una llamada de red
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
            estaEnviando = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registrarse") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Text("RY", fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text("ReusaYa", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(48.dp))

            // Campo Nombre de usuario
            MaterialDesign3TextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it; errores = errores - "nombreUsuario" },
                label = "Nombre de usuario",
                isError = errores.containsKey("nombreUsuario"),
                errorMessage = errores["nombreUsuario"] ?: ""
            )
            Spacer(Modifier.height(16.dp))

            // Campo Correo
            MaterialDesign3TextField(
                value = correo,
                onValueChange = { correo = it; errores = errores - "correo" },
                label = "Correo",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = errores.containsKey("correo"),
                errorMessage = errores["correo"] ?: ""
            )
            Spacer(Modifier.height(16.dp))

            // Campo Contraseña
            MaterialDesign3TextField(
                value = contrasena,
                onValueChange = { contrasena = it; errores = errores - "contrasena" },
                label = "Contraseña",
                visualTransformation = PasswordVisualTransformation(),
                isError = errores.containsKey("contrasena"),
                errorMessage = errores["contrasena"] ?: ""
            )
            Spacer(Modifier.height(16.dp))

            // Campo Confirmar Contraseña
            MaterialDesign3TextField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it; errores = errores - "confirmarContrasena" },
                label = "Confirmar Contraseña",
                visualTransformation = PasswordVisualTransformation(),
                isError = errores.containsKey("confirmarContrasena"),
                errorMessage = errores["confirmarContrasena"] ?: ""
            )
            Spacer(Modifier.height(16.dp))

            // Checkbox "Recordar Contraseña"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = recordarContrasena, onCheckedChange = { recordarContrasena = it })
                Text("Recordar Contraseña")
            }
            Spacer(Modifier.height(24.dp))

            // Botón REGISTRARSE
            Button(
                onClick = manejarRegistro,
                enabled = !estaEnviando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (estaEnviando) "REGISTRANDO..." else "REGISTRARSE")
            }
            Spacer(Modifier.height(24.dp))

            // Enlace inferior
            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text("¿Ya tienes una cuenta? Iniciar sesión")
            }
        }
    }
}