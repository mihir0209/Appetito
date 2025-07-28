package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// A simple data class to represent a user's address.
data class UserAddress(
    val id: Int,
    val fullName: String,
    val mobileNumber: String,
    val state: String,
    val city: String,
    val street: String
)

val savedAddresses = mutableStateListOf(
    UserAddress(1, "Kalyani Patil", "+91 9876543210", "DYP", "Talsande", "Happi Street"),
    UserAddress(2, "Kalyani Patil 2", "+91 9876543210", "DYP 2", "Talsande", "Happii Street")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAddressScreen(
    navController: NavHostController
) {
    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }

    var editingAddressId by remember { mutableStateOf<Int?>(null) }

    fun populateFieldsForEdit(address: UserAddress) {
        editingAddressId = address.id
        fullName = address.fullName
        mobileNumber = address.mobileNumber
        state = address.state
        city = address.city
        street = address.street
    }

    fun clearFieldsForNew() {
        editingAddressId = null
        fullName = ""
        mobileNumber = ""
        state = ""
        city = ""
        street = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Addresses", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color.Black)
            )
        },
        containerColor = Color(0xFFF9F9F9)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Section for Saved Addresses ---
            Text("Saved Addresses", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    savedAddresses.forEach { address ->
                        AddressRow(address = address, onEdit = { populateFieldsForEdit(address) })
                    }
                }
            }

            OutlinedButton(
                onClick = { clearFieldsForNew() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFFE724C))
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFFFE724C))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add New Address", color = Color(0xFFFE724C), fontWeight = FontWeight.Bold)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            AnimatedVisibility(
                visible = true, // Can be used to show/hide the form
                enter = fadeIn(tween(300))
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = if (editingAddressId == null) "Add a New Address" else "Edit Address",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    AddressTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it })
                    AddressTextField(label = "Mobile Number", value = mobileNumber, onValueChange = { mobileNumber = it }, keyboardType = KeyboardType.Phone)
                    AddressTextField(label = "State", value = state, onValueChange = { state = it })
                    AddressTextField(label = "City", value = city, onValueChange = { city = it })
                    AddressTextField(label = "Street (Include house number)", value = street, onValueChange = { street = it })

                    Button(
                        onClick = {
                            // TODO: Add actual save/update logic here
                            navController.popBackStack()
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
                    ) {
                        Text(
                            text = if (editingAddressId == null) "SAVE ADDRESS" else "UPDATE ADDRESS",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressRow(address: UserAddress, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(address.fullName, fontWeight = FontWeight.Bold)
            Text("${address.street}, ${address.city}, ${address.state}", color = Color.Gray)
        }
        TextButton(onClick = onEdit) {
            Text("Edit", color = Color(0xFFFE724C))
        }
    }
}


@Composable
private fun AddressTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFE724C),
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color(0xFFFE724C),
            focusedLabelColor = Color(0xFFFE724C)
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun AddNewAddressScreenPreview() {
    AddNewAddressScreen(navController = rememberNavController())
}