plugins {
    alias(libs.plugins.runique.android.library)
    alias(libs.plugins.runique.jvm.ktor)

//    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.cslori.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}