package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ReviewsScreen() {
    val reviews = listOf(
        Review(
            name = "Alyce Lambo",
            rating = 5.0,
            date = "25/06/2020",
            comment = "Really convenient and the points system helps benefit loyalty. Some mild glitches here and there, but nothing too egregious. Obviously needs to roll out to more remote."
        ),
        Review(
            name = "Gonela Solom",
            rating = 4.5,
            date = "22/06/2020",
            comment = "Been a life saver for keeping our sanity during the pandemic, although they could improve some of their ui and how they handle specials as it often is unclear how to use them or everything is sold out so fast it feels a bit bait and switch. Still I'd be stir crazy and losing track of days without so..."
        ),
        Review(
            name = "Brian C",
            rating = 2.5,
            date = "21/06/2020",
            comment = "Got an intro offer of 50% off first order that did not work..... I have scaled the app to find a contact us button but only a spend with us button available. "
        ),
        Review(
            name = "Helsmar E",
            rating = 3.5,
            date = "20/06/2020",
            comment = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis. Amet minim mollit non deserunt ullamco est sit."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Reviews",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF111719),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Write your review box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Write your review...",
                fontSize = 14.sp,
                color = Color(0xFF111719),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 48.dp)
            )
            // Profile image placeholder
            Image(
                painter = painterResource(id = R.drawable.image_13),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Reviews list
        reviews.forEach { review ->
            ReviewItem(review)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

data class Review(
    val name: String,
    val rating: Double,
    val date: String,
    val comment: String
)

@Composable
fun ReviewItem(review: Review) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 22.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_13),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = review.name,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFFFFC529), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = String.format("%.1f", review.rating),
                            fontSize = 9.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = review.date,
                        fontSize = 13.sp,
                        color = Color(0xFFB3B3B3)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = review.comment,
            fontSize = 15.sp,
            color = Color(0xFF7F7D92),
            lineHeight = 20.sp
        )
    }
}
