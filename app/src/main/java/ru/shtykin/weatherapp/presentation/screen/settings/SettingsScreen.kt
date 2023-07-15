package ru.shtykin.weatherapp.presentation.screen.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shtykin.weatherapp.presentation.state.ScreenState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    uiState: ScreenState,
    onAddClick: ((String) -> Unit)?,
    onDeleteClick: ((String) -> Unit)?,
    onBackClick: (() -> Unit)?,
) {

    BackHandler {
        onBackClick?.invoke()
    }
    val focusManager = LocalFocusManager.current
    val cities = (uiState as? ScreenState.SettingsScreen)?.cities
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFieldFocused by remember {
        mutableStateOf(false)
    }
    var searchText by remember { mutableStateOf(TextFieldValue(text = "")) }
    val searchChanged: (TextFieldValue) -> Unit = {
        searchText = it.copy(text = it.text)
    }
    val searchFocusRequester = FocusRequester()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchText,
                        onValueChange = searchChanged,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(searchFocusRequester)
                            .onFocusChanged { isFieldFocused = it.isFocused },
                        placeholder = { Text("Введите название города") },
                        label = { Text("Город") },
                        textStyle = TextStyle(fontSize = 16.sp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            onAddClick?.invoke(searchText.text)
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick?.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    if (searchText.text.isNotEmpty()) {
                        IconButton(onClick = {
                            onAddClick?.invoke(searchText.text)
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            contentPadding = innerPadding
        ) {
            item {
                Divider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
            cities?.let {
                items(it) { city ->
                    CityCard(
                        city = city,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }

    }
}

@Composable
fun CityCard(
    city: String,
    onDeleteClick: ((String) -> Unit)?,
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            Text(
                text = city,
                fontSize = 18.sp
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { onDeleteClick?.invoke(city) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Localized description",
                    tint = Color.Black
                )
            }
            Spacer(Modifier.width(16.dp))
        }
    }
}