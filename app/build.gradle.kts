import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "br.com.codigozeroum.movieappxml"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.codigozeroum.movieappxml"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    flavorDimensions += "default"

    productFlavors {
        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "local.properties")))
        }
        val api_key = prop.getProperty("API_KEY")
        val bearer_token = prop.getProperty("BEARER_TOKEN")

        create("prod"){
            buildConfigField("String", "API_KEY", "\"$api_key\"")
            buildConfigField("String", "BEARER_TOKEN", "\"$bearer_token\"")
        }
        create("dev"){
            buildConfigField("String", "API_KEY", "\"$api_key\"")
            buildConfigField("String", "BEARER_TOKEN", "\"$bearer_token\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
    }

}

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.gson.converter)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.androidx.recyclerview)
    implementation(libs.picasso)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}