package com.xd.flexible.activity

import android.os.Bundle
import com.xd.flexible.R
import com.xd.flexible.application.BaseActivity

/**
 * Created by Flexible on 2017/6/7 0007.
 */

class KtTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            test(Person("flexble"))
            test(Person("flexble", 11))
            val listOf = listOf<Person>()
    }

    fun test(p: Person): Int? {
        if (p.age == null)
            throw NullPointerException("您还没有输入过年龄")
        when (p.age) {
            11 -> print("age==11")
            22 -> print("age==22")
        }
        if (p.age!! in 10..18) {
            print("未成年")
        }
        return p.age
    }

    class Person(name: String) {
        constructor(name: String, age: Int) : this(name) {
            this.age = age
        }
        var name: String = name
        var age: Int? = null
        var sex: String? = ""
    }
}
