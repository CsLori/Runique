plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.cslori.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}