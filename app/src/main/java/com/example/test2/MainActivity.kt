package com.example.test2


import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var pwd: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnreg: Button
    private lateinit var mysql: Mysql
    private lateinit var db: SQLiteDatabase
    private lateinit var sp1: SharedPreferences
    private lateinit var sp2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        pwd = findViewById(R.id.pwd)
        btnlogin = findViewById(R.id.login)
        btnreg = findViewById(R.id.reg)
        sp1 = getSharedPreferences("useinfo", MODE_PRIVATE)
        sp2 = getSharedPreferences("username", MODE_PRIVATE)

        name.setText(sp1.getString("usname", null))
        pwd.setText(sp1.getString("uspwd", null))

        mysql = Mysql(this, "Userinfo", null, 1)
        db = mysql.readableDatabase

        btnlogin.setOnClickListener {
            val username = name.text.toString()
            val password = pwd.text.toString()

            // 查询用户名和密码相同的数据
            val cursor: Cursor = db.query(
                "logins", arrayOf("usname", "uspwd"),
                "usname=? and uspwd=?", arrayOf(username, password),
                null, null, null
            )

            val flag = cursor.count

            if (flag != 0) {
                val intent = Intent(this, Welcome::class.java)
                val editor = sp2.edit()
                cursor.moveToFirst()
                val loginname = cursor.getString(0)
                editor.putString("Loginname", loginname)
                editor.apply()
                startActivity(intent)
            } else {
                Toast.makeText(this, "帳號密碼錯誤", Toast.LENGTH_LONG).show()
            }
        }

        btnreg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            Toast.makeText(this, "前往注册！", Toast.LENGTH_SHORT).show()
        }
    }
}
