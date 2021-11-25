package com.ugmex.predial


import java.time.LocalDate

data class Pago(val fecha: LocalDate,
                val predios:List<Predio>) {
    fun importetotal():Double{
        var total=predios.sumByDouble{it.calcularImporte()}
        // it =iterador significa un recorrido automatico de cada elmento de la
        // lista 
        val persona=predios.get(0).persona
        if (persona.edad >= 70 || persona.esmadresoltera)
            total=if (fecha.monthValue<=2) total*0.30
                else total*0.50
        else if (fecha.monthValue<=2)
            total=total*0.60;
        return(total)
    }
}
