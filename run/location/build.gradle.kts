plugins {
    alias(libs.plugins.runique.android.library)
//    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.cslori.run.location"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}