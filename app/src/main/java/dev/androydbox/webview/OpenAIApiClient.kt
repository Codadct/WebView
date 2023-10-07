package dev.androydbox.webview

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class OpenAIApiClient(private val apiKey: String) {

    suspend fun generateText(prompt: String): String {
        return withContext(Dispatchers.IO) {
            makeApiCall(prompt)

        }
    }

    private fun makeApiCall(prompt: String): String {
        val url = URL("https://api.openai.com/v1/chat/completions")
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.connectTimeout=4000
            connection.setRequestProperty("Content-Type", "application/json")

            connection.setRequestProperty("Authorization", "Bearer $apiKey")


            connection.doOutput = true
            val postData = """
    {
        "model": "gpt-3.5-turbo",
        "messages": [
  {"role": "user", "content": "Tell me a joke."},
  {"role": "assistant", "content": "Why did the chicken cross the road?"},
  {"role": "user", "content": "I don't know, why did the chicken cross the road?"}
],
        "temperature": 0,
         "max_tokens": 256
    }
"""

            val outputStream: OutputStream = connection.outputStream
            outputStream.write(postData.toByteArray(charset("UTF-8")))
            outputStream.close()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                Log.e("API",response.toString())
                val jsonObject=JSONObject(response.toString())
                val Output=jsonObject
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")

                return Output.toString()
            } else {
                throw Exception("HTTP Error: ${connection.responseCode}")
                Log.e("API","HTTP Error")
            }

        }
        catch (e:Exception){
            Log.e("caught API", "Error: ${e.message}", e)
            return e.message.toString()
        }
        finally {
            connection.disconnect()
        }
    }
}
