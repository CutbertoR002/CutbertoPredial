package com.ugmex.predial

data class Zona(val clave:String,
                val zona:String,
                val costo:Double){
    override fun toString()=this.zona
}
