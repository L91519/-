package com.example.user.pretendingnews.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import edu.cmu.pocketsphinx.RecognitionListener
import edu.cmu.pocketsphinx.SpeechRecognizer
import edu.cmu.pocketsphinx.Assets
import edu.cmu.pocketsphinx.SpeechRecognizerSetup
import java.io.File
import java.io.IOException
import android.widget.Toast
import com.example.user.pretendingnews.TalkActivity
import edu.cmu.pocketsphinx.Hypothesis

/**
 * Created by dsa28s on 05/11/2017.
 */

class BackgroundSpeechRecognitionService: Service(), RecognitionListener {
    private val KWS_SEARCH = "play play trump"
    private val KEYPHRASE = "wakeup trump"

    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        runRecognizerSetup()

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()

        if (speechRecognizer != null) {
            speechRecognizer.cancel()
            speechRecognizer.shutdown()
        }
    }

    override fun onPartialResult(hypothesis: Hypothesis?) {
        if (hypothesis == null)
            return

        val text = hypothesis.hypstr
        if (text.contains(KEYPHRASE)) {
            val talkActivity = Intent(this@BackgroundSpeechRecognitionService, TalkActivity::class.java)
            talkActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            talkActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(talkActivity)
            //Toast.makeText(this, "onPartialResult text=" + text, Toast.LENGTH_SHORT).show()
            switchSearch(KWS_SEARCH)
        }
    }

    override fun onResult(hypothesis: Hypothesis?) {
        if (hypothesis != null) {
            val text = hypothesis.hypstr
            Log.i("TEST", "onResult text=" + text)
        }
    }

    override fun onBeginningOfSpeech() {
        Log.i("TEST", "onBeginningOfSpeech")
    }

    override fun onEndOfSpeech() {
        if (!speechRecognizer.getSearchName().contains(KWS_SEARCH))
            switchSearch(KWS_SEARCH)
        Log.i("TEST", "onEndOfSpeech")
    }

    private fun switchSearch(searchName: String) {
        Log.i("TEST", "switchSearch searchName = " + searchName)
        speechRecognizer.stop()

        speechRecognizer.startListening(searchName, 10000)
    }

    @SuppressLint("StaticFieldLeak")
    private fun runRecognizerSetup() {
        object : AsyncTask<Void, Void, Exception>() {
            override fun doInBackground(vararg params: Void): Exception? {
                try {
                    val assets = Assets(this@BackgroundSpeechRecognitionService)
                    val assetDir = assets.syncAssets()
                    setupRecognizer(assetDir)
                } catch (e: IOException) {
                    return e
                }

                return null
            }

            override fun onPostExecute(result: Exception?) {
                if (result != null) {
                    result!!.printStackTrace()
                } else {
                    Log.e("TEST", "asdfsdf")
                    switchSearch(KWS_SEARCH)
                }
            }
        }.execute()
    }

    @Throws(IOException::class)
    private fun setupRecognizer(assetsDir: File) {
        speechRecognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(File(assetsDir, "en-us-ptm"))
                .setDictionary(File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir)
                .setKeywordThreshold(1e-45f)
                .setBoolean("-allphone_ci", true)


                .recognizer
        speechRecognizer.addListener(this)

        speechRecognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE)
    }

    override fun onError(error: Exception) {
        Log.i("TEST", "onError " + error.message)
    }

    override fun onTimeout() {
        switchSearch(KWS_SEARCH)
        Log.i("TEST", "onTimeout")
    }
}