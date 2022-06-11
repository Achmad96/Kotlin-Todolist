import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

const val APP = "Todolist App"
const val VERSION = "0.0.1"
val todolists = arrayListOf<String>()

fun main(){
    showMenus()
    println("\nApp: $APP" +
            "\nVersion: $VERSION")
}

fun showMenus(){
    println("Pilih menu -> (A)dd todo | (R)emove todo | (C)lear todo | (S)how todos | (E)xit program: ")
    var menus = readLine()
    when (menus) {
        "A" -> addTodo()
        "R" ->  {
            println("Masukkan nomer todo yang akan di hapus:")
            removeTodo(readLine()!!.toInt())
        }
        "C" -> clearTodo()
        "S" -> showTodo()
        "E" -> exitProcess(0)
        else -> {
            showMenus()
        }
    }
}

fun addTodo(){
    println("Masukkan isi todo -> ")
    val todo = readLine()
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val time = sdf.format(Date())
    todolists.add("$todo | $time")
    showMenus()
}

fun showTodo(){
    println("\nTodo(s):")
    var i = 1
    for (todo in todolists){
        println("${i++}. $todo")
    }
    showMenus()
}

fun clearTodo(){
    todolists.clear()
    println("Berhasil menghapus todolists!")
    showMenus()
}

fun removeTodo(i:Int){
    println("Menghapus ( ${todolists.get(i)} ) dari Todolists")
    todolists.removeAt(i)
    showMenus()
}