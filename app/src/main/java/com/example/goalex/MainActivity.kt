package com.example.goalex

import android.content.Context
import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.goalex.ui.theme.GOAlexTheme
import androidx.compose.ui.platform.LocalContext
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.AlignmentLine

import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import classes.User
import java.io.File
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.FileOutputStream


import java.util.Locale

import kotlin.reflect.typeOf


fun initializeTextToSpeech(context: Context, onInitListener: TextToSpeech.OnInitListener): TextToSpeech {
    return TextToSpeech(context, onInitListener)
}
var gupta = ""


class MainActivity : ComponentActivity() {
    private lateinit var textToSpeech: TextToSpeech
    var atler = "date4"

    var gupta = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        textToSpeech = initializeTextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle the error
                } else {
                    speakEasy("Hello, world!", this@MainActivity)
                }
            } else {
                // Initialization failed
            }
        })


        setContent {
            val file = File(this.filesDir, atler)
            filecheck(this@MainActivity, atler, "",)

            val users = remember { mutableStateListOf<User>() }
            val navController = rememberNavController()
            val dynamicDestinations = remember { mutableSetOf("dynamic1", "dynamic2") }
            for (destination in dynamicDestinations)


                glow(this, Modifier)


            NavHost(navController = navController, startDestination = "defaultButton") {
                composable("defaultButton") {
                    defaultButton(this@MainActivity, onNavigate = {
                        navController.navigate("Greeting")
                    }, navController = navController, "destination")
                }
                composable("Greeting") {
                    Greeting(navController = navController)
                }
                composable("userCreator") {
                    userCreator(navController = navController)
                }


            }

        }


    }

    @Composable
    fun DynamicComposableList() {
        var currentComposableIndex by remember { mutableStateOf(0) }

        val composableList = listOf<@Composable () -> Unit>(
            { Greeting(navController = rememberNavController()) }

        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(onClick = {
                currentComposableIndex = (currentComposableIndex + 1) % composableList.size
            }) {
                Text(text = "Next Composable")
            }


            // Display the current composable from the list
            composableList[currentComposableIndex]()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun userCreator(navController: NavHostController) {
        val users = remember { mutableStateListOf<User>() }
        var uot by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = uot,
            onValueChange = { newText ->
                uot = newText
            },
            label = { Text("Enter User name or topic") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()

                    var newuser = User(uot, "")
                    users.add(newuser)
                    fileMaker(this@MainActivity, atler, "", newuser)



                    navController.navigate("defaultButton")

                })

        )
        uButtonmaker(this@MainActivity,atler, modifier = Modifier)
    }


    fun speakEasy(text: String, context: Context) {

        textToSpeech?.speak(gupta, TextToSpeech.QUEUE_FLUSH, null, null)


    }

    @Composable
    fun glow(context: Context, modifier: Modifier) {




    }


    @Composable
    fun Greeting(modifier: Modifier = Modifier, navController: NavHostController) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons
        ) {
            var context = LocalContext.current
            ImageButton(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Button 1",
                colors = ButtonDefaults.buttonColors(contentColor = Color.Blue),
                onClick = {
                    gupta = "GO"
                    spoken(gupta, context)


                }
            )
            ImageButton2(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Button 2",
                colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                onClick = {
                    gupta = "STOP!"
                    spoken(gupta, context)
                }

            )
            ImageButton3(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Button 3",
                colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                onClick = {

                    gupta = "MORE!"
                    spoken(gupta, context)


                }
            )
            trigger(context)

        }

    }

    @Composable
    fun ImageButton(
        painter: Painter,
        contentDescription: String,
        colors: ButtonColors,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(

                containerColor = Color.Green,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Adjust the height as needed
        ) {
            Text(text = "GO")
        }
    }

    @Composable
    fun ImageButton2(
        painter: Painter,
        contentDescription: String,
        colors: ButtonColors,
        onClick: () -> Unit
    ) {
        var context = LocalContext.current
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(

                containerColor = Color.Red,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Adjust the height as needed
        ) {

            Text(text = "Stop")

        }
    }

    @Composable
    fun ImageButton3(
        painter: Painter,
        contentDescription: String,
        colors: ButtonColors,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(

                containerColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Adjust the height as needed
        ) {
            Text(
                text = "More",


                )
        }
    }

    @Preview
    @Composable
    fun bu2(modifier: Modifier = Modifier) {
        Button(onClick = { /*TODO*/ }) {
            Modifier.background(color = Color.Blue)
            Modifier.height(50.dp)


        }
    }




    fun spoken(gupta: String, context: Context) {
        lateinit var textToSpeech: TextToSpeech
        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle the error
                } else {
                    textToSpeech.speak(gupta, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            } else {
                // Initialization failed
            }
        })
    }

    @Composable
    fun trigger(context: Context) {
        ImageButton(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Button 1",
            colors = ButtonDefaults.buttonColors(contentColor = Color.Blue),
            onClick = { })
    }

    @Composable
    fun defaultButton(context: Context, onNavigate: () -> Unit,navController: NavHostController,destination: Any ) {
        val users = remember { mutableStateListOf<User>() }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            Button(
                onClick = onNavigate,
                colors = ButtonDefaults.buttonColors(

                    containerColor = Color.Green,
                ),
                modifier = Modifier
                    .fillMaxWidth(.5F)
                    .height(250.dp) // Adjust the height as needed
            ) {
                Text(text = "Default")

            }
            Button(
                onClick = {  navController.navigate("userCreator") },
                colors = ButtonDefaults.buttonColors(

                    containerColor = Color.Red,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Adjust the height as needed
                    .padding(0.dp, 0.dp)
            ) {
                Text(text = "Add User")
            }
            users.forEach { t1 ->
                Button(
                    onClick = {  navController.navigate("userCreator") },
                    colors = ButtonDefaults.buttonColors(

                        containerColor = Color.Red,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) // Adjust the height as needed
                        .padding(0.dp, 0.dp)
                ) {
                    Text(text = t1.name)
                }

            }


        }
    }
