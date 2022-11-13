package com.example.mandatorycatapp

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.example.mandatorycatapp.databinding.ActivityMainBinding
import com.example.mandatorycatapp.models.AuthentificationLoginViewModel
import com.example.mandatorycatapp.models.Cat
import com.example.mandatorycatapp.models.CatsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.time.Duration.Companion.days

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    //selv indsat viewmodel modulet
    private val catsViewModel: CatsViewModel by viewModels()
    private val authentificationViewModel: AuthentificationLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            showDialog()
            Snackbar.make(view, "Post", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        //indsæt
        catsViewModel.updateMessageLiveData.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        authentificationViewModel.userMutableLiveData.observe(this)
        { firebaseUser ->
            if (firebaseUser != null) {
                binding.fab.visibility = View.VISIBLE
            } else {
                binding.fab.visibility = View.INVISIBLE
            }
        }
        if (FirebaseAuth.getInstance().currentUser == null) {
            //TODO this does not update after login: it needs observe prop (viewmodel)
            val menuItem = menu.findItem(R.id.action_signout)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            //added to signout
            R.id.action_settings -> true
            R.id.action_signout -> {
                //kalder authentificationViewModel signout()
                authentificationViewModel.signOut()
                /*{
                  if (Firebase.auth.currentUser != null)
                   {
                       Firebase.auth.signOut()
                       Snackbar.make(binding.root, "User signed out!", Snackbar.LENGTH_LONG).show()

                       val navController = findNavController(R.id.nav_host_fragment_content_main)
                       navController.popBackStack(R.id.FirstFragment, false)
                   }else{
                       Snackbar.make(binding.root, "Is not able to sign out!", Snackbar.LENGTH_LONG).show()
                   }
                    */
                true
            }
            R.id.action_signin -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(com.example.mandatorycatapp.R.id.action_FirstFragment_to_fragment_login)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    // Adapted from https://handyopinion.com/show-alert-dialog-with-an-input-field-edittext-in-android-kotlin/
    // Layout and function for POST dialog is implemented here
    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add Cat")

        val layout = LinearLayout(this@MainActivity)
        layout.orientation = LinearLayout.VERTICAL

        val inputFieldName = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        inputFieldName.setHint("Enter a name")
        inputFieldName.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(inputFieldName)

        val inputFieldDes = EditText(this)
        inputFieldDes.setHint("Enter a description")
        inputFieldDes.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(inputFieldDes)

        val inputFieldPlace = EditText(this)
        inputFieldPlace.setHint("Enter a Place")
        inputFieldPlace.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(inputFieldPlace)

        val inputFieldReward = EditText(this)
        inputFieldReward.setHint("Enter the Reward")
        inputFieldReward.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(inputFieldReward)

        /*val inputFieldUserId = EditText(this)
        inputFieldUserId.setHint("Enter a UserId")
        inputFieldUserId.inputType = InputType.TYPE_CLASS_TEXT
        //builder.setView(inputFieldUserId)
        layout.addView(inputFieldUserId)
         */

        val inputFieldDate = EditText(this)
        inputFieldDate.setHint("Enter a Date")
        inputFieldDate.inputType = InputType.TYPE_CLASS_NUMBER
        //builder.setView(inputFieldDate)
        layout.addView(inputFieldDate)

        val inputFieldPic = EditText(this)
        inputFieldPic.setHint("Picture path!")
        inputFieldPic.inputType = InputType.TYPE_CLASS_TEXT
        //builder.setView(inputFieldPic)
        layout.addView(inputFieldPic)

        builder.setView(layout)

        //The buttons needs setup here
        builder.setPositiveButton("OK") { dialog, which ->
            //val catId = inputFieldId.text.toString().trim().toInt()
            val catName = inputFieldName.text.toString().trim()
            val catDes = inputFieldDes.text.toString().trim()
            val catPlace = inputFieldPlace.text.toString().trim()
            val catReward = inputFieldReward.text.toString().trim().toInt()
            //val catUserId = inputFieldUserId.text.toString().trim()
            val catUserId: String =
                authentificationViewModel.userMutableLiveData.value?.email.toString()
            //date
            val catDate = inputFieldDate.text.toString().trim().toLong()
            val catPicture = inputFieldPic.text.toString().trim()

            // Here you get get input text from the Edittext
            when {
                /*catId.isEmpty() ->
                    Snackbar.make(binding.root, "Error, id's taken", Snackbar.LENGTH_LONG).show()
                catId.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, id's taken",
                    Snackbar.LENGTH_LONG
                ).show() */
                catName.isEmpty() ->
                    Snackbar.make(binding.root, "Error, needs a name", Snackbar.LENGTH_LONG).show()
                catName.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, needs a name",
                    Snackbar.LENGTH_LONG
                ).show()
                catDes.isEmpty() ->
                    Snackbar.make(binding.root, "Error, needs a description", Snackbar.LENGTH_LONG)
                        .show()
                catDes.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, needs a description",
                    Snackbar.LENGTH_LONG
                ).show()
                catPlace.isEmpty() ->
                    Snackbar.make(binding.root, "Error, no place", Snackbar.LENGTH_LONG).show()
                catPlace.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, no place",
                    Snackbar.LENGTH_LONG
                ).show()
                //Int
                /*catReward.isEmpty() ->
                    Snackbar.make(binding.root, "Error, needs reward", Snackbar.LENGTH_LONG).show()
                catReward.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, needs reward",
                    Snackbar.LENGTH_LONG
                ).show()*/
                catUserId.isEmpty() ->
                    Snackbar.make(binding.root, "Error, needs user id", Snackbar.LENGTH_LONG).show()
                catUserId.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, needs user id",
                    Snackbar.LENGTH_LONG
                ).show()
                /*catDate.isEmpty() ->
                    Snackbar.make(binding.root, "Error, needs a date", Snackbar.LENGTH_LONG).show()
                catDate.isEmpty() -> Snackbar.make(
                    binding.root,
                    "Error, needs a date",
                    Snackbar.LENGTH_LONG
                ).show()*/
                catPicture.isEmpty() ->
                    Snackbar.make(binding.root, "Error, needs a pic", Snackbar.LENGTH_LONG).show()
                catPicture.isEmpty() -> Snackbar.make(
                    binding.root, "Error, needs a pic", Snackbar.LENGTH_LONG
                )
                    .show()
                else -> {
                    val cat = Cat(
                        0,
                        catName,
                        catDes,
                        catPlace,
                        catReward,
                        catUserId,
                        catDate,
                        catPicture
                    )
                    catsViewModel.add(cat)
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }
}