package com.example.ado1calculadoravenda

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var nomeProduto: EditText = findViewById(R.id.txtNomeProduto)
        var precoVenda: EditText = findViewById(R.id.txtPrecoVenda)
        var precoCusto: EditText = findViewById(R.id.txtPrecoCusto)
        var btnCalcular: Button = findViewById(R.id.btCalcular)
        var btnSalvar: Button = findViewById(R.id.btSalvar)
        var btnRecuperar: Button = findViewById(R.id.btRecuperar)

        val sh = getSharedPreferences("calculadora", Context.MODE_PRIVATE)


        btnCalcular.setOnClickListener { v ->
            var valorPrecoCusto = precoCusto.text.toString().toFloat()
            var valorPrecoVenda = precoVenda.text.toString().toFloat()

            if (valorPrecoCusto < valorPrecoVenda) {
                Toast.makeText(
                    this,
                    "Lucro: " + (valorPrecoVenda - valorPrecoCusto),
                    Toast.LENGTH_LONG
                ).show()
            } else if (valorPrecoCusto == valorPrecoVenda) {
                Toast.makeText(
                    this,
                    "Sem lucro e Sem prejuízo ",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Prejuizo: " + (valorPrecoCusto - valorPrecoVenda),
                    Toast.LENGTH_LONG
                ).show()
            }


        }


        btnSalvar.setOnClickListener { v ->
            var nomeProdutoTexto = nomeProduto.text.toString()
            var valorPrecoCusto = precoCusto.text.toString()
            var valorPrecoVenda = precoVenda.text.toString()

            sh.edit().putString(nomeProdutoTexto, valorPrecoCusto + ";" + valorPrecoVenda).apply()
            Toast.makeText(this, "Produto Salvo", Toast.LENGTH_SHORT).show()

            nomeProduto.setText("")
            precoCusto.setText("")
            precoVenda.setText("")

        }

        btnRecuperar.setOnClickListener { v ->
            var nomeProdutoTexto = nomeProduto.text.toString()

            var result = sh.getString(nomeProdutoTexto, "")

            if(result!!.isNotEmpty()){
               var lista = result.split(";")
                var precoCustoLabel = lista.get(0)
                var precoVendaLabel = lista.get(1)

                precoCusto.setText(precoCustoLabel)
                precoVenda.setText(precoVendaLabel)


                Toast.makeText(this, "Produto Encontrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Produto Não Encontrado", Toast.LENGTH_SHORT).show()
            }

        }
    }
}