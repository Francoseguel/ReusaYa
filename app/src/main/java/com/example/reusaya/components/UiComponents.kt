package com.example.reusaya.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Componente TextField personalizado con estilo Material Design 3 (Outlined).
 * Se utiliza para asegurar la coherencia visual en todos los formularios.
 *
 * @param value Valor actual del campo de texto.
 * @param onValueChange Callback cuando el valor cambia.
 * @param label Etiqueta (placeholder) del campo.
 * @param keyboardOptions Opciones de teclado (e.g., tipo de teclado: Texto, Email, Número).
 * @param visualTransformation Transformación visual (e.g., ocultar para contraseñas).
 * @param isError Indica si el campo tiene un error de validación.
 * @param errorMessage Mensaje de error a mostrar bajo el campo.
 */
@Composable
fun MaterialDesign3TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    // Usamos OutlinedTextField que encaja mejor con el diseño Material 3
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        // Colores personalizados del TextField para el tema oscuro (dark mode)
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary, // Color de borde cuando está enfocado
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), // Color de borde normal
            errorBorderColor = MaterialTheme.colorScheme.error, // Color de borde con error
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.surface, // Fondo del campo enfocado
            unfocusedContainerColor = MaterialTheme.colorScheme.surface, // Fondo del campo normal
        ),
        modifier = modifier.fillMaxWidth()
    )

    // Mensaje de error
    if (isError && errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}