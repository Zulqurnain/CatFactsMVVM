
# Reach Me At

[![](https://i.imgur.com/TIaklaY.png)](https://www.facebook.com/Raja.jutt/)

[![](https://i.imgur.com/BeJw36c.png)](https://www.linkedin.com/in/zulqurnainjutt/)

[![](https://i.imgur.com/f3zNZLA.png)](https://jobee.pk/resume/zulqurnainh/impression)


# CatFactsMVVM
A simple application which uses REST of https://alexwohlbruck.github.io/cat-facts/docs/ and folllowing partial SOLID and MVVM strict

![CatFactsDemo](https://images.all-free-download.com/images/graphicthumb/realistic_cat_face_design_vector_580623.jpg)

# Technology Stack

Following are the features:

 - Show List of Cat Facts (5) ✔
 - On Click of Any Fact it expands and show it's complete details ✔
 - Test cases ⛏

## Android development

Latest cutting edge libraries and tools. As a summary:

 - Entirely written in [Kotlin](https://kotlinlang.org/)
 - Uses all of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/): Room, LiveData and Lifecycle-components
 - Uses [Coroutines](https://developer.android.com/kotlin/coroutines) 
 - Uses [dagger-hilt](https://dagger.dev/hilt/) for dependency injection
 - Uses [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) for persistence

## Development setup

First off, it requires latest Android Studio 4.0 (or newer) to be able to build the app.

### Compiling Requirements

#### Build Tools
Since mentioning specific build tools is no more mandatory with latest Android Studio, the project does not mention any particular build tool so that it uses latest build tools required to build this project.

#### Compile SDK
The project requires Android SDK 28 compile tools to build this project. If one faces error compiling this project, please install **Android SDK Platform 30** manually from `Preferences` -> `Appearance & Behavior` -> `System Settings` -> `Android SDK`

#### Gradle Plugin
The project requires gradle plugin version `6.5`.

### Flavors

Flavor can be described as combination of requirements to meet specific set of needs. The flavors used by this app can be broken down as follows:

#### Product Flavors

Product flavors are designed to identify each environment separately.

 - `live` Production server *(Live on Google Play)*
 - `staging` Staging server *(Used for testing purposes mainly)*

#### Build Types

Builds types are designed to identify build configurations separately.
 - `debug` Build types intended to be used for developers while development
 - `internal` Build types intended to be used when sharing builds internally with QA and other teams
 - `release` Build types intended to be released on Play Store. It should only be used with combination of `live`.

The following table describes basic differences between these build types:

| Feature | debug | internal | release |
| ------ | ------ | ------ | ------ |
| Debuggable | ✔ | ✔ | ✘ |
| Logs Enabled | ✔ | ✔ | ✘ |

`debug` and `internal` build types have suffix **`.internal`** appended to their application id so that they can be differentiated from release builds.

#### Build Variants

The final build created after combination of `Product Flavor` and `Build Type` is known as `Build Variant`. Each variant created reflects the properties mentioned in above section. Here are few examples from the variants used by this app:

 - `stagingInternal` *Internal* build pointing to *staging* server with debugging disabled to be used by QA and other team members before release for testing with *staging* server
 - `liveRelease` *Release* build pointing to *production* server to be released on Play Store

#### Ignored Variants

The [`build.gradle`](app/build.gradle) includes a map that skips some variants to reduce the compile time for this project by ignoring some of the build combinations that have no use. The following example describes how it works:

```
    // true to skip variant, default fallback for missing variants is true
    def variantFilterSkipMap = [
            // Other variants go here
            stagingDebug      : false, // Does not skip this variant
            stagingRelease    : true,  // Skips this variant
            // Other variants go here
    ]
```

Any variant not included here is skipped by default. Therefore variants that should not be skipped, must go here with value set to `false`.

> All new variants are skipped by default unless included using **`variantFilterSkipMap`**

### Signing the APKs

The build configurations file [`build.gradle`](app/build.gradle) contains signing configurations for each build so that builds created from any machine has same signing and can be installed over previous version of this project.

`storeConfig` can be used to generate signed apk for Play Store. This expects file containing information for signing at following relative path from this file:

> ../../signings/credentials/credentials_store.json

The file should contain JSON in following format:

```
{
    "android": {
        "storeFile": "../../signings/keystores/LIVE.jks",
        "storePassword": "password",
        "keyAlias": "alias_name",
        "keyPassword": "password"
    }
}
```

There are four properties that are read from the properties file:

 - storeFile (relative path to keystore file)
 - storePassword (Store password)
 - keyAlias (Name of alias used for this app)
 - keyPassword  (Alias password)


#### Generate Signed Bundle / APK

For uploading build on Play Store, signed build should be used. The signing used for uploading on Play Store can be obtained from Cheetay team. For signature versions use both signatures given in Android Studio:
 - V1 (Jar Signature) ✔
 - V2 (Full APK Signature) ✔

> **Please note that the project currently has Google Signing enabled on Google Play**

### APK (Android Package Kit) File Names

Refer to  [`build.gradle`](app/build.gradle) file starting with `applicationVariants.all`:

Each APK file name is named with following convention

> FactsV[versionName].[versionCode]_[variantName].apk

To differentiate all variants, each variant file is given a different name so it clearly specifies the configurations and environment it was built for with *app version* included.

e.g. *FactsV1.2.3.100_LiveRelease.apk* is release ready APK with version name *1.2.3*, version code *100* and is pointing to *production* server.

## Code Style

The project uses default code style guidelines recommended by Android Studio.
To match code style settings for this project, follow following steps:
 - `Preferences` -> `Editor` -> `Code Style` -> `Kotlin`
   - `Scheme` => `Project`
   - `Set from` => `Predefined Style` -> `Kotlin style guide`
 - `Preferences` -> `Editor` -> `Code Style` -> `XML`
   - `Scheme` => `Project`
   - `Set from` => `Predefined Style` -> `Android`
 - `Preferences` -> `Editor` -> `Code Style` -> `Java`
   - `Scheme` => `Project`
   - `Set from` => `Predefined Style` -> `Android`

## Application Versioning

The application uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html) for version names which is described as follows:

> Given a version number MAJOR.MINOR.PATCH, increment the:

 - **MAJOR** version when one make incompatible API changes,
 - **MINOR** version when one add functionality in a backwards compatible manner, and
 - **PATCH** version when one make backwards compatible bug fixes.

While the version code should be incremented every time when the build is shared with QA and/or other teams after any kind of change.

## Preparing the release

Once the build is approved from QA team. The code should be merged in `master` and release should be published from `master` branch using [GitHub Releases](https://help.github.com/en/enterprise/2.16/user/github/administering-a-repository/creating-releases) with release notes included. The build can then be uploaded on Play Store using the suggested process.

### GitHub Release

The release prepared for GitHub should follow these instructions:

 - *Target:* `master`
 - *Tag version:* v[Full version] (e.g. v1.2.3.100)
 - *Release title:* Version [Full version] (e.g. v1.2.3.100)
 - *Describe this release:* Detailed release notes.


## Contributing

To Contribute Contact me at zulqurnainjutt@gmail.com
