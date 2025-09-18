package com.soczuks.footballassistant

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.databinding.ActivityMainBinding
import com.soczuks.footballassistant.ui.competitions.AddCompetitionDialogFragment
import com.soczuks.footballassistant.ui.items.AddItemDialogFragment
import com.soczuks.footballassistant.ui.matches.AddMatchDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), AddCompetitionDialogFragment.AddCompetitionDialogListener,
    AddItemDialogFragment.AddItemDialogListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var footballAssistantApp: FootballAssistantApp
    private val fabAnimationDuration: Long = 200
    private var isFabMenuOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        setupFabMenu()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_matches, R.id.nav_competitions, R.id.nav_items
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        footballAssistantApp = application as FootballAssistantApp
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupFabMenu() {
        binding.appBarMain.fabItem.translationY =
            binding.appBarMain.fabItem.height.toFloat() + resources.getDimension(R.dimen.fab_margin)
        binding.appBarMain.fabMatch.translationY =
            binding.appBarMain.fabMatch.height.toFloat() + resources.getDimension(R.dimen.fab_margin)
        binding.appBarMain.fabCompetition.translationY =
            binding.appBarMain.fabMatch.height.toFloat() + resources.getDimension(R.dimen.fab_margin)
        binding.appBarMain.fabItem.alpha = 0f
        binding.appBarMain.fabMatch.alpha = 0f
        binding.appBarMain.fabCompetition.alpha = 0f

        binding.appBarMain.fab.setOnClickListener { view ->
            if (isFabMenuOpen) {
                closeFabMenu()
            } else {
                openFabMenu()
            }
        }

        binding.appBarMain.fabItem.setOnClickListener { view ->
            addItemDialog()
            closeFabMenu()
        }

        binding.appBarMain.fabMatch.setOnClickListener { view ->
            addMatchDialog()
            closeFabMenu()
        }

        binding.appBarMain.fabCompetition.setOnClickListener { view ->
            addCompetitionDialog()
            closeFabMenu()
        }
    }

    private fun openFabMenu() {
        isFabMenuOpen = true
        binding.appBarMain.fab.animate().rotation(45f).setDuration(fabAnimationDuration).start()

        binding.appBarMain.fabMatch.visibility = View.VISIBLE
        binding.appBarMain.fabMatch.animate().translationY(0f).alpha(1f)
            .setDuration(fabAnimationDuration).setListener(null).start()

        binding.appBarMain.fabItem.visibility = View.VISIBLE
        binding.appBarMain.fabItem.animate().translationY(0f).alpha(1f)
            .setDuration(fabAnimationDuration).setListener(null).start()

        binding.appBarMain.fabCompetition.visibility = View.VISIBLE
        binding.appBarMain.fabCompetition.animate().translationY(0f).alpha(1f)
            .setDuration(fabAnimationDuration).setListener(null).start()
    }

    private fun closeFabMenu() {
        isFabMenuOpen = false
        binding.appBarMain.fab.animate().rotation(0f).setDuration(fabAnimationDuration).start()

        binding.appBarMain.fabMatch.animate()
            .translationY(binding.appBarMain.fabMatch.height.toFloat() + resources.getDimension(R.dimen.fab_margin))
            .alpha(0f).setDuration(fabAnimationDuration)
            .withEndAction { binding.appBarMain.fabMatch.visibility = View.GONE }.start()

        binding.appBarMain.fabItem.animate()
            .translationY(binding.appBarMain.fabItem.height.toFloat() + resources.getDimension(R.dimen.fab_margin))
            .alpha(0f).setDuration(fabAnimationDuration)
            .withEndAction { binding.appBarMain.fabMatch.visibility = View.GONE }.start()

        binding.appBarMain.fabCompetition.animate().translationY(
            binding.appBarMain.fabCompetition.height.toFloat() + resources.getDimension(
                R.dimen.fab_margin
            )
        ).alpha(0f).setDuration(fabAnimationDuration)
            .withEndAction { binding.appBarMain.fabMatch.visibility = View.GONE }.start()
    }

    private fun addCompetitionDialog() {
        val dialog = AddCompetitionDialogFragment()
        dialog.setListener(this)
        dialog.show(supportFragmentManager, AddCompetitionDialogFragment.TAG)
    }

    private fun addItemDialog() {
        val dialog = AddItemDialogFragment()
        dialog.setListener(this)
        dialog.show(supportFragmentManager, AddItemDialogFragment.TAG)
    }

    private fun addMatchDialog() {
        val dialog = AddMatchDialogFragment()
        dialog.show(supportFragmentManager, AddMatchDialogFragment.TAG)
    }

    override fun onCompetitionAdded(competition: Competition) {
        Log.d("MainActivity", "New competition added: ${competition.name}")
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    footballAssistantApp.addCompetition(competition)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error adding competition: ${e.message}")
            }
        }
    }

    override fun onItemAdded(item: Item) {
        Log.d("MainActivity", "New item added: ${item.name}")
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    footballAssistantApp.addItem(item)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error adding item: ${e.message}")
            }
        }
    }
}
