package com.mobile.pft.reclamos.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.mobile.pft.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.mobile.pft.data.ApiContainer
import com.mobile.pft.model.EventoDTO
import com.mobile.pft.model.ReclamoCreateDTO
import com.mobile.pft.reclamos.data.ReclamosRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddClaimActivity() : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var apiContainer: ApiContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addclaim)

        apiContainer = ApiContainer()
        val reclamosRepository: ReclamosRepository = apiContainer.reclamosRepository

        val spinnerSemestre: Spinner = findViewById(R.id.semestre)
        val spinnerEventos: Spinner = findViewById(R.id.eventosdb)
        val semestres = resources.getStringArray(R.array.semestre)
        val adapterSemestre = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, semestres)

        val textTitle: EditText = findViewById(R.id.title)
        val textDesciption: EditText = findViewById(R.id.descripcion)
        val numberCredits: EditText = findViewById(R.id.creditos)
        val submit: Button = findViewById(R.id.addclaim)
        val toolbar: MaterialToolbar = findViewById(R.id.materialToolbar)

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("userLogged", null)?:""

        runBlocking {
            launch {
                Log.i("AddReclamoView","Consultando api por los eventos disponibles.")
                val eventos = reclamosRepository.getEventos()
                Log.i("AddReclamoView","Eventos disponibles: $eventos")
                val adapterEventos = ArrayAdapter(this@AddClaimActivity, android.R.layout.simple_spinner_dropdown_item, eventos)
                spinnerEventos.adapter = adapterEventos
            }
        }

        adapterSemestre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSemestre.adapter = adapterSemestre

        submit.setOnClickListener {
            val titulo = textTitle.text.toString().trim()  // Asegúrate de eliminar espacios innecesarios

            // Verifica que el título no esté vacío
            if (titulo.isBlank()) {
                // Muestra un Toast informando que el título es obligatorio
                Toast.makeText(applicationContext, "El título del reclamo es obligatorio.", Toast.LENGTH_LONG).show()
                textTitle.requestFocus()  // Pone el foco en el campo del título para que el usuario pueda corregirlo fácilmente
                return@setOnClickListener  // Detiene la ejecución adicional del código en este listener
            }

            val descripcion = textDesciption.text.toString().trim()  // Asegúrate de eliminar espacios innecesarios

            // Verifica que la descripción no esté vacía
            if (descripcion.isBlank()) {
                // Muestra un Toast informando que la descripción es obligatoria
                Toast.makeText(applicationContext, "La descripción del reclamo es obligatoria.", Toast.LENGTH_LONG).show()
                textDesciption.requestFocus()  // Pone el foco en el campo de la descripción para que el usuario pueda corregirlo fácilmente
                return@setOnClickListener  // Detiene la ejecución adicional del código en este listener
            }

            val selectedSemestre = spinnerSemestre.selectedItem.toString().toInt()
            val creditos = numberCredits.text.toString().toInt()
            val eventoSelected = spinnerEventos.selectedItem as EventoDTO?

            // Verifica que un evento haya sido seleccionado
            if (eventoSelected == null) {
                // Muestra un Toast informando que la selección de un evento es obligatoria
                Toast.makeText(applicationContext, "Seleccionar un evento es obligatorio.", Toast.LENGTH_LONG).show()
                spinnerEventos.requestFocus()  // Pone el foco en el spinner de eventos para que el usuario pueda corregirlo fácilmente
                return@setOnClickListener  // Detiene la ejecución adicional del código en este listener
            }

            // Limpia los campos de texto después de guardar los valores
            textTitle.text.clear()
            textDesciption.text.clear()
            numberCredits.text.clear()

            runBlocking {
                launch {
                    Log.d("AddReclamoView", "Añadiendo un nuevo reclamo...")
                    val reclamo = reclamosRepository.createReclamo(ReclamoCreateDTO(username, eventoSelected, selectedSemestre, creditos, titulo, descripcion))  // Crea el reclamo con los datos recogidos
                    Log.i("AddReclamoView", "Reclamo guardado exitosamente")
                    Log.d("AddReclamoView", "Nuevo reclamo: $reclamo")
                }
            }
            Toast.makeText(applicationContext, "Reclamo creado con éxito.", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnClickListener {
            finish()
        }
    }
}