package com.jys.catsapp.presentation.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

@Composable
fun AnimatedBottomBar() {
    val items = listOf(BottomBarItem.Home, BottomBarItem.Favorites, BottomBarItem.Settings)
    var selectedItem by remember { mutableStateOf(BottomBarItem.Home) }
    val selectedIndex = items.indexOf(selectedItem)

    // Recordar el tamaño del contenedor para cálculos posteriores
    var boxWidth by remember { mutableStateOf(0f) }

    // Contenedor principal
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
            .onSizeChanged { size ->
                boxWidth = size.width.toFloat()
            }
    ) {
        // Posiciones de los íconos
        val iconPositions = items.mapIndexed { index, _ ->
            boxWidth / items.size * (index + 0.5f)
        }

        // Animación de desplazamiento horizontal de la esfera
        val transition = updateTransition(targetState = selectedIndex, label = "Icon Transition")
        val sphereXOffset by transition.animateFloat(
            label = "Sphere X Offset",
            transitionSpec = { tween(durationMillis = 500, easing = FastOutSlowInEasing) }
        ) { index ->
            if (boxWidth > 0) iconPositions[index] else 0f
        }

        // Animación de escala para el efecto de emergente y retracción
        val sphereScale by transition.animateFloat(
            label = "Sphere Scale",
            transitionSpec = { tween(durationMillis = 500, easing = FastOutSlowInEasing) }
        ) { index ->
            if (index == selectedIndex) 1f else 0.8f
        }

        // Dibujar la esfera animada
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sphereRadius = 30.dp.toPx()
            val sphereCenterY = size.height - sphereRadius - 10.dp.toPx()

            // Aplicar RenderEffect para el desenfoque
            drawContext.canvas.saveLayer(
                Rect(0f, 0f, size.width, size.height),
                Paint().apply {
                    this.asFrameworkPaint().setMaskFilter(
                        BlurMaskFilter(15f, BlurMaskFilter.Blur.NORMAL)
                    )
                }
            )

            // Dibujar la esfera
            drawCircle(
                color = Color.Blue,
                radius = sphereRadius * sphereScale,
                center = Offset(sphereXOffset, sphereCenterY)
            )

            drawContext.canvas.restore()
        }

        // Íconos de navegación
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = if (isSelected) 20.dp else 10.dp)
                        .clickable {
                            selectedItem = item
                        }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.size(if (isSelected) 30.dp else 24.dp),
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                }
            }
        }
    }
}
enum class BottomBarItem2(val icon: ImageVector) {
    Home(Icons.Default.Home),
    Favorites(Icons.Default.Favorite),
    Settings(Icons.Default.Settings)
}


enum class BottomBarItem(val icon: ImageVector) {
    Home(Icons.Default.Home),
    Favorites(Icons.Default.Favorite),
    Settings(Icons.Default.Settings)
}

@Composable
fun LiquidBottomBar() {
    val items = listOf(BottomBarItem.Home, BottomBarItem.Favorites, BottomBarItem.Settings)
    var selectedItem by remember { mutableStateOf(BottomBarItem.Home) }
    val selectedIndex = items.indexOf(selectedItem)

    val circleRadius = with(LocalDensity.current) { 30.dp.toPx() }

    // Animación de selección
    val transition = updateTransition(targetState = selectedIndex, label = "Icon Transition")

    // Animaciones de escala para cada ícono
    val scales = items.mapIndexed { index, _ ->
        transition.animateFloat(
            transitionSpec = { tween(durationMillis = 500, easing = FastOutSlowInEasing) },
            label = "Scale $index"
        ) { state ->
            if (state == index) 1f else 0.8f
        }
    }

    // Contenedor principal
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
         val cont = LocalDensity.current
        // Canvas para dibujar las esferas y el efecto líquido
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Posiciones centrales de los íconos
            val iconCenters = items.mapIndexed { index, _ ->
                Offset(
                    x = width / items.size * (index + 0.5f),
                    y = height - circleRadius - with(cont) { 10.dp.toPx() }
                )
            }

            // Dibujar las esferas y el efecto líquido
            for (i in items.indices) {
                val scale = scales[i].value
                val radius = circleRadius * scale
                val center = iconCenters[i]

                // Dibujar la esfera
                drawCircle(
                    color = Color.Cyan,
                    radius = radius,
                    center = center
                )

                // Si es el ícono seleccionado, conectar con efecto líquido
                if (i == selectedIndex) {
                    drawMetaball(
                        circle1 = center,
                        radius1 = radius,
                        circle2 = Offset(center.x, height + radius),
                        radius2 = radius * 0.1f,
                        color = Color.Blue
                    )
                }
            }
        }

        // Íconos de navegación
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                val iconColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                val iconSize = if (isSelected) 30.dp else 24.dp
                val paddingBottom = if (isSelected) 20.dp else 10.dp

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = paddingBottom)
                        .clickable {
                            selectedItem = item
                        }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        modifier = Modifier.size(iconSize),
                        tint = iconColor
                    )
                }
            }
        }
    }
}

