package com.example.reusaya.screens

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
fun LoginScreen(navController: NavController) {
    // Estados para los campos del formulario
    var nombreUsuario by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var recordarContrasena by rememberSaveable { mutableStateOf(false) }

    // Estados para el manejo de errores
    var usuarioError by remember { mutableStateOf<String?>(null) }
    var contrasenaError by remember { mutableStateOf<String?>(null) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var estaEnviando by remember { mutableStateOf(false) }

    // Validación en tiempo real
    LaunchedEffect(nombreUsuario) {
        if (nombreUsuario.isEmpty()) {
            usuarioError = "El nombre de usuario es obligatorio."
        } else {
            usuarioError = null
        }
    }

    LaunchedEffect(contrasena) {
        if (contrasena.isEmpty()) {
            contrasenaError = "La contraseña es obligatoria."
        } else if (contrasena.length < 6) {
            contrasenaError = "La contraseña debe tener al menos 6 caracteres."
        } else {
            contrasenaError = null
        }
    }

    val esFormularioValido = nombreUsuario.isNotEmpty() && contrasena.length >= 6

    // Función para manejar el inicio de sesión
    val manejarInicioSesion = {
        if (esFormularioValido) {
            estaEnviando = true
            mensajeError = null
            // Simulación de una llamada de red
            // En una app real, aquí llamarías a tu ViewModel o Repositorio
            if (nombreUsuario == "test" && contrasena == "password123") {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            } else {
                mensajeError = "Credenciales inválidas."
            }
            estaEnviando = false
        } else {
            // Actualizar errores si los campos están vacíos al intentar iniciar sesión
            if (nombreUsuario.isEmpty()) usuarioError = "El nombre de usuario es obligatorio."
            if (contrasena.isEmpty()) contrasenaError = "La contraseña es obligatoria."
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registrarse") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
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
            Text(
                text = "RY",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "ReusaYa",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(48.dp))

            // Campo Nombre de usuario
            MaterialDesign3TextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = "Nombre de usuario",
                isError = usuarioError != null,
                errorMessage = usuarioError ?: "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.height(16.dp))

            // Campo Contraseña
            MaterialDesign3TextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = "Contraseña",
                visualTransformation = PasswordVisualTransformation(),
                isError = contrasenaError != null,
                errorMessage = contrasenaError ?: "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(Modifier.height(16.dp))

            // Checkbox "Recordar Contraseña"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = recordarContrasena,
                    onCheckedChange = { recordarContrasena = it }
                )
                Text("Recordar Contraseña")
            }
            Spacer(Modifier.height(24.dp))

            // Mensaje de Error
            if (mensajeError != null) {
                Text(
                    text = mensajeError!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botón INICIAR SESIÓN
            Button(
                onClick = manejarInicioSesion,
                enabled = !estaEnviando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (estaEnviando) "INICIANDO..." else "INICIAR SESIÓN")
            }
            Spacer(Modifier.height(24.dp))

            // Enlaces inferiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                    Text("Registrarse")
                }
                TextButton(onClick = { }) {
                    Text("Olvidé la contraseña")
                }
            }
        }
    }
}