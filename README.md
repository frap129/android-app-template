# Android App Template
A starting point for creating a new android app repo, with many tools and patterns for modern Android
development already implemented, such as:

- Compose
- Jetpack Navigation
	- Made more modular using [Destinations](core/ui/src/main/java/core/ui/model/data/Destination.kt)
	- Deep link support
- Koin dependency injection
- ktlint and EditorConfig for code style
- Timber for logging
- Multi-module project structure
- Gradle convention plugins to simplify creating new:
	- App modules
	- Library modules
	- Modules with Compose 
	- Modules with Android Hidden Api access
- Dynamic signing configuration

And more to come

Check out the `example` branch for some simple implementations of modules.

## Getting Started

### Module Structure
This template uses a multi-module structure to encourage separation of different components.

#### app
The "app" module is included as a starting point for your new app. After cloning this template, the
first thing you should do is change the package name to a more suitable one. 
- Open `app/build.gradle` and change the namespace and applicationId 
- Move `MainActivity.kt` to a path that matches your package name

When using this template, the App module should only contain your activity class and a list of
`destinations`, screens that are registered as navigable routes. All app functionality should be
implemented in `feature` modules.

#### build-logic
The `build-logic` directory is for code that is used in building your app, not a part of the
app itself.

##### convention
The `build-logic:convention` module provides a set of gradle plugins that simplify creating new modules. These
include plugins for creating new:
- Application Modules
- Library Modules
- Test Modules
- Hidden API Modules
- Compose Modules

These plugins can be combined to suit your needs, which the exception that a module can only be an
application or a library, not both. For example, if you are creating a new `core` module that relies
on hidden system APIs and has tests for its functionality, the `build.gradle` would look something
like:
```kotlin
plugins {
    alias(libs.plugins.modular.android.library)
    alias(libs.plugins.modular.android.hiddenapi)
    alias(libs.plugins.modular.android.test)
}

android {
    namespace = "core.my-module"
}
```
No further configuration is necessary, unless there are any external dependencies that are needed.

#### core
The `core` directory holds modules that provide functionality that can be reused across multiple
modules. This includes things like repositories, API accessors, and common Composables.

##### lifecycle
The `core:lifecycle` module holds lifecycle-related components that can be used or referenced across
multiple app modules or features. By default this includes:
- `MainApplication`, the common `Application` class used across app modules, which holds:
  - Timber Tree configuration
  - Koin Dependency registration
- `TemplateActivity`, an open implementation of `ComponentActivity` that supports:
  - :aunching to a deep-linked route

##### ui
The 'core:ui' module is a module used for common UI definitions throughout the app, such as the
theme, ui-related constants, an common composables. It also includes the `Destination` class, which
allows for modular registration of navigable routes. If you end up modifying `Destination` to
support a Jetpack Navigation feature that is not currently supported, consider contributing it back
to this template.

##### util
The `core:util` module is empty, but is meant for statically defined functions and one-offs used
in other modules.

#### feature
The `feature` directory holds modules that implement "features". In the context of this template, a
feature refers to a user-facing screen, set of related screens, or a user flow. Each feature should
define one `destination`, which is the entry point for navigating to that feature in app. Features
can navigate between one another, but should not have any build time dependency on one another. For
cleanliness, `feature` modules should only depend on `core` modules and external libraries.

### Dynamic Signing Configuration
This template supports providing custom signing config(s) via the `keystore.properties` pattern. If
`keystore.properties` exists in the root of the project, the values defined in it will be used for
the app module's signing config(s), otherwise the default is used.

`keystore.properties` supports both a singular signing config, and per-variant signing configs. As
an example, the following `keystore.properties` uses the generic config for all variants except
release, which has its own signing config defines.
```
storePassword=example
keyPassword=example
keyAlias=example
storeFile=example.keystore

release.storePassword=example
release.keyPassword=example
release.keyAlias=example
release.storeFile=release.keystore
```
