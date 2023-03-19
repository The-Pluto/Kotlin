import java.util.Scanner

fun main(args:Array<String>){
    println("输入表达式:")
    val scanner = Scanner(System.`in`)
    val readText = scanner.nextLine()
    process(readText)
}

fun process(args:String){
    val len = args.length
    val str = ""
    var pos = 0
    var num1 = 0F
    var num2 = 0F
    for (arg:Char in args){
        if(IsSymble(arg)){
            num1 = Char2Num(0,pos,args)
            num2 = Char2Num(pos+1,len,args)
            val ans = Operator(num1,num2,arg)
            println("$num1 $arg $num2 = $ans")
        }
        ++pos
    }
}

fun Operator(num1: Float,num2:Float,symble:Char):Float {
    var ans:Float = 0.0F
    when(symble){
        '+' -> ans = num1+num2
        '-' -> ans = num1-num2
        '*' -> ans = num1*num2
        '/' -> ans = num1/num2
    }
    return ans
}

fun IsSymble(symble:Char):Boolean{
    return symble.equals('+') || symble.equals('-') ||
            symble.equals('*') || symble.equals('/')
}

fun Char2Num(begin:Int, end:Int,str:String):Float{
    var num = 0F
    for(i in begin until end){
        num = num*10 + str[i].code - 48;
    }
    return num
}