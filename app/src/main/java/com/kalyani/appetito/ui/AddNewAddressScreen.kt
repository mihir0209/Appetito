package ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAddressScreen(
    navController: NavHostController
) {
    // State management for form fields and editing logic remains the same.
    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var editingAddressId by remember { mutableStateOf<Int?>(null) }
    // This is using a mutable list, which is fine for this example.
    val savedAddresses = remember { DemoDataProvider.savedAddresses }

    fun populateFieldsForEdit(address: Address) {
        editingAddressId = address.id
        fullName = address.fullName
        mobileNumber = address.mobileNumber
        val cityStateParts = address.cityState.split(",").map { it.trim() }
        city = cityStateParts.getOrElse(0) { "" }
        state = cityStateParts.getOrElse(1) { "" }
        street = address.street
    }

    fun clearFieldsForNew() {
        editingAddressId = null; fullName = ""; mobileNumber = ""; state = ""; city = ""; street = ""
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
                // CHANGE: Use theme colors for awareness.
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        // CHANGE: Use theme's background color.
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Saved Addresses",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground // Use theme color
            )
            Card(
                // CHANGE: Use theme surface color for card background.
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
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
                // CHANGE: Use primary color for border.
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                // CHANGE: Use primary color for icon tint.
                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                // CHANGE: Use primary color for text.
                Text("Add New Address", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant // Use theme color
            )
            // The form visibility logic remains the same.
            AnimatedVisibility(visible = true) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = if (editingAddressId == null) "Add a New Address" else "Edit Address",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground // Use theme color
                    )
                    // Custom text fields now use theme colors internally.
                    AddressTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it })
                    AddressTextField(label = "Mobile Number", value = mobileNumber, onValueChange = { mobileNumber = it }, keyboardType = KeyboardType.Phone)
                    AddressTextField(label = "State", value = state, onValueChange = { state = it })
                    AddressTextField(label = "City", value = city, onValueChange = { city = it })
                    AddressTextField(label = "Street (Include house number)", value = street, onValueChange = { street = it })
                    Button(
                        onClick = {
                            if (editingAddressId == null) {
                                val newId = (savedAddresses.maxOfOrNull { it.id } ?: 0) + 1
                                val newAddress = Address(id = newId, fullName = fullName, mobileNumber = mobileNumber, street = street, cityState = "$city, $state", fullAddress = "$street, $city")
                                savedAddresses.add(newAddress)
                                DemoDataProvider.selectedAddress.value = newAddress
                            } else { /* TODO: Implement update logic */ }
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        // CHANGE: Use theme colors for the button.
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = if (editingAddressId == null) "SAVE ADDRESS" else "UPDATE ADDRESS",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            // CHANGE: Use theme color for text on button.
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressRow(address: Address, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                address.fullName,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface // Use theme color
            )
            Text(
                address.fullAddress,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Use subtler theme color
            )
        }
        TextButton(onClick = onEdit) {
            // CHANGE: Use primary color for the text button.
            Text("Edit", color = MaterialTheme.colorScheme.primary)
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
        // CHANGE: Use theme colors for the text field states.
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary
            // Other colors like text and label color will be inherited from the theme.
        ),
        singleLine = true
    )
}

// --- Previews ---

@Preview(showBackground = true, name = "Add Address Screen Light")
@Composable
fun AddNewAddressScreenPreview() {
    AppetitoTheme(useDarkTheme = false) {
        AddNewAddressScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Add Address Screen Dark")
@Composable
fun AddNewAddressScreenDarkPreview() {
    AppetitoTheme(useDarkTheme = true) {
        AddNewAddressScreen(navController = rememberNavController())
    }
}