package com.example.alcoolougasolina.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = ShellYellow,
  secondary = ShellRed,
  tertiary = Color(0xFF757575),
  background = Color(0xFF121212), // Fundo escuro padrão
  surface = Color(0xFF1E1E1E),
  onPrimary = Color.Black,        // Texto preto sobre fundo amarelo
  onSecondary = Color.White,      // Texto branco sobre fundo vermelho
  onTertiary = Color.White,
  onBackground = Color.White,
  onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
  // Amarelo Shell com 20% de opacidade para o fundo
  primary = ShellYellow.copy(alpha = 0.2f),
  // Vermelho Shell suave para botões e detalhes
  secondary = ShellRed.copy(alpha = 0.7f),
  tertiary = ShellLightGrey,
  // Fundo quase branco, levemente amarelado
  background = Color(0xFFFFFBE6),
  surface = Color.White,
  onPrimary = Color.Black,
  onSecondary = Color.White,
  onBackground = ShellDarkGrey,
  onSurface = ShellDarkGrey
)

@Composable
fun AlcoolOuGasolina(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}