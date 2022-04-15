import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.20"
  kotlin("plugin.serialization") version "1.6.20"
  application
  id("com.ncorti.ktfmt.gradle") version "0.8.0"
}

group = "org.example"

version = "0.1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  val coroutinesVersion = "1.6.1-native-mt"
  val retrofitVersion = "2.9.0"

  implementation("ch.qos.logback:logback-classic:1.2.11")
  implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
  implementation("com.squareup.okhttp3:okhttp:4.9.3")
  implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
  implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
  implementation("io.github.zawn.retrofit2:retrofit-mock:2.10.6")
  implementation("io.reactivex.rxjava2:rxjava:2.2.21")
  implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutinesVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutinesVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
  kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

application { mainClass.set("contributors.MainKt") }
