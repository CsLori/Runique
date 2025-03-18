plugins {
    alias(libs.plugins.runique.android.library.compose)

}

android {
    namespace = "com.cslori.core.presentation.designsystem"
}

dependencies {
    implementation(libs.bundles.koin.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.material3)
}