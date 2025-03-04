package com.example.practi4eskaya_2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.practi4eskaya_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var useNavigationApi = true // Флаг для выбора типа навигации
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Устанавливаем Toolbar как ActionBar
        setSupportActionBar(binding.toolbar)

        // Настраиваем NavController для Navigation API
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        setupNavigation()
        toggleNavigationMode(useNavigationApi)
    }

    private fun setupNavigation() {
        binding.buttonNavigateManual.setOnClickListener {
            toggleNavigationMode(false)
            replaceFragment(FragmentOne()) // Ручная навигация
        }

        binding.buttonNavigateApi.setOnClickListener {
            toggleNavigationMode(true)
            navController.navigate(R.id.fragmentOne) // Навигация API
        }
    }

    private fun toggleNavigationMode(enableApi: Boolean) {
        useNavigationApi = enableApi
        binding.navHostFragment.visibility = if (useNavigationApi) View.VISIBLE else View.GONE
        binding.fragmentContainer.visibility = if (useNavigationApi) View.GONE else View.VISIBLE
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Добавляем в стек для возврата
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (useNavigationApi) {
            navController.navigateUp() || super.onSupportNavigateUp()
        } else {
            supportFragmentManager.popBackStack() // Возвращает Boolean
            true // Убедитесь, что метод возвращает true при ручной навигации
        }
    }
}
