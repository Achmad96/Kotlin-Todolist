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
    Thread.sleep(500)
    println("\nPilih menu -> (A)dd todo | (R)emove todo | (C)lear todos " +
            "\n| (S)how todos | (Ed)it todo | (Ex)it program: ")
    var menus = readLine()
    when (menus?.lowercase(Locale.getDefault())) {
        "a", "add", "add todo" -> {
            println("Masukkan isi todo -> ")
            val todo = readLine()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val time = sdf.format(Date())
            println(addTodo(todo!!, time))
            showMenus()
        }
        "r", "remove", "remove todo" -> {
            checkTodo()
            println("Masukkan nomer todo yang akan di hapus:")
            println(removeTodo(readLine()!!.toInt()))
            showMenus()
        }
        "c", "clear", "clear todos" -> {
            checkTodo()
            println(clearTodo())
            showMenus()
        }
        "s", "show", "show todos" ->{
            readTodoFile()
            checkTodo()
            showTodo()
            showMenus()
        }
        "ed", "edit", "edit todo" -> {
            checkTodo()
            println("Masukkan nomer yang akan di ubah:")
            val index = readLine()!!.toInt()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val time = sdf.format(Date())
            println("Mengganti '${todolist.get(index - 1)}' -> ")
            println(editTodo(index, readLine()!!, time))
            showMenus()
        }
        "ex", "exit", "exit program" -> exitProcess(0)
        else -> {
            println("Kata kunci ${menus} tidak di ketahui, silahkan isi kembali.")
            showMenus()
        }
    }
}

fun addTodo(todo: String, time: String): String {
    if (!file.exists()) file.createNewFile()
    todolist.add("$todo | $time")
    file.appendText("${todolist.get(todolist.lastIndex)}\n")
    return "Berhasil menambahkan ( ${todolist.get(todolist.lastIndex)} )"
}

fun removeTodo(i: Int): String{
    try {
        todolist.removeAt(i - 1)
        readTodo()
        return "Menghapus ( ${todolist.get(i - 1)} ) dari todolist"
    } catch (e:IndexOutOfBoundsException) {
        return "Nomor $i tidak tersedia."
    }
}

fun clearTodo(): String{
    todolist.clear()
    file.delete()
    return "Berhasil menghapus todolist!"
}

fun showTodo(){
    var i = 1
    println("\nTodo(s):")
    for (todo in todolist) println("${i++}. $todo")
}
fun editTodo(i: Int, newTodo: String, time: String): String{
    try {
        val lastTodo :String = todolist.get(i - 1)
        readTodoFile()
        todolist.set(i - 1, "${newTodo} | ${time}")
        readTodo()
        return "Berhasil mengubah ( ${lastTodo} ) -> ( ${newTodo} | ${time} )"
    } catch (e:IndexOutOfBoundsException) { return "Nomor $i tidak tersedia." }
}

fun readTodo(){
    file.delete()
    file.createNewFile()
    if (todolist.isNotEmpty())
        for (todo in todolist) {
            file.appendText("${todo}\n")
        }
}

fun readTodoFile(){
    if (!file.exists()) file.createNewFile()
    todolist.clear();
    for (todo in file.readLines()) {
        todolist.add(todo)
    }
}

fun checkTodo(){
    if (todolist.size == 0 || !file.exists()){
        println("Todo(s) tidak tersedia.")
        showMenus()
        return
    }
}