fun DrawScope.drawMetaball(
    circle1: Offset,
    radius1: Float,
    circle2: Offset,
    radius2: Float,
    handleLenRate: Float = 2.0f,
    maxDistance: Float = 70f,
    color: Color
) {
    val distance = hypot(circle2.x - circle1.x, circle2.y - circle1.y)
    if (distance > maxDistance || distance == 0f) return

    val scale = 1 - (distance / maxDistance)
    val radiusDiff = radius1 - radius2

    val angle1 = acos((radiusDiff / distance).coerceIn(-1f, 1f))
    val angle2 = -angle1

    val sinAngle1 = sin(angle1)
    val cosAngle1 = cos(angle1)
    val sinAngle2 = sin(angle2)
    val cosAngle2 = cos(angle2)

    val p1 = Offset(
        x = circle1.x + radius1 * cosAngle1,
        y = circle1.y + radius1 * sinAngle1
    )
    val p2 = Offset(
        x = circle2.x + radius2 * cosAngle1,
        y = circle2.y + radius2 * sinAngle1
    )
    val p3 = Offset(
        x = circle2.x + radius2 * cosAngle2,
        y = circle2.y + radius2 * sinAngle2
    )
    val p4 = Offset(
        x = circle1.x + radius1 * cosAngle2,
        y = circle1.y + radius1 * sinAngle2
    )

    val totalRadius = radius1 + radius2
    val d2 = distance - radiusDiff
    val handleLength = handleLenRate * totalRadius * scale

    val h1 = handleLength * (radius1 / totalRadius)
    val h2 = handleLength * (radius2 / totalRadius)

    val path = Path().apply {
        moveTo(p1.x, p1.y)
        cubicTo(
            p1.x + h1 * sinAngle1,
            p1.y - h1 * cosAngle1,
            p2.x + h2 * sinAngle1,
            p2.y - h2 * cosAngle1,
            p2.x,
            p2.y
        )
        lineTo(p3.x, p3.y)
        cubicTo(
            p3.x + h2 * sinAngle2,
            p3.y - h2 * cosAngle2,
            p4.x + h1 * sinAngle2,
            p4.y - h1 * cosAngle2,
            p4.x,
            p4.y
        )
        close()
    }

    drawPath(path = path, color = color)
}

@Composable
fun AnimatedCircularNavigationBar() {
    val items = listOf("Home", "Search", "Profile")
    var selectedIndex by remember { mutableStateOf(0) }
    val animatedOffset by animateDpAsState(targetValue = (selectedIndex * 80).dp)

    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1B1B1B)),
        contentAlignment = Alignment.Center
    ) {
        // Fondo recortado para crear la apariencia de esfera
        Canvas(
            modifier = Modifier
                .offset(x = animatedOffset)
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {}

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                IconButton(
                    onClick = { selectedIndex = index },
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent)
                ) {
                    Icon(
                        imageVector = when (item) {
                            "Home" -> Icons.Default.Home
                            "Search" -> Icons.Default.Search
                            "Profile" -> Icons.Default.Person
                            else -> Icons.Default.Home
                        },
                        contentDescription = item,
                        tint = if (selectedIndex == index) Color.Black else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedCircularMenu() {
    val circleColors = listOf(Color(0xFFE08A00), Color(0xFFB4D99B), Color(0xFFB4D99B), Color(0xFFE08A00))
    val animatedProgress = remember { Animatable(0f) }

    // Lanzar animación para mover las piezas hacia el centro
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1B1B)),
        contentAlignment = Alignment.Center
    ) {
        // Crear piezas del círculo con animación
        circleColors.forEachIndexed { index, color ->
            CircularPiece(
                index = index,
                color = color,
                progress = animatedProgress.value
            )
        }

        // Cuadrado central con botón
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2D2D2D))
                .border(2.dp, Color.White, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MENU",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* Acciones del botón central */ }
            )
        }
    }
}

@Composable
fun CircularPiece(index: Int, color: Color, progress: Float) {
    // Definir posiciones iniciales y finales de cada pieza
    val initialOffset = when (index) {
        0 -> Offset(-200f, -200f)
        1 -> Offset(200f, -200f)
        2 -> Offset(-200f, 200f)
        3 -> Offset(200f, 200f)
        else -> Offset(0f, 0f)
    }

    val finalOffset = Offset(0f, 0f)
    val animatedOffset = lerp(initialOffset, finalOffset, progress)

    Box(
        modifier = Modifier
            .size(120.dp)
            .offset(animatedOffset.x.dp, animatedOffset.y.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { /* Acción para cada pieza */ },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ITEM ${index + 1}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}
