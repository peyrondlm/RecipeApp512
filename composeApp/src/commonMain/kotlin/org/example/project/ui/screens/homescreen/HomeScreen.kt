package org.example.project.ui.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.example.project.domain.dtos.Prompt
import org.example.project.domain.dtos.RecipeDTO
import org.example.project.domain.utils.Preferences
import org.example.project.domain.utils.hideKeyboard
import org.example.project.ui.components.CustomOutlinedTextField
import org.example.project.ui.components.LoadingOverlay
import org.example.project.ui.screens.HomeScreenRoute
import org.example.project.ui.screens.LoginScreenRoute
import org.example.project.ui.screens.homescreen.components.GeneratedRecipe
import org.example.project.ui.screens.homescreen.components.Header
import org.example.project.ui.screens.homescreen.components.RecipeCard
import org.example.project.ui.screens.homescreen.components.RecipeItems
import org.example.project.ui.viewmodels.RecipeViewModel
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val colors = MaterialTheme.colorScheme
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
    var prompt by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val viewModel : RecipeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return RecipeViewModel() as T
            }
        }
    )

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .safeContentPadding()
            .padding(5.dp)
    ){
        item {
            Header(
                onLogout = {
                    Preferences.clearSettings()
                    navController.navigate(LoginScreenRoute){
                        popUpTo(HomeScreenRoute){
                            inclusive = true
                        }
                    }
                })
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            Text(
                text = "Crea, Cocina, Comparte y disfruta",
                style = MaterialTheme
                    .typography
                    .headlineMedium
                    .copy(fontWeight = FontWeight.ExtraBold)
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = prompt,
                onValueChange = { prompt = it},
                trailingIcon = Icons.Default.AutoAwesome,
                placeHolder = "Escribe tus ingredientes...",
                onTrailingIconClick = {
                    hideKeyboard(focusManager)
                    viewModel.generateRecipe(
                        prompt = Prompt(
                            ingredients = prompt
                        )
                    )
                    scope.launch {
                        sheetState.partialExpand()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        hideKeyboard(focusManager)
                        viewModel.generateRecipe(
                            prompt = Prompt(
                                ingredients =  prompt
                            )
                        )
                        scope.launch {
                            sheetState.partialExpand()
                        }
                    }
                )
            )
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            Text(
                text = "Tus recetas recientes"
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.recipes){ recipe ->
                    RecipeCard(recipe){
                        scope.launch {
                            val recipeDTO = RecipeDTO(
                                category = recipe.category,
                                ingredients = recipe.ingredients,
                                instructions = recipe.instructions,
                                minutes = recipe.minutes,
                                prompt = "",
                                stars = recipe.stars,
                                title = recipe.title,
                                imageUrl = recipe.imageUrl ?: ""
                            )
                            viewModel.showModalFromList(
                                recipe = recipeDTO
                            )
                            sheetState.partialExpand()
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            val tags = listOf(
                "Rapidas (10 min)",
                "Pocas calorias",
                "Sin horno",
                "Desayunos"
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Ideas Rapidas"
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(tags){ tag ->
                    Text(
                        text = tag,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colors.primary.copy(alpha = 0.1f))
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clickable{

                            },
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.primary.copy(alpha = 0.1f))
                    .padding(20.dp)
                    .clickable{
                        // Generar receta aleatoria
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "No sabes que cocinar hoy?"
                    )
                    Text(
                        text = "Genera una receta aleatoria"
                    )
                }
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
        items(viewModel.recipes) { recipe ->
            RecipeItems(recipe){
                scope.launch {
                    val recipeDTO = RecipeDTO(
                        category = recipe.category,
                        ingredients = recipe.ingredients,
                        instructions = recipe.instructions,
                        minutes = recipe.minutes,
                        prompt = "",
                        stars = recipe.stars,
                        title = recipe.title,
                        imageUrl = recipe.imageUrl ?: ""
                    )
                    viewModel.showModalFromList(
                        recipe = recipeDTO
                    )
                    sheetState.partialExpand()
                }
            }
        }
    }
    if (viewModel.showSheet){
        ModalBottomSheet(
            onDismissRequest = {viewModel.hideModal()},
            dragHandle = { BottomSheetDefaults.DragHandle()},
            containerColor = MaterialTheme.colorScheme.surface,
            sheetState = sheetState
        )
        {
            val recipe = RecipeDTO(
                category = "Mexicana",
                imageUrl = "https://images.unsplash.com/photo-1613585435238-5577aa11505f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w4MTg0MDZ8MHwxfHNlYXJjaHw4fHxUb3N0YWRhc3xlbnwwfHx8fDE3NjA5MTU5NzF8MA&ixlib=rb-4.1.0&q=80&w=1080",
                ingredients = listOf(
                    "ajo",
                    "jamón",
                    "jitomate",
                    "cebolla",
                    "queso",
                    "pan",
                    "aguacate",
                    "aceite de oliva",
                    "sal",
                    "azúcar",
                    "agua",
                    "pimienta negra"
                ),
                instructions = listOf(
                    "Reúne y prepara: pica finamente 1 diente de ajo, pica 1/2 cebolla en cubos pequeños, corta 1 jitomate en cubos pequeños, corta el jamón en tiras o cuadros, ralla o corta el queso en láminas, rebana el pan y corta el aguacate en láminas.",
                    "Precalienta una sartén a fuego medio-alto o el grill del horno. Unta ligeramente las rebanadas de pan con aceite de oliva.",
                    "Tuesta las rebanadas de pan en la sartén o grill hasta que estén doradas y crujientes por ambos lados (2–4 minutos). Si quieres, frota cada rebanada con el diente de ajo partido para aromatizar.",
                    "En la misma sartén, añade 1 cucharada de aceite de oliva y sofríe el ajo picado 30 segundos hasta que desprenda aroma; añade la cebolla y cocina 2–3 minutos hasta que esté translúcida.",
                    "Incorpora el jamón al sartén y saltea 2–3 minutos más hasta que tome un ligero dorado. Ajusta con sal y pimienta al gusto (ten en cuenta que el jamón puede ser salado).",
                    "Mientras se cocina, mezcla el jitomate con una pizca de sal, una pizca pequeña de azúcar y una o dos cucharaditas de agua para suavizar la acidez; deja reposar 1 minuto.",
                    "Monta las tostadas: sobre cada rebanada de pan tostado coloca una capa del salteado de jamón y cebolla, añade encima el jitomate preparado, luego el queso y finalmente las láminas de aguacate.",
                    "Si deseas queso fundido, coloca las tostadas montadas bajo el grill 2–3 minutos hasta que el queso se derrita ligeramente.",
                    "Termina con un chorrito pequeño de aceite de oliva y una última pizca de sal y pimienta al gusto. Sirve inmediatamente."
                ),
                minutes = 20,
                stars = 3,
                title = "Tostadas rústicas de jamón, aguacate y queso",
                prompt = ""
            )
            GeneratedRecipe(
                recipe = viewModel.generatedRecipe,
                onSave = {
                    scope.launch {
                        viewModel.hideModal()
                        sheetState.hide()
                    }
                    viewModel.saveRecipeInDb()
                },
                onClose = {
                    scope.launch {
                        viewModel.hideModal()
                        sheetState.hide()
                    }
                }
            )
        }
    }

    if (viewModel.isLoading){
        LoadingOverlay(colors)
    }
}