@Composable
    fun filecheck(context: Context, fileName: String, content: String): MutableList<User> {
        var userList: MutableList<User> = mutableListOf()
        var gson: Gson



    val file = File(context.getExternalFilesDir(null), fileName)
    if (!file.exists() == false) {




                spoken("welcome back please select user ", LocalContext.current)
        uButtonmaker(this@MainActivity,atler, modifier = Modifier)
            } else {
                val file = File(context.filesDir, atler)

                    spoken("welcom new user! Please add a user or topic. Ooor you can try our default profile.", LocalContext.current)

                }

                return userList


            }



}
fun dessy(gData: String):MutableList<User> {
    var gson = Gson()
    val listType = object : TypeToken<MutableList<User>>() {}.type
    var udata: MutableList<User> = gson.fromJson(gData,listType)
    return udata
}
fun dessyLow( context: Context, fileName: String):MutableList<User>{
    val file = File(context.getExternalFilesDir(null), fileName)
    var gData = file.readText()
    var gson = Gson()
    val listType = object : TypeToken<MutableList<User>>() {}.type
    var udata: MutableList<User> = gson.fromJson(gData,listType)
    return udata
}
@Composable
    fun uButtonmaker(context: Context,fileName: String, modifier: Modifier) {
    var udata = dessyLow(context, fileName)

    Column(
        modifier.paddingFromBaseline(300.dp, 0.dp)
    ) {
        udata.forEach { iuser ->
            uButton(iuser, rememberNavController(), modifier = Modifier)
        }


    }
}
    @Composable
    fun uButton(iuser:User, navController: NavHostController, modifier: Modifier) {


        val dynamicDestinations = remember { mutableSetOf("${iuser.name}") }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(

                containerColor = Color.Cyan,
            ),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(text = iuser.name)

        }
    }
        



        fun fileMaker(context: Context, fileName: String, content: String, nUser: User) {
            var gson = Gson()

            val file = File(context.getExternalFilesDir(null), fileName)
            if (!file.exists()) {
                try {
                    var newlist = mutableStateListOf<User>(nUser)
                    var jlist = gson.toJson(newlist)

                    FileOutputStream(file).use {
                        it.write(jlist.toByteArray()) // Write content to the file
                    }
                    // File successfully created
                } catch (e: IOException) {
                    e.printStackTrace()
                    // Handle error while creating file
                }

            } else {
                var lstring = file.readText()
                var datlist = dessy(lstring)
                datlist.add(nUser)
                var outer = gson.toJson(datlist)
                FileOutputStream(file).use {
                    it.write(outer.toByteArray()) // Write content to the file
                }






                @Composable
                fun userComposable(iuser: User, navController: NavController, modifier: Modifier) {
                    Button(
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(

                            containerColor = Color.Cyan,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text(text = iuser.name)

                    }

                }

            }  }