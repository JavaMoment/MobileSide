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
import com.mobile.pft.RetrofitClient
import com.mobile.pft.login.ApiService
import com.mobile.pft.model.Evento
import com.mobile.pft.model.NewClaimDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewClaimActivity() : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

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
        val eventos = apiService.getEventos()

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        // val username = sharedPreferences.getString("userLogged", null)
        val username = "lucas.gomez"


        eventos.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(eventos: Call<List<Evento>>, response: Response<List<Evento>>){
                if(response.isSuccessful){
                    val eventos = response.body()
                    eventos?.let{
                        val adapterEventos = ArrayAdapter(this@NewClaimActivity, android.R.layout.simple_spinner_dropdown_item, eventos)
                        spinnerEventos.adapter = adapterEventos

                    }
                }else {
                    Log.e("ClaimsActivity","Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
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
            val eventoSelect = spinnerEventos.selectedItem as Evento

            textTitle.text.clear()
            textDesciption.text.clear()
            numberCredits.text.clear()




            /* val claim = apiService.postClaims(username, NewClaimDTO(titulo, descripcion, eventoSelect, selectedSemestre, creditos))

             claim.enqueue(object  : Callback<NewClaimDTO>{
                 override fun onResponse(call: Call<NewClaimDTO>, response: Response<NewClaimDTO>) {
                     if(response.isSuccessful){
                         Toast.makeText(this@NewClaimActivity, "Success", Toast.LENGTH_SHORT).show()

                     } else {
                         Toast.makeText(this@NewClaimActivity, "Error", Toast.LENGTH_SHORT).show()

                     }
                 }
                 override fun onFailure(call: Call<NewClaimDTO>, t: Throwable) {
                     // Handle the failure
                     Toast.makeText(this@NewClaimActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                 }
             })*/



            Toast.makeText(this, "Reclamo creado con exito", Toast.LENGTH_LONG).show()




        }





    }



}
















