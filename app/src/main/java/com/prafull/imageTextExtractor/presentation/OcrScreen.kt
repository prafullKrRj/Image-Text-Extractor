package com.prafull.imageTextExtractor.presentation

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.prafull.imageTextExtractor.model.HistoryModel
import com.prafull.imageTextExtractor.presentation.mainScreenContent.MainScreenContent
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: OcrViewModel) {
   /* val history by viewModel.history.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(HistoryModel()) }
    Log.d("prafullhis", "MainScreen: $history")
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                SearchBar()
                Spacer(Modifier.height(12.dp))
                history.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.text) },
                        selected = item.toModel() == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item.toModel()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {

        }
    )*/
    MainScreenContent(
        drawerIcon = {

        }, viewModel = viewModel
    )
}

@Composable
fun SearchBar() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }

    OutlinedTextField(
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        shape = RoundedCornerShape(50),
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") }
    )
}