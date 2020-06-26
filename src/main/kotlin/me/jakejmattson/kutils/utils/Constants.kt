package me.jakejmattson.kutils.utils

import javax.swing.ImageIcon

object IMPORTS {
    private const val apiBase = "import me.jakejmattson.kutils.api"

    const val dsl = "$apiBase.dsl"
    const val annotations = "$apiBase.annotations"
}

object ICONS {
    val KUTILS_16 = ImageIcon(javaClass.getResource("/icons/kutils_16.png"))
}