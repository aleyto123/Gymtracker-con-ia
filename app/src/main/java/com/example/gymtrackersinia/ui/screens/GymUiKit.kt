package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

val AppBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF101227),
        Color(0xFF182B5A),
        Color(0xFF20245A)
    )
)

val SunriseBrush = Brush.linearGradient(
    colors = listOf(Color(0xFFFF7A59), Color(0xFFFFD166), Color(0xFF59F8B6))
)

val BerryBrush = Brush.linearGradient(
    colors = listOf(Color(0xFFFF4FA3), Color(0xFF8A5CFF), Color(0xFF28D8FF))
)

val LimeBrush = Brush.linearGradient(
    colors = listOf(Color(0xFF59F8B6), Color(0xFF9DFF57), Color(0xFFFFD166))
)

@Composable
fun GymBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color(0x33FF4FA3), radius = size.width * 0.42f, center = Offset(size.width * 0.08f, size.height * 0.12f))
            drawCircle(Color(0x3359F8B6), radius = size.width * 0.36f, center = Offset(size.width * 0.96f, size.height * 0.30f))
            drawCircle(Color(0x26FFD166), radius = size.width * 0.48f, center = Offset(size.width * 0.42f, size.height * 0.96f))
        }
        content()
    }
}

@Composable
fun HeroPanel(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    artworkVariant: Int = 0,
    brush: Brush = BerryBrush
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(brush)
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black, color = Color.White)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.90f))
            }
            WorkoutArtwork(
                variant = artworkVariant,
                modifier = Modifier.size(118.dp)
            )
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    brush: Brush,
    artworkVariant: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .background(brush)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WorkoutArtwork(variant = artworkVariant, modifier = Modifier.size(76.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(subtitle, color = Color.White.copy(alpha = 0.88f), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun GlassPanel(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xCCFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun PrimaryAction(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4FA3), contentColor = Color.White),
        contentPadding = PaddingValues(horizontal = 18.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BrightTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFF101227),
            unfocusedTextColor = Color(0xFF101227),
            focusedLabelColor = Color(0xFF8A5CFF),
            unfocusedLabelColor = Color(0xFF4B5578),
            cursorColor = Color(0xFFFF4FA3),
            focusedBorderColor = Color(0xFFFF4FA3),
            unfocusedBorderColor = Color(0x334B5578),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun SoftTextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Text(text, color = Color(0xFFFFD166), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatTile(label: String, value: String, brush: Brush, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(brush)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(value, color = Color.White, fontWeight = FontWeight.Black, style = MaterialTheme.typography.headlineSmall)
            Text(label, color = Color.White.copy(alpha = 0.88f), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun EmptyState(title: String, subtitle: String, onAction: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        WorkoutArtwork(variant = 2, modifier = Modifier.size(150.dp))
        Text(title, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
        Text(subtitle, color = Color.White.copy(alpha = 0.82f), textAlign = TextAlign.Center)
        PrimaryAction("Crear primera rutina", onAction)
    }
}

@Composable
fun WorkoutArtwork(variant: Int, modifier: Modifier = Modifier) {
    val palette = when (variant % 3) {
        0 -> listOf(Color(0xFFFFD166), Color(0xFFFF4FA3), Color(0xFF2DE2E6))
        1 -> listOf(Color(0xFF59F8B6), Color(0xFF8A5CFF), Color(0xFFFF7A59))
        else -> listOf(Color(0xFF28D8FF), Color(0xFF9DFF57), Color(0xFFFFD166))
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.18f))
            .border(1.dp, Color.White.copy(alpha = 0.34f), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            drawCircle(Color.White.copy(alpha = 0.20f), radius = w * 0.42f, center = Offset(w * 0.70f, h * 0.24f))
            drawCircle(palette[0], radius = w * 0.10f, center = Offset(w * 0.50f, h * 0.25f))
            drawLine(palette[1], Offset(w * 0.50f, h * 0.36f), Offset(w * 0.50f, h * 0.62f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
            drawLine(palette[2], Offset(w * 0.28f, h * 0.45f), Offset(w * 0.72f, h * 0.45f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawLine(palette[0], Offset(w * 0.42f, h * 0.64f), Offset(w * 0.26f, h * 0.86f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawLine(palette[0], Offset(w * 0.58f, h * 0.64f), Offset(w * 0.76f, h * 0.86f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawRoundRect(
                color = Color.White.copy(alpha = 0.86f),
                topLeft = Offset(w * 0.13f, h * 0.38f),
                size = Size(w * 0.16f, h * 0.14f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
            drawRoundRect(
                color = Color.White.copy(alpha = 0.86f),
                topLeft = Offset(w * 0.71f, h * 0.38f),
                size = Size(w * 0.16f, h * 0.14f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
            drawArc(
                color = Color.White.copy(alpha = 0.45f),
                startAngle = 200f,
                sweepAngle = 140f,
                useCenter = false,
                topLeft = Offset(w * 0.18f, h * 0.12f),
                size = Size(w * 0.64f, h * 0.64f),
                style = Stroke(width = w * 0.025f, cap = StrokeCap.Round)
            )
        }
    }
}

@Composable
fun SmallBadge(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.18f))
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Text(text, color = Color.White, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
}

@Composable
fun VerticalGap(height: Int = 12) {
    Spacer(modifier = Modifier.height(height.dp))
}
