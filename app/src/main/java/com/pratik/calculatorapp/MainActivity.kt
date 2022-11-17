package com.pratik.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pratik.calculatorapp.databinding.ActivityMainBinding
import com.pratik.calculatorlib.DialogCalculator

class MainActivity : AppCompatActivity(), View.OnClickListener, DialogCalculator.CalculatorResultCallback {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.buttonOpen.setOnClickListener(this)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.buttonOpen ->{
                DialogCalculator(this).show(supportFragmentManager,"")
            }
        }
    }

    override fun callback(res: String) {
        binding.textView.text = res
    }
}