# Android PoC with JetpackCompose and FirebaseAuth integration

## Dependencies

    In order to be able to use the Firebase library into your project you need to add the following dependencies into ythe build.grdle file.

```groovy
    implementation platform('com.google.firebase:firebase-bom:32.0.0')
implementation "com.google.firebase:firebase-auth-ktx"
implementation 'com.google.firebase:firebase-firestore-ktx'
```

    Regarding the jetpack compose framework you need to also add some dependencies in your build.gradle file.

```groovy
    buildFeatures {
    compose true
}
composeOptions {
    kotlinCompilerExtensionVersion '1.4.0'
}
```

```groovy
    implementation 'androidx.activity:activity-compose:1.7.1'
implementation platform('androidx.compose:compose-bom:2022.10.00')
implementation 'androidx.compose.ui:ui'
implementation 'androidx.compose.ui:ui-graphics'
implementation 'androidx.compose.ui:ui-tooling-preview'
implementation 'androidx.compose.material3:material3'
```

## Firebase Usage

    The "Firebase" object is the single access point to all firebase SDKs from Kotlin. In order to get an instance for the FirebaseAuthentification you need to call "Firebase.auth" which is the entry point of the Firebase Authentication SDK.First, obtain an instance of this class by calling getInstance().

```kotlin

@Provides
fun provideAuthRepository(scope: CoroutineScope): AuthenticationRepository = AuthenticationRepositoryImpl(auth = Firebase.auth, scope)
```

    In order to get the current user which is authentificated you need to add the following code

```kotlin
        val currentUser = auth.currentUser
```

    To get the Firestore which is the database provided by the Firebase you can add the following piece of code 

```kotlin
    //"users" representing the name of the collection
val collection = FirebaseFirestore.getInstance().collection("users")
```

    To signUp a user through FireAuth and save the account in Firestore you can see the following code

```kotlin

override suspend fun firebaseSignUp(user: User): Result<Boolean> {
    return try {
        val authenticationResult = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        authenticationResult.user?.let {
            collection.document(it.uid)
                .set(
                    user
                )
        }
        Result.Success(true)
    } catch (exception: Exception) {
        Result.Failure(exception)
    }
}
```

    In order to be able to login a user you don't need to do any verification if he has an existing account, the FireAuth is managing that part for you 

```kotlin
    override suspend fun firebaseLogIn(user: AuthenticationUser): Result<Boolean> {
    return try {
        auth.signInWithEmailAndPassword(user.email, user.password).await()
        Result.Success(true)
    } catch (e: Exception) {
        Result.Failure(e)
    }
}
```

    If the user wants to signOut you can simply just call the following code and the FireAuth will handle all the proccess for you 

```kotlin
 fun signOut() = auth.signOut()
```

## Jetpack Compose usage

The key benefits of Jetpack Compose are that it speeds up testing and uses a single code base to write code. There is, therefore, less chance of producing errors.
Key advantages of using Jetpack Compose framework:

1. Reduced Lines Of Code
2. Intuitive
3. Speeds up (accelerates ) development environment
4. Integration with Android best practices
5. Powerful

In order to create an element of UI you need to create a method annotated with the @Composable annotation

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
```

Using Jetpack Compose you can preview every UI element by created a new composable element having a @Preview annotation

```kotlin
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FDTheme {
        Greeting("Android")
    }
}
```

An example of a screen created using Jetpack Compose is the login screen where were used elements like Text, OutlinedTextField, Button, Spacer, Column. 

```kotlin
@Composable
fun LoginScreen(onLoginClicked: () -> Unit, onCreateAccountClicked: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = usernameInputText, onValueChange = { usernameInputText = it }, label = { Text(text = "Username") })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = passwordInputText, onValueChange = { passwordInputText = it }, label = { Text(text = "Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                viewModel.login(usernameInputText.text, passwordInputText.text, onComplete = {
                    if (it) {
                        onLoginClicked()
                    } else {
                        Toast.makeText(context,"Invalid credentials!",Toast.LENGTH_SHORT).show()
                    }
                })
            },
            colors = ButtonDefaults.buttonColors(Pink40, Pink40, Pink40, Pink40),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.DarkGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Login", color = Color.White, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ClickableText(registerText, onClick = { offset ->
            registerText.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { onCreateAccountClicked() }
        })
    }
}
```
In order to save the state of an element to resist during the recomposition process in Jetpack compose are some API s which contain the word "remember". Composable functions can use the remember API to store an object in memory.
A value computed by remember is stored in the Composition during initial composition, and the stored value is returned during recomposition. remember can be used to store both mutable and immutable objects. I used in as an example in the project the rememberSavable API which automatically saves any value that can be saved in a Bundle

```kotlin
    var usernameInputText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var passwordInputText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
```