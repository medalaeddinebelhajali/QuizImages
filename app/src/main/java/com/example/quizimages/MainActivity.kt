package com.example.quizimages

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Question(
    val texte: String,
    val image: Int,
    val choix: Array<String>,
    val bonneReponse: Int,
    val descriptionImage: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var txtScore: TextView
    private lateinit var txtProgress: TextView
    private lateinit var txtNumero: TextView
    private lateinit var txtQuestion: TextView
    private lateinit var txtFeedback: TextView
    private lateinit var txtResultat: TextView

    private lateinit var imgQuestion: ImageView
    private lateinit var progressQuiz: ProgressBar

    private lateinit var btnSuivant: Button
    private lateinit var btnRejouer: Button
    private lateinit var btnChoix: List<Button>
    private lateinit var layoutResultat: LinearLayout

    private var indiceQuestion = 0
    private var score = 0
    private var repondu = false
    private var partieTerminee = false
    private var nombreReponses = 0

    // Les cinq questions statiques du quiz
    private val questions = arrayOf(
        Question(
            "Quelle forme possède trois côtés ?",
            R.drawable.triangle,
            arrayOf("Cercle", "Triangle", "Carré"),
            1,
            "Un triangle"
        ),
        Question(
            "Quelle forme possède quatre côtés égaux ?",
            R.drawable.carre,
            arrayOf("Rectangle", "Carré", "Cercle"),
            1,
            "Un carré"
        ),
        Question(
            "Quelle forme est ronde et sans angle ?",
            R.drawable.cercle,
            arrayOf("Triangle", "Rectangle", "Cercle"),
            2,
            "Un cercle"
        ),
        Question(
            "Quelle forme ressemble à une étoile ?",
            R.drawable.etoile,
            arrayOf("Étoile", "Carré", "Triangle"),
            0,
            "Une étoile"
        ),
        Question(
            "Quelle forme possède quatre angles droits ?",
            R.drawable.rectangle,
            arrayOf("Cercle", "Triangle", "Rectangle"),
            2,
            "Un rectangle"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupération des composants XML
        txtScore = findViewById(R.id.txt_score)
        txtProgress = findViewById(R.id.txt_progress)
        txtNumero = findViewById(R.id.txt_numero)
        txtQuestion = findViewById(R.id.txt_question)
        txtFeedback = findViewById(R.id.txt_feedback)
        txtResultat = findViewById(R.id.txt_resultat)

        imgQuestion = findViewById(R.id.img_question)
        progressQuiz = findViewById(R.id.progress_quiz)

        btnSuivant = findViewById(R.id.btn_suivant)
        btnRejouer = findViewById(R.id.btn_rejouer)
        layoutResultat = findViewById(R.id.layout_resultat)

        btnChoix = listOf(
            findViewById(R.id.btn_choix1),
            findViewById(R.id.btn_choix2),
            findViewById(R.id.btn_choix3)
        )

        // Gestion des clics sur les trois réponses
        btnChoix.forEachIndexed { index, bouton ->
            bouton.setOnClickListener {
                verifierReponse(index)
            }
        }

        btnSuivant.setOnClickListener {
            if (indiceQuestion < questions.size - 1) {
                indiceQuestion++
                afficherQuestion()
            } else {
                afficherResultat()
            }
        }

        btnRejouer.setOnClickListener {
            recommencerQuiz()
        }

        afficherQuestion()
    }

    // Afficher la question courante
    private fun afficherQuestion() {
        val question = questions[indiceQuestion]

        txtNumero.text =
            "Question ${indiceQuestion + 1} sur ${questions.size}"

        txtQuestion.text = question.texte
        imgQuestion.setImageResource(question.image)
        imgQuestion.contentDescription = question.descriptionImage

        btnChoix.forEachIndexed { index, bouton ->
            bouton.text = question.choix[index]
            bouton.isEnabled = true
            bouton.visibility = View.VISIBLE
            bouton.setBackgroundColor(Color.rgb(255,128,0))
        }

        txtFeedback.text = ""
        txtFeedback.visibility = View.VISIBLE

        btnSuivant.visibility = View.GONE
        btnSuivant.isEnabled = false

        layoutResultat.visibility = View.GONE
        repondu = false
        partieTerminee = false

        actualiserBandeau()
    }

    // Vérifier la réponse sélectionnée
    private fun verifierReponse(indiceChoix: Int) {
        if (repondu || partieTerminee) return

        repondu = true
        nombreReponses++

        val question = questions[indiceQuestion]
        val estCorrecte = indiceChoix == question.bonneReponse

        if (estCorrecte) {
            score++

            txtFeedback.text = "Bonne réponse !"
            txtFeedback.setTextColor(Color.rgb(22, 163, 74))
        } else {
            txtFeedback.text =
                "Mauvaise réponse ! Réponse correcte : " +
                        question.choix[question.bonneReponse]

            txtFeedback.setTextColor(Color.rgb(220, 38, 38))
        }

        // Une question ne peut rapporter qu'un seul point
        btnChoix.forEachIndexed { index, bouton ->
            bouton.isEnabled = false

            if (index == question.bonneReponse) {
                bouton.setBackgroundColor(Color.rgb(22, 163, 74))
            } else if (index == indiceChoix) {
                bouton.setBackgroundColor(Color.rgb(220, 38, 38))
            }
        }

        progressQuiz.progress = nombreReponses
        txtProgress.text = "$nombreReponses réponse(s) sur 5"

        actualiserBandeau()

        btnSuivant.text =
            if (indiceQuestion == questions.size - 1) {
                "Voir le résultat"
            } else {
                "Question suivante"
            }

        btnSuivant.isEnabled = true
        btnSuivant.visibility = View.VISIBLE
    }

    // Actualiser le score et la progression
    private fun actualiserBandeau() {
        txtScore.text = "Score : $score/5"
        progressQuiz.progress = nombreReponses
        txtProgress.text = "$nombreReponses réponse(s) sur 5"
    }

    // Afficher le résultat final dans la même activité
    private fun afficherResultat() {
        partieTerminee = true

        txtResultat.text =
            "Quiz terminé !\nVotre score : $score/5"

        layoutResultat.visibility = View.VISIBLE
        btnSuivant.visibility = View.GONE

        txtNumero.visibility = View.GONE
        imgQuestion.visibility = View.GONE
        txtQuestion.visibility = View.GONE
        txtFeedback.visibility = View.GONE

        btnChoix.forEach { it.visibility = View.GONE }
    }

    // Recommencer une nouvelle partie
    private fun recommencerQuiz() {
        indiceQuestion = 0
        score = 0
        nombreReponses = 0
        repondu = false
        partieTerminee = false

        txtNumero.visibility = View.VISIBLE
        imgQuestion.visibility = View.VISIBLE
        txtQuestion.visibility = View.VISIBLE
        txtFeedback.visibility = View.VISIBLE

        btnChoix.forEach { bouton ->
            bouton.visibility = View.VISIBLE
            bouton.isEnabled = true
            bouton.setBackgroundColor(
                Color.rgb(255, 128, 0)
            )
        }

        btnSuivant.visibility = View.GONE
        layoutResultat.visibility = View.GONE

        afficherQuestion()
    }
}
