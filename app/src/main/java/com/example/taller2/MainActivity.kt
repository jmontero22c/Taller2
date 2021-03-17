package com.example.taller2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var listaArray = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaArray)
        val listaTareas = findViewById<ListView>(R.id.listaTareas)
        listaTareas.adapter = adapter

        var output = PrintStream(openFileOutput("tareas.txt", MODE_APPEND))
        val scan = Scanner(openFileInput("tareas.txt"))
        while (scan.hasNextLine()) {
            val line = scan.nextLine()
            listaArray.add(line)
        }

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener { view ->
            val tarea = findViewById<EditText>(R.id.addItem)
            val tareaString = tarea.text.toString()
            deleteFile()

            if (tareaString == "" || tareaString == null) {
                listaArray.add("null")
            } else {
                listaArray.add(tareaString)
            }
            adapter.notifyDataSetChanged()

            for( data in listaArray){
                PrintStream(openFileOutput("tareas.txt", MODE_APPEND)).println(data)
            }
            tarea.text = null
        }


        listaTareas.setOnItemLongClickListener { parent, view, position, id ->
            listaArray.remove(listaArray.get(position))
            adapter.notifyDataSetChanged()
            deleteFile()
            for( data in listaArray){
                PrintStream(openFileOutput("tareas.txt", MODE_APPEND)).println(data)
            }
            return@setOnItemLongClickListener true
        }
    }

    fun deleteFile(){
        val dir = filesDir
        val file = File(dir, "tareas.txt")
        val deleted = file.delete()
    }

}