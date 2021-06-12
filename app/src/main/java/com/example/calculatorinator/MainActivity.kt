package com.example.calculatorinator



import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {


    private lateinit var txtInput: TextView

    private var lastNumeric = false

    private var stateError = false

    private var lastDecimal = false



    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.textInput)
    }

    fun onDigit(view: View)
    {

        if(stateError)
        {
            txtInput.text = (view as Button).text
            stateError = false
        }else
        {
            txtInput.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onDecimal(view: View)
    {
        if(lastNumeric&&(!stateError)&&(!lastDecimal))
        {
            txtInput.append(".")
            lastNumeric=false
            lastDecimal=true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDecimal = false

        }
    }

    fun onClear(view: View) {
        this.txtInput.text = ""
        lastNumeric = false
        stateError = false
        lastDecimal = false
    }

    fun onEqual(view: View)
    {
        if(lastNumeric && !stateError)
        {
            val txt = txtInput.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try{
                val result = expression.evaluate()
                txtInput.text= System.getProperty("line.separator")
                txtInput.text = result.toString()
                lastDecimal= true

            }catch(ex: ArithmeticException){
                txtInput.text="error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}