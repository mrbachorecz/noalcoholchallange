import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

val hexagonShape = RoundedHexagonShape(4.dp)

class RoundedHexagonShape(private val cornerRadius: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawRoundedHexagonPath(size, cornerRadius.value * density.density)
        )
    }
}

fun drawRoundedHexagonPath(size: Size, cornerRadiusPx: Float): Path {
    val path = Path()
    val radius = min(size.width, size.height) / 2f
    val centerX = size.width / 2f
    val centerY = size.height / 2f

    // Ensure cornerRadiusPx is not too large
    val effectiveCornerRadius = min(cornerRadiusPx, radius / 2f) // Heuristic to avoid overly large radii

    val points = Array(6) { Offset(0f, 0f) }
    val angleStep = (2 * PI / 6).toFloat() // 60 degrees in radians

    for (i in 0..5) {
        val angle = i * angleStep
        points[i] = Offset(
            centerX + radius * cos(angle),
            centerY + radius * sin(angle)
        )
    }

    // Move to the start of the first rounded corner
    // We need to calculate points on the line segment before the vertex for the arc

    for (i in 0..5) {
        val p1 = points[i]
        val p2 = points[(i + 1) % 6] // Next vertex
        val p0 = points[(i + 5) % 6] // Previous vertex

        // Vector from p1 to p0
        val vec10 = p0 - p1
        val vec10Normalized = vec10 / vec10.getDistance()

        // Vector from p1 to p2
        val vec12 = p2 - p1
        val vec12Normalized = vec12 / vec12.getDistance()

        // Point on segment p1-p0 where the arc starts/ends
        val arcP1 = p1 + vec10Normalized * effectiveCornerRadius
        // Point on segment p1-p2 where the arc starts/ends
        val arcP2 = p1 + vec12Normalized * effectiveCornerRadius

        if (i == 0) {
            path.moveTo(arcP1.x, arcP1.y)
        } else {
            path.lineTo(arcP1.x, arcP1.y)
        }
        path.quadraticTo(p1.x, p1.y, arcP2.x, arcP2.y)
    }
    path.close()
    return path
}
