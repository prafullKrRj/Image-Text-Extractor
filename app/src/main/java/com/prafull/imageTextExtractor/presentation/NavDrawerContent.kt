package com.prafull.imageTextExtractor.presentation

import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Icon
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun NavDrawerContent (viewModel: OcrViewModel) {
    val history by viewModel.history.collectAsState()
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    DismissibleDrawerSheet {
        Spacer(Modifier.height(12.dp))
        history.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = {
                    Text(text = item.text)
                },
                selected = false,
                onClick = {
                    selectedItem = index
                },
            )
        }
    }

}