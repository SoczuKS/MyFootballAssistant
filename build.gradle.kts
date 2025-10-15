plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) version "2.2.0" apply false
    id("com.google.devtools.ksp") version "2.2.0-2.0.2" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.9.5" apply false
}
