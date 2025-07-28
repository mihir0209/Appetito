package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R // Import your project's R file

@Preview(showBackground = true)
@Composable
fun AddNewAddressScreen() {
    // Use a Column for vertical arrangement
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9)) // Added a light background for the whole screen
            .padding(16.dp), // Add some padding around the content
        horizontalAlignment = Alignment.CenterHorizontally // Center content like the button
    ) {
        Text(
            text = "Add new address",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF111719),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start) // Align title to the start if Column is wider
        )

        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.image_13), // Corrected R usage
            contentDescription = "Profile Photo", // Always provide content description
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Full name
        Text(
            text = "Full name",
            fontSize = 16.sp,
            color = Color(0xFF9796A1),
            modifier = Modifier.align(Alignment.Start) // This now works within the Column
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Arlene McCoy",
                fontSize = 17.sp,
                color = Color(0xFF111719),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mobile number
        Text(
            text = "Mobile number",
            fontSize = 16.sp,
            color = Color(0xFF9796A1),
            modifier = Modifier.align(Alignment.Start)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFFE724C), RoundedCornerShape(10.dp)) // Highlighted border
        ) {
            Text(
                text = "018-49862746",
                fontSize = 17.sp,
                color = Color(0xFF111719),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "State",
            fontSize = 16.sp,
            color = Color(0xFF9796A1),
            modifier = Modifier.align(Alignment.Start)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Select State", // Corrected typo: "Slect" to "Select"
                fontSize = 17.sp,
                color = Color(0xFF111719), // Changed placeholder color for better visibility
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // City
        Text(
            text = "City",
            fontSize = 16.sp,
            color = Color(0xFF9796A1),
            modifier = Modifier.align(Alignment.Start)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Select City",
                fontSize = 17.sp,
                color = Color(0xFF111719), // Changed placeholder color
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Street
        Text(
            text = "Street (Include house number)",
            fontSize = 16.sp,
            color = Color(0xFF9796A1),
            modifier = Modifier.align(Alignment.Start)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Street",
                fontSize = 17.sp,
                color = Color(0xFFC4C4C4),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Save button
        Button(
            onClick = { /* TODO: Save address logic */ },
            modifier = Modifier
                .fillMaxWidth(0.7f) // This will be 70% of the Column's width
                .height(60.dp),
            shape = RoundedCornerShape(28.5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
        ) {
            Text(
                text = "Save",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                letterSpacing = 1.2.sp,
                textAlign = TextAlign.Center
            )
        }
    }
    // Removed the extra closing braces that caused "Expecting a top level declaration"
}
