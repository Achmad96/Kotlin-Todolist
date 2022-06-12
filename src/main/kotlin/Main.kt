import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

const val APP = "Todolist-Application"
const val VERSION = "0.0.1"
val todolist = arrayListOf<String>()
val file = File("Todolist.txt")

fun main(){
    println(
        "\nApp: $APP " +
                "\nversion: $VERSION\n"
    )
    showMenus()
}

fun showMenus() {
    Thread.sleep(1000)
    println("\nPilih menu -> (A)dd todo | (R)emove todo | (C)lear todos " +
            "\n| (S)how todos | (Ed)it todo | (Ex)it program: ")
    var menus = readLine()
    when (menus?.lowercase(Locale.getDefault())) {
        "a", "add", "add todo" -> {
            println("Masukkan isi todo -> ")
            val todo = readLine()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val time = sdf.format(Date())
            addTodo(todo!!, time)
        }
        "r", "remove", "remove todo" -> {
            checkTodo()
            println("Masukkan nomer todo yang akan di hapus:")
            removeTodo(readLine()!!.toInt())
        }
        "c", "clear", "clear todos" -> clearTodo()
        "s", "show", "show todos" ->{
            readTodoFile()
            checkTodo()
            showTodo()
        }
        "ed", "edit", "edit todo" -> {
            checkTodo()
            println("Masukkan nomer yang akan di ubah:")
            val index = readLine()!!.toInt()
            println("Mengganti '${todolist.get(index - 1)}' -> ")
            editTodo(index, readLine()!!)
        }
        "ex", "exit", "exit program" -> exitProcess(0)
        else -> {
            println("Kata kunci ${menus} tidak di ketahui, silahkan isi kembali.")
            showMenus()
        }
    }
}

fun addTodo(todo: String, time: String){
    if (!file.exists()) file.createNewFile()
    todolist.add("$todo | $time")
    file.appendText("${todolist.get(todolist.lastIndex)}\n")
    println("Berhasil menambahkan '${todolist.get(todolist.lastIndex)}'")
    showMenus()
}

fun removeTodo(i: Int){
    try {
        println("Menghapus ( ${todolist.get(i - 1)} ) dari todolist")
        todolist.removeAt(i - 1)
        readTodo()
        showMenus()
    }catch (e:IndexOutOfBoundsException){
        println("Nomor $i tidak tersedia.")
        showMenus()
    }
}

fun clearTodo(){
    todolist.clear()
    file.delete()
    println("Berhasil menghapus todolist!")
    showMenus()
}

fun showTodo(){
    var i = 1
    println("\nTodo(s):")
    for (todo in todolist) println("${i++}. $todo")
    showMenus()
}
fun editTodo(i: Int, newTodo: String){
    try {
        readTodoFile()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val time = sdf.format(Date())
        println("Berhasil mengubah '${todolist.get(i - 1)}' -> '${newTodo} | ${time}'")
        todolist.set(i - 1, "${newTodo} | ${time}")
        readTodo()
        showMenus()
    }catch (e:IndexOutOfBoundsException){
        println("Nomor $i tidak tersedia.")
        showMenus()
    }
}

fun readTodo(){
    file.delete()
    file.createNewFile()
    if (todolist.isNotEmpty())
        for (todo in todolist) file.appendText("${todo}\n")
}

fun readTodoFile(){
    if (!file.exists()) file.createNewFile()
    todolist.clear();
    for (todo in file.readLines())
        todolist.add(todo)
}

fun checkTodo(){
    if (todolist.size == 0 || !file.exists()){
        println("Todo(s) tidak tersedia.")
        showMenus()
        return
    }
}