package ru.sumin.a2dolist.presentation.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sumin.a2dolist.R // Убедитесь, что R указывает на ваш проект
import ru.sumin.a2dolist.presentation.ui.theme.ErrorBackground
import ru.sumin.a2dolist.presentation.ui.theme.ErrorRed
import ru.sumin.a2dolist.presentation.ui.theme.InputFieldBackgroundColor
import ru.sumin.a2dolist.presentation.ui.theme.InputFieldBorderColor
import ru.sumin.a2dolist.presentation.ui.theme.LightBackgroundColor
import ru.sumin.a2dolist.presentation.ui.theme.MainBlue
import ru.sumin.a2dolist.presentation.ui.theme.SelectedTabLightBlue
import ru.sumin.a2dolist.presentation.ui.theme.TextGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    state: LoginStore.State,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTabSelected: (LoginTab) -> Unit,
    onSubmit: () -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightBackgroundColor
    ) {
        BackgroundShapes(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(180.dp)) // Убран IconButton

            // Заголовок "2DoList"
            Text(
                text = "2DoList",
                color = Color.Black,
                fontSize = 48.sp,
                fontFamily = FontFamily( Font(R.font.itim_regular, FontWeight.Normal)),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Переключатель Sign Up / Register
            SignUpRegisterToggle(
                selectedTab = state.selectedTab, // Управляется состоянием
                onTabSelected = onTabSelected // Отправляет событие
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Сообщение об ошибке
            if (state.error != null) {
                Text(
                    text = state.error,
                    color = ErrorRed,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- Динамическая часть экрана ---
            when (state.selectedTab) {
                LoginTab.SIGN_UP -> {
                    SignInFields(
                        email = state.email,
                        password = state.password,
                        onEmailChange = onEmailChange,
                        onPasswordChange = onPasswordChange,
                        isError = state.error != null,
                        onForgotPasswordClicked = onForgotPasswordClicked
                    )
                }

                LoginTab.REGISTER -> {
                    RegisterFields(
                        email = state.email,
                        password = state.password,
                        confirmPassword = state.confirmPassword,
                        onEmailChange = onEmailChange,
                        onPasswordChange = onPasswordChange,
                        onConfirmPasswordChange = onConfirmPasswordChange,
                        error = state.error
                    )
                }
            }
            // --- Конец динамической части ---

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка Sign In / Register
            Button(
                onClick = onSubmit, // Отправляет общее событие "Submit"
                enabled = !state.isLoading, // Блокируем, если идет загрузка
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainBlue
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    // Текст кнопки меняется в зависимости от вкладки
                    text = if (state.selectedTab == LoginTab.SIGN_UP) "Sign In" else "Register",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Другие варианты входа
            Text(
                // Текст тоже меняется
                text = if (state.selectedTab == LoginTab.SIGN_UP) "Other sign in options" else "Or Register with",
                color = TextGrey,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Кнопки соцсетей
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SocialMediaButton(iconResId = R.drawable.ic_facebook)
                Spacer(modifier = Modifier.width(16.dp))
                SocialMediaButton(iconResId = R.drawable.ic_google)
                Spacer(modifier = Modifier.width(16.dp))
                SocialMediaButton(iconResId = R.drawable.ic_apple)
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

// --- Поля для Входа ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isError: Boolean,
    onForgotPasswordClicked: () -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Поле Email
    Text(
        text = "Email address",
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = { Text("Enter email", color = TextGrey) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {
            Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = TextGrey)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldErrorColors(isError = isError),
        isError = isError
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Поле Password
    Text(
        text = "Password",
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("Enter password", color = TextGrey) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = TextGrey)
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Show/Hide password" else "Show/Hide password",
                    tint = TextGrey
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldErrorColors(isError = isError),
        isError = isError
    )

    // Забыли пароль
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Forgot password?",
        color = MainBlue,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onForgotPasswordClicked() }
    )
}

// --- Поля для Регистрации ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterFields(
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    error: String?
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    // Определяем, какие поля подсвечивать
    val isPasswordMismatch = error?.contains("match", ignoreCase = true) == true
    val isEmailError = error != null && !isPasswordMismatch // Ошибка email (например, "is already taken")

    // Поле Email
    Text(
        text = "Email",
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = { Text("example@gmail.com", color = TextGrey) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {
            Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = TextGrey)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldErrorColors(isError = isEmailError),
        isError = isEmailError
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Поле Password
    Text(
        text = "Password",
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("must be 8 characters", color = TextGrey) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = TextGrey)
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Show/Hide password",
                    tint = TextGrey
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldErrorColors(isError = isPasswordMismatch),
        isError = isPasswordMismatch
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Поле Confirm Password
    Text(
        text = "Confirm password",
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        placeholder = { Text("repeat password", color = TextGrey) },
        singleLine = true,
        visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = TextGrey)
        },
        trailingIcon = {
            IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                Icon(
                    imageVector = if (isConfirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Show/Hide password",
                    tint = TextGrey
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldErrorColors(isError = isPasswordMismatch),
        isError = isPasswordMismatch
    )
}

/**
 * Вспомогательная функция для цветов полей ввода.
 * Поля Email и Password при ошибке ВХОДА окрашиваются в красный.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun outlinedTextFieldErrorColors(isError: Boolean) = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = InputFieldBackgroundColor,
    unfocusedContainerColor = if (isError) ErrorBackground else InputFieldBackgroundColor,
    focusedBorderColor = if (isError) ErrorRed else MainBlue,
    unfocusedBorderColor = if (isError) ErrorRed else InputFieldBorderColor,
    cursorColor = MainBlue,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    errorContainerColor = ErrorBackground,
    errorBorderColor = ErrorRed
)


// --- Остальные Composable-функции (без изменений) ---

@Composable
fun BackgroundShapes(modifier: Modifier = Modifier) {
    val LightBlueShape = Color(0xFFC7E0FF).copy(alpha = 0.8f) // Цвет из вашего кода

    Canvas(modifier = modifier) {
        drawCircle(
            color = LightBlueShape,
            radius = 150.dp.toPx(),
            center = Offset(x = (80).dp.toPx(), y = (-20).dp.toPx()) // Сдвинем
        )
        drawCircle(
            color = LightBlueShape,
            radius = 150.dp.toPx(),
            center = Offset(x = (-20).dp.toPx(), y = (80).dp.toPx()) // Сдвинем его правее и ниже
        )
        drawCircle(
            color = LightBlueShape,
            radius = 120.dp.toPx(),
            center = Offset(x = size.width + (60).dp.toPx(), y = size.height + (-20).dp.toPx())
        )
        drawCircle(
            color = LightBlueShape,
            radius = 120.dp.toPx(),
            center = Offset(x = size.width + (-20).dp.toPx(), y = size.height + (60).dp.toPx())
        )
    }
}

@Composable
fun SignUpRegisterToggle(
    selectedTab: LoginTab,
    onTabSelected: (LoginTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .background(Color.White)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToggleTabButton(
            text = "Sign up",
            isSelected = selectedTab == LoginTab.SIGN_UP,
            onClick = { onTabSelected(LoginTab.SIGN_UP) },
            modifier = Modifier.weight(1f)
        )
        ToggleTabButton(
            text = "Register",
            isSelected = selectedTab == LoginTab.REGISTER,
            onClick = { onTabSelected(LoginTab.REGISTER) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ToggleTabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(CircleShape)
            .background(
                if (isSelected) SelectedTabLightBlue else Color.Transparent
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SocialMediaButton(iconResId: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(InputFieldBackgroundColor)
            .border(1.dp, InputFieldBorderColor, RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { /* TODO: Handle social sign in */ },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}


// --- Preview-функции ---

@Preview(showBackground = true, name = "Sign In Tab (No Error)")
@Composable
fun LoginScreenSignInPreview() {
    LoginContent(
        state = LoginStore.State(selectedTab = LoginTab.SIGN_UP),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onTabSelected = {},
        onSubmit = {},
        onForgotPasswordClicked = {}
    )
}

@Preview(showBackground = true, name = "Register Tab (No Error)")
@Composable
fun LoginScreenRegisterPreview() {
    LoginContent(
        state = LoginStore.State(selectedTab = LoginTab.REGISTER),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onTabSelected = {},
        onSubmit = {},
        onForgotPasswordClicked = {}
    )
}

@Preview(showBackground = true, name = "Sign In Tab (Error)")
@Composable
fun LoginScreenSignInErrorPreview() {
    LoginContent(
        state = LoginStore.State(
            selectedTab = LoginTab.SIGN_UP,
            error = "Incorrect email or password"
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onTabSelected = {},
        onSubmit = {},
        onForgotPasswordClicked = {}
    )
}

@Preview(showBackground = true, name = "Register Tab (Error)")
@Composable
fun LoginScreenRegisterErrorPreview() {
    LoginContent(
        state = LoginStore.State(
            selectedTab = LoginTab.REGISTER,
            error = "Passwords don't match"
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onTabSelected = {},
        onSubmit = {},
        onForgotPasswordClicked = {}
    )
}