import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

const val APP = "Todolist-Application"
const val VERSION = "0.0.1"
val todolist = arrayListOf<String>()

fun main(){
    println(
        "\nApp: $APP " +
                "\nversion: $VERSION\n"
    )
    showMenus()
}

fun showMenus() {
    Thread.sleep(1000)
    println("\nPilih menu -> (A)dd todo | (R)emove todo | (C)lear todo | (S)how todos | (Ed)it todo | (Ex)it program: ")
    var menus = readLine()
    when (menus?.lowercase(Locale.getDefault())) {
        "a", "add todo" -> addTodo()
        "r", "remove todo" -> {
            println("Masukkan nomer todo yang akan di hapus:")
            removeTodo(readLine()!!.toInt())
        }
        "c", "clear todos" -> clearTodo()
        "ed", "edit" ->{
            println("Masukkan nomer yang akan di ubah:")
            val index = readLine()!!.toInt()
            println("Masukkan todo yang akan di tambahkan:")
            editTodo(index, readLine()!!)
        }
        "ex", "exit", "exit program" -> exitProcess(0)
        "s", "show todo" -> showTodo()
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
    todolist.add("$todo | $time")
    println("Berhasil menambahkan '${todolist.get(todolist.lastIndex)}'")
    showMenus()
}

fun showTodo(){
    if (todolist.size <= 0){
        println("Todolist is empty")
        return
    }
    println("\nTodo(s):")
    var i = 1
    for (todo in todolist){
        println("${i++}. $todo")
    }
    showMenus()
}

fun clearTodo(){
    todolist.clear()
    println("Berhasil menghapus todolist!")
    showMenus()
}

fun removeTodo(i: Int){
    try {
        println("Menghapus ( ${todolist.get(i)} ) dari todolist")
        todolist.removeAt(i)
        showMenus()
    }catch (e:IndexOutOfBoundsException){
        println("Nomor $i tidak tersedia.")
        showMenus()
    }
}
fun editTodo(i: Int, newTodo: String){
    try {
        println("Berhasil mengubah '${todolist.get(i)}' -> '${newTodo}'")
        todolist.removeAt(i)
        todolist.set(i, newTodo)
        showMenus()
    }catch (e:IndexOutOfBoundsException){
        println("Nomor $i tidak tersedia.")
        showMenus()
    }
}