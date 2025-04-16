package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetbrains.kmpapp.screens.chatlist.ChatListScreen
import com.jetbrains.kmpapp.screens.login.LoginScreen
import com.jetbrains.kmpapp.screens.profile.ProfileScreen
import com.jetbrains.kmpapp.screens.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: Int)

@Serializable
object ChatListDestination

@Serializable
object RegisterDestination

@Serializable
object LoginDestination

@Serializable
object ProfileDestination

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ChatListDestination) {
                composable<ChatListDestination> {
                    ChatListScreen(
                        navigateToRegister = {
                            navController.navigate(RegisterDestination)
                        },
                        navigateToProfile = {
                            navController.navigate(ProfileDestination)
                        }
                    )
                }

                composable<ProfileDestination> {
                    ProfileScreen(
                        onLogout = {
                            navController.popBackStack()
                        },
                        onGoBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<RegisterDestination> {
                    RegisterScreen(
                        onGoBack = {
                            navController.popBackStack()
                        },
                        navigateToLogin = {
                            navController.navigate(LoginDestination) {
                                popUpTo(RegisterDestination) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable<LoginDestination> {
                    LoginScreen(
                        onSuccess = {
                            navController.popBackStack()
                        },
                        navigateToRegister = {
                            navController.navigate(RegisterDestination) {
                                popUpTo(LoginDestination) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}
