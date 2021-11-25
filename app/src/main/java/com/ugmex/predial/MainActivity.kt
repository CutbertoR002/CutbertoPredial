package com.ugmex.predial

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.io.BufferedReader
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    var predios=ArrayList<Predio>()
    var sexo:String=""
    var folio:Long=1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //abrir el archivo zonas
        val js = resources.openRawResource(R.raw.zonas)

        var jsonText = ""
        try {
            jsonText = js.bufferedReader().use(BufferedReader::readText)
        } finally {
            js.close()
        }


        val gson = Gson()
        //convertir rl archivo gson o lista
        val tipoZona = object : TypeToken<List<Zona>>() {}.type
        val zonas = gson.fromJson<List<Zona>>(jsonText, tipoZona)
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_dropdown_item_1line, zonas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spZonas.setSelection(0, true)
        spZonas.setAdapter(adapter)


        botonCalcularElPredio.setEnabled(false)
        botonAgregarElPredio.setOnClickListener({
            var persona = Persona(
                nombre = etNombre.text.toString(),
                edad = etEdad.text.toString().toInt(),
                sexo = sexo,
                esmadresoltera = comprobarMadreSoltera.isChecked
            )
            var zona: Zona = spZonas.selectedItem as Zona
            var predio = Predio(persona = persona,
                zona = zona,
                extension = etExtensionPredio.text.toString().toDouble())
            predios.add(predio)
            toast("Agregando Predio.")
            etExtensionPredio.setText("0")
            etExtensionPredio.requestFocus()
            botonCalcularElPredio.setEnabled(true)
        })



        botonCalcularElPredio.setOnClickListener({
            val fecha = LocalDate.now()
            var pago = Pago(fecha = fecha,
                predios = predios)
            escribirPagoTotalPantalla.text = "Nombre: " + etNombre.text
            escribirPagoTotalPantalla2.text = "Pago Total: " + pago.importetotal()
            escribirPagoTotalPantalla3.text = "Folio:"+folio
            folio++
            predios.clear()
        })


        rgSexo.setOnCheckedChangeListener(
            object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                    comprobarMadreSoltera.setEnabled(checkedId == R.id.opcSexoF)
                    sexo = when (checkedId) {
                        R.id.opcSexoM -> "M"
                        R.id.opcSexoF -> "F"
                        else -> ""
                    }
                }
            })

        }
}