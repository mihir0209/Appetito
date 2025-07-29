package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R

// This is the reusable UI for the address selection sheet.
@Composable
fun AddressSheetContent(
    addresses: List<Address>,
    selectedAddress: Address,
    onAddressSelected: (Address) -> Unit,
    onAddNewAddress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Text(
            "Select Address",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        addresses.forEach { address ->
            AddressListItem(
                address = address,
                isSelected = address.id == selectedAddress.id,
                onClick = { onAddressSelected(address) }
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onAddNewAddress)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "Add Address", tint = Color(0xFFFE724C))
            Spacer(modifier = Modifier.width(16.dp))
            Text("Add new address", color = Color(0xFFFE724C), fontWeight = FontWeight.Bold)
        }
    }
}

// In ui/SharedComposables.kt

@Composable
private fun AddressListItem(address: Address, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = null,
            tint = if (isSelected) Color(0xFFFE724C) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        // THE FIX: The address display is now a multi-line block.
        Column(modifier = Modifier.weight(1f)) {
            Text(text = address.fullName, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold)
            Text(text = address.mobileNumber, color = Color.Gray, fontSize = 14.sp)
            Text(text = "${address.street}, ${address.cityState}", color = Color.Gray, fontSize = 14.sp)
        }
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFE724C))
        )
    }
}