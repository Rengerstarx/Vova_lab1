package com.example.semenov_lab_1

import android.animation.ArgbEvaluator
import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация музыки
        mediaPlayer = MediaPlayer.create(this, R.raw.rington)
        mediaPlayer.start()

        // Получение масссива из ресурсов
        val EmailArray = resources.getStringArray(R.array.Emails)
        val PasswordArray = resources.getStringArray(R.array.Passwords)

        // Присваиваем переменным обьекты из разметки
        val email = findViewById<EditText>(R.id.textEmail)
        val password = findViewById<EditText>(R.id.textPassword)
        val button = findViewById<Button>(R.id.buttonExit)

        // Слушатель для кнопки (обработка клика)
        button.setOnClickListener {
            // полученние введенной инфы
            val nowEmail = email.text.toString()
            val nowPassword = password.text.toString()

            // маркер для проверки на правильность
            var marker = true

            // проверка
            if (EmailArray.contains(nowEmail)) {
                // получение индекса если правильный логин
                val index = EmailArray.indexOf(nowEmail)
                // проверка пароля
                if (PasswordArray[index] == nowPassword) {
                    marker = false
                    // переход если все ок
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                }
            }

            // обработка если ошибка (текст подкраситсья и вибрация будет)
            if (marker) {
                password.setTextColor(applicationContext.resources.getColor(android.R.color.holo_red_dark))
                email.setTextColor(applicationContext.resources.getColor(android.R.color.holo_red_dark))
                // вибрация
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(1000L)
            }
        }

        // возвращения цвета после изменения
        email.addTextChangedListener {
            email.setTextColor(applicationContext.resources.getColor(R.color.black))
            password.setTextColor(applicationContext.resources.getColor(R.color.black))
        }
        password.addTextChangedListener {
            email.setTextColor(applicationContext.resources.getColor(R.color.black))
            password.setTextColor(applicationContext.resources.getColor(R.color.black))
        }
    }

    // сохранения состояний при ребуте
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Pochta", findViewById<EditText>(R.id.textEmail).text.toString())
        outState.putString("Parol", findViewById<EditText>(R.id.textPassword).text.toString())
    }

    // отображения состояний при ребуте
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val Pochta = savedInstanceState.getString("Pochta")
        val Parol = savedInstanceState.getString("Parol")
        findViewById<EditText>(R.id.textEmail).setText(Pochta)
        findViewById<EditText>(R.id.textPassword).setText(Parol)
    }

    // конец музыки при выключении активити
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
