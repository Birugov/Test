package ru.techcrat.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.techcrat.test.R
import ru.techcrat.test.databinding.ActivityMainBinding
import ru.techcrat.test.databinding.AreasListBtnBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = TestFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.container, fragment, "fragment")
            .addToBackStack(null)
            .commit()
        val toolbar = binding.toolbar
        val btnConfirmBinding = AreasListBtnBinding.inflate(layoutInflater)
        toolbar.apply {
            addView(btnConfirmBinding.root, androidx.appcompat.widget.Toolbar.LayoutParams(Gravity.END))
        }
        setSupportActionBar(toolbar)
        openList()

    }

    fun openList() {
        binding.root.findViewById<ImageButton>(R.id.btn_confirm).setOnClickListener {
            val fragment = AreasListFragment()
            fragment.show(supportFragmentManager, "areaList")
        }
    }



}