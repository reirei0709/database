package app.itakura.reirei.database

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val realm:Realm = Realm.getDefaultInstance()

    companion object{
        private const val QUESTION_COUNT:Int = 10
    }

    var random1 : Random = Random
    var random2 : Random = Random
    val questions :IntArray = IntArray(QUESTION_COUNT)
    val questions1 :IntArray = IntArray(QUESTION_COUNT)
    val questions2 :IntArray = IntArray(QUESTION_COUNT)
    var point : Int = 0
    var answerCount:Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        point = 0
        answerCount = 0

        val memo: Memo? = read()

        if (memo != null) {
            titleEditText.setText(memo.answer)

        }

        for (i in 0 until QUESTION_COUNT) {

            saveButton.setOnClickListener {

                val number1 = random1.nextInt(100)
                Log.d("Number1", "Queation" + number1.toString())
                questions1[i] = number1

                val number2 = random2.nextInt(100)
                Log.d("Number2", "Queation" + number2.toString())
               questions2[i] = number2


                numberText1.text =  number1.toString() + ""
                numberText2.text =  number2.toString() + ""

                val answer: String = titleEditText.text.toString()
                save(answer)

                var correct = true

                correct = number1 +  number2  == answer.toInt()


                if (correct) {
                    Toast.makeText(this, "正解", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "不正解", Toast.LENGTH_SHORT).show()
                }

                Log.d("answer", answer.toString())

                if (correct) {
                    point++
                    Log.d("ポイント", "正解" + point.toString())
                } else {
                    Log.d("ポイント", "不正解")
                }

                answerCount++

                if (answerCount == QUESTION_COUNT) {
                    answerCountTextView.text = point.toString() + "点"
                    answerCountTextView.setTextColor(Color.RED)

                    point = 0
                    answerCount = 0


                }


            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun read():Memo?{
        return realm.where(Memo::class.java).findFirst()
    }

    fun save(answer:String){
        val memo:Memo? = read()
        realm.executeTransaction {
            if (memo != null) {
                memo.answer = answer
            } else {
                val newMemo: Memo = it.createObject(Memo::class.java)
                newMemo.answer = answer

            }

            Log.d("answer",answer.toString())



            }
//            }else{
//                numberTextView.text = questions[answerCount].toString() + ""
//                numberTextView.setTextColor(Color.BLACK)
//            }

//            Snackbar.make(container, "保存しました！", Snackbar.LENGTH_SHORT).show()
        }

    }
