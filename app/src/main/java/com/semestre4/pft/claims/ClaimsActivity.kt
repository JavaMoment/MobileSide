package com.semestre4.pft.claims

import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.semestre4.pft.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.semestre4.pft.RetrofitClient
import com.semestre4.pft.login.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClaimsActivity() : AppCompatActivity() {

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addclaim)

        val retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/WebSide/api/v1/")
        val apiService = retrofit.create(ApiService::class.java)


        val spinnerSemestre: Spinner = findViewById(R.id.semestre)
        val spinnerEventos: Spinner = findViewById(R.id.eventosdb)
        val semestres = resources.getStringArray(R.array.semestre)
        val adapterSemestre = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, semestres)

        val textTitle: EditText = findViewById(R.id.title)
        val textDesciption: EditText = findViewById(R.id.descripcion)
        val numberCredits: EditText = findViewById(R.id.creditos)
        val submit: Button = findViewById(R.id.addclaim)
        val eventos = apiService.getEvents()

        eventos.enqueue(object : Callback<List<EventoDTO>> {
            override fun onResponse(eventos: Call<List<EventoDTO>>, response: Response<List<EventoDTO>>){
                if(response.isSuccessful){
                    val eventos = response.body()
                    eventos?.let{
                        val adapterEventos = ArrayAdapter(this@ClaimsActivity, android.R.layout.simple_spinner_dropdown_item, eventos)
                        spinnerEventos.adapter = adapterEventos

                    }
                }else {
                     Log.e("ClaimsActivity","Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<EventoDTO>>, t: Throwable) {
                Log.e("ClaimsActivity","Network request failed: ${t.message}")
            }

        })


        adapterSemestre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSemestre.adapter = adapterSemestre

        submit.setOnClickListener(){
            val titulo = textTitle.text.toString()
            val descripcion = textDesciption.text.toString()
            val selectedSemestre = spinnerSemestre.selectedItem.toString().toInt()
            val creditos = numberCredits.text.toString().toInt()
            val eventoSelect = spinnerEventos.selectedItem as EventoDTO
            val selectedID = eventoSelect.tiposEvento.nombre


            val claim = apiService.postClaims(ClaimDTO(titulo, descripcion, eventoSelect, selectedSemestre, creditos))

            claim.enqueue(object  : Callback<ClaimDTO>{
                override fun onResponse(call: Call<ClaimDTO>, response: Response<ClaimDTO>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@ClaimsActivity, "Success", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this@ClaimsActivity, "Error", Toast.LENGTH_SHORT).show()

                    }
                }
                override fun onFailure(call: Call<ClaimDTO>, t: Throwable) {
                    // Handle the failure
                    Toast.makeText(this@ClaimsActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

            Toast.makeText(this, "ID $selectedID", Toast.LENGTH_LONG).show()




        }





    }


}
















