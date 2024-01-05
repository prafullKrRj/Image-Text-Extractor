package com.prafull.imageTextExtractor.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafull.imageTextExtractor.presentation.mainScreenContent.MainScreenContent
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: OcrViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                windowInsets = WindowInsets(right = 50)
            ) {
                Spacer(Modifier.height(12.dp))
                NavDrawerContent(viewModel)
            }
        },
        content = {
            MainScreenContent(
                drawerIcon = {
                    scope.launch { drawerState.open() }
                    }, viewModel = viewModel
            )
        }
    )
}

