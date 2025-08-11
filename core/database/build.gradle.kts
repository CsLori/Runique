plugins {
    alias(libs.plugins.runique.android.library)
    alias(libs.plugins.runique.android.room)
}

android {
    namespace = "com.cslori.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.kotlinx.coroutines.core)
//    implementation(libs.mongodb.driver.sync)
//    implementation(libs.mongodb.driver.kotlin.coroutine)



    androidTestImplementation(libs.junit.junit)
}