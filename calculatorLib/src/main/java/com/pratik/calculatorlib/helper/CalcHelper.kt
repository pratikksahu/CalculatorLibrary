package com.pratik.calculatorlib.helper

import kotlin.collections.ArrayDeque

class CalcHelper {
    companion object {
        @JvmStatic
        fun newInstance() =
            CalcHelper()
    }
    // A utility function to return
    // precedence of a given operator
    // Higher returned value means
    // higher precedence
    private fun precedence(ch: String): Int {
        when (ch) {
            "+","-" -> return 1
            "*","/" -> return 2
            "^" -> return 3
        }
        return -1
    }
    private fun isInteger(str: String?) = str?.toBigDecimalOrNull()?.let { true } ?: false

    fun infixToPostfix(exp: String): String {
        // initializing empty String for result
        var result = ""
        val token = exp.split(" ")
        // initializing empty stack
        val stack = ArrayDeque<String>()
        for (element in token) {

            // If the scanned character is an
            // operand, add it to output.
            if (isInteger(element)) {
                result += element
                result += " "
            }
            else  // an operator is encountered
            {
                while (!stack.isEmpty()
                    && precedence(element) <= precedence(stack.last())
                ) {
                    result += stack.last()
                    result += " "
                    stack.removeLast()
                }
                stack.addLast(element)
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.last() == "(") return "Invalid Expression"
            result += stack.last()
            result += " "
            stack.removeLast()
        }
        return result
    }

    fun evaluatePostfix(postfix:String): String{
        val operator = arrayListOf<String>("+","-","/","*","^")
        //Stack
        val token = postfix.split(" ")
        val stack = ArrayDeque<String>()
        for(element in token){
            if(isInteger(element)){
                stack.addLast(element)
            }else if(operator.contains(element)){
                val val1 = stack.removeLast()
                val val2 = stack.removeLast()
                val res = operate(val1,val2,element)
                stack.addLast(res)
            }
        }
        val result = stack.last().toDouble()
        if(result%1 == 0.0)
            return removeTrailingZeros(result.toString())
        return result.toString()
    }
    fun removeTrailingZeros(num: String): String {
        if(!num.contains('.')) // Return the original number if it doesn't contain decimal
            return num
        return num
            .dropLastWhile { it == '0' } // Remove trailing zero
            .dropLastWhile { it == '.' } // Remove decimal in case it's the last character in the resultant string
    }
    private fun operate(val1:String, val2:String, op:String):String{
        val v1 = val1.toDouble()
        val v2 = val2.toDouble()
        var res = ""
        when(op){
            "+" ->{
                    res = (v2+v1).toString()
            }
            "-" ->{
                    res = (v2-v1).toString()
            }
            "/" ->{
                try {
                    res = (v2/v1).toString()
                }catch (e:ArithmeticException){
                    println("Cannot divide by 0")
                }
            }
            "*" ->{
                    res = (v2*v1).toString()
            }
            "^" ->{
                var exponent = v1
                var localRes = 1.0
                while (exponent >= "1".toDouble()) {
                    localRes *= v2
                    --exponent
                }
                    res = localRes.toString()
            }
        }
        return res
    }

}

