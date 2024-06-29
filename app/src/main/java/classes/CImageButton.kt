package classes

import android.widget.ImageButton
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.goalex.R
import com.example.goalex.gupta



class CImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.imageButtonStyle
) : Button(context, attrs, defStyleAttr) {

    init {

        contentDescription = "Button 1"
        setBackgroundColor(android.graphics.Color.BLUE)



    }
}