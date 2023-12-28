package com.example.test2
import android.content.ContentValues
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

class Register : AppCompatActivity() {
    private lateinit var usename: EditText
    private lateinit var usepwd: EditText
    private lateinit var usepwd2: EditText
    private lateinit var submit: Button
    private lateinit var mysql: Mysql
    private lateinit var db: SQLiteDatabase
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usename = findViewById(R.id.usename)
        usepwd = findViewById(R.id.usepwd)
        usepwd2 = findViewById(R.id.usepwd2)
        submit = findViewById(R.id.submit)

        mysql = Mysql(this, "Userinfo", null, 1)
        db = mysql.readableDatabase
        sp = getSharedPreferences("useinfo", MODE_PRIVATE)

        submit.setOnClickListener {
            var flag = true // 判斷是否已有帳號

            val name = usename.text.toString()
            val pwd01 = usepwd.text.toString()
            val pwd02 = usepwd2.text.toString()

            if (name.isEmpty() || pwd01.isEmpty() || pwd02.isEmpty()) {
                Toast.makeText(this, "帳號密碼不能為空！", Toast.LENGTH_LONG).show()
            } else {
                val cursor: Cursor = db.query("logins", arrayOf("usname"), null, null, null, null, null)

                while (cursor.moveToNext()) {
                    if (cursor.getString(0) == name) {
                        flag = false
                        break
                    }
                }

                if (flag) { // 判斷是否已有帳號
                    if (pwd01 == pwd02) { // 判斷密碼輸入是一致
                        val cv = ContentValues()
                        cv.put("usname", name)
                        cv.put("uspwd", pwd01)
                        db.insert("logins", null, cv)

                        val editor: SharedPreferences.Editor = sp.edit()
                        editor.putString("usname", name)
                        editor.putString("uspwd", pwd01)
                        editor.apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(this, "註冊成功！", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "密碼不一致！", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "用戶已存在！", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
