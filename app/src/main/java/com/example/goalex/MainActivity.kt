package com.example.goalex

import android.content.Context
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import classes.User
import java.io.File
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileOutputStream


import java.util.Locale


fun initializeTextToSpeech(context: Context, onInitListener: TextToSpeech.OnInitListener): TextToSpeech {
    return TextToSpeech(context, onInitListener)
}
var gupta = ""


class MainActivity : ComponentActivity() {
    private lateinit var textToSpeech: TextToSpeech
    var atler = "day1s"

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

            fileInit(this@MainActivity, atler)
            val file = File(this.getExternalFilesDir(null), atler)
            var blist : MutableList<String> = mutableListOf("pleas add string")
            blist.add("Please add words")

            var sUser = remember {mutableStateOf(User("default user",blist)) }
            var sData = ""

            val users = remember { mutableStateListOf<User>() }
            val navController = rememberNavController()
            val dynamicDestinations = remember { mutableSetOf("dynamic1", "dynamic2") }
            for (destination in dynamicDestinations)


                glow(this, Modifier)




            NavHost(navController = navController, startDestination = "defaultButton") {
                composable("defaultButton") {
                    defaultButton(this@MainActivity, onNavigate = {
                        navController.navigate("Greeting")
                    }, navController = navController, "destination", file, sUser)
                }
                composable("Greeting") {
                    Greeting(navController = navController)
                }
                composable("userCreator") {
                    userCreator(navController)
                }
                composable("slecteduser"){
                    lister(user = sUser.value, file = file, sUser, spoken = ::spoken, navController  )
                }
                composable("addword"){
                    addWords(user = sUser.value, file = file , sUser =sUser, navController )
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


        var blist : MutableList<String> = mutableListOf("pleas add string")
        blist.add("Please add words")


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

                    var newuser = User(uot, blist)
                    users.add(newuser)
                    fileMaker(this@MainActivity, atler, "", newuser)



                    navController.navigate("defaultButton")

                })

        )


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
                    gupta = "Go!"
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
    fun defaultButton(
        context: Context,
        onNavigate: () -> Unit,
        navController: NavHostController,
        destination: Any,
        file: File,
        sUser: MutableState<User>
    ) {
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




        }
        Row( ) {
            uButtonmaker(this@MainActivity,atler, modifier = Modifier,
                 navController , Any(), file, sUser)

        }



    }
