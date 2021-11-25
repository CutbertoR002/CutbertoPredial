package com.ugmex.predial

data class Predio( val persona: Persona,
                   val extension: Double,
                    val zona: Zona) {
    fun calcularImporte(): Double {
        return(zona.costo*this.extension)
    }
}