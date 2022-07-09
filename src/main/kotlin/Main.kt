import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

const val APP = "Todolist-Application"
const val VERSION = "0.0.5"
val todolist = arrayListOf<String>()
val file = File("Todolist.txt")

fun main(){
    print(
        "\nApp: $APP " +
        "\nversion: $VERSION\n"
    )
    showMenus()
}

fun showMenus() {
    print("\nMenu (A)dd todo, (R)emove todo, (C)lear todos, (S)how todos, (Ed)it todo, (Ex)it program: ")
    val menus = readLine()
    when (menus?.lowercase(Locale.getDefault())) {
        "a", "add", "add todo" -> {
            print("Todo: ")
            val todo = readLine()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val time = sdf.format(Date())
            println(addTodo(todo!!, time))
            getTodos()
            showMenus()
        }
        "r", "remove", "remove todo" -> {
            if (todolist.isEmpty()) return
            print("Nomer todo: ")
            println(removeTodo(readLine()!!.toInt()))
            getTodos()
            showMenus()
        }
        "c", "clear", "clear todos" -> {
            println(clearTodo())
            showMenus()
        }
        "s", "show", "show todos" -> {
            if (todolist.isEmpty()) return
            showTodo()
            showMenus()
        }
        "ed", "edit", "edit todo" -> {
            if (todolist.isEmpty()) return
            print("Nomer todo: ")
            val index = readLine()!!.toInt()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val time = sdf.format(Date())
            print("Mengganti '${todolist[index - 1]}' -> ")
            println(editTodo(index, readLine()!!, time))
            getTodos()
            showMenus()
        }
        "ex", "exit", "exit program" -> exitProcess(0)
        else -> {
            println("Kata kunci $menus tidak di ketahui, silahkan isi kembali.")
            showMenus()
        }
    }
}

fun addTodo(todo: String, time: String): String {
    if (!file.exists()) file.createNewFile()
    todolist.add("$todo | $time")
    return "Berhasil menambahkan ( ${todolist[todolist.lastIndex]} )"
}

fun removeTodo(i: Int): String{
    return try {
        val todoRemoved = todolist[i - 1]
        todolist.removeAt(i - 1)
        "Menghapus ( $todoRemoved ) dari todolist"
    } catch (e : IndexOutOfBoundsException) {
        "Nomor $i tidak tersedia."
    } catch (e: NumberFormatException) {
        "Error: format number tidak diketahui."
    }
}

fun clearTodo(): String{
    todolist.clear()
    file.delete()
    return "Berhasil menghapus todolist!"
}

fun showTodo(){
    var i = 1
    println("LIST TODO")
    for (todo in todolist) {
        val todos = todo.split(" | ");
        println("${i++}. ${todos[0]}")
    }
}

fun editTodo(i: Int, newTodo: String, time: String): String{
    return try {
        val previousTodo : String = todolist[i - 1]
        todolist[i - 1] = "$newTodo | $time"
        "Berhasil mengubah ( $previousTodo ) -> ( ${todolist[i - 1]} )"
    } catch (e : IndexOutOfBoundsException) {
        "Nomor $i tidak tersedia."
    } catch (e: NumberFormatException) {
        "Error: format number tidak diketahui."
    }
}

fun getTodos(){
    file.delete()
    file.createNewFile()
    for (todo in todolist)
        file.appendText("${todo}\n")
}

fun getTodosInFile(){
    if (!file.exists()) file.createNewFile()
    todolist.clear()
    for (todo in file.readLines()) {
        todolist.add(todo);
    }
}