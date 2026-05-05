package com.example.alcoolougasolina.data.model

import java.io.Serializable

/**
 * Representa as configurações de preferência do usuário no aplicativo.
 * @property carEfficiencyIs75 Define se o cálculo usa 75% (true) ou 70% (false) de eficiência.
 * @property lastSelectedLanguage Opcional: Para persistir o idioma escolhido.
 */
data class UserPreferences(
    val carEfficiencyIs75: Boolean = true,
    val lastSelectedLanguage: String = "pt"
) : Serializable {


    fun getEfficiencyRatio(): Double {
        return if (carEfficiencyIs75) 0.75 else 0.70
    }
}