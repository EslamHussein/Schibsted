package com.schibsted


public object Configration {

    object Android {
        const val compileSdk = 28
        const val mindSdk = 21
        const val targetSdk = 28
        const val buildTools = "28.0.3"
        const val versionCode = 1
        const val versionName = "1.0"
        const val kotlinVersion = "1.3.30"
        const val applicationId = "com.schibsted"
    }

    object RemoteConfig {
        const val DEV_BASE_URL = "\"https://api.github.com/graphql\""
        const val PRODUCTION_BASE_URL = "\"https://api.github.com/graphql\""
        const val DEV_ACCESS_TOKEN = "\"8713c36955105899e975e8a48dfbcec847e706a0\""
        const val PRODUCTION_ACCESS_TOKEN = "\"8713c36955105899e975e8a48dfbcec847e706a0\""
    }

    object ClassPath {
        private const val gradleVersion = "3.4.0"
        const val gradle = "com.android.tools.build:gradle:$gradleVersion"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlinVersion}"

    }

    object Network {
        private const val retrofitVersion = "2.5.0"
        private const val okhttpVersion = "3.12.0"
        private const val coroutinesAadapterVersion = "0.9.2"
        const val coroutinesAadapter =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$coroutinesAadapterVersion"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"

    }

    object AndroidXSupport {
        private const val appCompact = "1.0.2"
        private const val materialVersion = "1.0.0-rc01"
        private const val constraintLayoutVersion = "2.0.0-alpha2"
        private const val ktxVersion = "1.0.1"


        const val coreKTX = "androidx.core:core-ktx:$ktxVersion"
        const val appcompat = "androidx.appcompat:appcompat:$appCompact"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"

        const val material = "com.google.android.material:material:$materialVersion"

    }


    object Kotlin {
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Android.kotlinVersion}"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Android.kotlinVersion}"
    }

    object Koin {
        private const val koinVersion = "1.0.2"
        const val koin = "org.koin:koin-android:$koinVersion"
        const val koinViewModel = "org.koin:koin-androidx-viewmodel:$koinVersion"
        const val koinTest = "org.koin:koin-test:$koinVersion"
    }

    object Coroutines {
        private const val version = "1.2.0"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Chart {
        private const val version = "1.1.2"
        const val anyChart = "com.github.AnyChart:AnyChart-Android:$version"
    }

    object Testing {

        private const val junitVersion = "4.12"
        private const val supportTestRunnerVersion = "1.0.2"
        private const val espressoVersion = "3.1.1"
        private const val mockitoVersion = "2.8.47"

        const val junit = "junit:junit:${junitVersion}"
        const val supportTestRunner = "com.android.support.test:runner:$supportTestRunnerVersion"
        const val supportTestRules = "com.android.support.test:rules:$supportTestRunnerVersion"

        const val espressoCore = "com.android.support.test.espresso:espresso-core:$espressoVersion"

        const val espressoIdlingResource =
            "com.android.support.test.espresso:espresso-idling-resource:$espressoVersion"
        const val mockitoCore = "org.mockito:mockito-core:$mockitoVersion"
        const val espressoContrib = "com.android.support.test.espresso:espresso-contrib:$espressoVersion"
    }

}