/*
    @Composable
    fun filecheck(context: Context, fileName: String, content: String): MutableList<User> {
        var userList: MutableList<User> = mutableListOf()
        var gson: Gson



    val file = File(context.getExternalFilesDir(null), fileName)
    if (!file.exists() == false) {




                spoken("welcome back please select user ", LocalContext.current)


        val onNavigate = Unit
        uButtonmaker(this@MainActivity,atler, modifier = Modifier,
             rememberNavController() , Any(),  )
            } else {
                    val file = File(context.filesDir, atler)




                    spoken("welcom new user! Please add a user or topic. Ooor you can try our default profile.", LocalContext.current)

                }

                return userList


            }*/



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
    fun uButtonmaker(context: Context,fileName: String, modifier: Modifier,navController: NavHostController,destination: Any, file: File, sUser: MutableState<User>) {
    var udata = dessyLow(context, fileName)

    Column(
        modifier.paddingFromBaseline(300.dp, 0.dp)
    ) {
        udata.forEach { iuser ->
            mla(iuser, modifier = Modifier, navController, file, sUser)
        }
    }

    }

    @Composable
    fun uButton(iuser:User, modifier: Modifier, context: Context,navController: NavHostController,destination: Any) {

        var navi = rememberNavController()
        val dynamicDestinations = remember { mutableSetOf("${iuser.name}") }

        Button(


            onClick = {  navController.navigate("userCreator") },
            colors = ButtonDefaults.buttonColors(

                containerColor = Color.Cyan,
            ),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(text = iuser.name)

        }
    }
        
        @Composable
        fun uclick(){
            Column {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Hey")
                    
                }
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
            }
        }

                fun fileInit(context: Context, fileName: String) {
                    var gson = Gson()

                    val file = File(context.getExternalFilesDir(null), fileName)
                    if (!file.exists()) {
                        try {
                            var blist : MutableList<String> = mutableListOf("pleas add string")
                            blist.add("Please add words")
                            var k1 = User("No users",blist )
                            var newlist = mutableStateListOf<User>()
                            var jlist = gson.toJson(newlist)

                            FileOutputStream(file).use {
                                it.write(jlist.toByteArray()) // Write content to the file
                            }
                            Toast.makeText(
                                context,
                                "No file found new file made",
                                Toast.LENGTH_SHORT
                            ).show()
                            // File successfully created
                        } catch (e: IOException) {
                            e.printStackTrace()
                            // Handle error while creating file
                        }

                    } else {
                        Toast.makeText(context, "File found", Toast.LENGTH_SHORT).show()
                    }







                    @Composable
                    fun userComposable(
                        iuser: User,
                        navController: NavController,
                        modifier: Modifier
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(

                                containerColor = Color.Cyan,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = iuser.name)

                        }

                    }
                }

                    @Composable
                    fun mla(user : User, modifier: Modifier, nagigation: NavController, file: File, sUser: MutableState<User>){

                        val context = LocalContext.current



                        Button(onClick = {sUser.value = user
                                nagigation.navigate("slecteduser")

                            },
                                colors = ButtonDefaults.buttonColors(

                                    containerColor = Color.DarkGray),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                      )


                            {
                                Text(text = user.name)
                            }




                    }

                    @Composable
                    fun lister(user: User, file: File, sUser: MutableState<User>,  spoken: (String, Context) -> Unit, navController: NavHostController ){
                        sUser.value = user
                        var context = LocalContext.current
                        var gData = file.readText()
                        var navi = rememberNavController()
                        var thislist = dessyHigh(gData, sUser)
                        Column {


                            Text(text = "Welcome ${user.name}", textAlign = TextAlign.Center,
                            )
                            
                            user.gsonHolder.fastForEach { button -> 
                                Button(onClick = { spoken(button, context)  }  )  {
                                    Text(text = button)
                                    
                                    
                                }
                            }
                            


                            Button(onClick = { navController.navigate("addword")  },  Modifier.fillMaxWidth()) {

                                Text(text = "Add words")

                            }


                        }
                    }
@Composable
fun addWords(user: User, file: File, sUser: MutableState<User>, navController: NavHostController)
{   val context = LocalContext.current
    var aWord by  remember { mutableStateOf("") }
    var gData = file.readText()
    var gson = Gson()
    val listType = object : TypeToken<MutableList<User>>() {}.type
    var udata: MutableList<User> = gson.fromJson(gData,listType)
    var thisUser = udata.find{it.name == user.name }
    Column {
        thisUser!!.gsonHolder.forEach { string ->
            Button(onClick = { /*TODO*/ }) {
                Text(text = string)
            }

        }
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = aWord,
            onValueChange = { newText ->
                aWord = newText
            },
            label = { Text("Enter a word") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()

                    thisUser.gsonHolder.add(aWord)
                    sUser.value = thisUser



                    fileSaver(context, file,  udata)

                    navController.navigate("slecteduser")
                }

            ))
    }


}

fun dessyHigh( gData: String, sUser: MutableState<User>):MutableList<User>{


    var gson = Gson()
    val listType = object : TypeToken<MutableList<User>>() {}.type
    var udata: MutableList<User> = gson.fromJson(gData,listType)
    return udata
}

fun decompiler(user: User){
    var data = user.gsonHolder



}

    fun fileSaver(context: Context, file: File, completeUsers: MutableList<User>) {
        var gson = Gson()




            try {

                var jlist = gson.toJson(completeUsers)

               file.outputStream().use { it ->
                   it.write(jlist.toByteArray())

                }
                // File successfully created
            } catch (e: IOException) {
                e.printStackTrace()
                // Handle error while creating file
            }


    }



