package com.pratik.calculatorlib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.pratik.calculatorlib.databinding.FragmentDialogCalculatorBinding
import com.pratik.calculatorlib.helper.CalcHelper

class DialogCalculator constructor(val callback: CalculatorResultCallback) : DialogFragment(),View.OnClickListener {


    lateinit var currTV:TextView
    lateinit var infixTV:TextView
    lateinit var buttonNumber : ArrayList<Button>
    lateinit var buttonOperator : ArrayList<Button>
    lateinit var binding: FragmentDialogCalculatorBinding
    lateinit var calcHelper: CalcHelper
    private var infix = MutableLiveData("")
    private var currNum = MutableLiveData("0")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogCalculatorBinding.inflate(inflater)
        calcHelper = CalcHelper.newInstance()
        currTV = binding.currTV
        infixTV = binding.infixTV
        binding.buttonOk.setOnClickListener(this)
        binding.buttonCancel.setOnClickListener(this)
        binding.button0.setOnClickListener(this)
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.buttonAdd.setOnClickListener(this)
        binding.buttonSubtract.setOnClickListener(this)
        binding.buttonMultiply.setOnClickListener(this)
        binding.buttonDivide.setOnClickListener(this)
        binding.buttonAc.setOnClickListener(this)
        binding.buttonEqual.setOnClickListener(this)
        binding.buttonDot.setOnClickListener(this)
        binding.buttonPercentage.setOnClickListener(this)
        binding.buttonExponent.setOnClickListener(this)

        buttonNumber = arrayListOf(binding.button0,binding.button1,binding.button2,
            binding.button3,binding.button4,binding.button5,
                    binding.button6,binding.button7,binding.button8,
            binding.button9)

        buttonOperator = arrayListOf(binding.buttonAdd,binding.buttonSubtract,binding.buttonExponent,
            binding.buttonDivide,binding.buttonMultiply)
        setupResult()
        return binding.root
    }

    private fun setupResult(){

        currNum.observe(viewLifecycleOwner){
            currTV.text = it
        }
        infix.observe(viewLifecycleOwner){
            infixTV.text = it
        }
    }
    override fun onClick(v: View?) {
        val textTV = (v as TextView).text.toString()
        if(buttonNumber.contains(v)){
            if(currNum.value == "0")
                currNum.value = textTV
            else
                currNum.value += textTV

        }else if(v == binding.buttonDot){
                if(currNum.value?.contains(Regex("[.]")) == false){
                    currNum.value+= "."
                }
        }else if(buttonOperator.contains(v)){
            if(currNum.value != "0"){
                infix.value+= currNum.value + " " + textTV + " "
            }
            currNum.value = "0"
        }else if(v == binding.buttonAc){
            infix.value = ""
            currNum.value = "0"
        }
        else if(v == binding.buttonPercentage){
          val temp = currNum.value?.toDouble()?.div(100)
            currNum.value = temp.toString()
        }
        else if(v == binding.buttonEqual){
            infix.value+= currNum.value
            val postfix = infix.value?.let { calcHelper.infixToPostfix(it) }
            val result = postfix?.let { calcHelper.evaluatePostfix(it) }
            currNum.value = result
            infix.value = ""
        }else if(v == binding.buttonOk){
            callback.callback(currNum.value.toString())
            dismiss()
        }else if(v == binding.buttonCancel){
            dismiss()
        }
    }

    interface CalculatorResultCallback{
        fun callback(res:String)
    }
}