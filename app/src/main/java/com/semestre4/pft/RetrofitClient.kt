package com.semestre4.pft

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// define un objeto singleton para crear y gestionar la instancia de retrofit
object RetrofitClient {
    // variable para almacenar la instancia de retrofit
    private var retrofit: Retrofit? = null

    // función para obtener la instancia de retrofit
    fun getClient(baseUrl: String): Retrofit {
        // si la instancia de retrofit es nula, la crea
        if (retrofit == null) {
            // crea un interceptor para loguear las solicitudes y respuestas HTTP
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            // crea un cliente HTTP y le añade el interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            // configura gson para que sea leniente con los formatos de json
            val gson = GsonBuilder()
                .setLenient()
                .create()

            // construye la instancia de retrofit con la url base, el convertidor gson y el cliente HTTP
            retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/WebSide/api/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }
        // devuelve la instancia de retrofit q no puede ser nula
        return retrofit!!
    }
}
