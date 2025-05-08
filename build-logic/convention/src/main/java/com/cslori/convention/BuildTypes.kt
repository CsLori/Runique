package com.cslori.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DynamicFeatureExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {

    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }
    }

    val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
    when (extensionType) {
        ExtensionType.LIBRARY -> {
            extensions.configure<LibraryExtension> {
                buildTypes {
                    release {
                        configureReleaseBuildType(commonExtension, apiKey, allowMinification = false)
                    }
                    debug {
                        configureDebugBuildType(apiKey)
                    }
                }
            }
        }

        ExtensionType.APPLICATION -> {
            extensions.configure<ApplicationExtension> {
                buildTypes {
                    release {
                        configureReleaseBuildType(commonExtension, apiKey, allowMinification = true)

                    }
                    debug {
                        configureDebugBuildType(apiKey)
                    }
                }
            }
        }
        ExtensionType.DYNAMIC_FEATURE -> {
            extensions.configure<DynamicFeatureExtension> {
                buildTypes {
                    release {
                        configureReleaseBuildType(commonExtension, apiKey, allowMinification = false)

                    }
                    debug {
                        configureDebugBuildType(apiKey)
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String,
    allowMinification: Boolean = true
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")

    if (allowMinification) {
        isMinifyEnabled = true
        proguardFiles(
            commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}